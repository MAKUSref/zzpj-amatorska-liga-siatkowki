import { MouseEvent, useMemo, useState } from 'react';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import Menu from '@mui/material/Menu';
import Avatar from '@mui/material/Avatar';
import Tooltip from '@mui/material/Tooltip';
import MenuItem from '@mui/material/MenuItem';
import { useNavigate } from 'react-router-dom';
import { EXTENDED_ROLES } from '../../../features/api/types';
import { useAppDispatch, useAppSelector } from '../../../features/hooks';
import { logout } from '../../../features/session/sessionSlice';
import { useGetSelfInfoQuery } from '../../../features/api/apiSlice';
import { stringAvatar, accountRolesIncludes } from '../../../utils';
import { Pathnames } from '../../../routes/pathnames';
import { useTranslation } from 'react-i18next';
import { CircularProgress } from '@mui/material';

const NavabarProfileBtn = () => {
  const [anchorElAccount, setAnchorElAccount] = useState<null | HTMLElement>(null);
  const { t } = useTranslation();
  const sessionState = useAppSelector((state) => state.session);

  const { data: selfInfo, isLoading } = useGetSelfInfoQuery();

  const navigate = useNavigate();
  const dispatch = useAppDispatch();

  const profileBgClassName = useMemo(() => {
    if (accountRolesIncludes(sessionState.tokenInfo.role, [EXTENDED_ROLES.ADMIN])) {
      return 'bg-profile-admin';
    }

    if (
      accountRolesIncludes(sessionState.tokenInfo.role, [
        EXTENDED_ROLES.MANAGER,
        EXTENDED_ROLES.COACH,
        EXTENDED_ROLES.COACH
      ])
    ) {
      return 'bg-success';
    }

    if (accountRolesIncludes(sessionState.tokenInfo.role, [EXTENDED_ROLES.REFREE])) {
      return 'bg-profile-referee';
    }

    return '';
  }, []);

  const handleOpenAccountMenu = (event: MouseEvent<HTMLElement>) => {
    setAnchorElAccount(event.currentTarget);
  };

  const handleCloseAccountMenu = () => {
    setAnchorElAccount(null);
  };

  const handleAccountMenuNavigate = (path: string) => {
    handleCloseAccountMenu();
    navigate(path);
  };

  const handleLogout = () => {
    handleCloseAccountMenu();
    dispatch(logout());
    navigate(Pathnames.login.fullPath);
  };

  if (isLoading) {
    return (
      <div>
        <CircularProgress
          color="secondary"
          className="mt-1 me-2"
          sx={{ width: '22px!important', height: '22px!important' }}
        />
      </div>
    );
  }

  if (sessionState.tokenInfo.role.includes(EXTENDED_ROLES.GUEST)) {
    return (
      <Button
        sx={{ my: 2, color: 'white', display: 'block' }}
        onClick={() => navigate(Pathnames.login.fullPath)}>
        {t('navbar.navbarProfileBtn.logIn')}
      </Button>
    );
  }

  return (
    <div className={`ps-4 p-1 rounded-5 ${profileBgClassName}`}>
      <Box sx={{ flexGrow: 0 }}>
        <span className="me-3">{`${selfInfo?.payload.name} ${selfInfo?.payload.lastname}`}</span>
        <Tooltip title="Open settings">
          <IconButton onClick={handleOpenAccountMenu} sx={{ p: 0 }}>
            <Avatar
              {...stringAvatar(
                `${selfInfo?.payload?.name?.toUpperCase()} ${selfInfo?.payload?.lastname?.toUpperCase()}`
              )}
            />
          </IconButton>
        </Tooltip>
        <Menu
          sx={{ mt: '45px' }}
          id="menu-appbar"
          anchorEl={anchorElAccount}
          anchorOrigin={{
            vertical: 'top',
            horizontal: 'right'
          }}
          keepMounted
          transformOrigin={{
            vertical: 'top',
            horizontal: 'right'
          }}
          open={Boolean(anchorElAccount)}
          onClose={handleCloseAccountMenu}>
          <MenuItem onClick={() => handleAccountMenuNavigate('/profile')}>
            <Typography textAlign="center">{t('navbar.navbarProfileBtn.profile')}</Typography>
          </MenuItem>
          {accountRolesIncludes(sessionState.tokenInfo.role, [EXTENDED_ROLES.ADMIN]) && (
            <MenuItem onClick={() => handleAccountMenuNavigate('/manage-accounts')}>
              <Typography textAlign="center">
                {t('navbar.navbarProfileBtn.manageAccounts')}
              </Typography>
            </MenuItem>
          )}
          {accountRolesIncludes(sessionState.tokenInfo.role, [EXTENDED_ROLES.ADMIN]) && (
            <MenuItem onClick={() => handleAccountMenuNavigate('/manage-requests')}>
              <Typography textAlign="center">
                {t('navbar.navbarProfileBtn.manageRequests')}
              </Typography>
            </MenuItem>
          )}

          {accountRolesIncludes(sessionState.tokenInfo.role, [
            EXTENDED_ROLES.ADMIN,
            EXTENDED_ROLES.CAPTAIN,
            EXTENDED_ROLES.COACH,
            EXTENDED_ROLES.MANAGER
          ]) && (
            <MenuItem onClick={() => handleAccountMenuNavigate('/create-team')}>
              <Typography textAlign="center">{t('menuItems.create_team')}</Typography>
            </MenuItem>
          )}
          {accountRolesIncludes(sessionState.tokenInfo.role, [EXTENDED_ROLES.ADMIN]) && (
            <MenuItem onClick={() => handleAccountMenuNavigate('/submit-team-for-league-admin')}>
              <Typography textAlign="center">{t('menuItems.submit_team_admin')}</Typography>
            </MenuItem>
          )}
          {accountRolesIncludes(sessionState.tokenInfo.role, [EXTENDED_ROLES.REFREE]) && (
            <MenuItem onClick={() => handleAccountMenuNavigate('/your-games')}>
              <Typography textAlign="center">{t('navbar.navbarProfileBtn.yourGames')}</Typography>
            </MenuItem>
          )}
          {accountRolesIncludes(sessionState.tokenInfo.role, [
            EXTENDED_ROLES.MANAGER,
            EXTENDED_ROLES.CAPTAIN,
            EXTENDED_ROLES.COACH
          ]) && (
            <MenuItem
              onClick={() => handleAccountMenuNavigate('/submit-team-for-league-representative')}>
              <Typography textAlign="center">
                {t('menuItems.submit_team_representative')}
              </Typography>
            </MenuItem>
          )}

          <MenuItem onClick={() => handleAccountMenuNavigate('/manage-teams')}>
            <Typography textAlign="center">{t('menuItems.teams')}</Typography>
          </MenuItem>

          <MenuItem onClick={handleLogout}>
            <Typography textAlign="center">{t('navbar.navbarProfileBtn.logout')}</Typography>
          </MenuItem>
        </Menu>
      </Box>
    </div>
  );
};

export default NavabarProfileBtn;
