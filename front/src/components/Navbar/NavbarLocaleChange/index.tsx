import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import TranslateIcon from '@mui/icons-material/Translate';
import { useEffect, useRef, useState } from 'react';
import { IconButton } from '@mui/material';
import { useTranslation } from 'react-i18next';
import { LANGS } from '../../../i18n';
import { useAppDispatch, useAppSelector } from '../../../features/hooks';
import { useChangeLocaleMutation, useGetSelfInfoQuery } from '../../../features/api/apiSlice';

const NavbarLocaleChange = () => {
  const [open, setOpen] = useState<boolean>(false);
  const sessionState = useAppSelector((state) => state.session);
  const { data: selfInfo } = useGetSelfInfoQuery();
  const { t } = useTranslation();
  const { i18n } = useTranslation();

  const buttonRef = useRef<any>(null);

  const [changeLocaleRequest] = useChangeLocaleMutation();
  const dispatch = useAppDispatch();

  const handleClick = () => {
    setOpen((prev) => !prev);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleChangeLocale = (lang: LANGS) => {
    i18n.changeLanguage(lang);
    handleClose();

    // if (sessionState.token) {
    //   changeLocaleRequest({
    //     id:
    //     locale: lang
    //   });
    // }
  };

  useEffect(() => {
    if (selfInfo) {
      i18n.changeLanguage(selfInfo.payload.locale);
    }
  }, [selfInfo]);

  return (
    <>
      <IconButton
        ref={buttonRef}
        className="ms-2"
        id="basic-button"
        aria-controls={open ? 'basic-menu' : undefined}
        aria-haspopup="true"
        aria-expanded={open ? 'true' : undefined}
        onClick={handleClick}>
        <TranslateIcon fontSize="small" className="text-white" />
      </IconButton>
      <Menu
        id="basic-menu"
        anchorEl={buttonRef.current}
        open={open}
        onClose={handleClose}
        MenuListProps={{
          'aria-labelledby': 'basic-button'
        }}>
        <MenuItem onClick={() => handleChangeLocale(LANGS.PL)}>
          <span className="me-2">🇵🇱</span> {t('navbar.navbarLocaleChange.polish')}
        </MenuItem>
        <MenuItem onClick={() => handleChangeLocale(LANGS.EN)}>
          <span className="me-2">🇬🇧</span> {t('navbar.navbarLocaleChange.english')}
        </MenuItem>
      </Menu>
    </>
  );
};

export default NavbarLocaleChange;
