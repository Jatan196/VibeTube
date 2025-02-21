import {createSlice} from "@reduxjs/toolkit";

const appSlice=createSlice({

    name:"app",
    initialState:{
        isOpen:true,    
    },  
    
    reducers:{      
        toggleBar: (state) => {     
            state.isOpen = !state.isOpen;   
        },      
    },  
}); 

export const {toggleBar} = appSlice.actions;    

export default appSlice.reducer;    