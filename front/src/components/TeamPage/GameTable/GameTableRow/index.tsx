import { Col, Row } from 'react-bootstrap';
import { ElementType, useCallback, useMemo, useRef, useState } from 'react';
import IconButton from '@mui/material/IconButton';
import InputLabel from '@mui/material/InputLabel';
import FormControl from '@mui/material/FormControl';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import OutlinedInput from '@mui/material/OutlinedInput';
import InputAdornment from '@mui/material/InputAdornment';
import SearchIcon from '@mui/icons-material/Search';
import Paper from '@mui/material/Paper';
import ClearIcon from '@mui/icons-material/Clear';
import {
  useAcceptGameScoreMutation,
  useChangeGameDateMutation,
  useDeclineGameScoreMutation,
  useGetGamesByTeamQuery,
  usePostponeGameMutation
} from '../../../../features/api/apiSlice';
import { Outlet, useParams } from 'react-router-dom';
import CachedIcon from '@mui/icons-material/Cached';
import {
  Button,
  ButtonGroup,
  Chip,
  CircularProgress,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Typography
} from '@mui/material';
import TransferList from '../../PlayerList';
import { EXTENDED_ROLES, GameDTO, ScoreDTO, ScoreDecision } from '../../../../features/api/types';
import { useAppSelector } from '../../../../features/hooks';
import { accountRolesIncludes } from '../../../../utils';
import { useTranslation } from 'react-i18next';
import { toast } from 'react-toastify';
import Switch from '@mui/material/Switch';
import PostponeDialog from '../../../PostponeDialog';
import dayjs, { Dayjs } from 'dayjs';
import { DateTimePicker } from '@mui/x-date-pickers';
import { NewGameDateDTO } from '../../../../features/api/types';
import AddManagerModal from '.././AddManagerModal';
import PlayersModal from '.././PlayersModal';
import { Tooltip } from 'react-tooltip';
import DoneIcon from '@mui/icons-material/Done';
import CloseIcon from '@mui/icons-material/Close';

interface GameTableRowProps {
  teamId: string;
  game: GameDTO;
  isFetching: boolean;
  refetch: () => void;
}

