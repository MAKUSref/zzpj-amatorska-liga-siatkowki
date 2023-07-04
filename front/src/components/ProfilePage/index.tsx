import AccountInformation from './UserInformation';
import ChangeLocale from './ChangeLocale';
import PasswordChanging from './PasswordChanging';
import DeleteAccount from './DeleteAccount';
import ProfileAvatar from './ProfileAvatar';
import { Col, Row } from 'react-bootstrap';
import { useAppSelector } from '../../features/hooks';
import { EXTENDED_ROLES } from '../../features/api/types';
import Breadcrumbs from '../Breadcrumbs';
import { useTranslation } from 'react-i18next';

const ProfilePage = () => {
  const sessionState = useAppSelector((state) => state.session);
  const { t } = useTranslation();
  return (
    <>
      <Breadcrumbs
        paths={[
          {
            label: 'Home',
            to: '/'
          },
          {
            label: 'Profile',
            to: '/profile'
          }
        ]}
      />
      <h3>{t('profilePage.info.profile')}</h3>
      <Row>
        <Col sx={12} md={6}>
          <div>
            <ProfileAvatar />
          </div>
          <div>
            <AccountInformation />
          </div>
        </Col>
        <Col sx={12} md={6}>
          <div className="mt-4 mt-md-0">
            <ChangeLocale />
          </div>
          <div className="mt-4">
            <PasswordChanging />
          </div>
          {!sessionState.tokenInfo.role.includes(EXTENDED_ROLES.ADMIN) && (
            <div className="mt-4">
              <DeleteAccount />
            </div>
          )}
        </Col>
      </Row>
    </>
  );
};

export default ProfilePage;
