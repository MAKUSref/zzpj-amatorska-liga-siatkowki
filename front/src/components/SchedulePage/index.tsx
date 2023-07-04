import { Row } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { Pathnames } from '../../routes/pathnames';
import Breadcrumbs from '../Breadcrumbs';
import Schedule from './Schedule';
import TimetableSelect from './Schedule/TimetableSelect';

const SchedulePage = () => {
  const { t } = useTranslation();

  return (
    <>
      <Breadcrumbs
        paths={[
          { label: t('paths.home'), to: Pathnames.home.fullPath },
          { label: t('timetable.title'), to: Pathnames.schedule.fullPath }
        ]}
      />
      <h3>{t('timetable.title')}</h3>
      <Row className="mt-4">
        <TimetableSelect />
      </Row>
      <Row className="mt-4">
        <Schedule />
      </Row>
    </>
  );
};

export default SchedulePage;
