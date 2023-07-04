import { EXTENDED_ROLES } from '../api/types';

export interface SessionSliceState {
  token?: string;
  etag?: string;
  tokenInfo: JwtTokenInfo;
}

export interface JwtTokenInfo {
  role: EXTENDED_ROLES[];
  jti?: string;
  iat?: number;
  exp?: number;
}
