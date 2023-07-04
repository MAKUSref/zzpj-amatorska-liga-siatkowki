import { useEffect, useState } from 'react';
import { Button, CircularProgress, TextField } from '@mui/material';
import CardContent from '@mui/material/CardContent';
import Card from '@mui/material/Card';
import { useTranslation } from 'react-i18next';
import { useForm } from 'react-hook-form';
import { useAppDispatch } from '../../../features/hooks';
import { yupResolver } from '@hookform/resolvers/yup';
import * as Yup from 'yup';
import {
  useEditAccountMutation,
  useGetSelfInfoQuery,
  useLazyRequestEmailChangeQuery
} from '../../../features/api/apiSlice';
import { toast } from 'react-toastify';
import { setEtag } from '../../../features/session/sessionSlice';

interface AccountInfo {
  name: string;
  lastname: string;
}

const AccountInformation = () => {
  const [loading, setLoading] = useState(false);
  const { data: accountInfo } = useGetSelfInfoQuery();

  const { t } = useTranslation();
  const [editAccount] = useEditAccountMutation();
  const [requestEmailChange] = useLazyRequestEmailChangeQuery();
  const dispatch = useAppDispatch();

  const changeAccountSchema = Yup.object().shape({
    name: Yup.string().required(t('profile.account_info.validation.name.required') as string),
    lastname: Yup.string().required(
      t('profile.account_info.validation.lastname.required') as string
    )
  });

  const {
    register,
    handleSubmit,
    formState: { errors }
  } = useForm<AccountInfo>({
    defaultValues: {
      name: accountInfo?.payload.name,
      lastname: accountInfo?.payload.lastname
    },
    resolver: yupResolver(changeAccountSchema)
  });

  const handleAcceptUpdateData = ({ name, lastname }: AccountInfo) => {
    editAccount({
      name,
      lastname,
      id: accountInfo?.payload?.id ?? '',
      version: accountInfo?.payload?.version ?? 0
    })
      .unwrap()
      .then(() => {
        toast.success(t('profilePage.info.infoChanged'));
      })
      .catch((err) => {
        toast.error(t(err.data));
      });
  };

  const handleRequestEmailChange = () => {
    setLoading(true);

    requestEmailChange()
      .then(() => {
        toast.success(t('profilePage.info.mailSent') as string);
        setLoading(false);
      })
      .catch((err) => {
        toast.error(t(err.data));
        setLoading(false);
      });
  };

  useEffect(() => {
    dispatch(setEtag(accountInfo?.etag));
  }, [accountInfo]);

  return (
    <>
      <Card>
        <CardContent>
          <form onSubmit={handleSubmit(handleAcceptUpdateData)}>
            <p>{t('profilePage.info.profileInfo')}</p>

            <div className="mt-3">
              <TextField
                {...register('name')}
                label={t('profilePage.info.labelName')}
                size="small"
                fullWidth
                error={!!errors.name}
                helperText={errors.name?.message}
              />
            </div>

            <div className="mt-3">
              <TextField
                {...register('lastname')}
                label={t('profilePage.info.labelLastName')}
                size="small"
                fullWidth
                error={!!errors.lastname}
                helperText={errors.lastname?.message}
              />
            </div>

            <div className="mt-3 text-center">
              <Button variant="contained" type="submit">
                {t('profilePage.info.save')}
              </Button>
              <Button
                className="ms-3"
                variant="outlined"
                onClick={handleRequestEmailChange}
                disabled={loading}>
                {!loading ? (
                  <span>{t('profilePage.info.changeMail')}</span>
                ) : (
                  <CircularProgress sx={{ width: '24px!important', height: '24px!important' }} />
                )}
              </Button>
            </div>
          </form>
        </CardContent>
      </Card>
    </>
  );
};

export default AccountInformation;
