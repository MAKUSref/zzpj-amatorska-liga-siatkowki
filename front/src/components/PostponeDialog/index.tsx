import { useState } from 'react';
import { Button, Dialog, DialogActions, DialogContent, DialogTitle } from '@mui/material';
import { toast } from 'react-toastify';
import { usePostponeGameMutation } from '../../features/api/apiSlice';
import { useTranslation } from 'react-i18next';

interface PostponeDialogProps {
  gameId: string;
  postponed: boolean;
  postponer: string;
}

const PostponeDialog = ({ gameId, postponed, postponer }: PostponeDialogProps) => {
  const [openC, setOpenC] = useState(false);
  const [postponeGame, { isLoading: postponeGameLoading }] = usePostponeGameMutation();
  const { t } = useTranslation();
  const handleChangePostponedGame = (gameId: string) => {
    if (!gameId) return;

    postponeGame(gameId)
      .unwrap()
      .catch((err) => toast.error(t(err.data)));
  };
  const handleConfirmPostponedGame = (gameId: string) => {
    handleChangePostponedGame(gameId);
    setOpenC(false);
  };
  const handleOpenC = () => setOpenC(true);
  const handleCloseC = () => setOpenC(false);
  return (
    <>
      {postponed ? (
        <p>
          {postponer.split(' ')[0] === 'admin'
            ? `${t('postponeDialog.postponedBy')}` + ': admin'
            : `${t('postponeDialog.postponedBy')}` + ': ' + postponer}
        </p>
      ) : (
        <Button variant="contained" type="submit" onClick={handleOpenC} disabled={postponed}>
          {t('postponeDialog.postponeButton')}
        </Button>
      )}
      <Dialog
        open={openC}
        onClose={handleCloseC}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description">
        <DialogTitle id="modal-modal-title">{t('postponeDialog.confirm')}</DialogTitle>
        <DialogContent>
          <p>{t('postponeDialog.confirmationInfo')}</p>
        </DialogContent>
        <DialogActions>
          <Button
            variant="contained"
            onClick={() => handleConfirmPostponedGame(gameId)}
            color="primary">
            {t('postponeDialog.yes')}
          </Button>
          <Button onClick={handleCloseC} color="primary">
            {t('postponeDialog.no')}
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
};

export default PostponeDialog;
