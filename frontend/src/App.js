import './App.css';
import Body from './components/Body.js';
import Header from './components/Header.jsx';
import Home from './components/Home.js'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import VideoContainer from './components/VideoContainer.js';
import WatchPage from './components/WatchPage.jsx';
import Sidebar from './components/Sidebar.js';
import { useState } from 'react';
import UploadVideo from './components/UploadVideo';
import ProtectedRoute from './components/ProtectedRoute';

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
