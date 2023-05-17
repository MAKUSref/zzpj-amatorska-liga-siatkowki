import { configureStore } from '@reduxjs/toolkit';
import sessionReducer from './session/sessionSlice';
import registerReducer from './register/registerSlice';
import { getSessionFromCookie, sessionListenerMiddleware } from './session/middleware';
import { apiSlice } from './api/apiSlice';

export const store = configureStore({
  reducer: {
    session: sessionReducer,
    register: registerReducer,
    [apiSlice.reducerPath]: apiSlice.reducer
  },
  preloadedState: {
    session: { ...getSessionFromCookie() }
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware().concat(sessionListenerMiddleware.middleware, apiSlice.middleware)
});

export type AppDispatch = typeof store.dispatch;
export type RootState = ReturnType<typeof store.getState>;
