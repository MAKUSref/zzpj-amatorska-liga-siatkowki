import { yupResolver } from '@hookform/resolvers/yup';
import { Button, Card, CardContent } from '@mui/material';
import { FormProvider, useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { toast } from 'react-toastify';
import * as Yup from 'yup';
import { useChangePasswordMutation, useGetSelfInfoQuery } from '../../../features/api/apiSlice';
import NewPassword from './NewPassword';
import RepeatNewPassword from './RepeatNewPassword';
import OldPassword from './OldPassword';
import { useAppDispatch } from '../../../features/hooks';
import { setEtag } from '../../../features/session/sessionSlice';

interface ChangingPassword {
  oldPassword: string;
  newPassword: string;
  repeatNewPassword: string;
}

function PasswordChanging() {
  const { t } = useTranslation();

  const changePasswordSchema = Yup.object().shape({
    oldPassword: Yup.string().required(t('profilePage.password.oldPasswordRequired') as string),
    newPassword: Yup.string()
      .required(t('profile.change_password.validation.new_password.required') as string)
      .min(8, t('profilePage.password.passwordChar') as string),
    repeatNewPassword: Yup.string()
      .required(t('profile.change_password.validation.repeat_new_password.required') as string)
      .oneOf([Yup.ref('newPassword')], t('profilePage.password.samePasswords') as string)
  });

  const { data: selfInfo } = useGetSelfInfoQuery();
  const dispatch = useAppDispatch();

  const methods = useForm<ChangingPassword>({
    resolver: yupResolver(changePasswordSchema)
  });

  const [changePassword] = useChangePasswordMutation();

  const handlePasswordUpdateData = ({ oldPassword, newPassword }: ChangingPassword) => {
    dispatch(setEtag(selfInfo?.etag));
    changePassword({
      id: selfInfo?.payload?.id ?? '',
      version: selfInfo?.payload?.version ?? 0,
      oldPassword,
      newPassword
    })
      .unwrap()
      .then(() => {
        toast.success(t('profilePage.password.passwordChanged'));
      })
      .catch((err) => {
        toast.error(t(err.data));
      });
  };

  return (
    <Card>
      <CardContent>
        <FormProvider {...methods}>
          <form onSubmit={methods.handleSubmit(handlePasswordUpdateData)}>
            <p>{t('profilePage.password.passwordChange')}</p>
            <div className="mt-4">
              <OldPassword />
            </div>
            <div className="mt-4">
              <NewPassword />
            </div>
            <div className="mt-4">
              <RepeatNewPassword />
            </div>

            <div className="mt-3 text-center">
              <Button variant="contained" type="submit">
                {t('profilePage.password.changePassword')}
              </Button>
            </div>
          </form>
        </FormProvider>
      </CardContent>
    </Card>
  );
}

export default PasswordChanging;
