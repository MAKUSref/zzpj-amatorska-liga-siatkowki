import Breadcrumbs from '../../Breadcrumbs';
import { Col, Row } from 'react-bootstrap';
import ListCheckedView, { ElementState } from './ListCheckedView';
import { DatePicker } from '@mui/x-date-pickers-pro';
import {
  useGenerateTimetableMutation,
  useGetAllLeaguesQuery,
  useGetAllVenuesQuery
} from '../../../features/api/apiSlice';
import { useEffect, useState } from 'react';
import dayjs, { Dayjs } from 'dayjs';
import { Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { Pathnames } from '../../../routes/pathnames';
import { toast } from 'react-toastify';
import { useTranslation } from 'react-i18next';

const TimeTableGeneration = () => {
  const { data: leagues, isLoading: isLeaguesLoading } = useGetAllLeaguesQuery();
  const { data: venues, isLoading: isVenuesLoading } = useGetAllVenuesQuery();

  const { t } = useTranslation();

  const [leaguesData, setLeaguesData] = useState<ElementState[]>([]);
  const [venuesData, setVenuesData] = useState<ElementState[]>([]);
  const [startDate, setStartDate] = useState<Dayjs>(dayjs());
  const [endDate, setEndDate] = useState<Dayjs>(dayjs());

  const navigate = useNavigate();
  const [generateTimetable] = useGenerateTimetableMutation();

  const handlerSubmit = async () => {
    generateTimetable({
      leagues: leaguesData.filter((league) => league.isChecked).map((league) => league.id),
      venues: venuesData.filter((venue) => venue.isChecked).map((venue) => venue.id),
      config: {
        startDate: startDate.format('DD/MM/YYYY'),
        endDate: endDate.format('DD/MM/YYYY')
      }
    })
      .then((res: any) => {
        if (res.error) {
          toast.error(t('exception.' + res.error.data));
          return;
        }
        toast.success('Pomyślnie stworzono terminarz');
        navigate(Pathnames.schedule.fullPath);
      })
      .catch((err) => toast.error(t('exception.' + err.data)));
  };

  useEffect(() => {
    if (!leagues) {
      return;
    }
    return setLeaguesData(
      leagues.map((league) => {
        return {
          id: league.id,
          title: 'Liga ' + league.leagueNumber,
          isChecked: false
        } as ElementState;
      })
    );
  }, [isLeaguesLoading]);

  useEffect(() => {
    if (!venues) {
      return;
    }
    return setVenuesData(
      venues.map((venue) => {
        return {
          id: venue.id,
          title: venue.address + ' ' + venue.courtNumber,
          isChecked: false
        } as ElementState;
      })
    );
  }, [isVenuesLoading]);

  return (
    <>
      <Breadcrumbs
        paths={[
          {
            label: t('paths.home'),
            to: Pathnames.home.fullPath
          },
          {
            label: t('schedulePage.schedule'),
            to: Pathnames.schedule.fullPath
          },
          {
            label: t('schedulePage.timetable_generating'),
            to: Pathnames.generateTimetable.fullPath
          }
        ]}
      />
      <h3>{t('schedulePage.timetable_generating')}</h3>
      <Row style={{ textAlign: 'center' }}>
        <Col sx={12} md={6}>
          <div>
            <ListCheckedView title={t('schedulePage.leagues')} elements={leaguesData} />
          </div>
        </Col>
        <Col sx={12} md={6}>
          <div>
            <ListCheckedView title={t('schedulePage.venuse')} elements={venuesData} />
          </div>
        </Col>
        <Col sx={12} md={6}>
          <DatePicker
            sx={{ width: '80%' }}
            defaultValue={startDate}
            onChange={(newVal: any) => newVal && setStartDate(newVal)}
            label={t('schedulePage.begin_time')}
          />
        </Col>
        <Col sx={12} md={6}>
          <DatePicker
            sx={{ width: '80%' }}
            defaultValue={endDate}
            onChange={(newVal: any) => newVal && setEndDate(newVal)}
            label={t('schedulePage.expected_end_date')}
          />
        </Col>
        <Col sx={12} md={12}>
          <Button onClick={handlerSubmit} sx={{ width: '100%', marginTop: '20px' }}>
            {t('schedulePage.create_timetable')}
          </Button>
        </Col>
      </Row>
    </>
  );
};

export default TimeTableGeneration;
