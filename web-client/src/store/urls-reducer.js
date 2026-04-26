import { createSlice } from '@reduxjs/toolkit';
import { getAllUrls, deleteUrl } from '../api/urls-api';

const urlsSlice = createSlice({

    name: 'urls',
    initialState: {
        isGetAllUrlsSuccess: false,
        isGetAllUrlsFail: false,
        isGetAllUrlsPending: false,
        isDeleteUrlSuccess: false,
        isDeleteUrlFail: false,
        isDeleteUrlPending: false,
        allUrls: [],
    },

    reducers: {
        clearGetAllUrlsState: (state) => {
            state.isGetAllUrlsSuccess = false;
            state.isGetAllUrlsFail = false;
            state.isGetAllUrlsPending = false;
            return state;
        },

        clearDeleteUrlState: (state) => {
            state.isDeleteUrlSuccess = false;
            state.isDeleteUrlFail = false;
            state.isDeleteUrlPending = false;
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

        builder.addCase(deleteUrl.fulfilled, (state, action) => {
            state.isDeleteUrlSuccess = action.payload;
            state.isDeleteUrlFail =  !action.payload;
            state.isDeleteUrlPending = false;
            return state;
        });
        builder.addCase(deleteUrl.rejected, (state, action) => {
            state.isDeleteUrlSuccess = false;
            state.isDeleteUrlFail = true;
            state.isDeleteUrlPending = false;
            return state;
        });
        builder.addCase(deleteUrl.pending, (state, action) => {
            state.isDeleteUrlSuccess = false;
            state.isDeleteUrlFail = false;
            state.isDeleteUrlPending = true;
            return state;
        });
    },
});

export const { clearGetAllUrlsState, clearDeleteUrlState } = urlsSlice.actions;
export default urlsSlice.reducer;
export const urlsSelector = (state) => state.urls;
