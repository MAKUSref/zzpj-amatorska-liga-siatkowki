import { TextField } from '@mui/material';
import { useFormContext } from 'react-hook-form';

const EmailInput = () => {
  const {
    register,
    formState: { errors }
  } = useFormContext<{ email: string }>();

  return (
    <TextField
      {...register('email')}
      size="small"
      className="register-form-input"
      label="Email"
      variant="outlined"
      error={!!errors.email}
      helperText={errors.email?.message}
    />
  );
};

export default EmailInput;
