import { DialogContentText } from '@mui/material';
import { useMemo } from 'react';
import { Col, Row } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { useParams } from 'react-router-dom';
import {
  useAddRoleMutation,
  useGetAccountByIdQuery,
  useRemoveRoleMutation
} from '../../../../../features/api/apiSlice';
import { ROLES } from '../../../../../features/api/types';
import { useAppDispatch } from '../../../../../features/hooks';
import { setEtag } from '../../../../../features/session/sessionSlice';
import ChangeRole from './ChangeRole';

const RolesSection = () => {
  const { accountId } = useParams();
  const { data: accountInfo } = useGetAccountByIdQuery(accountId ?? '');

  const dispatch = useAppDispatch();
  const [addRole] = useAddRoleMutation();
  const [removeRole] = useRemoveRoleMutation();
  const { t } = useTranslation();
  const lastRoleLeft: boolean = useMemo(() => {
    return accountInfo?.payload?.roles?.length === 1;
  }, [accountInfo]);

  const accountHasRole = (roleToCheck: ROLES): boolean => {
    return (
      (accountInfo?.payload?.roles?.filter((role) => role?.role === roleToCheck.toString())
        .length ?? 0) > 0
    );
  };

  const roleCannotBeChanged = (role: ROLES) => {
    if (!accountInfo?.payload?.active) return true;
    return lastRoleLeft && accountHasRole(role);
  };

  const handleChangeRole = (role: ROLES) => {
    dispatch(setEtag(accountInfo?.etag));

    if (accountHasRole(role)) {
      removeRole({
        id: accountInfo?.payload?.id ?? '',
        login: accountInfo?.payload?.login ?? '',
        version: accountInfo?.payload?.version ?? 0,
        role: { role, teamId: null }
      });
    } else {
      addRole({
        id: accountInfo?.payload?.id ?? '',
        login: accountInfo?.payload?.login ?? '',
        version: accountInfo?.payload?.version ?? 0,
        role: { role, teamId: null }
      });
    }
  };

  return (
    <Row>
      <DialogContentText className="mb-3">
        {t('manageAccountsPage.accountCard.changeAccountDialog.rolesSelection.userRoles')}
      </DialogContentText>
      <Col sx={12} md={6}>
        <ChangeRole
          label={
            t('manageAccountsPage.accountCard.changeAccountDialog.rolesSelection.manager') as string
          }
          defaultChecked={accountHasRole(ROLES.MANAGER)}
          onApprove={() => handleChangeRole(ROLES.MANAGER)}
          onDisapprove={() => handleChangeRole(ROLES.MANAGER)}
          disabled={roleCannotBeChanged(ROLES.MANAGER)}
        />

        <ChangeRole
          label={
            t('manageAccountsPage.accountCard.changeAccountDialog.rolesSelection.coach') as string
          }
          defaultChecked={accountHasRole(ROLES.COACH)}
          onApprove={() => handleChangeRole(ROLES.COACH)}
          onDisapprove={() => handleChangeRole(ROLES.COACH)}
          disabled={roleCannotBeChanged(ROLES.COACH)}
        />

        <ChangeRole
          label={
            t('manageAccountsPage.accountCard.changeAccountDialog.rolesSelection.captain') as string
          }
          defaultChecked={accountHasRole(ROLES.CAPTAIN)}
          onApprove={() => handleChangeRole(ROLES.CAPTAIN)}
          onDisapprove={() => handleChangeRole(ROLES.CAPTAIN)}
          disabled={roleCannotBeChanged(ROLES.CAPTAIN)}
        />
      </Col>
      <Col sx={12} md={6}>
        <ChangeRole
          label={
            t('manageAccountsPage.accountCard.changeAccountDialog.rolesSelection.referee') as string
          }
          defaultChecked={accountHasRole(ROLES.REFREE)}
          onApprove={() => handleChangeRole(ROLES.REFREE)}
          onDisapprove={() => handleChangeRole(ROLES.REFREE)}
          disabled={roleCannotBeChanged(ROLES.REFREE)}
        />
        <ChangeRole
          label="Administrator"
          defaultChecked={accountHasRole(ROLES.ADMIN)}
          onApprove={() => handleChangeRole(ROLES.ADMIN)}
          onDisapprove={() => handleChangeRole(ROLES.ADMIN)}
          disabled={roleCannotBeChanged(ROLES.ADMIN)}
        />
      </Col>
    </Row>
  );
};

export default RolesSection;
