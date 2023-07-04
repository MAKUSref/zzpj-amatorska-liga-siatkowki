import { Button, Step, StepLabel, Stepper } from '@mui/material';
import { useAppSelector } from '../../features/hooks';
import IntroContainer from '../IntroContainer';
import KeyboardBackspaceIcon from '@mui/icons-material/KeyboardBackspace';
import './style.scss';
import { Outlet, useNavigate } from 'react-router-dom';
import { Pathnames } from '../../routes/pathnames';

const RegisterPage = () => {
  const navigate = useNavigate();

  const currentStep = useAppSelector((state) => state.register.currentStep);
  return (
    <IntroContainer>
      <div className="register-form card border-0">
        <Stepper className="mb-5" activeStep={currentStep} alternativeLabel>
          <Step>
            <StepLabel>Login i Email</StepLabel>
          </Step>
          <Step>
            <StepLabel>Hasło</StepLabel>
          </Step>
          <Step>
            <StepLabel>Dane Personalne</StepLabel>
          </Step>
        </Stepper>
        <h3 className="mb-3">Rejestracja</h3>
        <Outlet />
        <div>
          <Button
            size="small"
            className="mt-3"
            variant="text"
            onClick={() => navigate(Pathnames.home.fullPath)}>
            <KeyboardBackspaceIcon /> <span className="ms-2">Strona główna</span>
          </Button>
        </div>
      </div>
    </IntroContainer>
  );
};

export default RegisterPage;
