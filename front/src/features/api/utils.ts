import { RegisterRequest, RegisterRequestFull } from './types';

export function registerDataToRegisterRequestFull({
  email,
  lastname,
  locale,
  login,
  name,
  password
}: RegisterRequest): RegisterRequestFull {
  return {
    userData: {
      version: Date.now(),
      login,
      password,
      name,
      lastname,
      email,
      isActive: true,
      isApproved: true,
      isBlocked: false,
      loginTimestamp: '2023-05-11T23:18:06Z',
      locale
    },
    password,
    managerData: {
      version: Date.now(),
      teamId: null
    }
  };
}
