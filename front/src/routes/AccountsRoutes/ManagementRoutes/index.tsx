import ManageTeams from '../../../components/ManageTeamsPage';
import CreateTeamPage from '../../../components/CreateTeamPage';
import ProfilePage from '../../../components/ProfilePage';
import { Pathnames } from '../../pathnames';
import { RouteType } from '../../types';
import TeamPage from '../../../components/TeamPage';
import SubmitTeamForLeagueAsRepresentativePage from '../../../components/SubmitTeamForLeagueAsRepresentativePage';

const ManagementRoutes: RouteType[] = [
  {
    ...Pathnames.profile,
    element: <ProfilePage />
  },
  {
    ...Pathnames.createTeam,
    element: <CreateTeamPage />
  },
  {
    path: `/${Pathnames.submitTeamForLeagueAsRepresentative.path}`,
    fullPath: `${Pathnames.submitTeamForLeagueAsRepresentative.fullPath}`,
    element: <SubmitTeamForLeagueAsRepresentativePage />
  },
  {
    ...Pathnames.manageTeams,
    element: <ManageTeams />
  },
  {
    path: `/${Pathnames.manageTeams.path}/:teamId`,
    fullPath: `${Pathnames.manageTeams.fullPath}/:teamId`,
    element: <TeamPage />
  }
];

export default ManagementRoutes;
