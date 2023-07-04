import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import TextField from '@mui/material/TextField';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import PersonIcon from '@mui/icons-material/Person';
import { useState } from 'react';
import { Col, Row } from 'react-bootstrap';
import { ROLES } from '../../../../features/api/types';
import * as Yup from 'yup';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { useCreateAccountMutation } from '../../../../features/api/apiSlice';
import { toast } from 'react-toastify';
import { useTranslation } from 'react-i18next';

interface CreateAccountFormSchema {
  name: string;
  lastname: string;
  locale: string;
  login: string;
  email: string;
  password: string;
  confirmPassword: string;
  role: ROLES;
}

const DialogAddAccount = () => {
  const [open, setOpen] = useState(false);
  const [createAccount] = useCreateAccountMutation();
  const { t } = useTranslation();

  const createAccountFormSchema = Yup.object().shape({
    name: Yup.string().required(
      t('manageAccountsPage.accountsTable.dialogAddUser.giveName') as string
    ),
    lastname: Yup.string().required(
      t('manageAccountsPage.accountsTable.dialogAddUser.giveLastName') as string
    ),
    locale: Yup.string().required().oneOf(['pl', 'en']),
    login: Yup.string().required(
      t('manageAccountsPage.accountsTable.dialogAddUser.giveLogin') as string
    ),
    email: Yup.string()
      .required(t('manageAccountsPage.accountsTable.dialogAddUser.giveMail') as string)
      .email(t('manageAccountsPage.accountsTable.dialogAddUser.emailError') as string),
    password: Yup.string()
      .required(t('manageAccountsPage.accountsTable.dialogAddUser.givePassword') as string)
      .min(8, t('manageAccountsPage.accountsTable.dialogAddUser.samePasswords') as string),
    confirmPassword: Yup.string()
      .required(t('manageAccountsPage.accountsTable.dialogAddUser.samePasswords') as string)
      .oneOf(
        [Yup.ref('password')],
        t('manageAccountsPage.accountsTable.dialogAddUser.samePasswords') as string
      ),
    role: Yup.string().required()
  });

  const {
    register,
    handleSubmit,
    formState: { errors }
  } = useForm<CreateAccountFormSchema>({
    defaultValues: {
      name: '',
      lastname: '',
      locale: 'pl',
      login: '',
      email: '',
      password: '',
      confirmPassword: '',
      role: ROLES.REFREE
    },
    resolver: yupResolver(createAccountFormSchema)
  });

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleCreateAccount = (data: CreateAccountFormSchema) => {
    createAccount({ ...data })
      .unwrap()
      .then(() => {
        toast.success(t('manageAccountsPage.accountsTable.dialogAddUser.userCreated'));
        handleClose();
      })
      .catch((err) => {
        if (err.data && err.originalStatus !== 401) {
          toast.error(t(err.data));
        } else {
          toast.error(t('manageAccountsPage.accountsTable.dialogAddUser.userCreationError'));
        }
      });
  };

  return (
    <>
      <Button variant="text" onClick={handleClickOpen}>
        {t('manageAccountsPage.accountsTable.dialogAddUser.addUserPlus')}
      </Button>
      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description">
        <form onSubmit={handleSubmit(handleCreateAccount)}>
          <DialogTitle className="d-flex align-items-center">
            <PersonIcon className="me-2 fs-3" />
            {t('manageAccountsPage.accountsTable.dialogAddUser.addUser')}
          </DialogTitle>
          <DialogContent>
            <Row>
              <Col sm={12} md={6}>
                <p className="text-muted">
                  {t('manageAccountsPage.accountsTable.dialogAddUser.personalData')}
                </p>
                <div className="mt-1">
                  <TextField
                    {...register('name')}
                    size="small"
                    label={t('manageAccountsPage.accountsTable.dialogAddUser.labelName')}
                    variant="outlined"
                    error={!!errors.name?.message}
                    helperText={errors.name?.message}
                    fullWidth
                  />
                </div>
                <div className="mt-3">
                  <TextField
                    {...register('lastname')}
                    size="small"
                    label={t('manageAccountsPage.accountsTable.dialogAddUser.labelLastName')}
                    variant="outlined"
                    error={!!errors.lastname?.message}
                    helperText={errors.lastname?.message}
                    fullWidth
                  />
                </div>
                <div className="mt-3">
                  <FormControl size="small" fullWidth>
                    <InputLabel id="demo-simple-select-label">
                      {t('manageAccountsPage.accountsTable.dialogAddUser.language')}
                    </InputLabel>
                    <Select
                      {...register('locale')}
                      labelId="demo-simple-select-label"
                      label={t('manageAccountsPage.accountsTable.dialogAddUser.language')}
                      defaultValue="pl">
                      <MenuItem value="pl">
                        {t('manageAccountsPage.accountsTable.dialogAddUser.polish')}
                      </MenuItem>
                      <MenuItem value="en">
                        {t('manageAccountsPage.accountsTable.dialogAddUser.english')}
                      </MenuItem>
                    </Select>
                  </FormControl>
                </div>
              </Col>
              <Col sm={12} md={6}>
                <p className="text-muted mt-sm-3 mt-md-0">
                  {t('manageAccountsPage.accountsTable.dialogAddUser.accountData')}
                </p>
                <div className="mt-3">
                  <TextField
                    {...register('login')}
                    size="small"
                    label={t('manageAccountsPage.accountsTable.dialogAddUser.labelLogin')}
                    variant="outlined"
                    error={!!errors.login?.message}
                    helperText={errors.login?.message}
                    fullWidth
                  />
                </div>
                <div className="mt-3">
                  <TextField
                    {...register('email')}
                    size="small"
                    label={t('manageAccountsPage.accountsTable.dialogAddUser.labelEmail')}
                    variant="outlined"
                    error={!!errors.email?.message}
                    helperText={errors.email?.message}
                    fullWidth
                  />
                </div>
                <div className="mt-3">
                  <TextField
                    {...register('password')}
                    size="small"
                    label={t('manageAccountsPage.accountsTable.dialogAddUser.labelPassword')}
                    variant="outlined"
                    type="password"
                    error={!!errors.password?.message}
                    helperText={errors.password?.message}
                    fullWidth
                  />
                </div>
                <div className="mt-3">
                  <TextField
                    {...register('confirmPassword')}
                    size="small"
                    label={t('manageAccountsPage.accountsTable.dialogAddUser.labelConfirmPassword')}
                    variant="outlined"
                    type="password"
                    error={!!errors.confirmPassword?.message}
                    helperText={errors.confirmPassword?.message}
                    fullWidth
                  />
                </div>
                <div className="mt-3">
                  <FormControl size="small" fullWidth>
                    <InputLabel id="demo-simple-select-label">* Rola</InputLabel>
                    <Select {...register('role')} label="* Rola" defaultValue={ROLES.REFREE}>
                      <MenuItem value={ROLES.REFREE}>
                        {t('manageAccountsPage.accountsTable.dialogAddUser.referee')}
                      </MenuItem>
                      <MenuItem value={ROLES.MANAGER}>
                        {t('manageAccountsPage.accountsTable.dialogAddUser.manager')}
                      </MenuItem>
                      <MenuItem value={ROLES.CAPTAIN}>
                        {t('manageAccountsPage.accountsTable.dialogAddUser.captain')}
                      </MenuItem>
                      <MenuItem value={ROLES.COACH}>
                        {t('manageAccountsPage.accountsTable.dialogAddUser.coach')}
                      </MenuItem>
                      <MenuItem value={ROLES.ADMIN}>Administrator</MenuItem>
                    </Select>
                  </FormControl>
                </div>
              </Col>
            </Row>
          </DialogContent>
          <DialogActions>
            <Button onClick={handleClose}>
              {t('manageAccountsPage.accountsTable.dialogAddUser.cancel')}
            </Button>
            <Button variant="contained" autoFocus type="submit">
              {t('manageAccountsPage.accountsTable.dialogAddUser.add')}
            </Button>
          </DialogActions>
        </form>
      </Dialog>
    </>
  );
};

export default DialogAddAccount;
