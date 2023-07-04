import { useMemo, useState } from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import { Checkbox, FormControlLabel, TextField } from '@mui/material';
import { useParams } from 'react-router-dom';
import {
  useAddPlayerToTeamMutation,
  useGetTeamByIdQuery,
  useRemovePlayerFromTeamMutation
} from '../../../../features/api/apiSlice';
import { useForm } from 'react-hook-form';
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import { toast } from 'react-toastify';

interface AddPlayerSchema {
  firstName: string;
  lastName: string;
  age: number;
  isPro: boolean;
}

const PlayersModal = () => {
  const { teamId } = useParams();
  const [open, setOpen] = useState(false);

  const { data: team } = useGetTeamByIdQuery(teamId as string);

  const [addPlayer] = useAddPlayerToTeamMutation();
  const [removePlayer] = useRemovePlayerFromTeamMutation();

  const addPlayerSchema = useMemo(() => {
    return yup.object().shape({
      firstName: yup.string().required(),
      lastName: yup.string().required(),
      age: yup.number().required().min(16).max(120),
      isPro: yup.boolean().required()
    });
  }, []);

  const { register, handleSubmit } = useForm<AddPlayerSchema>({
    resolver: yupResolver(addPlayerSchema)
  });

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleAddManager = ({ firstName, age, isPro, lastName }: AddPlayerSchema) => {
    if (team && team.teamVersion) {
      addPlayer({
        firstName,
        lastName,
        age,
        isPro,
        teamDTO: {
          teamId: teamId as string,
          teamVersion: team.teamVersion
        }
      })
        .unwrap()
        .then(() => toast.success('Dodano zawodnika'))
        .catch((err) => toast.error(err.data));
      handleClose();
    }
  };

  const handleRemovePlayer = (teamId: string, playerId: string) => {
    removePlayer({ teamId, playerId })
      .unwrap()
      .then(() => toast.success('Usunięto zawodnika'))
      .catch((err) => toast.error(err.data));
    handleClose();
  };

  return (
    <>
      <Button variant="outlined" onClick={handleClickOpen}>
        Skład drużyny
      </Button>
      <Dialog open={open} onClose={handleClose}>
        <form onSubmit={handleSubmit(handleAddManager)}>
          <DialogTitle>Drużyna</DialogTitle>
          <DialogContent>
            {team?.players?.map((player, i) => (
              <div className="d-flex justify-content-between align-items-center" key={i}>
                <span>
                  {player.name} {player.lastName} - {player.age}
                </span>
                <span>
                  <Button
                    variant="text"
                    size="small"
                    onClick={() => {
                      if (teamId) {
                        handleRemovePlayer(teamId, player.id);
                      }
                    }}>
                    remove
                  </Button>
                </span>
              </div>
            ))}
          </DialogContent>
          <DialogTitle>Dodaj gracza</DialogTitle>
          <DialogContent>
            <div className="my-2">
              <TextField
                {...register('firstName')}
                id="outlined-basic"
                label="* firstName"
                variant="outlined"
                size="small"
              />
            </div>
            <div className="my-2">
              <TextField
                {...register('lastName')}
                id="outlined-basic"
                label="* lastName"
                variant="outlined"
                size="small"
              />
            </div>
            <div className="my-2">
              <TextField
                {...register('age')}
                id="outlined-basic"
                label="* age"
                variant="outlined"
                size="small"
                type="number"
              />
            </div>
            <div className="my-2">
              <FormControlLabel
                control={<Checkbox {...register('isPro')} />}
                label="Professional player"
              />
            </div>
          </DialogContent>
          <DialogActions>
            <Button onClick={handleClose}>Anuluj</Button>
            <Button type="submit" autoFocus variant="contained">
              Dodaj
            </Button>
          </DialogActions>
        </form>
      </Dialog>
    </>
  );
};

export default PlayersModal;
