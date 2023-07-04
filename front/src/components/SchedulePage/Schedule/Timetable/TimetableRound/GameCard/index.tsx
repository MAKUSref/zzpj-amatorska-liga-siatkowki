import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import { EXTENDED_ROLES, GameDTO } from '../../../../../../features/api/types';
import { Col } from 'react-bootstrap';
import {
  useGetTeamByIdQuery,
  useLazyGetGameByIdQuery
} from '../../../../../../features/api/apiSlice';
import LaunchIcon from '@mui/icons-material/Launch';
import { Chip, CircularProgress } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { useAppSelector } from '../../../../../../features/hooks';
import { useTranslation } from 'react-i18next';
import AddReferee from './AddReferee';
import SportsIcon from '@mui/icons-material/Sports';
import { useCallback, useState } from 'react';

const GameCard = ({ id, teamA, teamB, venue, startTime, referee, score, postponed }: GameDTO) => {
  const [refereeInfo, setRefereeInfo] = useState<string>(referee);

  const { t } = useTranslation();
  const sessionState = useAppSelector((state) => state.session);
  const { data: teamARes, isLoading: isLoadingA } = useGetTeamByIdQuery(teamA.teamId);
  const { data: teamBRes, isLoading: isLoadingB } = useGetTeamByIdQuery(teamB.teamId);

  const [getGameById] = useLazyGetGameByIdQuery();

  const navigate = useNavigate();

  const handleUpdateRefereeInfo = useCallback(() => {
    getGameById(id).then((res) => {
      if (res.data) {
        setRefereeInfo(res.data.referee);
      }
    });
  }, [getGameById, id]);

  return (
    <Col xs={12} md={6} lg={4}>
      <Card className="m-2 bg-neutral">
        <CardContent>
          <div className="d-flex justify-content-between">
            <span>
              {t('timetable.venue')}: {venue.courtNumber}
            </span>
            {postponed && <Chip className="fw-bold" label="Przełożony" size="small" />}
          </div>
          <hr />
          <Typography className="my-2 d-flex align-items-center" variant="h5" component="div">
            <LaunchIcon
              className="me-2 text-primary cursor-pointer"
              fontSize="small"
              onClick={() => navigate(`/manage-teams/${teamA.teamId}`)}
            />
            {isLoadingA ? (
              <CircularProgress
                className=""
                sx={{ width: '22px!important', height: '22px!important' }}
              />
            ) : (
              <span>{teamARes?.teamName}</span>
            )}
            <span className="ms-auto">{score?.scoreboardPointsA ?? '-'}</span>
          </Typography>
          <Typography className="my-2 d-flex align-items-center" variant="h5" component="div">
            <LaunchIcon
              className="me-2 text-primary cursor-pointer"
              fontSize="small"
              onClick={() => navigate(`/manage-teams/${teamB.teamId}`)}
            />
            {isLoadingB ? (
              <CircularProgress
                className=""
                sx={{ width: '22px!important', height: '22px!important' }}
              />
            ) : (
              <span>{teamBRes?.teamName}</span>
            )}
            <span className="ms-auto">{score?.scoreboardPointsB ?? '-'}</span>
          </Typography>
          <hr />
          <div className="d-flex justify-content-between">
            <span>{startTime.split('T')[0]}</span>
            <span>{startTime.split('T')[1]}</span>
          </div>
          <div className="mt-3 d-flex justify-content-between align-items-center">
            <div>
              {refereeInfo !== 'Referee not yet set' && (
                <SportsIcon className="text-primary me-2 fw-bold" fontSize="small" />
              )}
              <span className={refereeInfo === 'Referee not yet set' ? 'fw-bold text-danger' : ''}>
                {refereeInfo}
              </span>
            </div>
            <div>
              {sessionState.tokenInfo.role.includes(EXTENDED_ROLES.ADMIN) &&
                refereeInfo === 'Referee not yet set' && (
                  <AddReferee gameId={id} onAddReferee={handleUpdateRefereeInfo} />
                )}
            </div>
          </div>
        </CardContent>
      </Card>
    </Col>
  );
};

export default GameCard;
