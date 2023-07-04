import { Col, Row } from 'react-bootstrap';
import { useMemo, useRef, useState } from 'react';
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
import { useGetAllTeamsQuery } from '../../../features/api/apiSlice';
import { useNavigate } from 'react-router-dom';
// import DialogAddAccount from './DialogAddUser';
import CachedIcon from '@mui/icons-material/Cached';
import { CircularProgress } from '@mui/material';
import { Pathnames } from '../../../routes/pathnames';
import { useTranslation } from 'react-i18next';

const GuestTeamsTable = () => {
  const [searchInputVal, setSearchInputVal] = useState('');
  const { data: teamsRes, refetch, isLoading, isFetching } = useGetAllTeamsQuery();
  const { t } = useTranslation();

  const navigate = useNavigate();

  const inputSearchFormRef = useRef<HTMLFormElement>(null);

  const filteredTeams = useMemo(() => {
    return teamsRes?.filter((team) => {
      const fullName = `${team.teamName} ${team.city}`.toLowerCase();

      return fullName.includes(searchInputVal.toLowerCase());
    });
  }, [teamsRes, searchInputVal]);

  const handleClearSearchInput = () => {
    inputSearchFormRef.current?.reset();
    setSearchInputVal('');
  };

  return (
    <>
      <Row className="mt-5">
        <Col className="d-flex justify-content-between align-items-center">
          <div className="d-flex justify-content-between align-items-center">
            {isLoading || isFetching ? (
              <CircularProgress sx={{ width: '22px!important', height: '22px!important' }} />
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
          </div>
        </Col>
      </Row>
      <Row className="mt-4">
        <Col xs={12}>
          <TableContainer component={Paper}>
            <Table sx={{ minWidth: 650 }} aria-label="simple table">
              <TableHead>
                <TableRow>
                  <TableCell>{t('manageTeamsPage.AllTeamsTable.team_name')}</TableCell>
                  <TableCell>{t('manageTeamsPage.AllTeamsTable.team_city')}</TableCell>
                  <TableCell>{t('manageTeamsPage.AllTeamsTable.team_league')}</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {filteredTeams?.map((team, i) => (
                  <TableRow
                    className={`cursor-pointer`}
                    onClick={() => navigate(`${Pathnames.manageTeams.fullPath}/${team.teamId}`)}
                    hover
                    key={i}
                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
                    <TableCell component="th" scope="row">
                      {team.teamName}
                    </TableCell>
                    <TableCell component="th" scope="row">
                      {team.city}
                    </TableCell>
                    {team.leagueNumber}
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </Col>
      </Row>
    </>
  );
};

export default GuestTeamsTable;
