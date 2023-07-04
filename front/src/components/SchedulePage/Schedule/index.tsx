import { Col } from 'react-bootstrap';
import Timetable from './Timetable';
import scheduleSvg from '../../../imgs/schedule.svg';
import { useGetTimetablesQuery } from '../../../features/api/apiSlice';
import { useTranslation } from 'react-i18next';

const Schedule = () => {
  const { data: timetables } = useGetTimetablesQuery();
  const { t } = useTranslation();

  if (!timetables || timetables?.length === 0) {
    return (
      <>
        <div className="d-flex justify-content-between align-items-center">
          <h5 className="m-0">{t('schedulePage.no_timetable')}</h5>
        </div>
        <div className="text-center">
          <img src={scheduleSvg} alt="no schedule img" style={{ maxWidth: '600px' }} />
        </div>
      </>
    );
  }

  return (
    <>
      <Col>
        <Timetable />
      </Col>
    </>
  );
};

export default Schedule;
