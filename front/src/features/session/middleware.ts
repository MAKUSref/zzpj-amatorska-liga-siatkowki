/* eslint-disable no-unused-vars */
import { isAnyOf, createListenerMiddleware } from '@reduxjs/toolkit';
import { EXTENDED_ROLES } from '../api/types';
import { RootState } from '../store';
import { logout, setToken } from './sessionSlice';
import { JwtTokenInfo, SessionSliceState } from './types';
import Cookie from 'js-cookie';
import jwtDecode from 'jwt-decode';

const LOCAL_SESSION_KEY = 'token';

export const sessionListenerMiddleware = createListenerMiddleware();
export const saveCookieMiddleware = createListenerMiddleware();

sessionListenerMiddleware.startListening({
  matcher: isAnyOf(logout),
  effect: () => {
    Cookie.set(LOCAL_SESSION_KEY, '', {
      expires: -1
    });
  }
});

saveCookieMiddleware.startListening({
  matcher: isAnyOf(setToken),
  effect: (action, listenerApi) => {
    const token = (listenerApi.getState() as RootState).session.token ?? '';

    Cookie.set(LOCAL_SESSION_KEY, token, { expires: new Date(Date.now() + 30 * 60000), path: '/' });
  }
});

export function preloadToken(): SessionSliceState {
  const token = Cookie.get(LOCAL_SESSION_KEY);
  const tokenInfo: JwtTokenInfo = token
    ? (jwtDecode(token) as JwtTokenInfo)
    : { role: [EXTENDED_ROLES.GUEST] };

  return { token, tokenInfo };
}
