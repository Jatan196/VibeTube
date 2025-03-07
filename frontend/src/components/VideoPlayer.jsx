import React, { useRef, useEffect } from "react";

function VideoPlayer({ src }) {
  const videoRef = useRef(null);

  useEffect(() => {
    if (videoRef.current) {
      videoRef.current.load(); // Ensure video reloads when src changes
    }
  }, [src]);

  return (
    <div>
      <video
        ref={videoRef}
        controls
        autoPlay
        muted
        style={{ width: "100%", height: "500px" }}
      >
        <source src={src} type="video/mp4" />
        Your browser does not support the video tag.
      </video>
    </div>
  );
}

export default VideoPlayer;
