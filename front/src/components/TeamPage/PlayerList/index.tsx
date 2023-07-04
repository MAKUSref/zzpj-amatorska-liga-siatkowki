import { useState, ReactNode, useEffect } from 'react';
import Grid from '@mui/material/Grid';
import List from '@mui/material/List';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import ListItemIcon from '@mui/material/ListItemIcon';
import Checkbox from '@mui/material/Checkbox';
import Button from '@mui/material/Button';
import Divider from '@mui/material/Divider';
import { useEditGamesquadMutation, useGetTeamByIdQuery } from '../../../features/api/apiSlice';
import { EditGamesquadDTO, PlayerDTO } from '../../../features/api/types';
import { useTranslation } from 'react-i18next';
import { toast } from 'react-toastify';

function not(a: PlayerDTO[], b: PlayerDTO[]) {
  return a.filter((value) => b.indexOf(value) === -1);
}

function notId(a: PlayerDTO[], b: PlayerDTO[]) {
  return a.filter((value) => !b.some((item) => item.id === value.id));
}

function intersection(a: PlayerDTO[], b: PlayerDTO[]) {
  return a.filter((value) => b.indexOf(value) !== -1);
}

function union(a: PlayerDTO[], b: PlayerDTO[]) {
  return [...a, ...not(b, a)];
}

interface TransferListProps {
  teamId: string;
  gamesquadId: string;
  gamesquad: PlayerDTO[];
  isGuest: boolean;
}
export default function TransferList({
  teamId,
  gamesquadId,
  gamesquad,
  isGuest
}: TransferListProps) {
  const { data: teamRes, isLoading } = useGetTeamByIdQuery(teamId);
  const [checked, setChecked] = useState<PlayerDTO[]>([]);
  const [right, setRight] = useState<PlayerDTO[]>(gamesquad);
  const [left, setLeft] = useState<PlayerDTO[]>(notId(teamRes?.players ?? [], gamesquad));
  const { t } = useTranslation();
  const [EditGamesquad] = useEditGamesquadMutation();

  const leftChecked = intersection(checked, left);
  const rightChecked = intersection(checked, right);

  const handleToggle = (value: PlayerDTO) => () => {
    const currentIndex = checked.indexOf(value);
    const newChecked = [...checked];

    if (currentIndex === -1) {
      newChecked.push(value);
    } else {
      newChecked.splice(currentIndex, 1);
    }

    setChecked(newChecked);
  };

  const numberOfChecked = (items: PlayerDTO[]) => intersection(checked, items).length;

  const handleToggleAll = (items: PlayerDTO[]) => () => {
    if (numberOfChecked(items) === items.length) {
      setChecked(not(checked, items));
    } else {
      setChecked(union(checked, items));
    }
  };

  const handleCheckedRight = () => {
    setRight(right.concat(leftChecked));
    setLeft(not(left, leftChecked));
    setChecked(not(checked, leftChecked));
  };

  const handleSubmit = () => {
    const editGamesquadDTO: EditGamesquadDTO = {
      playerIds: right.map((player) => player.id),
      gamesquadId: gamesquadId
    };
    EditGamesquad(editGamesquadDTO)
      .unwrap()
      .then(() => {
        toast.success(t('teamPage.playerList.add_gamesquad_success'));
      })
      .catch((err) => {
        toast.error(t(err.data));
      });
  };

  const handleCheckedLeft = () => {
    setLeft(left.concat(rightChecked));
    setRight(not(right, rightChecked));
    setChecked(not(checked, rightChecked));
  };

  const customList = (title: ReactNode, items: PlayerDTO[]) => (
    <Card>
      <CardHeader
        sx={{ px: 2, py: 1 }}
        avatar={
          <Checkbox
            onClick={handleToggleAll(items)}
            checked={numberOfChecked(items) === items.length && items.length !== 0}
            indeterminate={numberOfChecked(items) !== items.length && numberOfChecked(items) !== 0}
            disabled={items.length === 0 || isGuest}
            inputProps={{
              'aria-label': 'all items selected'
            }}
          />
        }
        title={title}
        subheader={`${numberOfChecked(items)}/${items.length} selected`}
      />
      <Divider />
      <List
        sx={{
          width: 200,
          height: 230,
          bgcolor: 'background.paper',
          overflow: 'auto'
        }}
        dense
        component="div"
        role="list">
        {items.map((value: PlayerDTO, i) => {
          const labelId = `transfer-list-all-item-${value}-label`;

          return (
            <ListItem key={i} role="listitem" onClick={handleToggle(value)}>
              <ListItemIcon>
                {isGuest ? (
                  <></>
                ) : (
                  <Checkbox
                    checked={checked.indexOf(value) !== -1}
                    tabIndex={-1}
                    disableRipple
                    inputProps={{
                      'aria-labelledby': labelId
                    }}
                  />
                )}
              </ListItemIcon>
              <ListItemText id={labelId} primary={value.name} />
            </ListItem>
          );
        })}
      </List>
    </Card>
  );

  useEffect(() => {
    if (teamRes) {
      setLeft(notId(teamRes?.players ?? [], gamesquad));
    }
  }, [teamRes]);

  if (isLoading) {
    return <div>Loading</div>;
  }

  return (
    <>
      <Grid container spacing={2} justifyContent="center" alignItems="center">
        {isGuest ? (
          <></>
        ) : (
          <Grid item>{customList(t('teamPage.playerList.team_players'), left)}</Grid>
        )}
        <Grid item>
          <Grid container direction="column" alignItems="center">
            {isGuest ? (
              <></>
            ) : (
              <>
                <Button
                  sx={{ my: 0.5 }}
                  variant="outlined"
                  size="small"
                  onClick={handleCheckedRight}
                  disabled={leftChecked.length === 0}
                  aria-label="move selected right">
                  &gt;
                </Button>
                <Button
                  sx={{ my: 0.5 }}
                  variant="outlined"
                  size="small"
                  onClick={handleCheckedLeft}
                  disabled={rightChecked.length === 0}
                  aria-label="move selected left">
                  &lt;
                </Button>
              </>
            )}
          </Grid>
        </Grid>{' '}
        <Grid item>{customList(t('teamPage.playerList.gamesquad'), right)}</Grid>
      </Grid>
      {isGuest ? (
        <></>
      ) : (
        <Button
          sx={{ my: 0.5 }}
          variant="outlined"
          size="small"
          onClick={handleSubmit}
          disabled={gamesquad.length === right.length}
          aria-label="submitChanges">
          {t('teamPage.playerList.confirm_gamesquad')}
        </Button>
      )}
    </>
  );
}
