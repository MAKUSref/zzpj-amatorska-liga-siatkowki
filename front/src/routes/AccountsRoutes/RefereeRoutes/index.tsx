import DefaultContainer from '../../../components/DefaultContainer';
import RefereeGames from '../../../components/RefereeGames';
import LeaderboardPage from '../../../components/LeaderboardPage';
import NewsPage from '../../../components/NewsPage';
import ProfilePage from '../../../components/ProfilePage';
import SchedulePage from '../../../components/SchedulePage';
import { Pathnames } from '../../pathnames';
import { RouteType } from '../../types';
import ScoreEdition from '../../../components/RefereeGames/ScoreEdition';

const RefereeRoutes: RouteType[] = [
  {
    path: '',
    fullPath: Pathnames.home.fullPath,
    element: <NewsPage />
  },
  {
    ...Pathnames.leaderboard,
    element: <LeaderboardPage />
  },
  {
    ...Pathnames.schedule,
    element: <SchedulePage />
  },
  {
    ...Pathnames.profile,
    element: <ProfilePage />
  },
  {
    ...Pathnames.yourGames,
    element: <RefereeGames />
  },
  {
    ...Pathnames.addScore,
    element: <ScoreEdition />
  }
];

export default RefereeRoutes;
