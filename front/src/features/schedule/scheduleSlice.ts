import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { ScheduleSliceState } from './types';

const initialState: ScheduleSliceState = {};

const scheduleSlice = createSlice({
  name: 'schedule',
  initialState,
  reducers: {
    setTimetable(state, action: PayloadAction<string>) {
      state.currentTimetableId = action.payload;
      state.currentQueue = undefined;
    },

    setQueue(state, action: PayloadAction<number>) {
      state.currentQueue = action.payload;
    }
  }
});

export const { setQueue, setTimetable } = scheduleSlice.actions;
export default scheduleSlice.reducer;
