import { getAllUrls, deleteUrl, createNewAlias } from '../../api/urls-api';

global.fetch = jest.fn();

beforeEach(() => {
    fetch.mockClear();
});

test('getAllUrls returns data on 200', async () => {
    const mockData = [{ id: 1, url: 'test.com' }];
    const dispatch = jest.fn();

    fetch.mockResolvedValue({
        status: 200,
        json: jest.fn().mockResolvedValue(mockData),
    });

    const thunkAPI = { rejectWithValue: jest.fn() };

    const result = await getAllUrls()(dispatch, null, thunkAPI);

    expect(result.payload).toEqual(mockData);
});

test('deleteUrl returns true on 204', async () => {
    const dispatch = jest.fn();
    fetch.mockResolvedValue({
        status: 204,
        headers: {
            get: jest.fn().mockReturnValue('0'),
        },
        statusText: 'No Content',
    });

    const thunkAPI = { rejectWithValue: jest.fn() };

    const result = await deleteUrl({ alias: 'abc' })(dispatch, null, thunkAPI);

    expect(result.payload).toBe(true);
});

test('deleteUrl returns false on failure', async () => {
    const dispatch = jest.fn();

    fetch.mockResolvedValue({
        status: 400,
        headers: {
            get: jest.fn().mockReturnValue('10'),
        },
        statusText: 'Bad Request',
    });

    const thunkAPI = { rejectWithValue: jest.fn() };

    const result = await deleteUrl({ alias: 'abc' })(dispatch, null, thunkAPI);

    expect(result.payload).toBe(false);
});

test('createNewAlias success', async () => {
    const mockResponse = { shortUrl: 'short.ly/abc' };
    const dispatch = jest.fn();

    fetch.mockResolvedValue({
        status: 201,
        json: jest.fn().mockResolvedValue(mockResponse),
    });

    const thunkAPI = { rejectWithValue: jest.fn() };

    const result = await createNewAlias(
        { longUrl: 'test.com', shortUrl: 'abc' }
    )(dispatch, null, thunkAPI);

    expect(result.payload).toEqual({
        isSuccess: true,
        shortUrl: 'short.ly/abc',
    });
});

test('createNewAlias returns failure on 400', async () => {
    const dispatch = jest.fn();
    fetch.mockResolvedValue({
        status: 400,
        json: jest.fn().mockResolvedValue({}),
    });

    const thunkAPI = { rejectWithValue: jest.fn() };

    const result = await createNewAlias(
        { longUrl: 'test.com', shortUrl: 'abc' }
    )(dispatch, null, thunkAPI);

    expect(result.payload).toEqual({
        isSuccess: false,
        shortUrl: '',
    });
});