function GameTableRow({ teamId, game, isFetching, refetch }: GameTableRowProps) {
  const [openA, setOpenA] = useState(false);
  const [openB, setOpenB] = useState(false);

  const [acceptScore, { isLoading: acceptScoreLoading }] = useAcceptGameScoreMutation();
  const [declineScore, { isLoading: declineScoreLoading }] = useDeclineGameScoreMutation();

  const [selectedDateTime, setSelectedDateTime] = useState<Dayjs>(dayjs());
  const [postponeGame, { isLoading: postponeGameLoading }] = usePostponeGameMutation();
  const [changeGameDate, { isLoading: changeGamedateLoading }] = useChangeGameDateMutation();

  const sessionState = useAppSelector((state) => state.session);

  const { t } = useTranslation();

  const isAdmin: boolean = useMemo(
    () => accountRolesIncludes(sessionState.tokenInfo.role, [EXTENDED_ROLES.ADMIN]),
    [sessionState]
  );
  const isGuest: boolean = useMemo(
    () => accountRolesIncludes(sessionState.tokenInfo.role, [EXTENDED_ROLES.GUEST]),
    [sessionState]
  );
  const showGameScore = useCallback(
    (score: ScoreDTO) => {
      if (score != null) {
        let tooltipText = '';

        score.sets.forEach((s) => {
          tooltipText += `${s.teamAPoints} : ${s.teamAPoints}\n`;
        });

        return (
          <>
            <a data-tooltip-id="my-tooltip-children-multiline">
              {' '}
              {score.scoreboardPointsA} : {score.scoreboardPointsB}{' '}
            </a>
            <Tooltip id="my-tooltip-children-multiline">
              <div style={{ display: 'flex', flexDirection: 'column', justifyContent: 'center' }}>
                {score.sets.map((s, i) => (
                  <p key={i}>
                    {s.teamAPoints} : {s.teamAPoints}
                  </p>
                ))}
              </div>
            </Tooltip>
          </>
        );
      }
    },
    [game]
  );

  const showApprovement = useCallback(
    (game: GameDTO) => {
      console.log(game.score);
      if (game.score == null) {
        return;
      }
      if (
        (game.teamA.teamId === teamId && game.score.approvalTeamA == ScoreDecision.APPROVED) ||
        (game.teamB.teamId === teamId && game.score.approvalTeamB == ScoreDecision.APPROVED)
      ) {
        return <Chip icon={<DoneIcon />} color="success" />;
      } else if (
        (game.teamA.teamId === teamId && game.score.approvalTeamA == ScoreDecision.DECLINE) ||
        (game.teamB.teamId === teamId && game.score.approvalTeamB == ScoreDecision.DECLINE)
      ) {
        return <Chip icon={<CloseIcon />} color="error" />;
      } else if (
        (game.teamA.teamId === teamId && game.score.approvalTeamA === ScoreDecision.NONE) ||
        (game.teamB.teamId === teamId && game.score.approvalTeamB === ScoreDecision.NONE)
      ) {
        return acceptScoreLoading || declineScoreLoading || isFetching ? (
          <CircularProgress
            className="me-3 my-2"
            sx={{ width: '22px!important', height: '22px!important' }}
          />
        ) : (
          <ButtonGroup variant="outlined">
            <Button
              onClick={() => {
                handleAcceptScore(game.id, game.version);
              }}>
              <DoneIcon />
            </Button>
            <Button
              onClick={() => {
                handleDeclineScore(game.id, game.version);
              }}>
              <CloseIcon />
            </Button>
          </ButtonGroup>
        );
      }
    },
    [teamId, acceptScoreLoading, declineScoreLoading, game]
  );

  const handleDateTimeChange = (gameId: string, gameVersion: number) => {
    const formattedDateTime = dayjs(selectedDateTime, 'YYYY-MM-DD HH:mm:ss');

    const newGameDateDTO: NewGameDateDTO = {
      newDate: formattedDateTime.format('YYYY-MM-DD HH:mm:ss'),
      gameVersion: gameVersion
    };
    changeGameDate({ gameId, newGameDateDTO })
      .unwrap()
      .then(() => {
        toast.success(t('teamPage.toast.postponed'));
        refetch();
      })
      .catch((err) => {
        toast.error(t(err.data));
      });
  };

  const handleOpenA = () => setOpenA(true);
  const handleCloseA = () => setOpenA(false);
  const handleOpenB = () => setOpenB(true);
  const handleCloseB = () => setOpenB(false);

  const handleDeclineScore = (gameId: string, gameVersion: number) => {
    declineScore({ teamId: teamId || '', acceptScoreDTO: { gameId, gameVersion } })
      .unwrap()
      .then(() => {
        toast.success(t('teamPage.toast.declineScore'));
        refetch();
      })
      .catch((err) => {
        toast.error(t(err.data));
      });
  };

  const handleAcceptScore = (gameId: string, gameVersion: number) => {
    acceptScore({ teamId: teamId || '', acceptScoreDTO: { gameId, gameVersion } })
      .unwrap()
      .then(() => {
        toast.success(t('teamPage.toast.acceptScore'));
        refetch();
      })
      .catch((err) => {
        toast.error(t(err.data));
      });
  };

  return (
    <TableRow
      className={`${teamId === game.id ? 'bg-neutral-light' : ''} cursor-pointer`}
      hover
      sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
      <TableCell component="th" scope="row">
        {game.startTime}
      </TableCell>
      <TableCell component="th" scope="row">
        <Button
          variant="contained"
          type="submit"
          onClick={handleOpenA}
          disabled={!isAdmin && game.teamA.teamId === teamId && !isGuest}>
          {t('teamPage.gamesTable.teamA')}
        </Button>
        <Dialog
          open={openA}
          onClose={handleCloseA}
          aria-labelledby="modal-modal-title"
          aria-describedby="modal-modal-description">
          <TransferList
            teamId={game.teamA?.teamId}
            gamesquadId={game.teamA.id}
            gamesquad={game.teamA.players}
            isGuest={isGuest}
          />
        </Dialog>
      </TableCell>
      <TableCell component="th" scope="row">
        <Button
          variant="contained"
          type="submit"
          onClick={handleOpenB}
          disabled={!isAdmin && game.teamB.teamId === teamId && !isGuest}>
          {t('teamPage.gamesTable.teamB')}
        </Button>
        <Dialog
          open={openB}
          onClose={handleCloseB}
          aria-labelledby="modal-modal-title"
          aria-describedby="modal-modal-description">
          <TransferList
            teamId={game.teamB?.teamId}
            gamesquadId={game.teamB.id}
            gamesquad={game.teamB.players}
            isGuest={isGuest}
          />
        </Dialog>
      </TableCell>
      <TableCell component="th" scope="row">
        {game.venue?.address},{game.venue?.courtNumber}
      </TableCell>
      <TableCell component="th" scope="row">
        {game.referee}
      </TableCell>
      <TableCell component="th" scope="row">
        {game.queue}
      </TableCell>
      <TableCell component="th" scope="row">
        {!isGuest && game.score == null ? (
          <PostponeDialog
            gameId={game.id}
            postponed={game.postponed}
            postponer={game.postponingAccount}
          />
        ) : (
          showApprovement(game)
        )}
      </TableCell>
      <TableCell component="th" scope="row">
        {game.postponed ? (
          game.postponeDate !== '' ? (
            <p>
              {t('gamesPostponeCalendar.newGameDate')} {game.postponeDate.slice(0, -3)}
            </p>
          ) : (
            isAdmin &&
            game.postponeDate === '' && (
              <>
                <DateTimePicker
                  ampm={false}
                  defaultValue={dayjs()}
                  onChange={(newVal) => newVal && setSelectedDateTime(newVal)}
                  label={t('gamesPostponeCalendar.postponeDate')}
                />
                <Button
                  variant="contained"
                  type="submit"
                  onClick={() => {
                    handleDateTimeChange(game.id, game.version);
                  }}>
                  {t('gamesPostponeCalendar.confirm')}
                </Button>
              </>
            )
          )
        ) : (
          game.score != null && showGameScore(game.score)
        )}
      </TableCell>
    </TableRow>
  );
}

export default GameTableRow;
