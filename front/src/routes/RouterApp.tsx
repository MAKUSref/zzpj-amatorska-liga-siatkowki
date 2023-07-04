import { Outlet, Route, Routes } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import { useAppSelector } from '../features/hooks';
import { EXTENDED_ROLES } from '../features/api/types';
import { accountRolesIncludes } from '../utils';
import {
  AdminRoutes,
  ManagementRoutes,
  PublicRoutes,
  RefereeRoutes,
  AuthRoutes
} from './AccountsRoutes';
import { RouteType } from './types';
import { useCallback } from 'react';
import DefaultContainer from '../components/DefaultContainer';
import Page404 from '../components/Page404';
import { Container } from 'react-bootstrap';

const RouterApp = () => {
  const currentRoles = useAppSelector((state) => state.session.tokenInfo.role);

  const rolesIncludes = (roles: EXTENDED_ROLES[]): boolean => {
    return accountRolesIncludes(currentRoles ?? [], roles);
  };

  const generateRoutes = useCallback(
    (routes: RouteType[]) =>
      routes?.map(({ element, path, children }, i) => {
        if (children) {
          return (
            <Route key={i} path={path} element={element}>
              {generateRoutes(children)}
            </Route>
          );
        }

        return <Route key={i} path={path} element={element} />;
      }),
    []
  );

  return (
    <Routes>
      <Route
        path="/"
        element={
          <>
            <ToastContainer hideProgressBar={true} autoClose={1500} />
            <Outlet />
          </>
        }>
        <Route path="" element={<DefaultContainer />}>
          {rolesIncludes([EXTENDED_ROLES.ADMIN]) && generateRoutes(AdminRoutes)}
          {rolesIncludes([EXTENDED_ROLES.REFREE]) && generateRoutes(RefereeRoutes)}
          {rolesIncludes([EXTENDED_ROLES.CAPTAIN, EXTENDED_ROLES.COACH, EXTENDED_ROLES.MANAGER]) &&
            generateRoutes(ManagementRoutes)}
          {generateRoutes(PublicRoutes)}

          <Route path="*" element={<Page404 />} />
        </Route>

        {generateRoutes(AuthRoutes)}
      </Route>
    </Routes>
  );
};

export default RouterApp;
