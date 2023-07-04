import { useNavigate, useParams } from 'react-router-dom';
import {
  useAddGameScoreMutation,
  useGetGameByIdQuery,
  useLazyGetTeamByIdQuery
} from '../../../features/api/apiSlice';
import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { TeamDTO } from '../../../features/api/types';
import {
  Box,
  Icon,
  IconButton,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  TextField
} from '@mui/material';
import { PlusOneRounded, DeleteSharp } from '@mui/icons-material/';
import { Button, Col, Row } from 'react-bootstrap';
import * as _ from 'lodash';
import { Pathnames } from '../../../routes/pathnames';
import { toast } from 'react-toastify';

const ScoreEdition = () => {
  const { id } = useParams() as { id: string };
  const [t] = useTranslation();
  const prefix = 'yourGames.score.';
  const prefixTable = 'yourGames.score.table.';

  const { data: game } = useGetGameByIdQuery(id);
  const navigate = useNavigate();

  const [getTeamA] = useLazyGetTeamByIdQuery();
  const [getTeamB] = useLazyGetTeamByIdQuery();

  const [teamA, setTeamA] = useState<TeamDTO>();
  const [teamB, setTeamB] = useState<TeamDTO>();

  const [scoreboardPointsA, setScoreboardPointsA] = useState(0);
  const [scoreboardPointsB, setScoreboardPointsB] = useState(0);

  useEffect(() => {
    if (!game) {
      return;
    }
    (async () => {
      setTeamA((await getTeamA(game?.teamA.teamId)).data);
      setTeamB((await getTeamB(game?.teamB.teamId)).data);
    })();
  }, [game]);

  const [sets, setSets] = useState<{ id: string; teamA: number; teamB: number }[]>([
    { id: window.crypto.randomUUID(), teamA: 0, teamB: 0 },
    { id: window.crypto.randomUUID(), teamA: 0, teamB: 0 },
    { id: window.crypto.randomUUID(), teamA: 0, teamB: 0 }
  ]);
  const handlerValue = (index: string) => () => {
    setSets(sets.filter((el) => el.id !== index));
  };

  const handlerAddValue = () => () => {
    setSets([...sets, { id: window.crypto.randomUUID(), teamA: 0, teamB: 0 }]);
  };

  const min = 0;

  const handlerChangeTeamValue = (id: string, isTeamA: boolean) => (event: any) => {
    let newValue = +event.target.value;

    const set = sets.filter((el) => el.id === id)[0];
    if (isTeamA) set.teamA = newValue;
    else set.teamB = newValue;
    setSets(sets);
  };

  const checkMinimum = () => {
    return sets.length < 4;
  };

  const checkMaximum = () => {
    return sets.length > 3;
  };

  const [addGameScore] = useAddGameScoreMutation();

  const handlerSubmit = async () => {
    addGameScore({
      sets: sets.map((set) => ({
        teamAPoints: set.teamA,
        teamBPoints: set.teamB
      })),
      gameId: id,
      scoreboardPointsA,
      scoreboardPointsB
    })
      .then((res: any) => {
        if (res.error) {
          toast.error(t('exception.' + res.error.data));
          return;
        }
        toast.success('Pomyślnie dodano wynik');
        navigate(Pathnames.yourGames.fullPath);
      })
      .catch((err: any) => toast.error(t('exception.' + err.data)));
  };

  return (
    <>
      <h3>
        {teamA && teamA.teamName} VS {teamB && teamB.teamName}
      </h3>
      <Box>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell></TableCell>
              <TableCell>{t(prefixTable + 'set')}</TableCell>
              <TableCell>
                {t(prefixTable + 'result')} {teamA?.teamName}
              </TableCell>
              <TableCell>
                {t(prefixTable + 'result')} {teamB?.teamName}
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {sets.map((set, element_id) => {
              return (
                <TableRow key={set.id}>
                  <TableCell>
                    <IconButton disabled={checkMinimum()} onClick={handlerValue(set.id)}>
                      <DeleteSharp />
                    </IconButton>
                  </TableCell>
                  <TableCell>Set {element_id + 1}</TableCell>
                  <TableCell>
                    <TextField
                      type="number"
                      defaultValue={set.teamA}
                      onChange={handlerChangeTeamValue(set.id, true)}
                      inputProps={{
                        min
                      }}
                    />
                  </TableCell>
                  <TableCell>
                    <TextField
                      type="number"
                      defaultValue={set.teamB}
                      onChange={handlerChangeTeamValue(set.id, false)}
                      inputProps={{
                        min
                      }}
                    />
                  </TableCell>
                </TableRow>
              );
            })}
          </TableBody>
        </Table>
        <IconButton disabled={checkMaximum()} onClick={handlerAddValue()}>
          <PlusOneRounded color="success" />
        </IconButton>
        <Box>
          <Row>
            <Col>
              <h3>
                {t(prefix + 'scoreboardPoints')} {teamA && teamA.teamName}
              </h3>
              <TextField
                type="number"
                defaultValue={setScoreboardPointsA}
                onChange={(event) => setScoreboardPointsA(+event.target.value)}
                inputProps={{
                  min
                }}
              />
            </Col>
            <Col>
              <h3>
                {t(prefix + 'scoreboardPoints')} {teamB && teamB.teamName}
              </h3>
              <TextField
                type="number"
                defaultValue={setScoreboardPointsB}
                onChange={(event) => setScoreboardPointsB(+event.target.value)}
                inputProps={{
                  min
                }}
              />
            </Col>
          </Row>
        </Box>
        <Button onClick={handlerSubmit}>{t(prefix + 'submit')}</Button>
      </Box>
    </>
  );
};

export default ScoreEdition;
