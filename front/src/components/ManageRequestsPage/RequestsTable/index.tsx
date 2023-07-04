import { Col, Row } from 'react-bootstrap';
import { useMemo } from 'react';
import Accordion from '@mui/material/Accordion';
import AccordionSummary from '@mui/material/AccordionSummary';
import AccordionDetails from '@mui/material/AccordionDetails';
import Typography from '@mui/material/Typography';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import CachedIcon from '@mui/icons-material/Cached';
import { CircularProgress } from '@mui/material';
import { useGetCurrentSeasonLeaguesQuery } from '../../../features/api/apiSlice';
import RequestsTableRow from './RequestsTableRow';
import { useState } from 'react';
import { useTranslation } from 'react-i18next';

const RequestsTable = () => {
  const [refetchCallback, setRefetchCallback] = useState<{ callback: () => void }>({
    callback: () => {}
  });

  const { t } = useTranslation();

  const { data: leagues, isLoading, isFetching } = useGetCurrentSeasonLeaguesQuery();

  const sortedLeagues = useMemo(() => {
    return leagues?.slice().sort((a, b) => a.leagueNumber - b.leagueNumber);
  }, [leagues]);

  const handleSetRefetchCallback = (callback: () => void) => {
    setRefetchCallback({ callback });
  };

  return (
    <>
      <Row className="mt-5">
        <Col className="d-flex justify-content-between align-items-center">
          <p className="m-0 p-0">
            {sortedLeagues && sortedLeagues.length > 0
              ? `${t('manageRequestsPage.leaguesInSeason')} ${sortedLeagues[0]?.season}`
              : `${t('manageRequestsPage.leagues')}`}
          </p>
          <div className="d-flex justify-content-between align-items-center">
            {isLoading || isFetching ? (
              <CircularProgress sx={{ width: '22px!important', height: '22px!important' }} />
            ) : (
              <CachedIcon
                className="text-muted mx-2 cursor-pointer"
                onClick={refetchCallback.callback}
              />
            )}
          </div>
        </Col>
      </Row>
      <Row className="mt-4">
        <Col xs={12}>
          {sortedLeagues?.map((league, i) => (
            <Accordion key={i} className="shadow-none">
              <AccordionSummary
                expandIcon={<ExpandMoreIcon />}
                id="panel1a-header"
                className="px-0">
                <Typography variant="h6" className="fw-normal">
                  {t('manageRequestsPage.league')} {league.leagueNumber}
                </Typography>
              </AccordionSummary>
              <AccordionDetails className="px-0">
                <RequestsTableRow id={league.id} setRefetchCallback={handleSetRefetchCallback} />
              </AccordionDetails>
            </Accordion>
          ))}
        </Col>
      </Row>
    </>
  );
};

export default RequestsTable;
