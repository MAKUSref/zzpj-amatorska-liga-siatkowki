import { useFormContext } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import PasswordComponent from '../../../shared/PasswordComponent';

function NewPassword() {
  const {
    register,
    formState: { errors }
  } = useFormContext<{ [p: string]: string }>();
  const { t } = useTranslation();
  return (
    <PasswordComponent
      register={register}
      errors={errors}
      label={t('profilePage.password.labelPassword')}
      controlId={{
        cammel: 'oldPassword',
        kebab: 'old-password'
      }}
    />
  );
}

export default NewPassword;
