import React, { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { toggleBar } from "../utils/appSlice";
import { Link } from "react-router-dom";

const Header = ({ isLoggedIn }) => {
  const [searchQuery, setSearchQuery] = useState("");
  const [searchList, setSearchList] = useState([]);
  const dispatch = useDispatch();

  const YOUTUBE_API_KEY = process.env.REACT_APP_YOUTUBE_API_KEY;

  useEffect(() => {
    const timer = setTimeout(() => {
      if (searchQuery.trim()) {
        apiCall();
      }
    }, 500);

    return () => clearTimeout(timer);
  }, [searchQuery]);

  const apiCall = async () => {
    try {
      console.log("Search for -> ", searchQuery);
      const response = await fetch(
        `https://youtube.googleapis.com/youtube/v3/search?part=snippet&maxResults=25&q=${searchQuery}&key=${YOUTUBE_API_KEY}`
      );
      const data = await response.json();

      const items = data.items || [];
      const suggestions = items.slice(0, 7).map((item) => item.snippet.title);
      setSearchList(suggestions);
    } catch (error) {
      console.error("Error fetching search suggestions:", error);
      setSearchList([]);
    }
  };

  const toggleHandler = () => {
    dispatch(toggleBar());
  };

  return (
    <div className="grid grid-flow-col p-5 m-2 shadow-lg relative border-x-2">
      {/* Left Section: Menu and YT Icon */}
      <div className="flex items-center gap-x-4">
        <img
          className="w-6 h-6 object-contain cursor-pointer"
          onClick={toggleHandler}
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
      <div className="relative">
        <div className="flex items-center space-x-0">
          <input
            type="text"
            className="border rounded-md px-2 py-1 w-64 rounded-l-full"
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            placeholder="Search..."
          />
          <button className="ml-2">
            <img
              className="w-8 h-8 object-contain rounded-r-full border-2"
              alt="search icon"
              src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADh..."
            />
          </button>
        </div>

        {/* Suggestions - Overflowing from the Header */}
        {searchList.length > 0 && (
          <div className="absolute left-0 w-80 bg-white shadow-lg rounded-md mt-2 z-50">
            {searchList.map((item, index) => (
              <div key={index} className="px-2 py-1 hover:bg-gray-200 cursor-pointer">
                {item}
              </div>
            ))}
          </div>
        )}
      </div>

      {/* Add the Upload link if user is logged in */}
      {isLoggedIn && window.location.pathname !== "/upload" && (
        <div className="flex items-center space-x-6">
          <Link to="/" className="text-gray-700 hover:text-gray-900 font-medium flex items-center gap-2 transition-colors">
            <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
              <path d="M10.707 2.293a1 1 0 00-1.414 0l-7 7a1 1 0 001.414 1.414L4 10.414V17a1 1 0 001 1h2a1 1 0 001-1v-2a1 1 0 011-1h2a1 1 0 011 1v2a1 1 0 001 1h2a1 1 0 001-1v-6.586l.293.293a1 1 0 001.414-1.414l-7-7z" />
            </svg>
            Home
          </Link>
          <Link to="/upload" className="text-gray-700 hover:text-gray-900 font-medium flex items-center gap-2 transition-colors">
            <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
              <path fillRule="evenodd" d="M10 3a1 1 0 011 1v5h5a1 1 0 110 2h-5v5a1 1 0 11-2 0v-5H4a1 1 0 110-2h5V4a1 1 0 011-1z" clipRule="evenodd" />
            </svg>
            Upload Video
          </Link>
        </div>
      )}
    </div>
  );
};

export default Header;
