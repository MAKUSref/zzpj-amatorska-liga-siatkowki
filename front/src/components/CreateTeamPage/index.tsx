import { Container } from 'react-bootstrap';
import Breadcrumbs from '../Breadcrumbs';
import CreateTeamForm from './CreateTeamForm';
import { useTranslation } from 'react-i18next';

const CreateTeamPage = () => {
  const { t } = useTranslation();

  return (
    <Container className="mt-4">
      <Breadcrumbs
        paths={[
          {
            label: 'Home',
            to: '/'
          },
          {
            label: t('create_team.create_team') as string,
            to: '/create-team'
          }
        ]}
      />
      <h3>{t('create_team.header')}</h3>
      <CreateTeamForm />
    </Container>
  );
};

export default CreateTeamPage;
