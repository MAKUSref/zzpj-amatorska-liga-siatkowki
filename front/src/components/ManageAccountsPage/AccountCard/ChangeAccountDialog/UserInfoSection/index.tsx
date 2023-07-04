import { DialogContentText } from '@mui/material';
import { Col, Row } from 'react-bootstrap';
import PersonalInfoForm from './PersonalInfoForm';
import NewPasswordForm from './NewPasswordForm';
import { useTranslation } from 'react-i18next';

const AccountInfoSection = () => {
  const { t } = useTranslation();
  return (
    <Row>
      <DialogContentText>
        {t('manageAccountsPage.accountCard.changeAccountDialog.userInfoSection.userDetails')}
      </DialogContentText>
      <Col sx={12} md={6}>
        <PersonalInfoForm />
      </Col>
      <Col sx={12} md={6}>
        <NewPasswordForm />
      </Col>
    </Row>
  );
};

export default AccountInfoSection;
