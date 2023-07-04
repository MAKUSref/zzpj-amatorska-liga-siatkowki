import { useTranslation } from 'react-i18next';
import { useGetAllScoreboardsQuery } from '../../features/api/apiSlice';
import ScoreTable from './ScoreTable';

const LeaderboardPage = () => {
  const { data: allScoreboards, refetch, isLoading, isFetching } = useGetAllScoreboardsQuery();
  const { t } = useTranslation();

  console.log(allScoreboards);

  return (
    <>
      {allScoreboards?.map((league, i) => {
        return (
          <div key={league.id} className="my-3">
            <h3>
              {t('leaderboard.league')} {league.leagueNumber}
            </h3>
            <ScoreTable teamScores={league} />
          </div>
        );
      })}
    </>
  );
};

export default LeaderboardPage;
