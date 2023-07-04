import { useForm } from 'react-hook-form';
import IntroContainer from '../IntroContainer';
import { yupResolver } from '@hookform/resolvers/yup';
import KeyboardBackspaceIcon from '@mui/icons-material/KeyboardBackspace';
import * as Yup from 'yup';
import { TextField } from '@mui/material';
import { Button } from '@mui/material';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { useResetPasswordMutation } from '../../features/api/apiSlice';
import { toast } from 'react-toastify';
import { Pathnames } from '../../routes/pathnames';
import { useTranslation } from 'react-i18next';
import { useMemo } from 'react';

interface CreateNewPasswordFormSchema {
  newPassword: string;
  repeatNewPassword: string;
}

const CreateNewPassword = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const [resetPasswordMutation] = useResetPasswordMutation();
  const { t } = useTranslation();

  const createNewPasswordFormSchema = useMemo(
    () =>
      Yup.object().shape({
        newPassword: Yup.string().required(t('createNewPassword.noGivenPassword') as string),
        repeatNewPassword: Yup.string()
          .required(t('createNewPassword.noRepeatedPassword') as string)
          .oneOf([Yup.ref('newPassword')], t('createNewPassword.differentPasswords') as string)
      }),
    []
  );

  const {
    handleSubmit,
    register,
    formState: { errors }
  } = useForm<CreateNewPasswordFormSchema>({
    resolver: yupResolver(createNewPasswordFormSchema)
  });

  const handleResetPassword = ({ newPassword }: CreateNewPasswordFormSchema) => {
    const token: string | null = searchParams.get('token');
    if (token) {
      resetPasswordMutation({ newPassword, token })
        .unwrap()
        .then(() => {
          toast.success(t('createNewPassword.passwordChangedSuccess'));
          navigate(Pathnames.login.fullPath);
        })
        .catch((err) => {
          toast.error(t(err.data));
        });
    } else {
      toast.error(t('exceptions.exceptionLinkExpired'));
    }
  };

  return (
    <IntroContainer>
      <div className="reset-password-form card border-0">
        <form onSubmit={handleSubmit(handleResetPassword)}>
          <h3 className="mb-3">{t('createNewPassword.resetPasswordInfo')}</h3>

          <p className="mb-4 text-muted fw-semibold">
            {t('createNewPassword.voleyballLeagueWelcomes')}
          </p>

          <div>
            <TextField
              {...register('newPassword')}
              size="small"
              className="newPassword-form-input"
              label={t('createNewPassword.labelNewPassword')}
              variant="outlined"
              error={!!errors.newPassword}
              helperText={errors.newPassword?.message}
            />
          </div>
          <div className="mt-3">
            <TextField
              {...register('repeatNewPassword')}
              size="small"
              className="repeatNewPassword-form-input"
              label={t('createNewPassword.labelRepeatPassword')}
              variant="outlined"
              error={!!errors.repeatNewPassword}
              helperText={errors.repeatNewPassword?.message}
            />
          </div>

          <div className="mt-3">
            <Button variant="contained" type="submit">
              {t('createNewPassword.confirmNewPassword')}
            </Button>
          </div>
          <Button
            size="small"
            className="mt-3"
            variant="text"
            onClick={() => navigate(Pathnames.home.fullPath)}>
            <KeyboardBackspaceIcon />
            <span className="ms-2">{t('createNewPassword.homePage')}</span>
          </Button>
        </form>
      </div>
    </IntroContainer>
  );
};

export default CreateNewPassword;
