import React, { useState } from "react";
import {
    Button,
    Card,
    Label,
    FileInput,
    TextInput,
    Textarea,
    Progress,
    Alert,
} from "flowbite-react";    
import axios from "axios";
import toast from "react-hot-toast";
import VideoPlayer from './VideoPlayer.jsx'; 

function VideoUpload() {
    const [selectedFile, setSelectedFile] = useState(null);
    const [meta, setMeta] = useState({
        title: "",
        desc: "",
    });
    // const [progress, setProgress] = useState(0);
    const [uploading, setUploading] = useState(false);
    const [message, setMessage] = useState("");
    const [videoId, setVideoId] = useState(null);
    const [showPlayer, setShowPlayer] = useState(false);
    const [videoSrc, setVideoSrc] = useState('');

    function handleFileChange(event) {
        console.log(event.target.files[0]);
        setSelectedFile(event.target.files[0]);
    }

    function formFieldChange(event) {
        console.log(event.target.name);
        console.log(event.target.value);
        setMeta({
            ...meta,
            [event.target.name]: event.target.value,
        });
    }
    const handleUploadNew = () => {
        // Reset videoId and videoSrc
        setVideoId(null);
        setVideoSrc('');
        setShowPlayer(false)
        // Clean the window's path/route

        console.log("Setting the path back to previous")

        window.history.back();

      //  window.history.pushState({}, document.title, window.location.pathname);
        
        // Optionally, reset the form
        // resetForm();
    };

    function handleForm(formEvent) {
        formEvent.preventDefault();
        if (!selectedFile) {
            alert("Select File !!");
            return;
        } 
        //submit the file to server: 
        saveVideoToServer(selectedFile, meta); 
    }

    function resetForm() {
        setMeta({
            title: "",
            desc: "",
        });
        setSelectedFile(null);
        setUploading(false);
        // setMessage("");
    }

    //submit file to server
    async function saveVideoToServer(video, videoMetaData) {
        setUploading(true);

        //api call
        try {
            let formData = new FormData();
            formData.append("title", videoMetaData.title);
            formData.append("desc", videoMetaData.description);
            formData.append("file", selectedFile);
    
            let response = await axios.post(
                `http://localhost:8080/api/v1/videos/create`,
                formData,
                {
                    headers: {
                        "Content-Type": "multipart/form-data",
                    },
                    // onUploadProgress: (progressEvent) => {
                    //     const progress = Math.round(
                    //         (progressEvent.loaded * 100) / progressEvent.total
                    //     );

                    //     console.log(progress);
                    //     setProgress(progress);
                    // },
                }
            );

            console.log(response);
           // setProgress(0);

            setMessage("File uploaded " + response.data.videoId);
            setUploading(false);
            toast.success("File uploaded successfully !!");
            resetForm();
            setVideoId(response.data.videoId);
        } catch (error) {
            console.log(error);
            setMessage("Error in uplaoding File");
            setUploading(false);
            toast.error("File not uploaded !!");
        }
    }

    const handleStreamNow = async () => {
        if (videoId) {
            console.log(videoId);
            setVideoSrc(`http://localhost:8080/api/v1/videos/${videoId}/master.m3u8`);
            setShowPlayer(true); 

            window.history.pushState(null, '', `/stream/videoId=${videoId}`);
        }
    };

    return (
        <div className="text-white p-4">
            {!showPlayer ? (
                <Card className="flex flex-col items-center justify-center">
                    <h1>Upload Videos</h1>

                    <div>
                        <form
                            noValidate
                            className=" flex flex-col space-y-6"
                            onSubmit={handleForm}
                        > 
                            <div>
                                <div className="mb-2 block">
                                    <Label htmlFor="file-upload" value="Video Title" />
                                </div>
                                <TextInput
                                    value={meta.title}
                                    onChange={formFieldChange}
                                    name="title"
                                    placeholder="Enter title"
                                    className="text-black" // Added text color
                                />  
                            </div>

                            <div className="max-w-md">
                                
                                <Textarea
                                    value={meta.desc}
                                    onChange={formFieldChange}
                                    name="desc" 
                                    id="comment"
                                    placeholder="Write video description..."
                                    required
                                    rows={4}
                                    className="text-black p-2" // Added text color and padding
                                />
                            </div>

                            <div className="flex items-center space-x-5 justify-center">
                                <label className="block">
                                    <span className="sr-only">Choose video file</span>
                                    <input
                                        name="file"
                                        onChange={handleFileChange}
                                        type="file"
                                        className="block w-full text-sm text-slate-500
          file:mr-4 file:py-2 file:px-4
          file:rounded-full file:border-0s
          file:text-sm file:font-semibold
          file:bg-violet-50 file:text-violet-700
          hover:file:bg-violet-100
        "
                                    />
                                </label>
                            </div>

                            {/* <div className="">
                                {uploading && (
                                    <Progress
                                        color="green"
                                        progress={progress}
                                        textLabel="Uploading"
                                        size={"lg"}
                                        labelProgress
                                        labelText
                                    />
                                )}
                            </div> */}

                            <div className="">
                                {message && (
                                    <Alert
                                        color={"success"}
                                        rounded
                                        withBorderAccent
                                        onDismiss={() => {
                                            setMessage("");
                                        }}
                                    >
                                        <span className="font-medium">Success alert! </span>
                                        {message}
                                    </Alert>
                                )}
                            </div>

                            <div className=" text-red-500">
                                <Button disabled={uploading} type="submit" className="p-2 bg-red-500 text-white">
                                    Submit
                                </Button>

                            </div>
                        </form>
                    </div>
                    {videoId && (
                        <Button 
                            onClick={handleStreamNow}
                            className="mt-4 bg-blue-500 text-white"
                        >
                            Stream Now
                        </Button>
                    )}
                </Card>
            ) : (
                <div className="mt-4">
                    <Button 
                        onClick={handleUploadNew}
                        className="mb-4 bg-gray-500 text-white"
                    >
                        Back to Upload
                    </Button>
          
                    <VideoPlayer src={videoSrc}/>
                </div>  
            )}
        </div>
    );
}


// videoSrc

export default VideoUpload;