import React, { useEffect, useRef } from "react";
import videojs from "video.js";
import "video.js/dist/video-js.css";
import toast from "react-hot-toast";
import { useParams } from "react-router-dom";

function VideoPlayer() {
  const videoRef = useRef(null); // Reference to <video> element
  const playerRef = useRef(null); // Reference to Video.js player instance
  const { videoId } = useParams(); // Get videoId from URL params

  useEffect(() => {
    if (!videoId) {
      toast.error("Video ID is missing!");
      console.log("Error: Video ID is null or undefined. Ensure the URL contains the correct videoId parameter.");
      return;
    }
    console.log("Video ID:", videoId);

    // const [videoSrc, setVideoSrc] = useState(`http://localhost:8080/api/v1/videos/stream/${videoId}`);

    // Initialize Video.js player only if not already initialized
    if (!playerRef.current) {
      playerRef.current = videojs(videoRef.current, {
        controls: true,
        autoplay: true,
        muted: false,
        preload: "auto",
      });
    }

    // Set video source properly using Video.js API
    //playerRef.current.src({ src: videoSrc, type: "video/mp4" });

    // Handle play errors
    playerRef.current.ready(() => {
      playerRef.current.play().catch((error) => {
        console.error("Video play error:", error);
        toast.error("Failed to play the video. Check console for details.");
      });
    });

    // Cleanup function to dispose of Video.js instance on unmount
    // return () => {
    //   if (playerRef.current) {
    //     playerRef.current.dispose();
    //     playerRef.current = null;
    //   }
    // };
  }, [videoId]);

  return (
    <div>
        erassssssfd
      <div data-vjs-player>
        <video
        //   ref={videoRef}
        src={`http://localhost:8080/api/v1/videos/stream/${videoId}`}
        type="video/mp4"
           className="video-js vjs-default-skin"
          style={{ width: "100%", height: "500px" }}
        />
      </div>
    </div>
  );
}

export default VideoPlayer;
