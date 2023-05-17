import IntroContainer from '../../IntroContainer';
import emailBg from '../../../imgs/email-bg.svg';
import confettiSvg from '../../../imgs/confetti.svg';
import { Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import './style.scss';

const RegisterSuccess = () => {
  const navigate = useNavigate();

  return (
    <IntroContainer image={emailBg} leftSideSize={4}>
      <div className="register-success-page text-center">
        <h3 className="text-primary fw-bolder">Gratulacje!</h3>
        <img className="confetti-img mt-3 mb-4" src={confettiSvg} alt="..." />
        <p className="text-muted">Rejestracja przebiegła pomyślnie.</p>
        <p className="text-muted">Wysłaliśmy na twojego maila link potwierdzający.</p>
        <p className="text-primary fw-bolder">Masz 12 godzin aby potwierdzić swoje konto!</p>
        <div className="d-flex justify-content-evenly mt-4">
          <Button variant="contained" onClick={() => navigate('/login')}>
            Zaloguj się
          </Button>
          <Button variant="outlined" onClick={() => navigate('/')}>
            Strona główna
          </Button>
        </div>
      </div>
    </IntroContainer>
  );
};

export default RegisterSuccess;
