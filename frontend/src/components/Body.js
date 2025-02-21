import React from 'react';
import VideoContainer from './VideoContainer';
import Sidebar from './Sidebar';
import { Outlet } from 'react-router-dom';

const Body = () => {
    return (<>
   
        <div className='border-2 col-span-11 p-2'>
            <Outlet />
        </div>
    </>
    );
}

export default Body;
