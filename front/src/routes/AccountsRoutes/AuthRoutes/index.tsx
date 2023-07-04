import ActivationPage from '../../../components/ActivationPage';
import CreateNewPassword from '../../../components/CreateNewPassword';
import LoginPage from '../../../components/LoginPage';
import RegisterPage from '../../../components/RegisterPage';
import RegisterStepOne from '../../../components/RegisterPage/RegisterStepOne';
import RegisterStepThree from '../../../components/RegisterPage/RegisterStepThree';
import RegisterStepTwo from '../../../components/RegisterPage/RegisterStepTwo';
import RegisterSuccess from '../../../components/RegisterPage/RegisterSuccess';
import ResetPassword from '../../../components/RequestResetPassword';
import ResetEmail from '../../../components/ResetEmail';
import { RouteType } from '../../types';
import { Pathnames } from '../../pathnames';

const AuthRoutes: RouteType[] = [
  {
    ...Pathnames.login,
    element: <LoginPage />
  },
  {
    ...Pathnames.register,
    element: <RegisterPage />,
    children: [
      {
        path: '',
        fullPath: Pathnames.register.fullPath,
        element: <RegisterStepOne />
      },
      {
        ...Pathnames.registerStepOne,
        element: <RegisterStepOne />
      },
      {
        ...Pathnames.registerStepTwo,
        element: <RegisterStepTwo />
      },
      {
        ...Pathnames.registerStepThree,
        element: <RegisterStepThree />
      }
    ]
  },
  {
    ...Pathnames.registerSuccess,
    element: <RegisterSuccess />
  },
  {
    ...Pathnames.resetEmail,
    element: <ResetEmail />
  },
  {
    ...Pathnames.resetPassword,
    element: <ResetPassword />
  },
  {
    ...Pathnames.newPassword,
    element: <CreateNewPassword />
  },
  {
    ...Pathnames.activate,
    element: <ActivationPage />
  }
];

export default AuthRoutes;
