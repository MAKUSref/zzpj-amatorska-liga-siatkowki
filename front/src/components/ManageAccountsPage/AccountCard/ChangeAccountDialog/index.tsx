import { useState } from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import UserInfoSection from './UserInfoSection';
import RolesSection from './RolesSection';
import CloseIcon from '@mui/icons-material/Close';
import ManageAccountsIcon from '@mui/icons-material/ManageAccounts';
import { IconButton } from '@mui/material';
import HistorySection from './HistorySection';
import { useTranslation } from 'react-i18next';

const DialogAddAccount = () => {
  const [open, setOpen] = useState(false);
  const { t } = useTranslation();
  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <>
      <Button variant="text" onClick={handleClickOpen}>
        {t('manageAccountsPage.accountCard.changeAccountDialog.edit')}
      </Button>
      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description">
        <DialogTitle className="d-flex align-items-center">
          <span className="d-flex align-items-center">
            <ManageAccountsIcon className="me-1" />
            {t('manageAccountsPage.accountCard.changeAccountDialog.userEdit')}
          </span>
          <IconButton
            aria-label="close"
            onClick={handleClose}
            sx={{
              position: 'absolute',
              right: 8,
              top: 8
            }}>
            <CloseIcon />
          </IconButton>
        </DialogTitle>
        <DialogContent>
          <UserInfoSection />
          <hr />
          <RolesSection />
          <hr />
          <HistorySection />
        </DialogContent>
      </Dialog>
    </>
  );
};

export default DialogAddAccount;
