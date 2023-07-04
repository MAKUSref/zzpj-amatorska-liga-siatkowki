import { useMemo, useState } from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select, { SelectChangeEvent } from '@mui/material/Select';
import CloseIcon from '@mui/icons-material/Close';
import { TextField } from '@mui/material';
import { useParams } from 'react-router-dom';
import {
  useAddManagerToTeamMutation,
  useGetTeamByIdQuery,
  useGetTeamWithRepresentativesQuery,
  useRemoveCaptainFromTeamMutation,
  useRemoveCoachFromTeamMutation,
  useRemoveManagerFromTeamMutation
} from '../../../../features/api/apiSlice';
import { ROLES } from '../../../../features/api/types';
import { useForm } from 'react-hook-form';
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import { useAppDispatch } from '../../../../features/hooks';
import { setEtag } from '../../../../features/session/sessionSlice';
import { toast } from 'react-toastify';
import { useTranslation } from 'react-i18next';

interface AddManagerSchema {
  role: string;
  login: string;
}

const AddManagerModal = () => {
  const { teamId } = useParams();
  const [open, setOpen] = useState(false);
  const [role, setRole] = useState<string>(ROLES.MANAGER);
  const { t } = useTranslation();
  const { data: team } = useGetTeamWithRepresentativesQuery(teamId as string);
  const [addManager] = useAddManagerToTeamMutation();
  const [removeManager] = useRemoveManagerFromTeamMutation();
  const [removeCaptain] = useRemoveCaptainFromTeamMutation();
  const [removeCoach] = useRemoveCoachFromTeamMutation();

  const addManagerSchema = useMemo(() => {
    return yup.object().shape({
      role: yup.string().required(),
      login: yup.string().required()
    });
  }, []);

  const { register, handleSubmit } = useForm<AddManagerSchema>({
    resolver: yupResolver(addManagerSchema)
  });

  const handleChange = (event: SelectChangeEvent) => {
    setRole(event.target.value as string);
  };

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleRemoveManager = (accountId: string) => {
    if (team && team.teamVersion) {
      removeManager({
        id: teamId as string,
        version: team.teamVersion,
        accountId
      })
        .unwrap()
        .then(() => toast.success(t('teamPage.addManagerModal.toast.added_representative')))
        .catch((err) => toast.error(err.data));
      handleClose();
    }
  };

  const handleRemoveCaptain = () => {
    if (team && team.teamVersion) {
      removeCaptain({
        id: teamId as string,
        version: team.teamVersion
      })
        .unwrap()
        .then(() => toast.success(t('teamPage.addManagerModal.toast.removed_representative')))
        .catch((err) => toast.error(err.data));
      handleClose();
    }
  };

  const handleRemoveCoach = () => {
    if (team && team.teamVersion) {
      removeCoach({
        id: teamId as string,
        version: team.teamVersion
      })
        .unwrap()
        .then(() => toast.success(t('teamPage.addManagerModal.toast.removed_representative')))
        .catch((err) => toast.error(err.data));
      handleClose();
    }
  };

  const handleAddManager = ({ login, role }: AddManagerSchema) => {
    if (team && team.teamVersion) {
      addManager({
        id: teamId as string,
        version: team.teamVersion,
        roleDTO: {
          login,
          roleType: role
        }
      })
        .unwrap()
        .then(() => toast.success(t('teamPage.addManagerModal.toast.added_representative')))
        .catch((err) => toast.error(err.data));
      handleClose();
    }
  };

  return (
    <>
      <Button variant="outlined" onClick={handleClickOpen}>
        {t('teamPage.addManagerModal.team_representatives')}
      </Button>
      <Dialog open={open} onClose={handleClose}>
        <form onSubmit={handleSubmit(handleAddManager)}>
          <DialogTitle>{t('teamPage.addManagerModal.current_representatives')}</DialogTitle>
          <DialogContent>
            <p className="m-0 fw-bold">{t('teamPage.addManagerModal.managers')}</p>
            {team?.managers?.map((manager, i) => (
              <div className="d-flex justify-content-between align-items-center" key={i}>
                <span>
                  {manager?.firstName} {manager?.lastName}
                </span>
                <span>
                  <Button
                    variant="text"
                    size="small"
                    onClick={() => {
                      if (manager.accountId) handleRemoveManager(manager.accountId);
                    }}>
                    remove
                  </Button>
                </span>
              </div>
            ))}
            <p className="m-0 fw-bold">{t('teamPage.addManagerModal.captain')}</p>
            <div className="d-flex justify-content-between align-items-center">
              <span>
                {team?.captain?.firstName} {team?.captain?.lastName}
              </span>
              <span>
                <Button
                  variant="text"
                  size="small"
                  disabled={team?.captain == undefined}
                  onClick={() => {
                    handleRemoveCaptain();
                  }}>
                  remove
                </Button>
              </span>
            </div>
            <p className="m-0 fw-bold">{t('teamPage.addManagerModal.coach')}</p>
            <div className="d-flex justify-content-between align-items-center">
              <span>
                {team?.coach?.firstName} {team?.coach?.lastName}
              </span>
              <span>
                <Button
                  variant="text"
                  size="small"
                  disabled={team?.coach == undefined}
                  onClick={() => {
                    handleRemoveCoach();
                  }}>
                  {t('teamPage.addManagerModal.remove')}
                </Button>
              </span>
            </div>
          </DialogContent>
          <DialogTitle>{t('teamPage.addManagerModal.add_representative')}</DialogTitle>
          <DialogContent>
            <div className="my-2">
              <FormControl fullWidth size="small">
                <InputLabel id="demo-simple-select-label">
                  {t('teamPage.addManagerModal.role')}
                </InputLabel>
                <Select
                  {...register('role')}
                  labelId="demo-simple-select-label"
                  id="demo-simple-select"
                  value={role}
                  label={t('teamPage.addManagerModal.role')}
                  onChange={handleChange}>
                  <MenuItem value={ROLES.MANAGER}>{t('teamPage.addManagerModal.manager')}</MenuItem>
                  <MenuItem value={ROLES.CAPTAIN}>{t('teamPage.addManagerModal.captain')}</MenuItem>
                  <MenuItem value={ROLES.REFREE}>{t('teamPage.addManagerModal.referee')}</MenuItem>
                </Select>
              </FormControl>
            </div>
            <div className="my-2">
              <TextField
                {...register('login')}
                id="outlined-basic"
                label="Login"
                variant="outlined"
                size="small"
              />
            </div>
          </DialogContent>
          <DialogActions>
            <Button onClick={handleClose}>{t('teamPage.addManagerModal.cancel')}</Button>
            <Button type="submit" autoFocus variant="contained">
              {t('teamPage.addManagerModal.add')}
            </Button>
          </DialogActions>
        </form>
      </Dialog>
    </>
  );
};

export default AddManagerModal;
