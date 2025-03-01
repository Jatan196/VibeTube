import './App.css';
import Body from './components/Body.js';
import Header from './components/Header.jsx';
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import VideoContainer from './components/VideoContainer.js';
import WatchPage from './components/WatchPage.jsx';
import Sidebar from './components/Sidebar.js';
import { useState } from 'react';
import UploadVideo from './components/UploadVideo';
import ProtectedRoute from './components/ProtectedRoute';
import VideoPlayer from './components/VideoPlayer';


const router = createBrowserRouter([
  {
    path: '/',
    element: (
      <>    
        <Header isLoggedIn={true} />  
        <div className='grid grid-flow-col'>  
          <Sidebar /> 
          <Body />  
        </div>  
      </> 
    ),  
    children: [
      {
        path: '/',
        element: <VideoContainer />
      },
      {
        path: '/watch',
        element: <WatchPage />
      },
      {
        path: '/upload',
        element: <UploadVideo />
      },
      {
        path: '/video-player/:videoId',
        element: <VideoPlayer />
      }
    ]
  }
]);

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(true);

  return (
    <div className="">
      <RouterProvider router={router} />
    </div>  
  );
} 

export default App;
