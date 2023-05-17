import { TextField } from '@mui/material';
import { useFormContext } from 'react-hook-form';

const ConfirmPasswordInput = () => {
  const {
    register,
    formState: { errors }
  } = useFormContext<{ confirmPassword: string }>();

  return (
    <TextField
      {...register('confirmPassword')}
      size="small"
      className="register-form-input"
      label="Potwierdź hasło"
      type="password"
      variant="outlined"
      error={!!errors.confirmPassword}
      helperText={errors.confirmPassword?.message}
    />
  );
};

export default ConfirmPasswordInput;
