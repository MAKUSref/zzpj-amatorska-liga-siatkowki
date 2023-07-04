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
} from '../../../features/api/apiSlice';
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
import TransferList from '../PlayerList';
import { EXTENDED_ROLES, GameDTO, ScoreDTO, ScoreDecision } from '../../../features/api/types';
import { useAppSelector } from '../../../features/hooks';
import { accountRolesIncludes } from '../../../utils';
import { useTranslation } from 'react-i18next';
import { toast } from 'react-toastify';
import Switch from '@mui/material/Switch';
import PostponeDialog from '../../PostponeDialog';
import dayjs, { Dayjs } from 'dayjs';
import { DateTimePicker } from '@mui/x-date-pickers';
import { NewGameDateDTO } from '../../../features/api/types';
import AddManagerModal from './AddManagerModal';
import PlayersModal from './PlayersModal';
import { Tooltip } from 'react-tooltip';
import DoneIcon from '@mui/icons-material/Done';
import CloseIcon from '@mui/icons-material/Close';
import GameTableRow from './GameTableRow';

const GamesTable = () => {
  const { teamId } = useParams<string>();
  const [searchInputVal, setSearchInputVal] = useState('');

  const sessionState = useAppSelector((state) => state.session);
  const isAdmin: boolean = useMemo(
    () => accountRolesIncludes(sessionState.tokenInfo.role, [EXTENDED_ROLES.ADMIN]),
    [sessionState]
  );
  const isGuest: boolean = useMemo(
    () => accountRolesIncludes(sessionState.tokenInfo.role, [EXTENDED_ROLES.GUEST]),
    [sessionState]
  );
  const { t } = useTranslation();

  const { data: gamesRes, refetch, isLoading, isFetching } = useGetGamesByTeamQuery(teamId!);

  const inputSearchFormRef = useRef<HTMLFormElement>(null);

  const filteredTeams = useMemo(() => {
    return gamesRes?.filter((game) => {
      const fullName = `${game.referee} ${game.queue}`.toLowerCase();

      return fullName.includes(searchInputVal.toLowerCase());
    });
  }, [gamesRes, searchInputVal]);

  const handleClearSearchInput = () => {
    inputSearchFormRef.current?.reset();
    setSearchInputVal('');
  };

  console.log(gamesRes);

  return (
    <>
      <Row className="mt-5">
        <Col className="d-flex justify-content-between align-items-center">
          <div className="d-flex justify-content-between align-items-center">
            {isLoading || isFetching ? (
              <CircularProgress />
            ) : (
              <CachedIcon className="text-muted mx-2 cursor-pointer" onClick={() => refetch()} />
            )}
            <form ref={inputSearchFormRef}>
              <FormControl size="small" sx={{ m: 1 }}>
                <InputLabel htmlFor="outlined-adornment-amount">{t('defaults.search')}</InputLabel>
                <OutlinedInput
                  id="outlined-adornment-amount"
                  onChange={(e) => setSearchInputVal(e.target.value)}
                  endAdornment={
                    <InputAdornment position="end">
                      <IconButton onClick={handleClearSearchInput} edge="end" size="small">
                        <ClearIcon />
                      </IconButton>
                    </InputAdornment>
                  }
                  startAdornment={
                    <InputAdornment position="start">
                      <SearchIcon />
                    </InputAdornment>
                  }
                  label="Amount"
                />
              </FormControl>
            </form>
            {!isGuest && (
              <>
                <AddManagerModal />
                <PlayersModal />
              </>
            )}
          </div>
        </Col>
      </Row>
      <Row className="mt-4">
        <Col xs={12}>
          <TableContainer component={Paper}>
            <Table sx={{ minWidth: 650 }} aria-label="simple table">
              <TableHead>
                <TableRow>
                  <TableCell>{t('teamPage.gamesTable.date')}</TableCell>
                  <TableCell>{t('teamPage.gamesTable.teamA')}</TableCell>
                  <TableCell>{t('teamPage.gamesTable.teamB')}</TableCell>
                  <TableCell>{t('teamPage.gamesTable.venue')}</TableCell>
                  <TableCell>{t('teamPage.gamesTable.referee')}</TableCell>
                  <TableCell>{t('teamPage.gamesTable.queue')}</TableCell>
                  <TableCell></TableCell>
                  <TableCell></TableCell>
                  <TableCell></TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {filteredTeams?.map((game, i) => (
                  <GameTableRow
                    key={i}
                    game={game}
                    teamId={teamId!}
                    isFetching={isFetching}
                    refetch={refetch}
                  />
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </Col>
        <Outlet />
      </Row>
    </>
  );
};

export default GamesTable;
