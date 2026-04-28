
import React, { useEffect, useState } from 'react';
import { Oval } from 'react-loader-spinner';
import { ALIAS_LOCAL_BASE_URL } from '../constants/url-constants';

const CreateNewAliasResult = ({ alias }) => {
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        const timer = setTimeout(() => {
            setIsLoading(false);
        }, 1000);

        return () => {
            clearTimeout(timer);
        }
    }, []);

    return (<>
        {isLoading &&
            <div className='div-center'>
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
        {
            !isLoading &&
            <div>
                <h2>URL successfully shortened</h2>
                <h3 className='alias-success'>Your new Alias: {ALIAS_LOCAL_BASE_URL}{alias}</h3>
            </div>
        }
    </>);

}

export default CreateNewAliasResult;
