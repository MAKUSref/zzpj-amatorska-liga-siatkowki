import TextField from '@mui/material/TextField';
import { useFormContext } from 'react-hook-form';

const LoginInput = () => {
  const {
    register,
    formState: { errors }
  } = useFormContext<{ login: string }>();

  return (
    <TextField
      {...register('login')}
      size="small"
      className="login-form-input"
      label="* Login"
      variant="outlined"
      error={!!errors.login}
      helperText={errors.login?.message}
    />
  );
};

export default LoginInput;
