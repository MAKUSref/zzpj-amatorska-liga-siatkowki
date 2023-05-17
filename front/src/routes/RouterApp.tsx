import { createBrowserRouter, Outlet } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import LoginPage from '../components/LoginPage';
import RegisterPage from '../components/RegisterPage';
import RegisterStepOne from '../components/RegisterPage/RegisterStepOne';
import RegisterStepTwo from '../components/RegisterPage/RegisterStepTwo';
import RegisterStepThree from '../components/RegisterPage/RegisterStepThree';
import RegisterSuccess from '../components/RegisterPage/RegisterSuccess';
import Navbar from '../components/Navbar';
import ProfilePage from '../components/ProfilePage';
import ManageAccounts from '../components/ManageAccounts';

const RouterApp = createBrowserRouter([
  {
    path: '/',
    element: (
      <>
        <ToastContainer autoClose={1700} />
        <Outlet />
      </>
    ),
    children: [
      {
        path: '',
        element: (
          <>
            <Navbar />
            <Outlet />
          </>
        ),
        children: [
          {
            path: '/profile',
            element: <ProfilePage />
          },
          {
            path: '/manage-accounts',
            element: <ManageAccounts />
          }
        ]
      },
      {
        path: '/login',
        element: <LoginPage />
      },
      {
        path: '/register',
        element: (
          <RegisterPage>
            <Outlet />
          </RegisterPage>
        ),
        children: [
          {
            path: '',
            element: <RegisterStepOne />
          },
          {
            path: 'step-one',
            element: <RegisterStepOne />
          },
          {
            path: 'step-two',
            element: <RegisterStepTwo />
          },
          {
            path: 'step-three',
            element: <RegisterStepThree />
          }
        ]
      },
      {
        path: 'register-success',
        element: <RegisterSuccess />
      }
    ]
  },
  {
    path: '*',
    element: <>404 not found</>
  }
]);

export default RouterApp;
