import { FormControlLabel, FormGroup, Switch } from '@mui/material';
import { useTranslation } from 'react-i18next';
import React from 'react';

interface Props {
  isEditable: boolean;
  setEditable: (val: boolean) => void;
}

const EditableSwitch = ({ isEditable, setEditable }: Props) => {
  const { t } = useTranslation();

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEditable(event.target.checked);
  };

  return (
    <FormGroup>
      <FormControlLabel
        control={
          <Switch
            inputProps={{ 'aria-label': 'controlled' }}
            checked={isEditable}
            onChange={handleChange}
          />
        }
        label={t('defaults.edit')}
      />
    </FormGroup>
  );
};

export default EditableSwitch;
