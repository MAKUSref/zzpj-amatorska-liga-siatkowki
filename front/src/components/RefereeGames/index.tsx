import { useTranslation } from 'react-i18next';
import GameTable from './GameTable';
import { useGetAllRefereeGamesQuery } from '../../features/api/apiSlice';
import { Table, TableBody, TableCell, TableHead, TableRow } from '@mui/material';

const RefereeGames = () => {
  const [t] = useTranslation();

  const { data: games } = useGetAllRefereeGamesQuery();

  return (
    <>
      <h3>{t('yourGames.title')}</h3>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>{t('yourGames.table.teamA')}</TableCell>
            <TableCell>{t('yourGames.table.teamB')}</TableCell>
            <TableCell>{t('yourGames.table.startTime')}</TableCell>
            <TableCell>{t('yourGames.table.endTime')}</TableCell>
            <TableCell></TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {games?.map((game) => {
            return <GameTable game={game} key={game.id} />;
          })}
        </TableBody>
      </Table>
    </>
  );
};

export default RefereeGames;
