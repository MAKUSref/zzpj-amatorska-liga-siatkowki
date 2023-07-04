import { Paths } from './types';

const PATHNAME_HOME = '/';
const PATHNAME_LEADERBOARD = 'leaderboard';
const PATHNAME_SCHEDULE = 'schedule';
const PATHNAME_PROFILE = 'profile';
const PATHNAME_MANAGE_ACCOUNTS = 'manage-accounts';
const PATHNAME_LOGIN = 'login';
const PATHNAME_REGISTER = 'register';
const PATHNAME_STEP_ONE = 'step-one';
const PATHNAME_STEP_TWO = 'step-two';
const PATHNAME_STEP_THREE = 'step-three';
const PATHNAME_REGISTER_SUCCESS = 'register-success';
const PATHNAME_RESET_PASSWORD = 'reset-password';
const PATHNAME_NEW_PASSWORD = 'new-password';
const PATHNAME_NEW_EMAIL = 'new-email';
const PATHNAME_ACTIVATE = 'activate';
const PATHNAME_MANAGE_TEAMS = 'manage-teams';
const PATHNAME_MANAGE_REQUESTS = 'manage-requests';
const PATHNAME_CREATE_TEAM = 'create-team';
const PATHNAME_SUBMIT_TEAM_FOR_LEAGUE_ADMIN = 'submit-team-for-league-admin';
const PATHNAME_SUBMIT_TEAM_FOR_LEAGUE_REPRESENTATIVE = 'submit-team-for-league-representative';
const PATHNAME_GENERATE_TIMETABLE = 'generate-timetable';
const PATHNAME_YOUR_GAMES = 'your-games';
const PATHNAME_YOUR_GAMES_ADD_SCORE = 'your-games-add-score';

export const Pathnames: Paths = {
  home: { path: PATHNAME_HOME, fullPath: PATHNAME_HOME },
  leaderboard: { path: PATHNAME_LEADERBOARD, fullPath: `/${PATHNAME_LEADERBOARD}` },
  schedule: { path: PATHNAME_SCHEDULE, fullPath: `/${PATHNAME_SCHEDULE}` },
  profile: { path: PATHNAME_PROFILE, fullPath: `/${PATHNAME_PROFILE}` },
  manageAccounts: { path: PATHNAME_MANAGE_ACCOUNTS, fullPath: `/${PATHNAME_MANAGE_ACCOUNTS}` },
  manageTeams: { path: PATHNAME_MANAGE_TEAMS, fullPath: `/${PATHNAME_MANAGE_TEAMS}` },
  login: { path: PATHNAME_LOGIN, fullPath: `/${PATHNAME_LOGIN}` },
  register: { path: PATHNAME_REGISTER, fullPath: `/${PATHNAME_REGISTER}` },
  registerStepOne: {
    path: PATHNAME_STEP_ONE,
    fullPath: `/${PATHNAME_REGISTER}/${PATHNAME_STEP_ONE}`
  },
  registerStepTwo: {
    path: PATHNAME_STEP_TWO,
    fullPath: `/${PATHNAME_REGISTER}/${PATHNAME_STEP_TWO}`
  },
  registerStepThree: {
    path: PATHNAME_STEP_THREE,
    fullPath: `/${PATHNAME_REGISTER}/${PATHNAME_STEP_THREE}`
  },
  registerSuccess: { path: PATHNAME_REGISTER_SUCCESS, fullPath: `/${PATHNAME_REGISTER_SUCCESS}` },
  resetPassword: { path: PATHNAME_RESET_PASSWORD, fullPath: `/${PATHNAME_RESET_PASSWORD}` },
  newPassword: { path: PATHNAME_NEW_PASSWORD, fullPath: `/${PATHNAME_NEW_PASSWORD}` },
  newEmail: { path: PATHNAME_NEW_EMAIL, fullPath: `/${PATHNAME_NEW_EMAIL}` },
  activate: { path: PATHNAME_ACTIVATE, fullPath: `/${PATHNAME_ACTIVATE}` },
  submitTeamForLeagueAsAdmin: {
    path: PATHNAME_SUBMIT_TEAM_FOR_LEAGUE_ADMIN,
    fullPath: `/${PATHNAME_SUBMIT_TEAM_FOR_LEAGUE_ADMIN}`
  },
  submitTeamForLeagueAsRepresentative: {
    path: PATHNAME_SUBMIT_TEAM_FOR_LEAGUE_REPRESENTATIVE,
    fullPath: `/${PATHNAME_SUBMIT_TEAM_FOR_LEAGUE_REPRESENTATIVE}`
  },
  manageRequests: { path: PATHNAME_MANAGE_REQUESTS, fullPath: `/${PATHNAME_MANAGE_REQUESTS}` },
  createTeam: { path: PATHNAME_CREATE_TEAM, fullPath: `/${PATHNAME_CREATE_TEAM}` },
  generateTimetable: {
    path: PATHNAME_GENERATE_TIMETABLE,
    fullPath: `/${PATHNAME_GENERATE_TIMETABLE}`
  },
  yourGames: {
    path: PATHNAME_YOUR_GAMES,
    fullPath: `/${PATHNAME_YOUR_GAMES}`
  },
  addScore: {
    path: PATHNAME_YOUR_GAMES_ADD_SCORE + '/:id',
    fullPath: `/${PATHNAME_YOUR_GAMES_ADD_SCORE}/:id`
  }
};
