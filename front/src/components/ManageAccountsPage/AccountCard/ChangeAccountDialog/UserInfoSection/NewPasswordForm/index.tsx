import { yupResolver } from '@hookform/resolvers/yup';
import { Button, CircularProgress, TextField } from '@mui/material';
import { useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { useParams } from 'react-router-dom';
import { toast } from 'react-toastify';
import * as Yup from 'yup';
import {
  useChangeAccountPasswordMutation,
  useGetAccountByIdQuery
} from '../../../../../../features/api/apiSlice';
import { useAppDispatch } from '../../../../../../features/hooks';
import { setEtag } from '../../../../../../features/session/sessionSlice';
import { useTranslation } from 'react-i18next';

interface NewPasswordFormSchema {
  password: string;
  confirmPassword: string;
}

const NewPasswordForm = () => {
  const { accountId } = useParams();
  const { data: accountInfo } = useGetAccountByIdQuery(accountId ?? '');
  const [changeAccountPassword, { isLoading, isSuccess }] = useChangeAccountPasswordMutation();
  const dispatch = useAppDispatch();
  const { t } = useTranslation();

  const newPasswordFormSchema = Yup.object().shape({
    password: Yup.string()
      .required(
        t(
          'manageAccountsPage.accountCard.changeAccountDialog.userInfoSection.newPasswordForm.passwordRequired'
        ) as string
      )
      .min(
        8,
        t(
          'manageAccountsPage.accountCard.changeAccountDialog.userInfoSection.newPasswordForm.passwordChar'
        ) as string
      ),
    confirmPassword: Yup.string()
      .required(
        t(
          'manageAccountsPage.accountCard.changeAccountDialog.userInfoSection.newPasswordForm.noRepeatedPassword'
        ) as string
      )
      .oneOf(
        [Yup.ref('password')],
        t(
          'manageAccountsPage.accountCard.changeAccountDialog.userInfoSection.newPasswordForm.samePasswords'
        ) as string
      )
  });

  const {
    handleSubmit,
    register,
    formState: { errors }
  } = useForm<NewPasswordFormSchema>({
    resolver: yupResolver(newPasswordFormSchema)
  });

  const handleChangeAccountPassword = ({ password }: NewPasswordFormSchema) => {
    if (accountId) {
      dispatch(setEtag(accountInfo?.etag));
      changeAccountPassword({
        id: accountId,
        version: accountInfo?.payload?.version ?? 0,
        newPassword: password
      })
        .unwrap()
        .catch((err) => toast.error(t(err.data)));
    }
  };

  useEffect(() => {
    if (isSuccess) {
      toast.success(
        t(
          'manageAccountsPage.accountCard.changeAccountDialog.userInfoSection.newPasswordForm.passwordChangeSuccess'
        )
      );
    }
  }, [isSuccess]);

  return (
    <form onSubmit={handleSubmit(handleChangeAccountPassword)}>
      <div className="my-3">
        <TextField
          {...register('password')}
          fullWidth
          size="small"
          label={t(
            'manageAccountsPage.accountCard.changeAccountDialog.userInfoSection.newPasswordForm.labelNewPassword'
          )}
          type="password"
          error={!!errors.password}
          helperText={errors.password?.message}
        />
      </div>
      <div className="my-3">
        <TextField
          {...register('confirmPassword')}
          fullWidth
          size="small"
          label={t(
            'manageAccountsPage.accountCard.changeAccountDialog.userInfoSection.newPasswordForm.labelRepeatPassword'
          )}
          type="password"
          error={!!errors.confirmPassword}
          helperText={errors.confirmPassword?.message}
        />
      </div>
      <Button className="ms-auto d-block" type="submit" disabled={isLoading} size="small">
        {isLoading ? (
          <CircularProgress
            className="my-1"
            sx={{ width: '16px!important', height: '16px!important' }}
          />
        ) : (
          t(
            'manageAccountsPage.accountCard.changeAccountDialog.userInfoSection.newPasswordForm.changePassword'
          )
        )}
      </Button>
    </form>
  );
};

export default NewPasswordForm;
