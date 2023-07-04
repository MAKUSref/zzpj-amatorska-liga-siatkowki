import { Col, Row } from 'react-bootstrap';
import MenuItem from '@mui/material/MenuItem';
import Select, { SelectChangeEvent } from '@mui/material/Select';
import { useMemo, useRef, useState } from 'react';
import { ROLES } from '../../../features/api/types';
import IconButton from '@mui/material/IconButton';
import InputLabel from '@mui/material/InputLabel';
import FormControl from '@mui/material/FormControl';
import Chip from '@mui/material/Chip';
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
import { useGetAccountByRoleQuery } from '../../../features/api/apiSlice';
import { Outlet, useNavigate, useParams } from 'react-router-dom';
import DialogAddAccount from './DialogAddUser';
import CachedIcon from '@mui/icons-material/Cached';
import { CircularProgress } from '@mui/material';
import { useTranslation } from 'react-i18next';

const AccountsTable = () => {
  const { accountId } = useParams();
  const [currentRole, setCurrentRole] = useState<ROLES>(ROLES.MANAGER);
  const [searchInputVal, setSearchInputVal] = useState('');
  const { t } = useTranslation();
  const {
    data: accountsRes,
    refetch,
    isLoading,
    isFetching
  } = useGetAccountByRoleQuery(currentRole);

  const navigate = useNavigate();

  const inputSearchFormRef = useRef<HTMLFormElement>(null);

  const filteredAccounts = useMemo(() => {
    return accountsRes?.filter((account) => {
      const fullName = `${account.name} ${account.lastname}`.toLowerCase();

      return fullName.includes(searchInputVal.toLowerCase());
    });
  }, [accountsRes, searchInputVal]);

  const handleChangeRole = (event: SelectChangeEvent) => {
    setCurrentRole(event.target.value as ROLES);
  };

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
                <InputLabel htmlFor="outlined-adornment-amount">
                  {t('manageAccountsPage.accountsTable.search')}
                </InputLabel>
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

            <p className="m-0 p-0 mx-3">{t('manageAccountsPage.accountsTable.choseRole')}</p>
            <FormControl size="small">
              <InputLabel id="demo-simple-select-label">
                {t('manageAccountsPage.accountsTable.role')}
              </InputLabel>
              <Select
                labelId="demo-simple-select-label"
                value={currentRole}
                label="Role"
                onChange={handleChangeRole}>
                <MenuItem value={ROLES.MANAGER}>
                  {t('manageAccountsPage.accountsTable.dialogAddUser.manager')}
                </MenuItem>
                <MenuItem value={ROLES.CAPTAIN}>
                  {t('manageAccountsPage.accountsTable.dialogAddUser.captain')}
                </MenuItem>
                <MenuItem value={ROLES.COACH}>
                  {t('manageAccountsPage.accountsTable.dialogAddUser.coach')}
                </MenuItem>
                <MenuItem value={ROLES.ADMIN}>Administrator</MenuItem>
                <MenuItem value={ROLES.REFREE}>
                  {t('manageAccountsPage.accountsTable.dialogAddUser.referee')}
                </MenuItem>
              </Select>
            </FormControl>
          </div>
          <div>
            <DialogAddAccount />
          </div>
        </Col>
      </Row>
      <Row className="mt-4">
        <Col xs={accountId ? 9 : 12}>
          <TableContainer component={Paper}>
            <Table sx={{ minWidth: 650 }} aria-label="simple table">
              <TableHead>
                <TableRow>
                  <TableCell>{t('manageAccountsPage.accountsTable.name')}</TableCell>
                  <TableCell align="right">
                    {t('manageAccountsPage.accountsTable.lastName')}
                  </TableCell>
                  <TableCell align="right">{t('manageAccountsPage.accountsTable.email')}</TableCell>
                  <TableCell align="right">
                    {t('manageAccountsPage.accountsTable.country')}
                  </TableCell>
                  <TableCell align="center">
                    {t('manageAccountsPage.accountsTable.activated')}
                  </TableCell>
                  <TableCell align="center">
                    {t('manageAccountsPage.accountsTable.accepted')}
                  </TableCell>
                  <TableCell align="center">
                    {t('manageAccountsPage.accountsTable.blocked')}
                  </TableCell>
                  <TableCell align="right">{t('manageAccountsPage.accountsTable.roles')}</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {filteredAccounts?.map((account, i) => (
                  <TableRow
                    className={`${
                      accountId === account.id ? 'bg-neutral-light' : ''
                    } cursor-pointer`}
                    onClick={() => navigate(`/manage-accounts/${account.id}`)}
                    hover
                    key={i}
                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
                    <TableCell component="th" scope="row">
                      {account.name}
                    </TableCell>
                    <TableCell align="right">{account.lastname}</TableCell>
                    <TableCell align="right">{account.email}</TableCell>
                    <TableCell align="right">{account.locale}</TableCell>
                    <TableCell align="center">
                      <Chip
                        className={`${account.active ? 'pane-success' : 'pane-danger'} fw-bolder`}
                        label={`${account.active}`}
                        size="small"
                      />
                    </TableCell>
                    <TableCell align="center">
                      <Chip
                        className={`${account.approved ? 'pane-success' : 'pane-danger'} fw-bolder`}
                        label={`${account.approved}`}
                        size="small"
                      />
                    </TableCell>
                    <TableCell align="center">
                      <Chip
                        className={`${account.blocked ? 'pane-danger' : 'pane-success'} fw-bolder`}
                        label={`${account.blocked}`}
                        size="small"
                      />
                    </TableCell>
                    <TableCell align="right">
                      {account.roles?.map((role, i) => (
                        <Chip
                          className="pane-neutral fw-bolder"
                          key={i}
                          label={role.role}
                          size="small"
                        />
                      ))}
                    </TableCell>
                  </TableRow>
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

export default AccountsTable;
