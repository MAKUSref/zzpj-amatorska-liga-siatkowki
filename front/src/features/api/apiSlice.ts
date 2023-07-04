import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import { RootState } from '../store';
import {
  ChangeRoleRequest,
  EtagRequestBody,
  EtagResponseBody,
  RegisterRequest,
  ResetEmailRequest,
  ResetPasswordRequest,
  ROLES,
  AccountDTO,
  HistoryAccountDTO,
  ShowTeamDTO,
  SubmitTeamForLeagueRequest,
  TeamDTO,
  SimpleTeamDTO,
  GameDTO,
  EditGamesquadDTO,
  LeagueTeamDTO,
  LeagueDTO,
  CreateTeamRequest,
  VenueDTO,
  NewGameDateDTO,
  CreateTimetableRequest,
  TimetableDTO,
  LeagueWithScoreboardsDTO,
  AddManagerToTeamRequest,
  AddPlayerToTeamRequest,
  CreateScoreboardDTO,
  RefereeGameDTO,
  RoundDTO,
  RemovePlayerFromTeamRequest,
  removeManagerFromTeamRequest,
  AcceptScoreDTO
} from './types';
import { registerDataToRegisterRequestFull } from './utils';

// eslint-disable-next-line no-unused-vars
enum TAG {
  // eslint-disable-next-line no-unused-vars
  ACCOUNT = 'ACCOUNT',
  // eslint-disable-next-line no-unused-vars
  TEAMS = 'TEAMS'
}

const ACCOUNT_PATH = '/account';
const TEAM_PATH = '/team';
const GAME_PATH = '/game';
const LEAGUE_PATH = '/league';
const VENUES_PATH = '/venue';
const TIMETABLE_PATH = '/timetable';
const ROUND_PATH = '/round';

const CURRENT_SEASON = '2022/2023';

