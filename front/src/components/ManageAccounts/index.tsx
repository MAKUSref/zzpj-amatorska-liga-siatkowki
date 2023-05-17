import { Col, Container, Row } from 'react-bootstrap';
import MenuItem from '@mui/material/MenuItem';
import Select, { SelectChangeEvent } from '@mui/material/Select';
import { useState } from 'react';
import { MANAGEMENT_ROLES } from '../../features/api/types';
import { FormControl, InputLabel } from '@mui/material';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';

function createData(name: string, calories: number, fat: number, carbs: number, protein: number) {
  return { name, calories, fat, carbs, protein };
}

const rows = [
  createData('Frozen yoghurt', 159, 6.0, 24, 4.0),
  createData('Ice cream sandwich', 237, 9.0, 37, 4.3),
  createData('Eclair', 262, 16.0, 24, 6.0),
  createData('Cupcake', 305, 3.7, 67, 4.3),
  createData('Gingerbread', 356, 16.0, 49, 3.9)
];

const ManageAccounts = () => {
  const [currentRole, setCurrentRole] = useState<MANAGEMENT_ROLES>(MANAGEMENT_ROLES.MANAGER);

  const handleChangeRole = (event: SelectChangeEvent) => {
    setCurrentRole(event.target.value as MANAGEMENT_ROLES);
  };

  return (
    <Container className="mt-4">
      <h3>Zarządzaj kontami</h3>
      <Row className="mt-5">
        <Col>
          <FormControl size="small">
            <InputLabel id="demo-simple-select-label">Role</InputLabel>
            <Select
              labelId="demo-simple-select-label"
              value={currentRole}
              label="Role"
              onChange={handleChangeRole}>
              <MenuItem value={MANAGEMENT_ROLES.MANAGER}>{MANAGEMENT_ROLES.MANAGER}</MenuItem>
              <MenuItem value={MANAGEMENT_ROLES.CAPTAIN}>{MANAGEMENT_ROLES.CAPTAIN}</MenuItem>
              <MenuItem value={MANAGEMENT_ROLES.COACH}>{MANAGEMENT_ROLES.COACH}</MenuItem>
            </Select>
          </FormControl>
        </Col>
      </Row>
      <Row className="mt-4">
        <Col>
          <TableContainer component={Paper}>
            <Table sx={{ minWidth: 650 }} aria-label="simple table">
              <TableHead>
                <TableRow>
                  <TableCell>Dessert (100g serving)</TableCell>
                  <TableCell align="right">Calories</TableCell>
                  <TableCell align="right">Fat&nbsp;(g)</TableCell>
                  <TableCell align="right">Carbs&nbsp;(g)</TableCell>
                  <TableCell align="right">Protein&nbsp;(g)</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {rows.map((row) => (
                  <TableRow
                    key={row.name}
                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
                    <TableCell component="th" scope="row">
                      {row.name}
                    </TableCell>
                    <TableCell align="right">{row.calories}</TableCell>
                    <TableCell align="right">{row.fat}</TableCell>
                    <TableCell align="right">{row.carbs}</TableCell>
                    <TableCell align="right">{row.protein}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </Col>
      </Row>
    </Container>
  );
};

export default ManageAccounts;
