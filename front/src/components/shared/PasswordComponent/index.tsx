import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import {
  InputLabel,
  OutlinedInput,
  InputAdornment,
  IconButton,
  FormControl,
  FormHelperText
} from '@mui/material';
import React, { useState } from 'react';
import { FieldErrors, FieldValues, UseFormRegister } from 'react-hook-form';

interface Props<T extends FieldValues> {
  register: UseFormRegister<T>;
  errors: FieldErrors<T>;
  label: string;
  controlId: {
    cammel: string;
    kebab: string;
  };
}

function PasswordComponent({ register, errors, label, controlId }: Props<{ [p: string]: string }>) {
  const [showPassword, setShowPassword] = useState(false);
  const handleClickShowPassword = () => setShowPassword((show) => !show);
  const handleMouseDownPassword = (event: React.MouseEvent<HTMLButtonElement>) => {
    event.preventDefault();
  };
  return (
    <FormControl variant="outlined" size="small" fullWidth>
      <InputLabel htmlFor={controlId.kebab}>{label}</InputLabel>
      <OutlinedInput
        {...register(controlId.cammel)}
        error={!!errors[controlId.cammel]}
        id={controlId.kebab}
        type={showPassword ? 'text' : 'password'}
        endAdornment={
          <InputAdornment position="end">
            <IconButton
              onClick={handleClickShowPassword}
              onMouseDown={handleMouseDownPassword}
              edge="end">
              {showPassword ? <VisibilityOff /> : <Visibility />}
            </IconButton>
          </InputAdornment>
        }
        label={label}
      />
      <FormHelperText
        sx={{
          color: 'red'
        }}>
        {errors[controlId.cammel]?.message}
      </FormHelperText>
    </FormControl>
  );
}

export default PasswordComponent;
