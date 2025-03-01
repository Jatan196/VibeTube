import React, { useEffect, useRef } from "react";
import videojs from "video.js";
import "video.js/dist/video-js.css";
import toast from "react-hot-toast";

function VideoPlayer({ src }) {
  const videoRef = useRef(null);
  const playerRef = useRef(null);

  useEffect(() => {
    if (!src) {
      toast.error("Video source is missing!");
      return;
    }

    playerRef.current = videojs(videoRef.current, {
      controls: true,
      autoplay: true,
      muted: false,
      preload: "auto",
    });

    // Set video source
    videoRef.current.src = src;
    videoRef.current.load();
    videoRef.current.play().catch((error) => {
      console.error("Video play error:", error);
      toast.error("Failed to play the video.");
    });

    return () => {
      if (playerRef.current) {
        playerRef.current.dispose();
      }
    };
  }, [src]);

  return (
    <div>
      <div data-vjs-player>
        <video
          ref={videoRef}
          className="video-js vjs-default-skin"
          style={{ width: "100%", height: "500px" }}
        />
      </div>
    </div>
  );
}

export default VideoPlayer;
