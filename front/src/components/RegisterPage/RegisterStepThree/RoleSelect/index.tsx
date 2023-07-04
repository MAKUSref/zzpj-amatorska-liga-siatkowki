import { MenuItem, Select } from '@mui/material';
import { useFormContext } from 'react-hook-form';
import { MANAGEMENT_ROLES } from '../../../../features/api/types';

const RoleSelect = () => {
  const { register } = useFormContext<{ role: MANAGEMENT_ROLES }>();

  return (
    <Select
      {...register('role')}
      size="small"
      className="register-form-input"
      label="* Rola"
      defaultValue={MANAGEMENT_ROLES.MANAGER}>
      <MenuItem value={MANAGEMENT_ROLES.MANAGER}>{MANAGEMENT_ROLES.MANAGER}</MenuItem>
      <MenuItem value={MANAGEMENT_ROLES.CAPTAIN}>{MANAGEMENT_ROLES.CAPTAIN}</MenuItem>
      <MenuItem value={MANAGEMENT_ROLES.COACH}>{MANAGEMENT_ROLES.COACH}</MenuItem>
    </Select>
  );
};

export default RoleSelect;
