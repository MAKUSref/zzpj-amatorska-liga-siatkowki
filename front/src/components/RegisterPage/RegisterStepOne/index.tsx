import { useNavigate } from 'react-router-dom';
import { FormProvider, useForm } from 'react-hook-form';
import { useAppDispatch, useAppSelector } from '../../../features/hooks';
import { setCurrentStep, updateRegisterInfo } from '../../../features/register/registerSlice';
import * as Yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import { Button } from '@mui/material';
import LoginInput from './LoginInput';
import EmailInput from './EmailInput';
import { useEffect } from 'react';
import { Pathnames } from '../../../routes/pathnames';

interface StepOneFormSchema {
  login: string;
  email: string;
}

const stepOneSchema = Yup.object().shape({
  login: Yup.string().required('Login jest wymagany'),
  email: Yup.string().required('Email jest wymagany').email('Podany e-mail jest niepoprawny')
});

const RegisterStepOne = () => {
  const registerState = useAppSelector((state) => state.register.registerAccountInfo);
  const navigate = useNavigate();
  const dispatch = useAppDispatch();

  const methods = useForm<StepOneFormSchema>({
    defaultValues: {
      login: registerState?.login ?? '',
      email: registerState?.email ?? ''
    },
    resolver: yupResolver(stepOneSchema)
  });

  const handleNavigateToLoginPage = () => {
    navigate(Pathnames.login.fullPath);
  };

  const handleNavigateToStepoTwo = () => {
    navigate(Pathnames.registerStepTwo.fullPath);
  };

  const handleAcceptStepOne = ({ login, email }: { login: string; email: string }) => {
    handleNavigateToStepoTwo();
    dispatch(updateRegisterInfo({ login, email }));
  };

  useEffect(() => {
    dispatch(setCurrentStep(0));
  }, []);

  return (
    <FormProvider {...methods}>
      <form onSubmit={methods.handleSubmit(handleAcceptStepOne)}>
        <div className="mt-3">
          <LoginInput />
        </div>

        <div className="mt-4">
          <EmailInput />
        </div>

        <p className="my-4 text-muted fw-semibold">
          Masz już konto?{' '}
          <span className="text-link" onClick={handleNavigateToLoginPage}>
            Zaloguj się
          </span>
          .
        </p>

        <div className="mt-3">
          <Button variant="contained" type="submit">
            Dalej
          </Button>
        </div>
      </form>
    </FormProvider>
  );
};

export default RegisterStepOne;
