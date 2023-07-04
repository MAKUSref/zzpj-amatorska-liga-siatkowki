import { yupResolver } from '@hookform/resolvers/yup';
import { Button, CircularProgress, TextField } from '@mui/material';
import { useForm } from 'react-hook-form';
import { useParams } from 'react-router-dom';
import { toast } from 'react-toastify';
import * as Yup from 'yup';
import {
  useEditAccountAccountMutation,
  useGetAccountByIdQuery
} from '../../../../../../features/api/apiSlice';
import { useAppDispatch } from '../../../../../../features/hooks';
import { setEtag } from '../../../../../../features/session/sessionSlice';
import { useTranslation } from 'react-i18next';

interface PersonalInfoFormSchema {
  name: string;
  lastname: string;
}

const PersonalInfoForm = () => {
  const { accountId } = useParams();
  const { data: accountInfo } = useGetAccountByIdQuery(accountId ?? '');
  const { t } = useTranslation();
  const [editAccountAccount, { isLoading }] = useEditAccountAccountMutation();
  const dispatch = useAppDispatch();

  const personalInfoFormSchema = Yup.object().shape({
    name: Yup.string().required(
      t(
        'manageAccountsPage.accountCard.changeAccountDialog.userInfoSection.personalInfoForm.nameRequired'
      ) as string
    ),
    lastname: Yup.string().required(
      t(
        'manageAccountsPage.accountCard.changeAccountDialog.userInfoSection.personalInfoForm.lastNameRequired'
      ) as string
    )
  });

  const {
    handleSubmit,
    register,
    formState: { errors }
  } = useForm<PersonalInfoFormSchema>({
    defaultValues: {
      name: accountInfo?.payload?.name ?? '',
      lastname: accountInfo?.payload?.lastname ?? ''
    },
    resolver: yupResolver(personalInfoFormSchema)
  });

  const handleEditAccountInfo = ({ name, lastname }: PersonalInfoFormSchema) => {
    if (!accountInfo?.payload?.id && !accountInfo?.payload?.version && !accountInfo?.payload?.login)
      return;

    dispatch(setEtag(accountInfo.etag));

    editAccountAccount({
      id: accountInfo.payload.id as string,
      version: accountInfo.payload.version as number,
      login: accountInfo.payload.login as string,
      name,
      lastname
    })
      .unwrap()
      .then(() =>
        toast.success(
          t(
            'manageAccountsPage.accountCard.changeAccountDialog.userInfoSection.personalInfoForm.dataChangedSuccess'
          ) as string
        )
      )
      .catch((err) => toast.error(t(err.data)));
  };

  return (
    <form onSubmit={handleSubmit(handleEditAccountInfo)}>
      <div className="my-3">
        <TextField
          {...register('name')}
          fullWidth
          size="small"
          label={t(
            'manageAccountsPage.accountCard.changeAccountDialog.userInfoSection.personalInfoForm.labelName'
          )}
          error={!!errors.name}
          helperText={errors.name?.message}
        />
      </div>
      <div className="my-3">
        <TextField
          {...register('lastname')}
          fullWidth
          size="small"
          label={t(
            'manageAccountsPage.accountCard.changeAccountDialog.userInfoSection.personalInfoForm.labelLastName'
          )}
          error={!!errors.lastname}
          helperText={errors.lastname?.message}
        />
      </div>
      <Button className="ms-auto d-block" type="submit" size="small" disabled={isLoading}>
        {isLoading && (
          <CircularProgress
            className="mx-2 mt-2"
            sx={{ width: '14px!important', height: '14px!important' }}
          />
        )}
        {t(
          'manageAccountsPage.accountCard.changeAccountDialog.userInfoSection.personalInfoForm.saveChanges'
        )}
      </Button>
    </form>
  );
};

export default PersonalInfoForm;
