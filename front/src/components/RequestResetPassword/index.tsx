import { useForm } from 'react-hook-form';
import IntroContainer from '../IntroContainer';
import { yupResolver } from '@hookform/resolvers/yup';
import KeyboardBackspaceIcon from '@mui/icons-material/KeyboardBackspace';
import * as Yup from 'yup';
import { TextField } from '@mui/material';
import { Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { useLazyRequestResetPasswordQuery } from '../../features/api/apiSlice';
import { toast } from 'react-toastify';
import { Pathnames } from '../../routes/pathnames';
import { useTranslation } from 'react-i18next';

interface ResetPasswordFormSchema {
  email: string;
}

const reserPasswordFormSchema = Yup.object().shape({
  email: Yup.string().required('Nie podano e-maila').email('Podany e-mail jest niepoprawny')
});

const ResetPassword = () => {
  const navigate = useNavigate();
  const [requsetResetPasswordQuery] = useLazyRequestResetPasswordQuery();
  const { t } = useTranslation();

  const {
    handleSubmit,
    register,
    formState: { errors }
  } = useForm<ResetPasswordFormSchema>({
    resolver: yupResolver(reserPasswordFormSchema)
  });

  const handleNavigateToLogin = () => {
    navigate(Pathnames.login.fullPath);
  };

  const handleResetPassword = ({ email }: ResetPasswordFormSchema) => {
    requsetResetPasswordQuery(email)
      .unwrap()
      .then(() => {
        toast.success('Wysłaliśmy na twojego mail link do zmiany hasła');
        navigate(Pathnames.login.fullPath);
      })
      .catch((err) => {
        toast.error(t(err.data));
      });
  };

  return (
    <IntroContainer>
      <div className="reset-password-form card border-0">
        <form onSubmit={handleSubmit(handleResetPassword)}>
          <h3 className="mb-3">{'Resetuj hasło'}</h3>

          <p className="mb-4 text-muted fw-semibold">Amatorska liga siatkówki wita.</p>

          <div>
            <TextField
              {...register('email')}
              size="small"
              className="email-form-input"
              label="* Email"
              variant="outlined"
              error={!!errors.email}
              helperText={errors.email?.message}
            />
          </div>

          <p className="my-4 text-muted fw-semibold">
            <span className="text-link" onClick={handleNavigateToLogin}>
              Powrót do logowania
            </span>
          </p>

          <div className="mt-3">
            <Button variant="contained" type="submit">
              Resetuj hasło
            </Button>
          </div>
          <Button
            size="small"
            className="mt-3"
            variant="text"
            onClick={() => navigate(Pathnames.home.fullPath)}>
            <KeyboardBackspaceIcon /> <span className="ms-2">Strona główna</span>
          </Button>
        </form>
      </div>
    </IntroContainer>
  );
};

export default ResetPassword;
