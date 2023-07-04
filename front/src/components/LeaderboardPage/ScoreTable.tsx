import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import {
  LeagueWithScoreboardsDTO,
  OverallTeamScoreDTO,
  RoundWithScoreboardsDTO
} from '../../features/api/types';
import { useTranslation } from 'react-i18next';
import { useGetTeamsInLeagueQuery } from '../../features/api/apiSlice';

interface ScoreTableProps {
  teamScores: LeagueWithScoreboardsDTO;
}

function a11yProps(index: number) {
  return {
    id: `simple-tab-${index}`,
    'aria-controls': `simple-tabpanel-${index}`
  };
}

type CalculateScoreFunction = (rounds: RoundWithScoreboardsDTO[]) => OverallTeamScoreDTO[];

const calculateAll: CalculateScoreFunction = (rounds: RoundWithScoreboardsDTO[]) => {
  const all: OverallTeamScoreDTO[] = [];
  const teams = new Set<string>();
  rounds.forEach((round) => {
    round.scoreboards.teamScores.forEach((score) => {
      teams.add(score.team);
    });
  });

  teams.forEach((team) => {
    let teamScore: OverallTeamScoreDTO = {
      team: team,
      points: 0,
      wonGames: 0,
      lostGames: 0,
      wonSets: 0,
      lostSets: 0
    };
    rounds.forEach((round) => {
      const roundScore = round.scoreboards.teamScores.filter((score) => score.team === team)[0];
      if (roundScore) {
        teamScore.points += roundScore.points;
        teamScore.wonGames += roundScore.wonGames;
        teamScore.lostGames += roundScore.lostGames;
        teamScore.wonSets += roundScore.wonSets;
        teamScore.lostSets += roundScore.lostSets;
      }
    });
    all.push(teamScore);
  });
  return all;
};

const ScoreTable = ({ teamScores }: ScoreTableProps) => {
  const [value, setValue] = React.useState(0);

  const [totalScore, setTotalScore] = React.useState<OverallTeamScoreDTO[]>([]);

  const {
    data: allTeams,
    refetch,
    isLoading,
    isFetching
  } = useGetTeamsInLeagueQuery(teamScores.id);

  const { t } = useTranslation();

  const handleChange = (event: React.SyntheticEvent, newValue: number) => {
    console.log(allTeams);

    setValue(newValue);
  };

  React.useEffect(() => {
    setTotalScore(calculateAll(teamScores.rounds));
  }, [teamScores]);

  React.useEffect(() => {
    console.log(teamScores);
    console.log(allTeams);
  }, []);

  const getRoundScores = React.useCallback(
    (roundId: string | null = null) => {
      const all: OverallTeamScoreDTO[] = [];

      allTeams?.forEach((team) => {
        console.log(team.teamName);

        let teamScore: OverallTeamScoreDTO = {
          team: team.id,
          points: 0,
          wonGames: 0,
          lostGames: 0,
          wonSets: 0,
          lostSets: 0
        };

        console.log(teamScore);

        teamScores.rounds
          .filter((r) => r.id === roundId || !roundId)
          .forEach((round) => {
            const roundScore = round.scoreboards.teamScores.filter(
              (score) => score.team === team.id
            )[0];
            if (roundScore) {
              teamScore.points += roundScore.points;
              teamScore.wonGames += roundScore.wonGames;
              teamScore.lostGames += roundScore.lostGames;
              teamScore.wonSets += roundScore.wonSets;
              teamScore.lostSets += roundScore.lostSets;
            }
          });
        all.push(teamScore);
      });
      return all;
    },
    [teamScores, allTeams]
  );

  return (
    <>
      <Tabs
        value={value}
        onChange={handleChange}
        textColor="secondary"
        indicatorColor="secondary"
        aria-label="secondary tabs example">
        <Tab label={t('leaderboard.all')} {...a11yProps(0)} />
        {teamScores.rounds.map((round, i) => (
          <Tab label={`${t('leaderboard.round')} ${round.roundNumber}`} {...a11yProps(i)} key={i} />
        ))}
      </Tabs>

      {value === 0 ? (
        <TableContainer component={Paper}>
          <Table sx={{ minWidth: 650 }} aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell>{t('leaderboard.table.team')}</TableCell>
                <TableCell align="right">{t('leaderboard.table.points')}</TableCell>
                <TableCell align="right">{t('leaderboard.table.games_won')}</TableCell>
                <TableCell align="right">{t('leaderboard.table.games_lost')}</TableCell>
                <TableCell align="right">{t('leaderboard.table.sets_won')}</TableCell>
                <TableCell align="right">{t('leaderboard.table.sets_lost')}</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {getRoundScores()
                .sort((score1, score2) => {
                  console.log(score2.points - score1.points);

                  return score2.points - score1.points;
                })
                .map((row) => (
                  <TableRow
                    key={row.team}
                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
                    <TableCell component="th" scope="row">
                      {allTeams?.filter((t) => t.id === row.team)[0].teamName}
                    </TableCell>
                    <TableCell align="right">{row.points}</TableCell>
                    <TableCell align="right">{row.wonGames}</TableCell>
                    <TableCell align="right">{row.lostGames}</TableCell>
                    <TableCell align="right">{row.wonSets}</TableCell>
                    <TableCell align="right">{row.lostSets}</TableCell>
                  </TableRow>
                ))}
            </TableBody>
          </Table>
        </TableContainer>
      ) : (
        <></>
      )}

      {teamScores.rounds
        .filter((round) => round.roundNumber === value)
        .map((round) => (
          <TableContainer component={Paper} key={round.id}>
            <Table sx={{ minWidth: 650 }} aria-label="simple table">
              <TableHead>
                <TableRow>
                  <TableCell>{t('leaderboard.table.team')}</TableCell>
                  <TableCell align="right">{t('leaderboard.table.points')}</TableCell>
                  <TableCell align="right">{t('leaderboard.table.games_won')}</TableCell>
                  <TableCell align="right">{t('leaderboard.table.games_lost')}</TableCell>
                  <TableCell align="right">{t('leaderboard.table.sets_won')}</TableCell>
                  <TableCell align="right">{t('leaderboard.table.sets_lost')}</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {getRoundScores(round.id)
                  .sort((score1, score2) => {
                    console.log(score2.points - score1.points);

                    return score2.points - score1.points;
                  })
                  .map((row) => (
                    <TableRow
                      key={row.team}
                      sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
                      <TableCell component="th" scope="row">
                        {allTeams?.filter((t) => t.id === row.team)[0].teamName}
                      </TableCell>
                      <TableCell align="right">{row.points}</TableCell>
                      <TableCell align="right">{row.wonGames}</TableCell>
                      <TableCell align="right">{row.lostGames}</TableCell>
                      <TableCell align="right">{row.wonSets}</TableCell>
                      <TableCell align="right">{row.lostSets}</TableCell>
                    </TableRow>
                  ))}
              </TableBody>
            </Table>
          </TableContainer>
        ))}
    </>
  );
};

export default ScoreTable;
