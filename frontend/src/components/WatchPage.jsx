import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { toggleBar } from '../utils/appSlice';

const WatchPage = () => {
    const dispatch = useDispatch();
    const { isOpen } = useSelector(store => store.app);
    if (isOpen===1) {
        dispatch(toggleBar());
    }
    return (
        <div>
            watch
        </div>
    );
}

export default WatchPage;
