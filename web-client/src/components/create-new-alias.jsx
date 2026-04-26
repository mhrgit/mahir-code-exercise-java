import React, { useEffect, useState } from 'react';
import Header from "../features/header";
import { Oval } from 'react-loader-spinner';
import { useSelector, useDispatch } from 'react-redux';
import { getAllUrls, deleteUrl } from '../api/urls-api';
import { urlsSelector, clearGetAllUrlsState, clearDeleteUrlState } from '../store/urls-reducer';
import { Table, Space, Button } from 'antd';


const CreateNewAlias = () => {

    const [isLoading, setIsLoading] = useState(true);
    const [longUrl, setLongUrl] = useState('');
    const [alias, setAlias] = useState('');

    useEffect(() => {
        const timer = setTimeout(() => {
            setIsLoading(false);
        }, 300);

        return () => {
            clearTimeout(timer);
        }
    }, []);

    const createNewAlias = () => {

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
                        <input
                            value={longUrl}
                            placeholder='Enter New URL'
                            onChange={(text) => setLongUrl(text.target.value)}
                            className={'input-box'}
                            type='password'
                        />
                        {/* <label className="errorLabel">{newPassError}</label> */}
                    </div>

                    <div className='div-leff-align'>
                        <input
                            value={alias}
                            placeholder='Enter New Alias'
                            onChange={(text) => setAlias(text.target.value)}
                            className={'input-box'}
                            type='password'
                        />
                        {/* <label className="errorLabel">{newPassConfirmError}</label> */}
                        {/* <label className="errorLabel">{newPassNotMatchedError}</label> */}
                    </div>

                    <button className='submit-button' id='submitBtn'
                        type='submit'
                        onClick={createNewAlias}>
                        Create Alias</button>
                </>

            }
        </div>
    )

}

export default CreateNewAlias;
