import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import ClearIcon from '@mui/icons-material/Clear';
import IconButton from '@mui/material/IconButton';
import Button from '@mui/material/Button';
import { useNavigate } from 'react-router-dom';
import {
  useDeclineTeamSubmissionMutation,
  useAcceptTeamSubmissionMutation,
  useGetTeamByIdQuery
} from '../../../../../features/api/apiSlice';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import ManageRequests from '../../..';
import { toast } from 'react-toastify';

interface RequestCardProps {
  id: string;
}

const RequestCard = ({ id }: RequestCardProps) => {
  const { t } = useTranslation();

  const { data: team } = useGetTeamByIdQuery(id as string);
  const navigate = useNavigate();
  const [declineSubmission] = useDeclineTeamSubmissionMutation();
  const [acceptSubmission] = useAcceptTeamSubmissionMutation();

  const representantName = useMemo(() => {
    return team?.captain
      ? `${team.captain.firstName} ${team.captain.lastName}`
      : team?.coach
      ? `${team.coach.firstName} ${team.coach.lastName}`
      : team?.managers && team.managers.length > 0
      ? `${team.managers[0].firstName} ${team.managers[0].lastName}`
      : `${t('manageRequestsPage.noInfo')}`;
  }, [team]);

  const handleAccept = () => {
    acceptSubmission(id)
      .unwrap()
      .then(() => {
        toast.success(
          t('manageRequestsPage.requestTable.requestTableRow.requestCard.successfully_accepted')
        );
      })
      .catch((err) => {
        toast.error(t(err.data));
      });
  };

  const handleDecline = () => {
    declineSubmission(id)
      .unwrap()
      .then(() => {
        toast.success(
          t('manageRequestsPage.requestTable.requestTableRow.requestCard.successfully_declined')
        );
      })
      .catch((err) => {
        toast.error(t(err.data));
      });
  };

  return (
    <Card>
      <CardContent>
        <Typography
          className="d-flex justify-content-between align-items-center"
          variant="h5"
          component="div">
          <span>
            {team?.teamName} {team?.city}
          </span>
          <IconButton onClick={() => navigate('/manage-requests')} edge="end">
            <ClearIcon />
          </IconButton>
        </Typography>
        <Typography sx={{ fontSize: 14 }} color="text.secondary">
          <span className="ms-2">
            {t('manageRequestsPage.representative')}: {representantName}
          </span>
        </Typography>
        <hr />
        <Button variant="contained" type="submit" onClick={handleAccept}>
          {t('manageRequestsPage.requestTable.requestTableRow.requestCard.accept')}
        </Button>
        <Button variant="text" type="submit" onClick={handleDecline}>
          {t('manageRequestsPage.requestTable.requestTableRow.requestCard.decline')}
        </Button>
      </CardContent>
    </Card>
  );
};

export default RequestCard;
