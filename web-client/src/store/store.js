import { configureStore } from "@reduxjs/toolkit";
import urlsReducer from "./urls-reducer";

export const store = configureStore({

    reducer: {
      urls: urlsReducer,
    }
});
