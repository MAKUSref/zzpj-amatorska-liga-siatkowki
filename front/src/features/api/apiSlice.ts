import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import { RootState } from '../store';
import { RegisterRequest } from './types';
import { registerDataToRegisterRequestFull } from './utils';

export const apiSlice = createApi({
  reducerPath: 'api',
  baseQuery: fetchBaseQuery({
    baseUrl: '/api',
    prepareHeaders: (headers, { getState }) => {
      headers.set('Content-Type', 'application/json');

      const session = (getState() as RootState).session;
      if (session.token) {
        headers.set('Authorization', `Bearer ${session.token}`);
      }

      return headers;
    }
  }),
  tagTypes: [],
  endpoints(builder) {
    return {
      // this endpoint is going to be deleted
      getPing: builder.query<string, void>({
        query: () => '/account/ping'
      }),

      // user
      register: builder.mutation<unknown, RegisterRequest>({
        query: (data) => ({
          url: '/account/register',
          method: 'POST',
          body: registerDataToRegisterRequestFull(data)
        })
      })
    };
  }
});

export const { useGetPingQuery, useRegisterMutation } = apiSlice;
