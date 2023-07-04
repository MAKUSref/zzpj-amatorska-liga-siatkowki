import { RegisterRequest, RegisterRequestFull } from './types';

export function registerDataToRegisterRequestFull({
  email,
  lastname,
  locale,
  login,
  name,
  password,
  role
}: RegisterRequest): RegisterRequestFull {
  return {
    accountData: {
      version: Date.now(),
      login,
      name,
      lastname,
      email,
      active: false,
      approved: true,
      blocked: false,
      loginTimestamp: '2023-05-11T23:18:06Z',
      locale
    },
    password,
    role: {
      role: role?.toString()
    }
  };
}
