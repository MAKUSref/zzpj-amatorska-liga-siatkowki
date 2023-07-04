import { useState } from 'react';
import Switch from '@mui/material/Switch';

interface ChangeRoleProps {
  label?: string;
  disabled?: boolean;
  defaultChecked?: boolean;
  onApprove?: () => void;
  onDisapprove?: () => void;
}

const ChangeRole = ({
  defaultChecked = false,
  disabled = false,
  label,
  onApprove,
  onDisapprove
}: ChangeRoleProps) => {
  const [approve, setApprove] = useState(defaultChecked);

  const handleToggleApprove = () => {
    setApprove((prevState) => {
      if (prevState) {
        onDisapprove?.();
      } else {
        onApprove?.();
      }

      return !prevState;
    });
  };

  return (
    <div className="d-flex justify-content-between align-items-center">
      <span className={`${disabled ? 'text-muted' : ''}`}>{label}</span>
      <Switch checked={approve} onChange={handleToggleApprove} disabled={disabled} />
    </div>
  );
};

export default ChangeRole;
