import { useFormContext } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import PasswordComponent from '../../../shared/PasswordComponent';

function NewPassword() {
  const { t } = useTranslation();
  const {
    register,
    formState: { errors }
  } = useFormContext<{ [p: string]: string }>();

  return (
    <PasswordComponent
      register={register}
      errors={errors}
      label={t('profile.change_password.new_password')}
      controlId={{
        cammel: 'newPassword',
        kebab: 'new-password'
      }}
    />
  );
}

export default NewPassword;
