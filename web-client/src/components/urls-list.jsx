import React, { useEffect, useState } from 'react';
import Header from "../features/header";
import { Oval } from 'react-loader-spinner';
import { useSelector, useDispatch } from 'react-redux';
import { getAllUrls } from '../api/urls-api';
import { urlsSelector, clearGetAllUrlsState } from '../store/urls-reducer';
import { Table } from 'antd';

const UrlsList = () => {
    const [isLoading, setIsLoading] = useState(true);
    const dispatch = useDispatch();
    const { isGetAllUrlsSuccess,
        isGetAllUrlsFail,
        isGetAllUrlsPending, allUrls } = useSelector(urlsSelector);

    useEffect(() => {
        const timer = setTimeout(() => {
            setIsLoading(false);
        }, 300);

        const getUrls = async () => {
            dispatch(getAllUrls());
        }

        getUrls();

        return () => {
            clearTimeout(timer);
        }
    }, []);

    const columns = [
        {
            title: () => { return <h3 style={{ color: 'navy', }}>Long Url</h3> },
            dataIndex: 'longUrl',
            key: 'longUrl',
            render: (text) => <h3>{text}</h3>,
        },
        {
            title: () => { return <h3 style={{ color: 'navy', }}>Short Url</h3> },
            dataIndex: 'shortUrl',
            key: 'shortUrl',
            render: (text) => <h3>{text}</h3>,
        },
    ];

    return (
        <div>
            <Header />
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

            {!isLoading && isGetAllUrlsSuccess &&
                <div>
                    <h2 className="h2-lefAlign">Urls List</h2>

                    {allUrls && allUrls.length > 0 &&
                        <Table columns={columns} dataSource={allUrls} pagination={{ pageSize: 50 }} />
                    }

                    {allUrls && allUrls.length == 0 &&
                        <h3>No URL found</h3>
                    }

                </div>
            }

            {!isLoading && isGetAllUrlsFail &&
                <div>
                    <h3>Error occured feching the URLs</h3>

                </div>
            }
        </div>
    )

}

export default UrlsList;
