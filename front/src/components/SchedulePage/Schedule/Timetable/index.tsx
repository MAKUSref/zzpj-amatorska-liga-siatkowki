import { LinearProgress } from '@mui/material';
import { Row } from 'react-bootstrap';
import { useGetTimetableByIdQuery } from '../../../../features/api/apiSlice';
import { useAppSelector } from '../../../../features/hooks';
import TimetableRound from './TimetableRound/inedx';

const Timetable = () => {
  const scheduleContext = useAppSelector((state) => state.schedule);
  const {
    data: timetable,
    isLoading,
    isFetching
  } = useGetTimetableByIdQuery(scheduleContext.currentTimetableId ?? '');

  return (
    <Row>
      {isLoading && isFetching && (
        <div className="px-5 my-4">
          <LinearProgress />
        </div>
      )}
      {timetable?.roundList.map((roundId, i) => (
        <TimetableRound key={i} roundId={roundId} index={i + 1} />
      ))}
    </Row>
  );
};

export default Timetable;
