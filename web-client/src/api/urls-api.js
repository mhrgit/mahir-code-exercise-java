import { createAsyncThunk } from '@reduxjs/toolkit';
import { BASE_API_URL } from '../constants/url-constants';

export const getAllUrls = createAsyncThunk(
    'urls/get-all-urls', async thunkAPI => {
        const endPoint = `api/urls/urls`;

        try {
            const response = await fetch(
                endPoint,
                {
                    method: 'GET',
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                    },
                }
            );
            let data = await response.json();
            if (response.status === 200) {
                return data;
            } else {
                return thunkAPI.rejectWithValue(data);
            }
        } catch (e) {
            thunkAPI.rejectWithValue(e.response.data);
        }
    }
);

export const deleteUrl = createAsyncThunk(
    'urls/delete-url', async ({ alias }, thunkAPI) => {
        const endPoint = `api/urls/${alias}`;

        try {
            const response = await fetch(
                endPoint,
                {
                    method: 'DELETE',
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                    },
                }
            );
            if (response.status === 204 || response.headers.get('content-length') === '0' || response.statusText === 'No Content') {
                return true;
            } else {
                return false;
            }
        } catch (e) {
            thunkAPI.rejectWithValue(e.response.data);
        }
    }
);
