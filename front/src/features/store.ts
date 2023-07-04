import { configureStore } from '@reduxjs/toolkit';
import sessionReducer from './session/sessionSlice';
import registerReducer from './register/registerSlice';
import scheduleReducer from './schedule/scheduleSlice';
import {
  preloadToken,
  sessionListenerMiddleware,
  saveCookieMiddleware
} from './session/middleware';
import { apiSlice } from './api/apiSlice';

export const store = configureStore({
  reducer: {
    session: sessionReducer,
    register: registerReducer,
    schedule: scheduleReducer,
    [apiSlice.reducerPath]: apiSlice.reducer
  },
  preloadedState: {
    session: { ...preloadToken() }
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware().concat(
      sessionListenerMiddleware.middleware,
      saveCookieMiddleware.middleware,
      apiSlice.middleware
    )
});

export type AppDispatch = typeof store.dispatch;
export type RootState = ReturnType<typeof store.getState>;
