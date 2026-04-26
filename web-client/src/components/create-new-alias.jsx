import React, { useEffect, useState } from 'react';
import Header from "../features/header";
import { Oval } from 'react-loader-spinner';
import { useSelector, useDispatch } from 'react-redux';
import { createNewAlias } from '../api/urls-api';
import { urlsSelector } from '../store/urls-reducer';
import { Table, Space, Button } from 'antd';
import { checkValidUrl } from '../utils/url-validator';

const CreateNewAlias = () => {

    const [isLoading, setIsLoading] = useState(true);
    const [longUrl, setLongUrl] = useState('');
    const [alias, setAlias] = useState('');
    const [registeredLongUrl, setRegisteredLongUrl] = useState('');
    const [resetMessage, setResetMessage] = useState(true);
    const [errorLongUrl, setErrorLongUrl] = useState(false);
    const [errorShortUrl, setErrorShortUrl] = useState(false);
    const dispatch = useDispatch();
    const { isCreaeteNewAliasSuccess,
        isCreaeteNewAliasFail,
        isCreaeteNewAliasPending, newAlias,
    } = useSelector(urlsSelector);

    useEffect(() => {
        const timer = setTimeout(() => {
            setIsLoading(false);
        }, 300);

        return () => {
            clearTimeout(timer);
        }
    }, []);

    const createAlias = async () => {

        setErrorLongUrl(false);
        setErrorShortUrl(false);

        if (checkValidUrl(longUrl) && (alias === '' || !checkValidUrl(alias))) {
            dispatch(createNewAlias({ longUrl: longUrl, shortUrl: alias }));
            setRegisteredLongUrl(longUrl);
            setResetMessage(false);
        } else {
            const isLongUrlValid = checkValidUrl(longUrl);
            const isShortUrlValid = checkValidUrl(alias);

            if (!isLongUrlValid) {
                setErrorLongUrl(true);
            }
            if (alias && isShortUrlValid) {
                setErrorShortUrl(true);
            }
        }
    }

    return (
        <div>
            <Header />
            <h2 className="h2-lefAlign">Create new alias</h2>

            {isLoading &&
                <div className='loader'>
                    <Oval
                        visible={true}
                        height="80"
                        width="80"
                        color="black"
                        ariaLabel="oval-loading"
                        wrapperStyle={{}}
                        wrapperClass="spinner"
                        secondaryColor="blue"
                    />
                </div>
            }

            {!isLoading &&
                <>
                    <div className='div-leff-align'>
                        <label>Long URL</label><br/>
                        <input
                            value={longUrl}
                            placeholder='Enter New URL'
                            onChange={(text) => setLongUrl(text.target.value)}
                            className={'input-box'}
                        /><br/>
                        {errorLongUrl &&
                            <label className="error-label">Long URL format is not valid</label>
                        }
                    </div>

                    <div className='div-leff-align'>
                        <label>Alias</label><br />
                        <input
                            value={alias}
                            placeholder='Enter New Alias'
                            onChange={(text) => setAlias(text.target.value)}
                            className={'input-box'}
                        /><br />
                        {alias &&
                            <p>Your alias: http://localhost:8080/api/urls/{alias}</p>
                        }
                        {errorShortUrl &&
                            <label className="error-label">Short URL format is not valid.</label>
                        }
                    </div>

                    <button className='submit-button' id='submitBtn'
                        type='submit'
                        onClick={createAlias}>
                        Create Alias</button>

                    {newAlias &&
                        <div>
                            <h2>URL successfully shortened</h2>
                            <h3 className='alias-success'>Alias http://localhost:8080/api/urls/{newAlias} has been created for {registeredLongUrl}</h3>
                        </div>
                    }

                    {!isCreaeteNewAliasSuccess && !resetMessage &&
                        <h2 className='alias-fail'>Invalid input or alias already taken</h2>
                    }
                </>

            }
        </div>
    )

}

export default CreateNewAlias;
