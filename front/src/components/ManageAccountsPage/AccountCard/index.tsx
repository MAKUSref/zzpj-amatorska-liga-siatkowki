import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import ChangeAccountDialog from './ChangeAccountDialog';
import Typography from '@mui/material/Typography';
import ClearIcon from '@mui/icons-material/Clear';
import IconButton from '@mui/material/IconButton';
import CircularProgress from '@mui/material/CircularProgress';
import { useNavigate, useParams } from 'react-router-dom';
import {
  useApproveAccountMutation,
  useBlockAccountMutation,
  useDisapproveAccountMutation,
  useGetAccountByIdQuery,
  useUnblockAccountMutation
} from '../../../features/api/apiSlice';
import Switch from '@mui/material/Switch';
import EmailIcon from '@mui/icons-material/Email';
import LoginIcon from '@mui/icons-material/Login';
import { Chip } from '@mui/material';
import { toast } from 'react-toastify';
import { useAppSelector } from '../../../features/hooks';
import { Pathnames } from '../../../routes/pathnames';
import { useTranslation } from 'react-i18next';

const AccountCard = () => {
  const { accountId } = useParams();
  const { data: account } = useGetAccountByIdQuery(accountId ?? '');
  const sessionState = useAppSelector((state) => state.session);
  const navigate = useNavigate();
  const { t } = useTranslation();
  const [blockAccount, { isLoading: blockAccountLoading }] = useBlockAccountMutation();
  const [approveAccount, { isLoading: approveAccountLoading }] = useApproveAccountMutation();
  const [unblockAccount, { isLoading: unblockAccountLoading }] = useUnblockAccountMutation();
  const [disapproveAccount, { isLoading: disapproveAccountLoading }] =
    useDisapproveAccountMutation();

  const handleChangeBlockAccount = () => {
    if (!accountId) return;

    if (account?.payload?.blocked) {
      unblockAccount(accountId)
        .unwrap()
        .catch((err) => toast.error(t(err.data)));
    } else {
      blockAccount(accountId)
        .unwrap()
        .catch((err) => toast.error(t(err.data)));
    }
  };

  const handleChangeApproveAccount = () => {
    if (!accountId) return;

    if (account?.payload?.approved) {
      disapproveAccount(accountId)
        .unwrap()
        .catch((err) => toast.error(t(err.data)));
    } else {
      approveAccount(accountId)
        .unwrap()
        .catch((err) => toast.error(t(err.data)));
    }
  };

  return (
    <Card>
      <CardContent>
        <Typography
          className="d-flex justify-content-between align-items-center"
          variant="h5"
          component="div">
          <span>
            {account?.payload?.name} {account?.payload?.lastname}
          </span>
          <IconButton onClick={() => navigate(Pathnames.manageAccounts.fullPath)} edge="end">
            <ClearIcon />
          </IconButton>
        </Typography>
        <Typography sx={{ fontSize: 14 }} color="text.secondary">
          <EmailIcon sx={{ fontSize: 16 }} />
          <span className="ms-2">{account?.payload?.email}</span>
        </Typography>
        <Typography sx={{ fontSize: 14 }} color="text.secondary">
          <LoginIcon sx={{ fontSize: 16 }} />
          <span className="ms-2">{account?.payload?.login}</span>
        </Typography>
        <hr />
        <div className="d-flex justify-content-between align-items-center mt-3">
          <Typography variant="body2">{t('manageAccountsPage.accountCard.accept')}</Typography>
          {approveAccountLoading || disapproveAccountLoading ? (
            <CircularProgress
              className="me-3 my-2"
              sx={{ width: '22px!important', height: '22px!important' }}
            />
          ) : (
            <Switch
              checked={account?.payload?.approved ?? false}
              onChange={handleChangeApproveAccount}
              disabled={accountId === sessionState.tokenInfo.jti}
            />
          )}
        </div>
        <div className="d-flex justify-content-between align-items-center mt-1">
          <Typography variant="body2">{t('manageAccountsPage.accountCard.block')}</Typography>
          {blockAccountLoading || unblockAccountLoading ? (
            <CircularProgress
              className="me-3 my-2"
              sx={{ width: '22px!important', height: '22px!important' }}
            />
          ) : (
            <Switch
              checked={account?.payload?.blocked ?? false}
              onChange={handleChangeBlockAccount}
              disabled={accountId === sessionState.tokenInfo.jti}
            />
          )}
        </div>
        <div className="d-flex justify-content-between align-items-center mt-2">
          <Typography variant="body2">
            {t('manageAccountsPage.accountCard.rolesPossesed')}
          </Typography>
          <div>
            {account?.payload?.roles?.map((role, i) => (
              <Chip className="pane-neutral fw-bolder" key={i} label={role.role} size="small" />
            ))}
          </div>
        </div>
      </CardContent>
      <CardActions>
        {accountId !== sessionState.tokenInfo.jti && <ChangeAccountDialog />}
      </CardActions>
    </Card>
  );
};

export default AccountCard;
