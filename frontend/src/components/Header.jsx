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
    <div className="grid grid-flow-col p-5 m-2 shadow-lg">
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
      <div>
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
              src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAAflBMVEX///8AAADi4uLo6Oj5+fns7OyZmZnv7+/R0dHa2tqWlpb19fWzs7OJiYnb29uSkpI1NTWjo6O7u7vHx8daWloaGhp/f3+tra1wcHAMDAxHR0c9PT1iYmKDg4MnJyfDw8MsLCxxcXFDQ0MTExNSUlIiIiJnZ2c4ODimpqYRERGLRUn5AAAG5UlEQVR4nO2diXbiOgyGIQskJWGnFChbC53O+7/gHRp6D8W..."
            />
          </button>
        </div>

        {/* Suggestions */}
        <div className="bg-white shadow-lg rounded-md mt-2 max-w-sm">
          {searchList.map((item, index) => (
            <div
              key={index}
              className="px-2 py-1 hover:bg-gray-200 cursor-pointer"
            >
              {item}
            </div>
          ))}
        </div>
      </div>

      {/* Add the Upload link if user is logged in */}
      {isLoggedIn && (
        <div className="flex justify-end">
          <Link 
            to="/upload" 
            className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 transition-colors"
          >
            Upload Video
          </Link>
        </div>
      )}
    </div>
  );
};

export default Header;
