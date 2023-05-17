import { TextField } from '@mui/material';
import { useFormContext } from 'react-hook-form';

const PasswordInput = () => {
  const {
    register,
    formState: { errors }
  } = useFormContext<{ password: string }>();

  return (
    <TextField
      {...register('password')}
      size="small"
      className="register-form-input"
      label="Hasło"
      type="password"
      variant="outlined"
      error={!!errors.password}
      helperText={errors.password?.message}
    />
  );
};

export default PasswordInput;
