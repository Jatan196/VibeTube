import React from 'react';
import { useSelector } from 'react-redux';

const Sidebar = () => {

     const { isOpen } = useSelector(store => store.app);
    const menuItems = [
        { icon: "🏠", label: "Home" },
        { icon: "🔥", label: "Trending" },
        { icon: "🎥", label: "Subscriptions" },
        { icon: "📚", label: "Library" },
        { icon: "⏱", label: "History" },
        { icon: "▶️", label: "Your Videos" },
        { icon: "⏳", label: "Watch Later" },
        { icon: "❤️", label: "Liked Videos" },
    ];

     if(isOpen===false) return null;

    return (
        <div className="border-2 col-span-1 p-4 h-screen shadow">
            <ul className="space-y-4 text-black">
                {menuItems.map((item, index) => (
                    <li key={index} className="flex items-center space-x-3 hover:bg-slate-600 p-2 rounded-lg cursor-pointer">
                        <span className="text-lg">{item.icon}</span>
                        <span className="text-sm">{item.label}</span>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default Sidebar;
