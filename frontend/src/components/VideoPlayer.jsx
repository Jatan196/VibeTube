import React, { useRef, useEffect } from "react";
import videojs from "video.js";
import Hls from "hls.js"
import "video.js/dist/video-js.css"

function VideoPlayer({ src }) {
  const videoRef = useRef(null);
  const playerRef=useRef(null);

  useEffect(() => {

    playerRef.current=videojs(videoRef.current,{
      controls:true,
      autoplay:true,
      muted:true,
      preload:"auto"
    })

    if(Hls.isSupported()){
       const hls=new Hls();

        console.log(src)
       hls.loadSource(src);
       hls.attachMedia(videoRef.current);
       hls.on(Hls.Events.MANIFEST_PARSED,()=>{
        videoRef.current.play(); 
       })  
    } else if(videoRef.current.canPlayType("application/vnd.apple.mpegurl")){
       videoRef.current.src=src
       videoRef.current.addEventListener("canplay", ()=>{
        videoRef.current.play();
       }); 
    } else {
      console.log("video format not supportted");
    //  toast.error("Video format not supporteds");
    } 

    if (videoRef.current) {
      videoRef.current.load(); // Ensure video reloads when src changes
    } 
  }, [src]);

  return (
    <div data-vjs-player>
      <video
        ref={videoRef} 
        controls
        autoPlay
        muted
        style={{ width: "100%", height: "500px" }}
        className="video-js vjs-control-bar"
      >
        {/* <source src={src} type="video/mp4" />
        Your browser does not support the video tag. */}
      </video>
    </div>
  );
}

export default VideoPlayer;
