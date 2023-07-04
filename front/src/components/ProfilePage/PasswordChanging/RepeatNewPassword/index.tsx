import { useFormContext } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import PasswordComponent from '../../../shared/PasswordComponent';

function RepeatNewPassword() {
  const { t } = useTranslation();
  const {
    register,
    formState: { errors }
  } = useFormContext<{ [p: string]: string }>();

  return (
    <PasswordComponent
      register={register}
      errors={errors}
      label={t('profile.change_password.repeat_new_password')}
      controlId={{
        cammel: 'repeatNewPassword',
        kebab: 'repeat-new-password'
      }}
    />
  );
}

export default RepeatNewPassword;
