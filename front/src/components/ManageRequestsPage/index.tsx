import { Container } from 'react-bootstrap';
import Breadcrumbs from '../Breadcrumbs';
import RequestsTable from './RequestsTable';
import { useTranslation } from 'react-i18next';

const ManageRequests = () => {
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
            label: 'Manage Requests',
            to: '/manage-requests'
          }
        ]}
      />
      <h3>{t('manageRequestsPage.manageRequests')}</h3>
      <RequestsTable />
    </Container>
  );
};

export default ManageRequests;
