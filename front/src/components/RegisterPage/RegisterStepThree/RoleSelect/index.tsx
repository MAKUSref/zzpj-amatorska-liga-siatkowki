import { MenuItem, Select } from '@mui/material';
import { MANAGEMENT_ROLES } from '../../../../features/api/types';

const RoleSelect = () => {
  return (
    <Select size="small" className="register-form-input" defaultValue={MANAGEMENT_ROLES.MANAGER}>
      <MenuItem value={MANAGEMENT_ROLES.MANAGER}>{MANAGEMENT_ROLES.MANAGER}</MenuItem>
      <MenuItem value={MANAGEMENT_ROLES.CAPTAIN}>{MANAGEMENT_ROLES.CAPTAIN}</MenuItem>
      <MenuItem value={MANAGEMENT_ROLES.COACH}>{MANAGEMENT_ROLES.COACH}</MenuItem>
    </Select>
  );
};

export default RoleSelect;
