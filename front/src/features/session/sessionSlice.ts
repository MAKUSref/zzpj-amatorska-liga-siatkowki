import { PayloadAction } from '@reduxjs/toolkit';
import { createSlice } from '@reduxjs/toolkit';
import { EXTENDED_ROLES } from '../api/types';
import { JwtTokenInfo, SessionSliceState } from './types';
import jwtDecode from 'jwt-decode';

const initialState: SessionSliceState = {
  tokenInfo: {
    role: [EXTENDED_ROLES.GUEST]
  }
};

const sessionSlice = createSlice({
  name: 'session',
  initialState,
  reducers: {
    setToken(state, action: PayloadAction<string>) {
      state.token = action.payload;
      const tokenInfo = jwtDecode(action.payload) as JwtTokenInfo;
      state.tokenInfo = tokenInfo;
    },
    setEtag(state, action: PayloadAction<string | undefined>) {
      state.etag = action.payload;
    },
    logout(state) {
      state.token = undefined;
      state.tokenInfo = { ...initialState.tokenInfo };
      state.etag = undefined;
    }
  }
});

export const { setToken, logout, setEtag } = sessionSlice.actions;
export default sessionSlice.reducer;
