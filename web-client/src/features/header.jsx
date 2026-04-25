import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import {
    HomeFilled, FormOutlined,
    GroupOutlined, LinkOutlined,
} from '@ant-design/icons';
import { Menu } from 'antd';

const Header = () => {

    useEffect(() => {

        return () => {
        }
    }, []);


    const items = [
        {
            label: <Link to={'/'}>Home</Link>,
            key: '0',
            icon: <HomeFilled height={150} style={{ fontSize: '30px', margin: 20, }} />,
        },
        {
            label: <Link to={'/create-alias'}>Create new alias</Link>,
            key: '1',
            icon: <LinkOutlined  height={150} style={{ fontSize: '30px', margin: 20, }} />,
        },
        {
            label: <Link to={'/urls-list'}>URLs</Link>,
            key: '2',
            icon: <GroupOutlined height={150} style={{ fontSize: '30px', margin: 20, }} />,
        },
        // {
        //     label: <Link to={ CURRENT_BASE_URL + '/contributor/sections'}>Sections</Link>,
        //     key: '2',
        //     icon: <GroupOutlined height={15} />,
        // },
        // {
        //     label: <Link to={CURRENT_BASE_URL + '/contributor/brief-texts'}>Brief Texts</Link>,
        //     key: '3',
        //     icon: <AlignLeftOutlined height={15} />,
        // },
        // {
        //     label: <Link to={CURRENT_BASE_URL + '/contributor/profile'}>Profile</Link>,
        //     key: '4',
        //     icon: <SettingOutlined height={15} />,
        // },
    ];

    return (
        <Menu mode="horizontal" items={items} style={{ fontSize: 16 }} />
    );
}
export default Header;
