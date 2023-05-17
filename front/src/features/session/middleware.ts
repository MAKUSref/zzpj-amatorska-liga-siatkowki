/* eslint-disable no-unused-vars */
import { isAnyOf, createListenerMiddleware } from '@reduxjs/toolkit';
import { RootState } from '../store';
import { setToken } from './sessionSlice';

const LOCAL_SESSION_KEY = 'ssbd-session';

export const sessionListenerMiddleware = createListenerMiddleware();

sessionListenerMiddleware.startListening({
  matcher: isAnyOf(setToken),
  effect: (action, listenerApi) => {
    const session = (listenerApi.getState() as RootState).session;
    // set token to cookie
  }
});

export function getSessionFromCookie() {
  // get token from cookie and return it
  return { token: undefined };
}
