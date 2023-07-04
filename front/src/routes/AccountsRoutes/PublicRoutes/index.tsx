import { RouteType } from '../../types';
import { Pathnames } from '../../pathnames';
import NewsPage from '../../../components/NewsPage';
import LeaderboardPage from '../../../components/LeaderboardPage';
import SchedulePage from '../../../components/SchedulePage';
import ManageTeams from '../../../components/ManageTeamsPage';
import TeamPage from '../../../components/TeamPage';

const PublicRoutes: RouteType[] = [
  {
    path: '',
    fullPath: Pathnames.home.fullPath,
    // element: <NewsPage />
    element: <LeaderboardPage />
  },
  // {
  //   ...Pathnames.leaderboard,
  //   element: <LeaderboardPage />
  // },
  {
    ...Pathnames.schedule,
    element: <SchedulePage />
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

export default PublicRoutes;
