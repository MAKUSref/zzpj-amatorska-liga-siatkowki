import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import Menu from '@mui/material/Menu';
import MenuIcon from '@mui/icons-material/Menu';
import Container from '@mui/material/Container';
import Button from '@mui/material/Button';
import MenuItem from '@mui/material/MenuItem';
import SportsVolleyballIcon from '@mui/icons-material/SportsVolleyball';
import React, { useState } from 'react';
import NavbarProfileBtn from './NavbarProfileBtn';
import { EXTENDED_ROLES } from '../../features/api/types';
import { accountRolesIncludes } from '../../utils';
import { useAppSelector } from '../../features/hooks';
// import adminGif from '../../imgs/fireAdmin.gif';
import './style.scss';
import { NavLink } from 'react-router-dom';
import { PublicRoutes } from '../../routes/AccountsRoutes';
import NavbarLocaleChange from './NavbarLocaleChange';
import { useTranslation } from 'react-i18next';

const Navbar = () => {
  const [anchorElNav, setAnchorElNav] = useState<null | HTMLElement>(null);
  const sessionState = useAppSelector((state) => state.session);

  const { t } = useTranslation();

  const handleOpenNavMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorElNav(event.currentTarget);
  };

  const handleCloseNavMenu = () => {
    setAnchorElNav(null);
  };

  return (
    <AppBar
      className={
        accountRolesIncludes(sessionState.tokenInfo.role, [EXTENDED_ROLES.ADMIN])
          ? 'navbar-admin'
          : ''
      }
      position="static">
      <Container maxWidth="xl">
        <Toolbar disableGutters>
          <SportsVolleyballIcon sx={{ display: { xs: 'none', md: 'flex' }, mr: 1 }} />
          <Typography
            variant="h6"
            noWrap
            component="a"
            href="/"
            sx={{
              mr: 2,
              display: { xs: 'none', md: 'flex' },
              fontFamily: 'monospace',
              fontWeight: 700,
              letterSpacing: '.3rem',
              color: 'inherit',
              textDecoration: 'none'
            }}>
            ALS
          </Typography>
          <Box sx={{ flexGrow: 1, display: { xs: 'flex', md: 'none' } }}>
            <IconButton
              size="large"
              aria-label="account of current account"
              aria-controls="menu-appbar"
              aria-haspopup="true"
              onClick={handleOpenNavMenu}
              color="inherit">
              <MenuIcon />
            </IconButton>
            <Menu
              id="menu-appbar"
              anchorEl={anchorElNav}
              anchorOrigin={{
                vertical: 'bottom',
                horizontal: 'left'
              }}
              keepMounted
              transformOrigin={{
                vertical: 'top',
                horizontal: 'left'
              }}
              open={Boolean(anchorElNav)}
              onClose={handleCloseNavMenu}
              sx={{
                display: { xs: 'block', md: 'none' }
              }}>
              {PublicRoutes.map((route, i) => (
                <MenuItem key={i} onClick={handleCloseNavMenu}>
                  <NavLink to={route.fullPath} className="text-decoration-none">
                    <Typography textAlign="center">
                      {route.path === '' ? t('navbar.news') : t(route.path)}
                    </Typography>
                  </NavLink>
                </MenuItem>
              ))}
            </Menu>
          </Box>
          <SportsVolleyballIcon sx={{ display: { xs: 'flex', md: 'none' }, mr: 1 }} />
          <Typography
            variant="h5"
            noWrap
            component="a"
            href=""
            sx={{
              mr: 2,
              display: { xs: 'flex', md: 'none' },
              flexGrow: 1,
              fontFamily: 'monospace',
              fontWeight: 700,
              letterSpacing: '.3rem',
              color: 'inherit',
              textDecoration: 'none'
            }}>
            ALS
          </Typography>
          <Box sx={{ flexGrow: 1, display: { xs: 'none', md: 'flex' } }}>
            {PublicRoutes.map((route, i) => (
              <NavLink key={i} to={route.fullPath} className="text-decoration-none">
                <Button
                  onClick={handleCloseNavMenu}
                  sx={{ my: 2, color: 'white', display: 'block' }}>
                  {route.path === ''
                    ? t('navbar.news')
                    : route.path !== '/manage-teams/:teamId' && t(`navbar.${route.path}`)}
                </Button>
              </NavLink>
            ))}
          </Box>
          <NavbarProfileBtn />
          <NavbarLocaleChange />
        </Toolbar>
      </Container>
      {accountRolesIncludes(sessionState.tokenInfo.role, [EXTENDED_ROLES.ADMIN]) && (
        <Typography className="text-center administrator-label fw-bold py-3">
          {t('navbar.loggedAs')}
          {/* <img src={adminGif} /> */}
        </Typography>
      )}
    </AppBar>
  );
};

export default Navbar;