export const apiSlice = createApi({
  reducerPath: 'api',
  baseQuery: fetchBaseQuery({
    baseUrl: '/api',
    credentials: 'include',
    prepareHeaders: (headers, { getState }) => {
      headers.set('Content-Type', 'application/json');

      const session = (getState() as RootState).session;

      if (session.etag) {
        headers.set('If-Match', session.etag);
      }

      if (session.token) {
        // headers.set('Cookie', session.token);
      }

      return headers;
    }
  }),
  tagTypes: [TAG.ACCOUNT, TAG.TEAMS],
  endpoints(builder) {
    return {
      // this endpoint is going to be deleted
      getPing: builder.query<string, void>({
        query: () => '/account/ping'
      }),

      // account
      register: builder.mutation<unknown, RegisterRequest>({
        query: (data) => ({
          url: `${ACCOUNT_PATH}/register`,
          method: 'POST',
          body: registerDataToRegisterRequestFull(data),
          headers: {
            'If-Match': data.role
          }
        })
      }),

      login: builder.mutation<string, { login: string; password: string }>({
        query: (credentials) => ({
          url: '/auth',
          method: 'POST',
          body: credentials
        }),
        invalidatesTags: [TAG.ACCOUNT, TAG.TEAMS]
      }),

      // account
      requestEmailChange: builder.query<void, void>({
        query: () => `${ACCOUNT_PATH}/requestEmailChange`
      }),
      getSelfHistory: builder.query<HistoryAccountDTO[], void>({
        query: () => `${ACCOUNT_PATH}/getSelfAccountHistory`,
        providesTags: [TAG.ACCOUNT]
      }),
      getAccountHistory: builder.query<HistoryAccountDTO[], string>({
        query: (userId) => `${ACCOUNT_PATH}/getAccountHistory/${userId}`,
        providesTags: [TAG.ACCOUNT]
      }),
      getSelfInfo: builder.query<EtagResponseBody<AccountDTO>, void>({
        query: () => `${ACCOUNT_PATH}/getSelfAccountInfo`,
        providesTags: [TAG.ACCOUNT],
        transformResponse(apiResponse, meta) {
          return {
            payload: apiResponse as AccountDTO,
            etag: meta?.response?.headers.get('ETAG') as string
          };
        }
      }),
      getAllAccounts: builder.query<AccountDTO[], void>({
        query: () => `${ACCOUNT_PATH}`,
        providesTags: [TAG.ACCOUNT]
      }),
      getAccountByRole: builder.query<AccountDTO[], ROLES>({
        query: (role) => `${ACCOUNT_PATH}/${role.toLowerCase()}`,
        providesTags: [TAG.ACCOUNT]
      }),
      getAccountById: builder.query<EtagResponseBody<AccountDTO>, string>({
        query: (id) => `${ACCOUNT_PATH}/getAccountByUUID/${id}`,
        providesTags: [TAG.ACCOUNT],
        transformResponse: (apiResponse, meta) => ({
          payload: apiResponse as AccountDTO,
          etag: meta?.response?.headers.get('ETAG') as string
        })
      }),
      createAccount: builder.mutation<AccountDTO, RegisterRequest>({
        query: (data) => ({
          url: `${ACCOUNT_PATH}/createAccountAdmin`,
          method: 'POST',
          body: registerDataToRegisterRequestFull(data)
        }),
        invalidatesTags: [TAG.ACCOUNT]
      }),
      blockAccount: builder.mutation<void, string>({
        query: (accountId) => ({
          url: `${ACCOUNT_PATH}/block/${accountId}`,
          method: 'PATCH'
        }),
        invalidatesTags: [TAG.ACCOUNT]
      }),
      blockSelf: builder.mutation<void, void>({
        query: () => ({
          url: `${ACCOUNT_PATH}/block/self`,
          method: 'PATCH'
        }),
        invalidatesTags: [TAG.ACCOUNT]
      }),
      approveAccount: builder.mutation<void, string>({
        query: (accountId) => ({
          url: `${ACCOUNT_PATH}/approve/${accountId}`,
          method: 'PATCH'
        }),
        invalidatesTags: [TAG.ACCOUNT]
      }),
      unblockAccount: builder.mutation<void, string>({
        query: (accountId) => ({
          url: `${ACCOUNT_PATH}/unblock/${accountId}`,
          method: 'PATCH'
        }),
        invalidatesTags: [TAG.ACCOUNT]
      }),
      disapproveAccount: builder.mutation<void, string>({
        query: (accountId) => ({
          url: `${ACCOUNT_PATH}/disapprove/${accountId}`,
          method: 'PATCH'
        }),
        invalidatesTags: [TAG.ACCOUNT]
      }),
      editAccount: builder.mutation<void, EtagRequestBody<{ name: string; lastname: string }>>({
        query: (data) => ({
          url: `${ACCOUNT_PATH}/editAccount`,
          method: 'PATCH',
          body: data
        }),
        invalidatesTags: [TAG.ACCOUNT]
      }),
      editAccountAccount: builder.mutation<
        void,
        EtagRequestBody<{ name: string; lastname: string; login: string }>
      >({
        query: (data) => ({
          url: `${ACCOUNT_PATH}/editAccount/${data.login}`,
          method: 'PATCH',
          body: data,
          prepareHeaders: (headers: any) => {
            console.log('---RTK----', headers);

            return headers;
          }
        }),
        invalidatesTags: [TAG.ACCOUNT]
      }),
      changePassword: builder.mutation<
        void,
        EtagRequestBody<{ oldPassword: string; newPassword: string }>
      >({
        query: (data) => ({
          url: `${ACCOUNT_PATH}/changePassword`,
          method: 'PATCH',
          body: data
        }),
        invalidatesTags: [TAG.ACCOUNT]
      }),
      changeAccountPassword: builder.mutation<void, EtagRequestBody<{ newPassword: string }>>({
        query: (data) => ({
          url: `${ACCOUNT_PATH}/changeAccountPassword/${data.id}`,
          method: 'POST',
          body: data
        }),
        invalidatesTags: [TAG.ACCOUNT]
      }),
      requestResetPassword: builder.query<void, string>({
        query: (email) => ({
          url: `${ACCOUNT_PATH}/requestResetPassword/?email=${email}`,
          method: 'GET'
        })
      }),
      resetPassword: builder.mutation<void, ResetPasswordRequest>({
        query: (body) => ({
          url: `${ACCOUNT_PATH}/resetPassword`,
          method: 'POST',
          body
        })
      }),
      addRole: builder.mutation<void, EtagRequestBody<ChangeRoleRequest>>({
        query: (data) => ({
          url: `${ACCOUNT_PATH}/addRole`,
          method: 'PATCH',
          body: data
        }),
        invalidatesTags: [TAG.ACCOUNT]
      }),
      removeRole: builder.mutation<void, EtagRequestBody<ChangeRoleRequest>>({
        query: (data) => ({
          url: `${ACCOUNT_PATH}/removeRole`,
          method: 'PATCH',
          body: data
        }),
        invalidatesTags: [TAG.ACCOUNT]
      }),
      resetEmail: builder.mutation<void, ResetEmailRequest>({
        query: ({ newEmail, token }) => ({
          url: `${ACCOUNT_PATH}/changeEmail`,
          method: 'POST',
          body: { newEmail, token }
        })
      }),
      activate: builder.query<void, string>({
        query: (token) => ({
          url: `${ACCOUNT_PATH}/activate?token=${token}`,
          method: 'GET'
        })
      }),
      changeLocale: builder.mutation<void, EtagRequestBody<{ locale: string }>>({
        query: (data) => ({
          url: `${ACCOUNT_PATH}/changeLanguage`,
          method: 'PATCH',
          body: data
        }),
        invalidatesTags: [TAG.ACCOUNT]
      }),

      //league
      getTeamsRequests: builder.query<LeagueTeamDTO[], string>({
        query: (leagueId) => `${LEAGUE_PATH}/teamsRequests/${leagueId}`,
        providesTags: [TAG.TEAMS]
      }),
      getAllLeagues: builder.query<LeagueDTO[], void>({
        query: () => ({
          url: `${LEAGUE_PATH}`
        }),
        providesTags: [TAG.TEAMS]
      }),
      getCurrentSeasonLeagues: builder.query<LeagueDTO[], void>({
        query: () => ({
          url: `${LEAGUE_PATH}/getLeaguesBySeason`,
          params: {
            season: CURRENT_SEASON
          }
        }),
        providesTags: [TAG.TEAMS]
      }),
      getTeamsInLeague: builder.query<LeagueTeamDTO[], string>({
        query: (leagueId) => `${LEAGUE_PATH}/teamsInLeague/${leagueId}`,
        providesTags: [TAG.TEAMS]
      }),

      // teams
      createTeam: builder.mutation<unknown, CreateTeamRequest>({
        query: (data) => ({
          url: `${TEAM_PATH}/createTeam`,
          method: 'POST',
          body: data
        }),
        invalidatesTags: [TAG.TEAMS]
      }),
      submitTeamForLeague: builder.mutation<unknown, SubmitTeamForLeagueRequest>({
        query: (data) => ({
          url: `${TEAM_PATH}/submitTeamForLeague`,
          method: 'POST',
          body: data
        })
      }),
      submitOwnTeamForLeague: builder.mutation<unknown, SubmitTeamForLeagueRequest>({
        query: (data) => ({
          url: `${TEAM_PATH}/submitOwnTeam`,
          method: 'POST',
          body: data
        })
        // invalidatesTags: [TAG.TEAMS]
      }),
      getMyTeams: builder.query<ShowTeamDTO[], void>({
        query: () => `${TEAM_PATH}/getMyTeams`,
        providesTags: [TAG.TEAMS]
      }),
      getAllTeams: builder.query<SimpleTeamDTO[], void>({
        query: () => `${TEAM_PATH}/getAllTeams`,
        providesTags: [TAG.TEAMS]
      }),
      getAllNotSubmittedTeams: builder.query<ShowTeamDTO[], void>({
        query: () => `${TEAM_PATH}/getAllNotSubmittedTeams`,
        providesTags: [TAG.TEAMS]
      }),
      doesHaveATeam: builder.query<boolean, void>({
        query: () => `${TEAM_PATH}/doesHaveATeam`
      }),

      // timetable
      generateTimetable: builder.mutation<void, CreateTimetableRequest>({
        query: (data) => ({
          url: `${TIMETABLE_PATH}`,
          method: 'POST',
          body: data
        }),
        invalidatesTags: [TAG.TEAMS]
      }),

      getTimetables: builder.query<TimetableDTO[], void>({
        query: () => `${TIMETABLE_PATH}`,
        providesTags: [TAG.TEAMS]
      }),

      getTimetableById: builder.query<TimetableDTO, string>({
        query: (timetableId) => `${TIMETABLE_PATH}/${timetableId}`,
        providesTags: [TAG.TEAMS]
      }),

      // round
      getRoundById: builder.query<RoundDTO, string>({
        query: (roundId) => `${ROUND_PATH}/${roundId}`,
        providesTags: [TAG.TEAMS]
      }),

      // venues
      getAllVenues: builder.query<VenueDTO[], void>({
        query: () => `${VENUES_PATH}`
      }),

      //team
      getTeamWithRepresentatives: builder.query<TeamDTO, string>({
        query: (id) => `${TEAM_PATH}/getTeamWithRepresentatives/${id}`,
        providesTags: [TAG.TEAMS]
      }),
      getOwnTeam: builder.query<EtagResponseBody<TeamDTO>, void>({
        query: () => `${TEAM_PATH}/getMyTeams`,
        transformResponse(apiResponse, meta) {
          return {
            payload: apiResponse as TeamDTO,
            etag: meta?.response?.headers.get('ETAG') as string
          };
        },
        providesTags: [TAG.TEAMS]
      }),
      getOwnTeams: builder.query<SimpleTeamDTO[], void>({
        query: () => `${TEAM_PATH}/getMyTeams`,
        providesTags: [TAG.TEAMS]
      }),
      getTeamById: builder.query<TeamDTO, string>({
        query: (id) => `${TEAM_PATH}/getById/${id}`,
        providesTags: [TAG.TEAMS]
      }),
      getTeamByName: builder.query<TeamDTO, string>({
        query: (name) => `${TEAM_PATH}/getByTeamName/${name}`,
        providesTags: [TAG.TEAMS]
      }),
      declineTeamSubmission: builder.mutation<void, string>({
        query: (id) => ({
          url: `${TEAM_PATH}/declineSubmission/${id}`,
          method: 'POST'
        }),
        invalidatesTags: [TAG.TEAMS]
      }),
      acceptTeamSubmission: builder.mutation<void, string>({
        query: (id) => ({
          url: `${TEAM_PATH}/acceptSubmission/${id}`,
          method: 'POST'
        }),
        invalidatesTags: [TAG.TEAMS]
      }),
      addManagerToTeam: builder.mutation<void, EtagRequestBody<AddManagerToTeamRequest>>({
        query: (data) => ({
          url: `${TEAM_PATH}/addRepresentative`,
          method: 'POST',
          body: data
        }),
        invalidatesTags: [TAG.TEAMS]
      }),
      removeManagerFromTeam: builder.mutation<void, EtagRequestBody<removeManagerFromTeamRequest>>({
        query: (data) => ({
          url: `${TEAM_PATH}/removeManagerFromTeam`,
          method: 'POST',
          body: data
        }),
        invalidatesTags: [TAG.TEAMS]
      }),
      removeCaptainFromTeam: builder.mutation<void, EtagRequestBody<{}>>({
        query: (data) => ({
          url: `${TEAM_PATH}/removeCaptainFromTeam`,
          method: 'POST',
          body: data
        }),
        invalidatesTags: [TAG.TEAMS]
      }),
      removeCoachFromTeam: builder.mutation<void, EtagRequestBody<{}>>({
        query: (data) => ({
          url: `${TEAM_PATH}/removeCoachFromTeam`,
          method: 'POST',
          body: data
        }),
        invalidatesTags: [TAG.TEAMS]
      }),

      //game
      getGameById: builder.query<GameDTO, string>({
        query: (id) => `${GAME_PATH}/getGameById/${id}`,
        providesTags: [TAG.TEAMS]
      }),
      getGamesByTeam: builder.query<GameDTO[], string>({
        query: (id) => `${GAME_PATH}/getGamesByTeam/${id}`,
        providesTags: [TAG.TEAMS]
      }),
      getAllRefereeGames: builder.query<RefereeGameDTO[], void>({
        query: () => `${GAME_PATH}/getRefereeGames`,
        providesTags: [TAG.TEAMS]
      }),
      getGamesByIds: builder.query<GameDTO[], string[]>({
        query: (ids) => ({
          url: `${GAME_PATH}/getGameListByIdList/`,
          method: 'POST',
          body: ids
        }),
        providesTags: [TAG.TEAMS]
      }),
      editGamesquad: builder.mutation<void, EditGamesquadDTO>({
        query: (data) => ({
          url: `${GAME_PATH}/editGamesquad`,
          method: 'POST',
          body: data
        }),
        invalidatesTags: [TAG.TEAMS]
      }),
      postponeGame: builder.mutation<void, string>({
        query: (gameId) => ({
          url: `${GAME_PATH}/requestPostpone/${gameId}`,
          method: 'PATCH'
        }),
        invalidatesTags: [TAG.TEAMS]
      }),
      changeGameDate: builder.mutation<void, { gameId: string; newGameDateDTO: NewGameDateDTO }>({
        query: ({ gameId, newGameDateDTO }) => ({
          url: `${GAME_PATH}/changeGameDate/${gameId}`,
          method: 'POST',
          body: newGameDateDTO
        }),
        invalidatesTags: [TAG.TEAMS]
      }),
      addGameScore: builder.mutation<void, CreateScoreboardDTO>({
        query: (score) => ({
          url: `${GAME_PATH}/addGameScore`,
          method: 'POST',
          body: score
        }),
        invalidatesTags: [TAG.TEAMS]
      }),
      acceptGameScore: builder.mutation<void, { teamId: string; acceptScoreDTO: AcceptScoreDTO }>({
        query: ({ teamId, acceptScoreDTO }) => ({
          url: `${GAME_PATH}/acceptScore/${teamId}`,
          method: 'PATCH',
          body: acceptScoreDTO
        }),
        invalidatesTags: [TAG.TEAMS]
      }),
      declineGameScore: builder.mutation<void, { teamId: string; acceptScoreDTO: AcceptScoreDTO }>({
        query: ({ teamId, acceptScoreDTO }) => ({
          url: `${GAME_PATH}/declineScore/${teamId}`,
          method: 'PATCH',
          body: acceptScoreDTO
        }),
        invalidatesTags: [TAG.TEAMS]
      }),
      addRefereeToGame: builder.mutation<void, { gameId: string; refereeId: string }>({
        query: ({ gameId, refereeId }) => ({
          url: `${GAME_PATH}/${gameId}/addReferee`,
          method: 'PUT',
          body: { refereeId }
        })
      }),

      //scoreboard
      getAllScoreboards: builder.query<LeagueWithScoreboardsDTO[], void>({
        query: () => `${LEAGUE_PATH}/scoreboards`
      }),

      // player
      addPlayerToTeam: builder.mutation<void, AddPlayerToTeamRequest>({
        query: (data) => ({
          url: `player`,
          method: 'POST',
          body: data
        }),
        invalidatesTags: [TAG.TEAMS]
      }),
      removePlayerFromTeam: builder.mutation<void, RemovePlayerFromTeamRequest>({
        query: (data) => ({
          url: `player/removePlayer`,
          method: 'PATCH',
          body: data
        }),
        invalidatesTags: [TAG.TEAMS]
      })
    };
  }
});

