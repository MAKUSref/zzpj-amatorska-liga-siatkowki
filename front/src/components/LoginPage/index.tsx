import './style.scss';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';
import IntroContainer from '../IntroContainer';
import { Button, TextField } from '@mui/material';
import KeyboardBackspaceIcon from '@mui/icons-material/KeyboardBackspace';
import { useGetPingQuery } from '../../features/api/apiSlice';
import { useEffect } from 'react';

const LoginPage = () => {
  const { t } = useTranslation();
  const navigate = useNavigate();

  const { data: checkConnection } = useGetPingQuery();

  const {
    handleSubmit,
    formState: { errors }
  } = useForm({
    defaultValues: {
      login: '',
      password: ''
    }
  });

  const handleLogin = (login: string, password: string) => {
    console.log(login, password);
    toast.error(t('login.exception.badLogin') as string);
  };

  const handleNavigateToRegister = () => {
    navigate('/register');
  };

  useEffect(() => {
    console.log(checkConnection);
  }, [checkConnection]);

  return (
    <IntroContainer>
      <div className="login-form card border-0">
        <form onSubmit={handleSubmit((data) => handleLogin(data.login, data.password))}>
          <h3 className="mb-3">{t('login.title')}</h3>

          <p className="mb-4 text-muted fw-semibold">Amatorska liga siatkówki wita.</p>

          <TextField
            size="small"
            className="mt-1 login-form-input"
            label={t('login.placeholder.login') as string}
            variant="outlined"
          />
          <p className="text-danger fw-bold">
            <small>{errors.login?.message}</small>
          </p>

          <TextField
            size="small"
            className="mt-1 login-form-input"
            label={t('login.placeholder.password') as string}
            variant="outlined"
          />
          <p className="text-danger fw-bold">
            <small>{errors.password?.message}</small>
          </p>

          <p className="my-4 text-muted fw-semibold">
            Chcesz dołączyć do rozgrywek? <br /> Nic prostrzego, zarejestruj się{' '}
            <span className="text-link" onClick={handleNavigateToRegister}>
              tutaj
            </span>
            .
          </p>
          <div className="mt-3">
            <Button variant="contained" type="submit">
              {t('login.loginBtn')}
            </Button>
          </div>
          <Button size="small" className="mt-3" variant="text" onClick={() => navigate('/')}>
            <KeyboardBackspaceIcon /> <span className="ms-2">Strona główna</span>
          </Button>
        </form>
      </div>
    </IntroContainer>
  );
};

export default LoginPage;
