/* eslint-disable no-unused-vars */
export enum MANAGEMENT_ROLES {
  MANAGER = 'Manager',
  CAPTAIN = 'Captain',
  COACH = 'Coach'
}

export interface RegisterRequest {
  login?: string;
  email?: string;
  password?: string;
  name?: string;
  lastname?: string;
  locale?: string;
}

export interface RegisterRequestFull {
  userData: {
    version: number;
    login?: string;
    password?: string;
    name?: string;
    lastname?: string;
    email?: string;
    isActive: boolean;
    isApproved: boolean;
    isBlocked: boolean;
    loginTimestamp: string;
    locale?: string;
  };
  password?: string;
  managerData: {
    version: number;
    teamId: null;
  };
}
