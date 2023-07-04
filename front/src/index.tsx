import React from 'react';
import ReactDOM from 'react-dom/client';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter } from 'react-router-dom';
import { Provider } from 'react-redux';
import theme from './themes/muiTheme';
import RouterApp from './routes/RouterApp';
import { store } from './features/store';
import { ThemeProvider } from '@mui/material';
import 'react-toastify/dist/ReactToastify.css';
import './styles/index.scss';
import './i18n';
import { LocalizationProvider } from '@mui/x-date-pickers-pro';
import { AdapterDayjs } from '@mui/x-date-pickers-pro/AdapterDayjs';

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);

root.render(
  <React.StrictMode>
    <Provider store={store}>
      <LocalizationProvider dateAdapter={AdapterDayjs}>
        <ThemeProvider theme={theme}>
          <BrowserRouter>
            <RouterApp />
          </BrowserRouter>
          {/* <RouterProvider router={RouterApp} /> */}
        </ThemeProvider>
      </LocalizationProvider>
    </Provider>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))z
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
