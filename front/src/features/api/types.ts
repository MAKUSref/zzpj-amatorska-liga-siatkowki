/* eslint-disable no-unused-vars */
export enum MANAGEMENT_ROLES {
  MANAGER = 'MANAGER',
  CAPTAIN = 'CAPTAIN',
  COACH = 'COACH'
}

export enum ROLES {
  MANAGER = 'MANAGER',
  CAPTAIN = 'CAPTAIN',
  COACH = 'COACH',
  REFREE = 'REFEREE',
  ADMIN = 'ADMIN'
}

export enum EXTENDED_ROLES {
  MANAGER = 'MANAGER',
  CAPTAIN = 'CAPTAIN',
  COACH = 'COACH',
  REFREE = 'REFEREE',
  ADMIN = 'ADMIN',
  GUEST = 'GUEST'
}

export enum GAME_STATES {
  POSTPONED = 'POSTPONED',
  ENDED = 'ENDED',
  NOT_PLAYED = 'NOT_PLAYED'
}

interface AbstractDTO {
  id: string;
  version: number;
}

export interface AccountDTO {
  // to AccountDto
  version?: number;
  id?: string;
  login?: string;
  name?: string;
  lastname?: string;
  email?: string;
  active?: boolean;
  approved?: boolean;
  blocked?: boolean;
  loginTimestamp?: string;
  locale?: string;
  roles?: RoleDTO[];
  payload?: string;
}

export interface RoleDTO {
  id?: string;
  version?: number;
  role?: string;
}

export interface RegisterRequest {
  login?: string;
  email?: string;
  password?: string;
  name?: string;
  lastname?: string;
  locale?: string;
  role?: MANAGEMENT_ROLES | ROLES;
}

export interface ResetPasswordRequest {
  newPassword: string;
  token: string;
}

export interface ResetEmailRequest {
  newEmail: string;
  token: string;
}

export interface RegisterRequestFull {
  accountData: AccountDTO;
  password?: string;
  role: RoleDTO;
}

export interface ChangeRoleRequest {
  login: string;
  role: { role: ROLES; teamId: null };
}

export type EtagRequestBody<T> = {
  id: string;
  version: number;
} & T;

export type EtagResponseBody<T> = {
  payload: T;
  etag: string;
};

export interface HistoryAccountDTO extends AbstractDTO {
  changeType: string;
  changedAccount: AccountDTO;
  changedAt: string;
  changedBy: AccountDTO;
  property: string;
}

//league
export interface LeagueDTO extends AbstractDTO {
  leagueNumber: number;
  season: string;
}

//team
export interface TeamRoleDTO extends AbstractDTO {
  role: string;
  firstName: string;
  lastName: string;
}
export interface LeagueTeamDTO extends AbstractDTO {
  teamName: string;
  city: string;
  coach?: TeamRoleDTO;
  captain?: TeamRoleDTO;
  managers?: TeamRoleDTO[];
}

export interface CreateTeamRequest {
  teamName: string;
  city: string;
}

export interface SubmitTeamForLeagueRequest {
  teamId?: string;
  leagueId?: string;
}

export interface SubmitOwnTeamForLeagueRequest {
  teamId?: string;
}

export interface ShowTeamDTO {
  id: string;
  teamName: string;
  city: string;
  inLeague?: boolean;
  approved?: boolean;
  manager?: string;
  captain?: string;
  coach?: string;
}

export interface TeamRepresentativeDTO {
  role?: string;
  firstName?: string;
  lastName?: string;
  accountId?: string;
}

export interface TeamDTO {
  teamVersion?: number;
  id?: string;
  version?: number;
  teamName?: string;
  city?: string;
  players: PlayerDTO[];
  captain: TeamRepresentativeDTO;
  managers: TeamRepresentativeDTO[];
  coach: TeamRepresentativeDTO;
}

export interface SimpleTeamDTO {
  id?: string;
  teamId: string;
  version?: number;
  teamName?: string;
  city?: string;
  inLeague: boolean;
  approved?: boolean;
  leagueNumber: number;
}

export interface VenueDTO {
  version: number;
  id: string;
  address: string;
  courtNumber: number;
}

export interface CreateTimetableRequest {
  leagues: string[];
  venues: string[];
  config: {
    startDate: string;
    endDate: string;
  };
}

export interface TimetableDTO extends AbstractDTO {
  endDate: string;
  startDate: string;
  roundList: string[];
}

export interface RoundDTO extends AbstractDTO {
  games: string[];
  leagues: string[];
  roundNumber: number;
}

export interface GameSquadDTO {
  id: string;
  version: number;
  players: PlayerDTO[];
  teamId: string;
}

export enum ScoreDecision {
  APPROVED = 'APPROVED',
  DECLINE = 'DECLINE',
  NONE = 'NONE'
}

export interface SetDTO {
  id: string;
  teamAPoints: number;
  teamBPoints: number;
}

export interface ScoreDTO {
  scoreboardPointsA: number;
  scoreboardPointsB: number;
  approvalTeamA: ScoreDecision;
  approvalTeamB: ScoreDecision;
  sets: SetDTO[];
  version: number;
}

export interface PlayerDTO {
  id: string;
  version: number;
  name: string;
  lastName: string;
  age: number;
  isPro: boolean;
}

export interface GameDTO {
  id: string;
  version: number;
  venue: VenueDTO;
  teamA: GameSquadDTO;
  teamB: GameSquadDTO;
  referee: string;
  score: ScoreDTO;
  startTime: string;
  endTime: string;
  queue: number;
  postponed: boolean;
  postponingAccount: string;
  postponeDate: string;
}

export interface RefereeGameDTO {
  teamA: string;
  teamB: string;
  startTime: string;
  version: number;
  queue: number;
  endTime: string;
  scored: boolean;
  id: string;
}

export interface NewGameDateDTO {
  newDate: string;
  gameVersion: number;
}

export interface EditGamesquadDTO {
  playerIds: string[];
  gamesquadId: string;
}

export interface LeagueWithScoreboardsDTO {
  id: string;
  leagueNumber: number;
  rounds: RoundWithScoreboardsDTO[];
}

export interface RoundWithScoreboardsDTO {
  id: string;
  roundNumber: number;
  scoreboards: ScoreboardDTO;
}

export interface ScoreboardDTO {
  id: string;
  roundId: string;
  teamScores: OverallTeamScoreDTO[];
}

export interface OverallTeamScoreDTO {
  team: string;
  points: number;
  wonGames: number;
  lostGames: number;
  wonSets: number;
  lostSets: number;
}

export interface AddManagerToTeamRequest {
  roleDTO: {
    roleType: string;
    login: string;
  };
}

export interface removeManagerFromTeamRequest {
  accountId: string;
}

export interface AddPlayerToTeamRequest {
  firstName: string;
  lastName: string;
  age: number;
  isPro: boolean;
  teamDTO: {
    teamId: string;
    teamVersion: number;
  };
}

export interface RemovePlayerFromTeamRequest {
  playerId: string;
  teamId: string;
}

export interface CreateScoreboardDTO {
  sets: {
    teamAPoints: number;
    teamBPoints: number;
  }[];
  gameId: string;
  scoreboardPointsA: number;
  scoreboardPointsB: number;
}

export interface AcceptScoreDTO {
  gameId: string;
  gameVersion: number;
}
