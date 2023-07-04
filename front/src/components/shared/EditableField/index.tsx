import { TextField } from '@mui/material';
import { HTMLInputTypeAttribute } from 'react';
import { useFormContext } from 'react-hook-form';

interface Props {
  label: string;
  isEditable: boolean;
  currentValue: string;
  type: HTMLInputTypeAttribute;
  isRequired?: boolean;
  controlId: string;
  errorMessage?: string;
}

const EditableField = ({ isEditable, currentValue, type, label, controlId, isRequired }: Props) => {
  const {
    register,
    formState: { errors }
  } = useFormContext<{ [p: string]: string }>();

  if (isEditable) {
    return (
      <>
        <TextField
          {...register(controlId)}
          size="small"
          className="register-form-input"
          required={isRequired}
          defaultValue={currentValue}
          type={type}
          label={label}
          variant="outlined"
          error={!!errors[controlId]}
          helperText={errors[controlId]?.message}
        />
      </>
    );
  } else {
    return (
      <>
        <TextField
          {...register(`${controlId}-label`)}
          size="small"
          defaultValue={currentValue}
          type={type}
          label={label}
          variant="outlined"
          disabled
          InputProps={{
            readOnly: true
          }}
          InputLabelProps={{
            shrink: true
          }}
        />
      </>
    );
  }
};

export default EditableField;
