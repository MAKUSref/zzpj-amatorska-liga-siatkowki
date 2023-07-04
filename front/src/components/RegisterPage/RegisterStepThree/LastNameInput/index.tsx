import { TextField } from '@mui/material';
import { useFormContext } from 'react-hook-form';

const LastNameInput = () => {
  const {
    register,
    formState: { errors }
  } = useFormContext<{ lastname: string }>();

  return (
    <TextField
      {...register('lastname')}
      size="small"
      className="register-form-input"
      label="* Nazwisko"
      variant="outlined"
      error={!!errors.lastname}
      helperText={errors.lastname?.message}
    />
  );
};

export default LastNameInput;
