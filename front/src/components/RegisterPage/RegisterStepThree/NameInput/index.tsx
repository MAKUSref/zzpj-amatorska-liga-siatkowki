import { TextField } from '@mui/material';
import { useFormContext } from 'react-hook-form';

const NameInput = () => {
  const {
    register,
    formState: { errors }
  } = useFormContext<{ name: string }>();

  return (
    <TextField
      {...register('name')}
      size="small"
      className="register-form-input"
      label="* Imię"
      variant="outlined"
      error={!!errors.name}
      helperText={errors.name?.message}
    />
  );
};

export default NameInput;
