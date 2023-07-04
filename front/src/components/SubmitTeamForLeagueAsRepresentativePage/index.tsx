import { useTranslation } from 'react-i18next';
import { Pathnames } from '../../routes/pathnames';
import Breadcrumbs from '../Breadcrumbs';
import SubmitTeamForLeagueAsRepresentative from './SubmitTeamForLeagueAsRepresentative';

const SubmitTeamForLeagueAsRepresentativePage = () => {
  const { t } = useTranslation();

  return (
    <>
      <Breadcrumbs
        paths={[
          {
            label: 'Home',
            to: Pathnames.home.fullPath
          },
          {
            label: t('submit_team.submit_team') as string,
            to: Pathnames.submitTeamForLeagueAsRepresentative.fullPath
          }
        ]}
      />
      <h3>{t('submit_team.header')}</h3>
      <SubmitTeamForLeagueAsRepresentative />
    </>
  );
};

export default SubmitTeamForLeagueAsRepresentativePage;
