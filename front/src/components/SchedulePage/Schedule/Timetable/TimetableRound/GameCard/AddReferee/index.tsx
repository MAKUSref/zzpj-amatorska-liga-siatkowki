import { Button } from '@mui/material';
import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import {
  useAddRefereeToGameMutation,
  useGetAccountByRoleQuery
} from '../../../../../../../features/api/apiSlice';
import { ROLES } from '../../../../../../../features/api/types';
import { toast } from 'react-toastify';

interface AddRefereeProps {
  gameId: string;
  onAddReferee?: () => void;
}

const AddReferee = ({ gameId, onAddReferee }: AddRefereeProps) => {
  const { t } = useTranslation();
  const [open, setOpen] = useState(false);
  const { data: referees } = useGetAccountByRoleQuery(ROLES.REFREE);

  const [addReferee] = useAddRefereeToGameMutation();

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleAddReferee = (data: { gameId: string; refereeId: string }) => {
    addReferee(data)
      .unwrap()
      .then(() => toast.success('Dodano sędziego do meczu'))
      .catch((err) => toast.error(err.data))
      .finally(() => {
        onAddReferee?.();
      });

    handleClose();
  };

  return (
    <>
      <Button size="small" onClick={handleClickOpen}>
        + {t('timetable.ref')}
      </Button>
      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description">
        <DialogTitle id="alert-dialog-title">Dodaj sędziego</DialogTitle>
        <DialogContent>
          {referees?.map((referee, i) => (
            <div
              className="d-flex justify-content-between align-items-center"
              key={i}
              style={{ minWidth: '200px' }}>
              <span>
                {referee.name} {referee.lastname}
              </span>
              <Button
                onClick={() => {
                  if (referee.id) {
                    handleAddReferee({ gameId, refereeId: referee.id });
                  }
                }}>
                {t('timetable.add')}
              </Button>
            </div>
          ))}
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>{t('timetable.cancel')}</Button>
        </DialogActions>
      </Dialog>
    </>
  );
};

export default AddReferee;
