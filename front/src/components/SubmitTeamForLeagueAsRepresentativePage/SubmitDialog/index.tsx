import { Button, Dialog, DialogActions, DialogContent, DialogTitle } from '@mui/material';
import { toast } from 'react-toastify';
import { useTranslation } from 'react-i18next';
import { useSubmitOwnTeamForLeagueMutation } from '../../../features/api/apiSlice';

interface SubmitDialogProps {
  refetch: any;
  open: boolean;
  onClose: any;
  selectedTeamId?: string;
  selectedLeagueId?: string;
}

const SubmitDialog = ({
  refetch,
  open,
  onClose,
  selectedTeamId,
  selectedLeagueId
}: SubmitDialogProps) => {
  const { t } = useTranslation();
  const [submitTeamForLeague] = useSubmitOwnTeamForLeagueMutation();

  const handleSubmitTeamForLeague = (teamId?: string, leagueId?: string) => {
    submitTeamForLeague({
      teamId,
      leagueId
    })
      .unwrap()
      .then(() => {
        toast.success(t('submit_team.succeed'));
        handleCancel();
        refetch();
      })
      .catch((err) => {
        toast.error(t(err.data));
      });
  };

  const handleCancel = () => {
    onClose();
  };

  return (
    <Dialog
      open={open}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description">
      <DialogTitle id="modal-modal-title">{t('submit_team.dialog_msg')}</DialogTitle>
      <DialogContent>
        <p>{t('submit_team.confirmation_msg')}</p>
      </DialogContent>
      <DialogActions>
        <Button
          variant="contained"
          onClick={() => handleSubmitTeamForLeague(selectedTeamId, selectedLeagueId)}
          color="primary">
          {t('submit_team.confirmation')}
        </Button>
        <Button onClick={() => handleCancel()} color="primary">
          {t('submit_team.cancel')}
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default SubmitDialog;
