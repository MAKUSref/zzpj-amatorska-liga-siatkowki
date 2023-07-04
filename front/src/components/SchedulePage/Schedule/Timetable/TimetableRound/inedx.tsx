import { LinearProgress } from '@mui/material';
import { useEffect, useMemo, useState } from 'react';
import { Row } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import {
  useGetRoundByIdQuery,
  useLazyGetGamesByIdsQuery
} from '../../../../../features/api/apiSlice';
import { GameDTO } from '../../../../../features/api/types';
import GameCard from './GameCard';

interface TimetableRoundProps {
  roundId: string;
  index: number;
}

const TimetableRound = ({ roundId, index }: TimetableRoundProps) => {
  const { data: round } = useGetRoundByIdQuery(roundId);
  const [games, setGames] = useState<GameDTO[]>([]);

  const [getGames, { isLoading, isFetching }] = useLazyGetGamesByIdsQuery();

  const { t } = useTranslation();

  const sortedGames = useMemo(() => {
    return [...games].sort(
      (a, b) => new Date(a.startTime).getTime() - new Date(b.startTime).getTime()
    );
  }, [games]);

  useEffect(() => {
    if (round) {
      getGames(round.games).then((res) => setGames(res.data ?? []));
    }
  }, [round]);

  return (
    <Row>
      <h4>
        {t('timetable.round')} {index}
      </h4>
      {isLoading && isFetching && (
        <div className="px-5 my-4">
          <LinearProgress />
        </div>
      )}
      {sortedGames.map((game, i) => (
        <GameCard {...game} key={i} />
      ))}
    </Row>
  );
};

export default TimetableRound;
