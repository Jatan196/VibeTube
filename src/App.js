
import './App.css';
import Body from './components/Body.js';
import Header from './components/Header.jsx';
import Home from './components/Home.js'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import VideoContainer from './components/VideoContainer.js';
import WatchPage from './components/WatchPage.jsx';
import Sidebar from './components/Sidebar.js';

const router = createBrowserRouter([ // array of objects
  {
    path: '/',
    element: <Body />,
    children: [{
      path: '/',
      element: <VideoContainer />
    },
    {
      path: '/watch',
      element: <WatchPage />
    }
    ]
  }
])
function App() {
  return (
    <div className="">
      <Header />
      <div className='grid grid-flow-col'>
        <Sidebar />
        <RouterProvider router={router} />
      </div>
    </div>
  );
}

export default App;
