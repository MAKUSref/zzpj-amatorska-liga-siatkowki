import { Avatar, DialogContentText } from '@mui/material';
import { useMemo } from 'react';
import { Row } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { useParams } from 'react-router-dom';
import { useGetAccountHistoryQuery } from '../../../../../features/api/apiSlice';
import { stringAvatar } from '../../../../../utils';
import './style.scss';

const HistorySection = () => {
  const { accountId } = useParams();
  const { data: userHistory } = useGetAccountHistoryQuery(accountId ?? '');
  const { t } = useTranslation();
  const historyDesc = useMemo(() => {
    return userHistory?.slice(0)?.reverse();
  }, [userHistory]);

  return (
    <Row>
      <DialogContentText className="mb-3">
        {t('manageAccountsPage.accountCard.changeAccountDialog.historySelection.history')}
      </DialogContentText>
      {historyDesc?.map((historyEvent, i) => (
        <div
          key={i}
          className="history-event my-2 d-flex justify-content-start align-items-center small">
          <Avatar
            {...stringAvatar(
              `${historyEvent.changedBy.name?.toUpperCase()} ${historyEvent.changedBy.lastname?.toUpperCase()}`
            )}
          />
          <span className="ms-3 fw-bold change-type">{historyEvent.changeType}</span>
          <span className="mx-1">
            {t('manageAccountsPage.accountCard.changeAccountDialog.historySelection.at')}
          </span>
          <span>{historyEvent.changedAt}</span>
          <span className="mx-1">
            {t('manageAccountsPage.accountCard.changeAccountDialog.historySelection.in')}
          </span>
          <span className="fw-bold change-type">{historyEvent.property}</span>
          <span className="mx-1">
            {t('manageAccountsPage.accountCard.changeAccountDialog.historySelection.by')}
          </span>
          <span>
            {historyEvent.changedBy.name} {historyEvent.changedBy.lastname}
          </span>
        </div>
      ))}
    </Row>
  );
};

export default HistorySection;
