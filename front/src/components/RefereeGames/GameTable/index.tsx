import { Button, TableCell, TableRow } from '@mui/material';
import { RefereeGameDTO } from '../../../features/api/types';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { Pathnames } from '../../../routes/pathnames';

const GameTable = ({ game }: { game: RefereeGameDTO }) => {
  const [t] = useTranslation();
  const navigate = useNavigate();

  return (
    <TableRow>
      <TableCell>{game.teamA}</TableCell>
      <TableCell>{game.teamB}</TableCell>
      <TableCell>{game.startTime}</TableCell>
      <TableCell>{game.endTime ?? ''}</TableCell>
      <TableCell>
        {game.scored && (
          <Button
            onClick={() =>
              navigate(Pathnames.addScore.fullPath.split(':')[0] + game.id, {
                state: {
                  id: game.id
                }
              })
            }>
            {t('yourGames.table.makeResault')}
          </Button>
        )}
      </TableCell>
    </TableRow>
  );
};

export default GameTable;
