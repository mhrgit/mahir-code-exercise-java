import React, { useEffect, useState } from 'react';
import Header from "../features/header";
import { Oval } from 'react-loader-spinner';
import { useSelector, useDispatch } from 'react-redux';
import { getAllUrls, deleteUrl } from '../api/urls-api';
import { urlsSelector, clearGetAllUrlsState, clearDeleteUrlState } from '../store/urls-reducer';
import { Table, Space, Button } from 'antd';
import {
    DeleteOutlined,
} from '@ant-design/icons';

const UrlsList = () => {
    const [isLoading, setIsLoading] = useState(true);
    const [deleteSucccess, setShowDeleteSuccess] = useState(false);
    const dispatch = useDispatch();
    const { isGetAllUrlsSuccess,
        isGetAllUrlsFail,
        isGetAllUrlsPending, allUrls,
        isDeleteUrlSuccess,
    } = useSelector(urlsSelector);

    useEffect(() => {
        const timer = setTimeout(() => {
            setIsLoading(false);
        }, 300);

        const timerDeleteSucccess = setTimeout(() => {
            setShowDeleteSuccess(false);
        }, 2000);

        if (isDeleteUrlSuccess) {
            dispatch(getAllUrls());
            dispatch(clearDeleteUrlState());
        }

        const getUrls = async () => {
            dispatch(getAllUrls());
        }

        getUrls();

        return () => {
            clearTimeout(timer);
            clearTimeout(timerDeleteSucccess);
        }
    }, [isDeleteUrlSuccess]);

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
        {
            title: () => { return <h3 style={{ color: 'navy', }}>Action</h3> },
            key: 'action',
            render: (_, item) => (
                <Space size="middle">
                    <Button color='danger' variant="filled" icon={<DeleteOutlined color='red' />} onClick={() => removeUrl(item)}>Delete</Button>
                </Space>
            ),
        },
    ];

    const removeUrl = (url) => {
        dispatch(deleteUrl({ alias: url.shortUrl }));
        setShowDeleteSuccess(true);
    }

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
                        <>
                            <Table columns={columns} dataSource={allUrls} pagination={{ pageSize: 50 }} />
                            {deleteSucccess &&
                                <h3 className='error-message'>Selected URL has been deleted..</h3>
                            }
                        </>
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
