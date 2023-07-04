import CardContent from '@mui/material/CardContent';
import Card from '@mui/material/Card';
import { Button, TextField } from '@mui/material';
import { useGetSelfInfoQuery } from '../../../features/api/apiSlice';
import { yupResolver } from '@hookform/resolvers/yup';
import { useForm } from 'react-hook-form';
import * as Yup from 'yup';
import { useTranslation } from 'react-i18next';

interface EmailChangeSchema {
  email: string;
}

const ChangeEmail = () => {
  const { data: accountInfo } = useGetSelfInfoQuery();
  const { t } = useTranslation();
  const emailChangeSchema = Yup.object().shape({
    email: Yup.string()
      .required(t('profilePage.changeMail.mailRequired') as string)
      .email(t('profilePage.changeMail.mailFormat') as string)
  });

  const {
    register,
    handleSubmit,
    formState: { errors }
  } = useForm<EmailChangeSchema>({
    resolver: yupResolver(emailChangeSchema)
  });

  return (
    <Card>
      <CardContent>
        <form onSubmit={handleSubmit(() => {})}>
          <p>{t('profilePage.changeMail.mailChange')}</p>
          <div className="mt-3">
            <TextField
              {...register('email')}
              label={t('profilePage.changeMail.labelMail')}
              size="small"
              fullWidth
              defaultValue={accountInfo?.payload?.email ?? ''}
              error={!!errors.email}
              helperText={errors.email?.message}
            />
          </div>

          <div className="mt-3 text-center">
            <Button variant="contained" type="submit">
              {t('profilePage.changeMail.changeMail')}
            </Button>
          </div>
        </form>
      </CardContent>
    </Card>
  );
};

export default ChangeEmail;
