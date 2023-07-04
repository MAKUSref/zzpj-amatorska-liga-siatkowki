import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { RegisterAccountInfo, RegisterSliceState } from './types';

const initialState: RegisterSliceState = {
  currentStep: 0
};

const sessionSlice = createSlice({
  name: 'register',
  initialState,
  reducers: {
    updateRegisterInfo(state, action: PayloadAction<RegisterAccountInfo>) {
      state.registerAccountInfo = {
        ...state.registerAccountInfo,
        ...action.payload
      };
    },

    clearRegisterInfo(state) {
      state.registerAccountInfo = {};
    },

    setCurrentStep(state, action: PayloadAction<number>) {
      if (action.payload >= 0 && action.payload <= 2) {
        state.currentStep = action.payload;
      }
    }
  }
});

export const { clearRegisterInfo, updateRegisterInfo, setCurrentStep } = sessionSlice.actions;
export default sessionSlice.reducer;
