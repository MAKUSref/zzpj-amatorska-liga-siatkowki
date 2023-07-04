import React, { useEffect, useMemo } from 'react';
import { Button, Card, CardContent, CircularProgress } from '@mui/material';
import { useTranslation } from 'react-i18next';
import { useGetAllLeaguesQuery, useGetMyTeamsQuery } from '../../../features/api/apiSlice';
import { useNavigate } from 'react-router-dom';
import { Col, Row } from 'react-bootstrap';
import { useState } from 'react';
import Radio from '@mui/material/Radio';
import CachedIcon from '@mui/icons-material/Cached';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';
import { Pathnames } from '../../../routes/pathnames';
import SubmitDialog from '../SubmitDialog';

const SubmitTeamForLeagueAsRepresentative = () => {
  const navigate = useNavigate();
  const { t } = useTranslation();

  const { data: teams, refetch, isLoading, isSuccess, isFetching } = useGetMyTeamsQuery();
  const { data: leagues } = useGetAllLeaguesQuery();

  const filteredTeams = useMemo(() => {
    return teams?.filter((team) => {
      return !team.inLeague;
    });
  }, [teams]);

  const [open, setOpen] = useState(false);
  const [selectedTeamId, setSelectedTeamId] = useState<string>();
  const [selectedLeagueId, setSelectedLeagueId] = useState<string>();

  const handleClickOpenDialog = () => {
    setOpen(true);
  };

  const handleCloseDialog = () => {
    setOpen(false);
  };

  const handleSelectedTeamChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSelectedTeamId((event.target as HTMLInputElement).value);
  };

  const handleSelectedLeagueChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSelectedLeagueId((event.target as HTMLInputElement).value);
  };

  useEffect(() => {
    console.log(filteredTeams);
    setSelectedTeamId(filteredTeams?.at(0)?.id);
    setSelectedLeagueId(leagues?.at(0)?.id);
  }, [filteredTeams, teams, leagues, isSuccess]);

  return (
    <Row className="mt-5">
      <Row sx={{ mt: 4 }}>
        <Col sx={6} md={6}>
          <Card>
            <CardContent>
              <div className="d-flex justify-content-between align-items-center">
                <h4>{t('submit_team.your_not_submitted_teams_header')}</h4>
                <div className="d-flex justify-content-between align-items-center">
                  {isLoading || isFetching ? (
                    <CircularProgress sx={{ width: '22px!important', height: '22px!important' }} />
                  ) : (
                    <CachedIcon
                      className="text-muted mx-2 cursor-pointer"
                      onClick={() => refetch()}
                    />
                  )}
                </div>
              </div>
              {filteredTeams && filteredTeams.length !== 0 ? (
                <FormControl fullWidth>
                  <FormLabel id="team-radio-buttons-group-label">
                    {t('submit_team.teams_input_label')}
                  </FormLabel>
                  <RadioGroup
                    aria-labelledby="team-radio-buttons-group-label"
                    value={selectedTeamId ?? ' '}
                    onChange={handleSelectedTeamChange}>
                    {filteredTeams?.map((team, i) => (
                      <Row key={i} fullWidth>
                        <Col md={4}>
                          <FormControlLabel
                            value={team?.id}
                            control={<Radio />}
                            label={team?.teamName}
                          />
                        </Col>
                        <Col style={{ display: 'flex', justifyContent: 'right' }}>
                          <Button
                            variant="contained"
                            onClick={() => navigate(`${Pathnames.manageTeams.fullPath}/${team.id}`)}
                            size="small"
                            sx={{ my: 1, mr: 0 }}
                            key={i}>
                            {t('submit_team.details')}
                          </Button>
                        </Col>
                      </Row>
                    ))}
                  </RadioGroup>
                </FormControl>
              ) : (
                <Col>
                  <p className="m-0 p-0">{t('submit_team.no_not_submitted_teams')}</p>
                </Col>
              )}
            </CardContent>
          </Card>
        </Col>
        <Col sx={6} md={6}>
          <Card>
            <CardContent>
              <h4>{t('submit_team.leagues')}</h4>
              {leagues && leagues.length !== 0 ? (
                <FormControl>
                  <FormLabel id="league-radio-buttons-group-label">
                    {t('submit_team.leagues_input_label')}
                  </FormLabel>
                  <RadioGroup
                    aria-labelledby="league-radio-buttons-group-label"
                    value={selectedLeagueId ?? ' '}
                    onChange={handleSelectedLeagueChange}>
                    {leagues?.map((league, i) => (
                      <FormControlLabel
                        key={i}
                        value={league?.id}
                        control={<Radio />}
                        label={`${league?.leagueNumber}. ${t('submit_team.league')}`}
                      />
                    ))}
                  </RadioGroup>
                </FormControl>
              ) : (
                <Col>
                  <p className="m-0 p-0">{t('submit_team.no_leagues_available')}</p>
                </Col>
              )}
            </CardContent>
          </Card>
        </Col>
      </Row>
      <Row className="justify-content-md-center">
        <Col sx={1} md={2}>
          <Button
            variant="contained"
            disabled={filteredTeams?.length === 0 || leagues?.length === 0 ? true : false}
            onClick={() => handleClickOpenDialog()}
            sx={{ mt: 2 }}
            fullWidth>
            {t('submit_team.submit_button_label')}
          </Button>
        </Col>
        <SubmitDialog
          refetch={refetch}
          open={open}
          onClose={handleCloseDialog}
          selectedTeamId={selectedTeamId}
          selectedLeagueId={selectedLeagueId}
        />
      </Row>
    </Row>
  );
};

export default SubmitTeamForLeagueAsRepresentative;