export const {
  useGetPingQuery,

  // account
  useRegisterMutation,
  useLoginMutation,
  useLazyRequestResetPasswordQuery,
  useResetPasswordMutation,
  useResetEmailMutation,

  // account
  useLazyRequestEmailChangeQuery,
  useLazyActivateQuery,
  useGetSelfInfoQuery,
  useGetSelfHistoryQuery,
  useGetAccountHistoryQuery,
  useGetAllAccountsQuery,
  useGetAccountByRoleQuery,
  useGetAccountByIdQuery,
  useCreateAccountMutation,
  useBlockAccountMutation,
  useBlockSelfMutation,
  useApproveAccountMutation,
  useUnblockAccountMutation,
  useDisapproveAccountMutation,
  useEditAccountMutation,
  useEditAccountAccountMutation,
  useChangePasswordMutation,
  useChangeAccountPasswordMutation,
  useAddRoleMutation,
  useRemoveRoleMutation,

  //team
  useGetTeamWithRepresentativesQuery,
  useGetOwnTeamQuery,
  useGetOwnTeamsQuery,
  useGetTeamByIdQuery,
  useGetTeamByNameQuery,
  useCreateTeamMutation,
  useDeclineTeamSubmissionMutation,
  useAcceptTeamSubmissionMutation,
  useGetMyTeamsQuery,
  useGetAllTeamsQuery,
  useSubmitTeamForLeagueMutation,
  useSubmitOwnTeamForLeagueMutation,
  useGetAllNotSubmittedTeamsQuery,
  useLazyGetTeamByIdQuery,
  useDoesHaveATeamQuery,
  useAddManagerToTeamMutation,
  useRemoveManagerFromTeamMutation,
  useRemoveCaptainFromTeamMutation,
  useRemoveCoachFromTeamMutation,

  //game
  useLazyGetGameByIdQuery,
  useGetGameByIdQuery,
  useGetGamesByTeamQuery,
  useEditGamesquadMutation,
  useChangeLocaleMutation,
  useGetAllRefereeGamesQuery,
  useAddGameScoreMutation,
  useDeclineGameScoreMutation,
  useAcceptGameScoreMutation,

  //league
  useGetTeamsRequestsQuery,
  useLazyGetTeamsRequestsQuery,
  useGetAllLeaguesQuery,
  useGetCurrentSeasonLeaguesQuery,
  useGetTeamsInLeagueQuery,

  // timetable
  useGenerateTimetableMutation,
  useGetTimetablesQuery,
  useGetTimetableByIdQuery,

  // round
  useGetRoundByIdQuery,
  useLazyGetRoundByIdQuery,

  // venues
  useGetAllVenuesQuery,

  //games
  usePostponeGameMutation,
  useGetGamesByIdsQuery,
  useLazyGetGamesByIdsQuery,
  useChangeGameDateMutation,

  //scoreboards
  useGetAllScoreboardsQuery,

  // player
  useAddPlayerToTeamMutation,
  useRemovePlayerFromTeamMutation,
  useAddRefereeToGameMutation
} = apiSlice;
