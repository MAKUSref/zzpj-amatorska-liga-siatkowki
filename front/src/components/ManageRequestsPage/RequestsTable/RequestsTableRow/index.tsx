import { Col, Row } from 'react-bootstrap';
import { useGetTeamsRequestsQuery } from '../../../../features/api/apiSlice';
import RequestCard from './RequestCard';
import { useEffect } from 'react';
import { useTranslation } from 'react-i18next';

interface RequestsTableRowProps {
  id: string;
  setRefetchCallback: (callback: () => void) => void;
}

const RequestsTableRow = ({ id, setRefetchCallback }: RequestsTableRowProps) => {
  const { data: requestsInLeague, refetch } = useGetTeamsRequestsQuery(id);
  const { t } = useTranslation();

  useEffect(() => {
    setRefetchCallback(refetch);
  }, []);

  return (
    <>
      <Row>
        {requestsInLeague?.length === 0 ? (
          <Col>
            <p className="m-0 p-0">{t('manageRequestsPage.noRequests')}</p>
          </Col>
        ) : (
          requestsInLeague?.map((team, i) => (
            <Col key={i} xs={3}>
              <RequestCard id={team.id} />
            </Col>
          ))
        )}
      </Row>
    </>
  );
};
export default RequestsTableRow;
