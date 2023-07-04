import TextField from '@mui/material/TextField';
import { useFormContext } from 'react-hook-form';
import { useTranslation } from 'react-i18next';

const PasswordInput = () => {
  const {
    register,
    formState: { errors }
  } = useFormContext<{ password: string }>();
  const { t } = useTranslation();
  return (
    <TextField
      {...register('password')}
      size="small"
      className="login-form-input"
      label={t('login.passwordInput')}
      type="password"
      variant="outlined"
      error={!!errors.password}
      helperText={errors.password?.message}
    />
  );
};

export default PasswordInput;
