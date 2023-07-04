import { ReactNode } from 'react';

export interface Path {
  path: string;
  fullPath: string;
}

export interface Paths {
  [key: string]: Path;
}

export interface RouteType extends Path {
  element: ReactNode;
  children?: RouteType[];
}
