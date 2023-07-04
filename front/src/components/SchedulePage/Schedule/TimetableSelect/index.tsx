import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select, { SelectChangeEvent } from '@mui/material/Select';
import { useEffect, useState } from 'react';
import { Col } from 'react-bootstrap';
import { useGetTimetablesQuery } from '../../../../features/api/apiSlice';
import { useTranslation } from 'react-i18next';
import { useAppDispatch, useAppSelector } from '../../../../features/hooks';
import { setTimetable } from '../../../../features/schedule/scheduleSlice';
import { EXTENDED_ROLES } from '../../../../features/api/types';
import { Pathnames } from '../../../../routes/pathnames';
import { NavLink } from 'react-router-dom';
import { Button } from '@mui/material';
import CalendarMonthOutlinedIcon from '@mui/icons-material/CalendarMonthOutlined';

const TimetableSelect = () => {
  const { t } = useTranslation();

  const tokenInfo = useAppSelector((state) => state.session.tokenInfo);
  const { data: timetables } = useGetTimetablesQuery();
  const [currentTimetableId, setCurrentTimetableId] = useState<string>('');
  const scheduleContext = useAppSelector((state) => state.schedule);

  const dispatch = useAppDispatch();

  const handleChange = (event: SelectChangeEvent) => {
    const id = event.target.value as string;
    setCurrentTimetableId(id);
    dispatch(setTimetable(id));
  };

  useEffect(() => {
    if (timetables && timetables.length !== 0) {
      const id = scheduleContext.currentTimetableId ?? timetables[timetables.length - 1].id;
      setCurrentTimetableId(id);
      dispatch(setTimetable(id));
    }
  }, [timetables]);

  return (
    <>
      <Col xs={12} lg={2}>
        <FormControl size="small" fullWidth>
          <InputLabel id="label-id">{t('timetable.season')}</InputLabel>
          <Select
            labelId="label-id"
            value={currentTimetableId}
            label={t('timetable.season')}
            onChange={handleChange}>
            {timetables?.map((timetable, i) => (
              <MenuItem key={i} value={timetable.id}>
                {`${timetable.startDate.split('-')[0]} / ${timetable.endDate.split('-')[0]}`}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
      </Col>
      <Col className="d-flex justify-content-end align-items-center" sx={12} lg={10}>
        {tokenInfo?.role.includes(EXTENDED_ROLES.ADMIN) && (
          <NavLink className="text-decoration-none" to={Pathnames.generateTimetable.fullPath}>
            <Button variant="text">
              <CalendarMonthOutlinedIcon className="me-2" /> {t('timetable.startNewSeason')}
            </Button>
          </NavLink>
        )}
      </Col>
    </>
  );
};

export default TimetableSelect;
