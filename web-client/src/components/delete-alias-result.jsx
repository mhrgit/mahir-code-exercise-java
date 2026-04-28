
import React, { useEffect, useState } from 'react';
import { Oval } from 'react-loader-spinner';

const DeleteAliasResult = () => {
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
        {!isLoading &&
            <div>
                <h3 className='error-message'>Selected URL has been deleted..</h3>
            </div>
        }
    </>);

}

export default DeleteAliasResult;
