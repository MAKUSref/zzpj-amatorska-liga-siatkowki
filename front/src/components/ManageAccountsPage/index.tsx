import { useTranslation } from 'react-i18next';
import Breadcrumbs from '../Breadcrumbs';
import AccountsTable from './AccountsTable';

const ManageAccounts = () => {
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
            label: 'Manage Account',
            to: '/manage-accounts'
          }
        ]}
      />
      <h3>{t('manageAccountsPage.manageAccounts')}</h3>
      <AccountsTable />
    </>
  );
};

export default ManageAccounts;
