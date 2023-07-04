import './style.scss';
import { FormProvider, useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';
import IntroContainer from '../IntroContainer';
import { Button } from '@mui/material';
import KeyboardBackspaceIcon from '@mui/icons-material/KeyboardBackspace';
import { useLoginMutation } from '../../features/api/apiSlice';
import * as Yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import LoginInput from './LoginInput';
import PasswordInput from './PasswordLogin';
import { useAppDispatch } from '../../features/hooks';
import { setToken } from '../../features/session/sessionSlice';
import { Pathnames } from '../../routes/pathnames';
import { useMemo } from 'react';

interface LoginFormSchema {
  login: string;
  password: string;
}

const LoginPage = () => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const [loginMutation] = useLoginMutation();

  const loginFormSchema = useMemo(
    () =>
      Yup.object().shape({
        login: Yup.string().required(t('login.noLogin') as string),
        password: Yup.string().required(t('login.noPassword') as string)
      }),
    []
  );

  const methods = useForm<LoginFormSchema>({
    defaultValues: {
      login: '',
      password: ''
    },
    resolver: yupResolver(loginFormSchema)
  });

  const handleLogin = ({ login, password }: LoginFormSchema) => {
    loginMutation({ login, password })
      .unwrap()
      .then(() => {})
      .catch((res) => {
        if (res.originalStatus === 202) {
          dispatch(setToken(res.data));
          navigate(Pathnames.home.fullPath);
        } else {
          toast.error(t(res.data));
        }
      });
  };

  return (
    <IntroContainer>
      <div className="login-form card border-0">
        <FormProvider {...methods}>
          <form onSubmit={methods.handleSubmit(handleLogin)}>
            <h3 className="mb-3">{t('login.title')}</h3>

            <p className="mb-4 text-muted fw-semibold">{t('login.voleyballLeagueWelcomes')}</p>

            <div>
              <LoginInput />
            </div>

            <div className="mt-3">
              <PasswordInput />
            </div>

            <p className="my-4 text-muted fw-semibold">
              {t('login.joinGames')} <br /> {t('login.noEasier')}{' '}
              <span className="text-link" onClick={() => navigate(Pathnames.register.fullPath)}>
                {t('login.here')}
              </span>
            </p>
            <div className="mt-3">
              <Button variant="contained" type="submit">
                {t('login.loginBtn')}
              </Button>
            </div>
            <p className="my-4 text-muted fw-semibold">
              <span
                className="text-link"
                onClick={() => navigate(Pathnames.resetPassword.fullPath)}>
                {t('login.resetPassword')}
              </span>
            </p>
            <Button
              size="small"
              className="mt-3"
              variant="text"
              onClick={() => navigate(Pathnames.home.fullPath)}>
              <KeyboardBackspaceIcon /> <span className="ms-2">{t('login.homePage')}</span>
            </Button>
          </form>
        </FormProvider>
      </div>
    </IntroContainer>
  );
};

export default LoginPage;
