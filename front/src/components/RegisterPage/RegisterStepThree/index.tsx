import { useNavigate } from 'react-router-dom';
import { FormProvider, useForm } from 'react-hook-form';
import { useAppDispatch, useAppSelector } from '../../../features/hooks';
import * as Yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import {
  clearRegisterInfo,
  setCurrentStep,
  updateRegisterInfo
} from '../../../features/register/registerSlice';
import { Button } from '@mui/material';
import NameInput from './NameInput';
import LastNameInput from './LastNameInput';
import LocaleSelect from './LocaleSelect';
import RoleSelect from './RoleSelect';
import { useEffect } from 'react';
import { useRegisterMutation } from '../../../features/api/apiSlice';
import { toast } from 'react-toastify';

interface StepThreeSchema {
  name: string;
  lastname: string;
  locale: string;
}

const stepThreeSchema = Yup.object().shape({
  name: Yup.string().required('Imie jest wymagane'),
  lastname: Yup.string().required('Nazwisko jest wymagane'),
  locale: Yup.string().required()
});

const RegisterStepThree = () => {
  const registerState = useAppSelector((state) => state.register.registerAccountInfo);

  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const [registerMutation] = useRegisterMutation();

  const methods = useForm<StepThreeSchema>({
    defaultValues: {
      name: registerState?.name ?? '',
      lastname: registerState?.lastname ?? '',
      locale: registerState?.locale ?? 'pl'
    },
    resolver: yupResolver(stepThreeSchema)
  });

  const handleNavigateToLoginPage = () => {
    navigate('/login');
  };

  const handleNavigateToStepoTwo = () => {
    navigate('/register/step-two');
  };

  const handleAcceptStepThree = async (data: StepThreeSchema) => {
    dispatch(updateRegisterInfo({ ...data }));

    registerMutation({ ...registerState, ...data })
      .unwrap()
      .then(() => {
        dispatch(clearRegisterInfo());
        navigate('/register-success');
      })
      .catch((error) => {
        toast.error(error.data);
      });
  };

  useEffect(() => {
    dispatch(setCurrentStep(2));
  }, []);

  return (
    <FormProvider {...methods}>
      <form onSubmit={methods.handleSubmit(handleAcceptStepThree)}>
        <div>
          <NameInput />
        </div>

        <div className="mt-4">
          <LastNameInput />
        </div>

        <div className="mt-4">
          <LocaleSelect />
        </div>

        <div className="mt-4">
          <RoleSelect />
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
            Stwórz konto
          </Button>
          <Button className="ms-2" variant="text" onClick={handleNavigateToStepoTwo}>
            Powrót
          </Button>
        </div>
      </form>
    </FormProvider>
  );
};

export default RegisterStepThree;
