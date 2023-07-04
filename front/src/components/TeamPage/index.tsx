import { Container } from 'react-bootstrap';
import Breadcrumbs from '../Breadcrumbs';
import GamesTable from './GameTable';
import { useTranslation } from 'react-i18next';

const Team = () => {
  const { t } = useTranslation();

  return (
    <Container className="mt-4">
      <Breadcrumbs
        paths={[
          {
            label: t('paths.home'),
            to: '/'
          },
          {
            label: t('manageTeamsPage.manage_teams_header'),
            to: '/manage-teams'
          },
          {
            label: t('teamPage.team_header'),
            to: '/:id'
          }
        ]}
      />
      <h3>{t('teamPage.team_header')}</h3>
      <GamesTable />
    </Container>
  );
};

export default Team;
