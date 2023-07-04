import { Container } from 'react-bootstrap';
import Breadcrumbs from '../Breadcrumbs';
import { useAppSelector } from '../../features/hooks';
import { accountRolesIncludes } from '../../utils';
import GuestTeamsTable from './GuestTeamsTable';
import { EXTENDED_ROLES } from '../../features/api/types';
import AllTeamsTable from './TeamsTable';
import { useTranslation } from 'react-i18next';

const ManageTeams = () => {
  const { t } = useTranslation();
  const sessionState = useAppSelector((state) => state.session);

  return (
    <Container className="mt-4">
      <Breadcrumbs
        paths={[
          {
            label: 'Home',
            to: '/'
          },
          {
            label: t('manageTeamsPage.manage_teams_header'),
            to: '/manage-teams'
          }
        ]}
      />
      <h3>{t('manageTeamsPage.manage_teams_header')}</h3>
      {accountRolesIncludes(sessionState.tokenInfo.role, [EXTENDED_ROLES.GUEST]) ? (
        <GuestTeamsTable />
      ) : (
        <AllTeamsTable />
      )}
    </Container>
  );
};

export default ManageTeams;
