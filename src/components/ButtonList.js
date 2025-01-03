import React from 'react';
import Button from './Button';

const ButtonList = () => {
    const buttonName = ['All', 'Trending', 'Cricket', 'Bollywood'];
    return (
        <div className='flex'>
            {buttonName?.map((name) => {
                return (
                    <Button name={name} />
                )
            })}
        </div>
    );
}

export default ButtonList;
