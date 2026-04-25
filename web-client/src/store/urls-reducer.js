import { createSlice } from '@reduxjs/toolkit';
import { getAllUrls } from '../api/urls-api';

const urlsSlice = createSlice({

    name: 'urls',
    initialState: {
        isGetAllUrlsSuccess: false,
        isGetAllUrlsFail: false,
        isGetAllUrlsPending: false,
        allUrls: [],
    },

    reducers: {
        clearGetAllUrlsState: (state) => {
            state.isGetAllUrlsSuccess = false;
            state.isGetAllUrlsFail = false;
            state.isGetAllUrlsPending = false;
            return state;
        }
    },

    extraReducers: (builder) => {
        builder.addCase(getAllUrls.fulfilled, (state, action) => {
            state.isGetAllUrlsSuccess = true;
            state.isGetAllUrlsFail = false;
            state.isGetAllUrlsPending = false;
            state.allUrls = action.payload;
            return state;
        });
        builder.addCase(getAllUrls.rejected, (state, action) => {
            state.isGetAllUrlsSuccess = false;
            state.isGetAllUrlsFail = true;
            state.isGetAllUrlsPending = false;
            state.allUrls = [];
            return state;
        });
        builder.addCase(getAllUrls.pending, (state, action) => {
            state.isGetAllUrlsSuccess = false;
            state.isGetAllUrlsFail = false;
            state.isGetAllUrlsPending = true;
            state.allUrls = [];
            return state;
        });
    },
});

export const { clearGetAllUrlsState } = urlsSlice.actions;
export default urlsSlice.reducer;
export const urlsSelector = (state) => state.urls;
