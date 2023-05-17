import { PayloadAction } from '@reduxjs/toolkit';
import { createSlice } from '@reduxjs/toolkit';
import { SessionSliceState } from './types';

const initialState: SessionSliceState = {};

const sessionSlice = createSlice({
  name: 'session',
  initialState,
  reducers: {
    setToken(state, action: PayloadAction<string>) {
      state.token = action.payload;
    }
  }
});

export const { setToken } = sessionSlice.actions;
export default sessionSlice.reducer;
