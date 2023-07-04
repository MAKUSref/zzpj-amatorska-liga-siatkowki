import { useNavigate } from 'react-router-dom';
import { FormProvider, useForm } from 'react-hook-form';
import { useAppDispatch, useAppSelector } from '../../../features/hooks';
import { setCurrentStep, updateRegisterInfo } from '../../../features/register/registerSlice';
import * as Yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import { Button } from '@mui/material';
import PasswordInput from './PasswordInput';
import ConfirmPasswordInput from './ConfirmPasswordInput';
import { useEffect } from 'react';
import { Pathnames } from '../../../routes/pathnames';

interface StepTwoSchema {
  password: string;
  confirmPassword: string;
}

const stepTwoSchema = Yup.object().shape({
  password: Yup.string()
    .required('Hasło jest wymagane')
    .min(8, 'Hasło musi mieć co najmniej 8 znaków'),
  confirmPassword: Yup.string()
    .required('Musisz potwierdzić hasło')
    .oneOf([Yup.ref('password')], 'Hasła muszą być takie same')
});

const RegisterStepTwo = () => {
  const registerState = useAppSelector((state) => state.register.registerAccountInfo);
  const navigate = useNavigate();
  const dispatch = useAppDispatch();

  const methods = useForm<StepTwoSchema>({
    defaultValues: {
      password: registerState?.password ?? '',
      confirmPassword: ''
    },
    resolver: yupResolver(stepTwoSchema)
  });

  const handleNavigateToLoginPage = () => {
    navigate(Pathnames.login.fullPath);
  };

  const handleNavigateToStepOne = () => {
    navigate(Pathnames.registerStepOne.fullPath);
  };

  const handleNavigateToStepThree = () => {
    navigate(Pathnames.registerStepThree.fullPath);
  };

  const handleAcceptStepTwo = ({ password }: StepTwoSchema) => {
    handleNavigateToStepThree();
    dispatch(updateRegisterInfo({ password }));
  };

  useEffect(() => {
    dispatch(setCurrentStep(1));
  }, []);

  return (
    <FormProvider {...methods}>
      <form onSubmit={methods.handleSubmit(handleAcceptStepTwo)}>
        <div>
          <PasswordInput />
        </div>

        <div className="mt-4">
          <ConfirmPasswordInput />
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
          <Button className="ms-2" variant="text" onClick={handleNavigateToStepOne}>
            Powrót
          </Button>
        </div>
      </form>
    </FormProvider>
  );
};

export default RegisterStepTwo;
