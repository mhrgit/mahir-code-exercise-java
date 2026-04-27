import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import UrlsList from '../../components/urls-list';
import { useDispatch, useSelector } from 'react-redux';
import { getAllUrls, deleteUrl } from '../../api/urls-api';

jest.mock('react-redux', () => ({
  useDispatch: jest.fn(),
  useSelector: jest.fn(),
}));

jest.mock('../../api/urls-api', () => ({
  getAllUrls: jest.fn(),
  deleteUrl: jest.fn(),
}));

jest.mock('antd', () => ({
  Table: ({ dataSource }) => (
    <div>
      {dataSource.map((item, i) => (
        <div key={i}>
          <span>{item.longUrl}</span>
          <span>{item.shortUrl}</span>
          <button onClick={() => item.onDelete?.(item)}>Delete</button>
        </div>
      ))}
    </div>
  ),
  Space: ({ children }) => <div>{children}</div>,
  Button: ({ children, onClick }) => (
    <button onClick={onClick}>{children}</button>
  ),
}));

jest.mock('../../features/header', () => () => <div>Header</div>);

const mockDispatch = jest.fn();

beforeEach(() => {
  jest.clearAllMocks();
  useDispatch.mockReturnValue(mockDispatch);
});

test('dispatches getAllUrls on mount', () => {
  useSelector.mockReturnValue({
    isGetAllUrlsSuccess: false,
    isGetAllUrlsFail: false,
    isGetAllUrlsPending: false,
    allUrls: [],
    isDeleteUrlSuccess: false,
  });

  render(<UrlsList />);

  expect(mockDispatch).toHaveBeenCalled();
});

test('renders urls when success', async () => {
  useSelector.mockReturnValue({
    isGetAllUrlsSuccess: true,
    isGetAllUrlsFail: false,
    isGetAllUrlsPending: false,
    allUrls: [
      { longUrl: 'google.com', shortUrl: 'g' },
      { longUrl: 'github.com', shortUrl: 'gh' },
    ],
    isDeleteUrlSuccess: false,
  });

  render(<UrlsList />);

  expect(await screen.findByText('Urls List')).toBeInTheDocument();
  expect(screen.getByText('google.com')).toBeInTheDocument();
  expect(screen.getByText('github.com')).toBeInTheDocument();
});


test('shows empty message when no urls', async () => {
  useSelector.mockReturnValue({
    isGetAllUrlsSuccess: true,
    isGetAllUrlsFail: false,
    isGetAllUrlsPending: false,
    allUrls: [],
    isDeleteUrlSuccess: false,
  });

  render(<UrlsList />);

  expect(await screen.findByText('No URL found')).toBeInTheDocument();
});


test('shows error message on failure', async () => {
  useSelector.mockReturnValue({
    isGetAllUrlsSuccess: false,
    isGetAllUrlsFail: true,
    isGetAllUrlsPending: false,
    allUrls: [],
    isDeleteUrlSuccess: false,
  });

  render(<UrlsList />);

  expect(
    await screen.findByText('Error occured feching the URLs')
  ).toBeInTheDocument();
});

test('dispatches deleteUrl when delete button clicked', async () => {
  const urls = [{ longUrl: 'google.com', shortUrl: 'g' }];

  useSelector.mockReturnValue({
    isGetAllUrlsSuccess: true,
    isGetAllUrlsFail: false,
    isGetAllUrlsPending: false,
    allUrls: urls,
    isDeleteUrlSuccess: false,
  });

  render(<UrlsList />);

  const deleteBtn = await screen.findByText('Delete');

  fireEvent.click(deleteBtn);

  expect(mockDispatch).toHaveBeenCalled();
});

test('re-fetches urls after delete success', () => {
  useSelector.mockReturnValue({
    isGetAllUrlsSuccess: true,
    isGetAllUrlsFail: false,
    isGetAllUrlsPending: false,
    allUrls: [],
    isDeleteUrlSuccess: true,
  });

  render(<UrlsList />);

  expect(mockDispatch).toHaveBeenCalled();
});
