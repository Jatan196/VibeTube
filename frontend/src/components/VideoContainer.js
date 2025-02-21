import React, { useEffect, useRef, useState } from 'react';
import VideoCard, { AdVideoCard } from './VideoCard';
import ButtonList from './ButtonList';


// Accessing the environment variable
const YOUTUBE_API_URL = process.env.REACT_APP_YOUTUBE_API_URL;

const VideoContainer = () => {
    const [videos, setVideos] = useState([]);
    // api call to YouTube videos
    useEffect(() => {
        getVideos();
        console.log("Videos state changed:", videos);
    }, []);
    const [cnt, setCnt] = useState(0);

    // const f = useRef(1);
    // if (f===1) {
    //     setCnt(5);
    //     f.current = 0;
    //     console.log(f);
    // }

    const getVideos = async () => {
        try {
            const raw = await fetch(YOUTUBE_API_URL);

            const data = await raw.json();

            console.log(data.items);
            setVideos(data.items);
            // setVideos([1, 2, 3, 4, 5]);


        } catch (error) {
            console.error("Error fetching videos:", error);
        }
        // console.log(videos)
        // console.log(cnt)
    };

    return (
        <>
            <ButtonList />
            <div className='col-span-11 flex flex-wrap items-center'>
                <div> 
                    {
                        videos[0] &&

                        <AdVideoCard videoInfo={videos[0]} />
                    }
                    </div>
                    {

                        videos.map((video) => {
                            return (
                                <VideoCard videoInfo={video} />
                            )
                        })
                    }
                </div></>
            );
}

            export default VideoContainer;
