import React from 'react';

const Button = ({name}) => {
    return (
        <div className='bg-slate-300 p-2 mx-2 rounded-lg cursor-pointer'>
            {name}
        </div>
    );
}

export default Button;
