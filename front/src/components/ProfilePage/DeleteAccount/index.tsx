import { useTranslation } from 'react-i18next';
import { Button, Card, CardContent, Checkbox, FormControlLabel } from '@mui/material';
import React, { useState } from 'react';
import { useBlockSelfMutation } from '../../../features/api/apiSlice';
import { toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';
import { useAppDispatch } from '../../../features/hooks';
import { logout } from '../../../features/session/sessionSlice';
import { Pathnames } from '../../../routes/pathnames';

function DeleteAccount() {
  const { t } = useTranslation();
  const [checked, setChecked] = useState(false);

  const [deleteAccount] = useBlockSelfMutation();
  const navigate = useNavigate();
  const dispatch = useAppDispatch();

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setChecked(event.target.checked);
  };

  const handleDeleteAccount = () => {
    deleteAccount()
      .unwrap()
      .then(() => {
        toast.info(t('profilePage.deleteAccount.accountBlocked'));
        dispatch(logout());
        navigate(Pathnames.login.fullPath);
      })
      .catch((err) => toast.error(t(err.data)));
  };

  return (
    <Card>
      <CardContent>
        <form onSubmit={handleDeleteAccount}>
          <div className="md-4">
            <FormControlLabel
              control={<Checkbox checked={checked} onChange={handleChange} size="small" />}
              label={t('profile.delete_account.aggriment')}
            />
          </div>
          <div className="mt-3 text-center">
            <Button
              variant="contained"
              disabled={!checked}
              color="error"
              onClick={handleDeleteAccount}>
              {t('profilePage.deleteAccount.delete')}
            </Button>
          </div>
        </form>
      </CardContent>
    </Card>
  );
}

export default DeleteAccount;
