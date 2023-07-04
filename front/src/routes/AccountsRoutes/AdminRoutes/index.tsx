import { Col } from 'react-bootstrap';
import ManageAccounts from '../../../components/ManageAccountsPage';
import AccountCard from '../../../components/ManageAccountsPage/AccountCard';
import ProfilePage from '../../../components/ProfilePage';
import { Pathnames } from '../../pathnames';
import { RouteType } from '../../types';

import ManageRequests from '../../../components/ManageRequestsPage';
import CreateTeamPage from '../../../components/CreateTeamPage';
import TimeTableGeneration from '../../../components/SchedulePage/TimeTableGeneration';
import SubmitTeamForLeagueAsAdminPage from '../../../components/SubmitTeamForLeaguePage';

const AdminRoutes: RouteType[] = [
  {
    ...Pathnames.profile,
    element: <ProfilePage />
  },
  {
    ...Pathnames.manageAccounts,
    element: <ManageAccounts />,
    children: [
      {
        path: `/${Pathnames.manageAccounts.path}/:accountId`,
        fullPath: `${Pathnames.manageAccounts.fullPath}/:accountId`,
        element: (
          <Col xs={3}>
            <AccountCard />
          </Col>
        )
      }
    ]
  },
  {
    ...Pathnames.manageRequests,
    element: <ManageRequests />
  },
  {
    ...Pathnames.createTeam,
    element: <CreateTeamPage />
  },
  {
    path: `/${Pathnames.submitTeamForLeagueAsAdmin.path}`,
    fullPath: `${Pathnames.submitTeamForLeagueAsAdmin.fullPath}`,
    element: <SubmitTeamForLeagueAsAdminPage />
  },
  {
    ...Pathnames.generateTimetable,
    element: <TimeTableGeneration />
  }
];

export default AdminRoutes;
