import React from 'react';
import { useDispatch } from 'react-redux';
import { toggleBar } from '../utils/appSlice';

const Header = () => {
    const dispatch=useDispatch();
    const toggleHandler = () => {
        dispatch(toggleBar()); // nothing to pass it in, since this reducer don't have any payload      
    }
    return (    
        <div className="grid grid-flow-col items-center text-center  px-4 shadow-md">
            {/* Left Section: Menu and YT Icon */}
            <div className="flex items-center gap-x-4">
                <img
                    className="w-6 h-6 object-contain cursor-pointer"
                    onClick={()=>toggleHandler()}
                    alt="menu icon"
                    src="/menu icon.png"
                />
                <img
                    className="w-20 h-20 object-contain"
                    alt="yt icon"
                   
                    src="https://uxwing.com/wp-content/themes/uxwing/download/brands-and-social-media/youtube-logo-icon.png"
                />
            </div>

            {/* Middle Section: Search Bar */}
            <div className="flex items-center  space-x-0">
                <input      
                    type="text"
                    className="border rounded-md px-2 py-1 w-64 rounded-l-full "
                    placeholder="Search..."
                />
                <button className="ml-2 ">
                    <img
                        className="w-8 h-8 object-contain rounded-r-full border-2"
                      
                        alt="search icon"
                        src="data:image/png;base64,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"
                    />
                </button>
            </div>

            {/* Right Section: Profile Icon */}
            <div className="flex items-center justify-end">
                <img
                    className="w-12 h-12 rounded-full"
                    
                    alt="profile icon"
                    src="/download.png"
                />
            </div>
        </div>
    );
};

export default Header;
