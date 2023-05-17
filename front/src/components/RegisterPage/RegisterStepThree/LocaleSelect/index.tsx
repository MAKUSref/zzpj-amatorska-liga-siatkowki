import { MenuItem, Select } from '@mui/material';
import { useFormContext } from 'react-hook-form';

const LocaleSelect = () => {
  const { register } = useFormContext<{ locale: string }>();

  return (
    <Select {...register('locale')} size="small" className="register-form-input" defaultValue="pl">
      <MenuItem value="pl">PL</MenuItem>
      <MenuItem value="en">EN</MenuItem>
    </Select>
  );
};

export default LocaleSelect;
