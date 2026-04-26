import { createSlice } from '@reduxjs/toolkit';
import { getAllUrls, deleteUrl, createNewAlias, } from '../api/urls-api';

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
        isCreaeteNewAliasSuccess: false,
        isCreaeteNewAliasFail: false,
        isCreaeteNewAliasPending: false,
        newAlias: '',
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
        },

        clearCreateNewAliasState: (state) => {
            state.isCreaeteNewAliasSuccess = false;
            state.isCreaeteNewAliasFail = false;
            state.isCreaeteNewAliasPending = false;
            state.newAlias = '';
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
            state.isDeleteUrlFail = !action.payload;
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

        builder.addCase(createNewAlias.fulfilled, (state, action) => {
            state.isCreaeteNewAliasSuccess = action.payload.isSuccess;
            state.isCreaeteNewAliasFail = !action.payload.isSuccess;
            state.isCreaeteNewAliasPending = false;
            state.newAlias = action.payload.shortUrl;
            return state;
        });
        builder.addCase(createNewAlias.rejected, (state, action) => {
            state.isCreaeteNewAliasSuccess = false;
            state.isCreaeteNewAliasFail = true;
            state.isCreaeteNewAliasPending = false;
            state.newAlias = '';
            return state;
        });
        builder.addCase(createNewAlias.pending, (state, action) => {
            state.isCreaeteNewAliasSuccess = false;
            state.isCreaeteNewAliasFail = false;
            state.isCreaeteNewAliasPending = true;
            state.newAlias = '';
            return state;
        });
    },
});

export const { clearGetAllUrlsState, clearDeleteUrlState, clearCreateNewAliasState } = urlsSlice.actions;
export default urlsSlice.reducer;
export const urlsSelector = (state) => state.urls;
