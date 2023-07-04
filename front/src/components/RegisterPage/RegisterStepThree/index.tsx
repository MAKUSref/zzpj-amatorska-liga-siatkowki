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
import { MANAGEMENT_ROLES } from '../../../features/api/types';
import { Pathnames } from '../../../routes/pathnames';
import { useTranslation } from 'react-i18next';

interface StepThreeSchema {
  name: string;
  lastname: string;
  locale: string;
  role: MANAGEMENT_ROLES;
}

const stepThreeSchema = Yup.object().shape({
  name: Yup.string().required('Imie jest wymagane'),
  lastname: Yup.string().required('Nazwisko jest wymagane'),
  locale: Yup.string().required(),
  role: Yup.string().required()
});

const RegisterStepThree = () => {
  const registerState = useAppSelector((state) => state.register.registerAccountInfo);

  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const [registerMutation] = useRegisterMutation();
  const { t } = useTranslation();

  const methods = useForm<StepThreeSchema>({
    defaultValues: {
      name: registerState?.name ?? '',
      lastname: registerState?.lastname ?? '',
      locale: registerState?.locale ?? 'pl',
      role: registerState?.role ?? MANAGEMENT_ROLES.MANAGER
    },
    resolver: yupResolver(stepThreeSchema)
  });

  const handleNavigateToLoginPage = () => {
    navigate(Pathnames.login.fullPath);
  };

  const handleNavigateToStepoTwo = () => {
    navigate(Pathnames.registerStepTwo.fullPath);
  };

  const handleAcceptStepThree = async (data: StepThreeSchema) => {
    dispatch(updateRegisterInfo({ ...data }));

    registerMutation({ ...registerState, ...data })
      .unwrap()
      .then(() => {
        dispatch(clearRegisterInfo());
        navigate(Pathnames.registerSuccess.fullPath);
      })
      .catch((error) => {
        toast.error(t(error.data));
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
