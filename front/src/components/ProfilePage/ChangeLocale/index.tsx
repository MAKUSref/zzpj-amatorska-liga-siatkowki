import CardContent from '@mui/material/CardContent';
import Card from '@mui/material/Card';
import { Button, FormControl, InputLabel, MenuItem, Select } from '@mui/material';
import { useChangeLocaleMutation, useGetSelfInfoQuery } from '../../../features/api/apiSlice';
import { yupResolver } from '@hookform/resolvers/yup';
import { useForm } from 'react-hook-form';
import * as Yup from 'yup';
import { useAppDispatch } from '../../../features/hooks';
import { setEtag } from '../../../features/session/sessionSlice';
import { toast } from 'react-toastify';
import { useTranslation } from 'react-i18next';

interface LocaleChangeSchema {
  locale: string;
}

const ChangeLocale = () => {
  const { data: accountInfo } = useGetSelfInfoQuery();
  const dispatch = useAppDispatch();
  const [changeLocale, { isLoading }] = useChangeLocaleMutation();
  const { t } = useTranslation();
  const localeChangeSchema = Yup.object().shape({
    locale: Yup.string().required(t('profilePage.changeLocale.mailRequired') as string)
  });

  const { register, handleSubmit } = useForm<LocaleChangeSchema>({
    resolver: yupResolver(localeChangeSchema)
  });

  const handleChangeLocale = ({ locale }: LocaleChangeSchema) => {
    if (!accountInfo) return;

    const { id, version } = accountInfo.payload;

    dispatch(setEtag(accountInfo.etag));

    changeLocale({
      id: id ?? '',
      version: version ?? 0,
      locale
    })
      .unwrap()
      .then(() => toast.success(t('profilePage.changeLocale.languageChanged')))
      .catch((err) => toast.error(t(err.data)));
  };

  return (
    <Card>
      <CardContent>
        <form onSubmit={handleSubmit(handleChangeLocale)}>
          <p>{t('profilePage.changeLocale.changeLanguage')}</p>
          <div className="mt-3">
            <FormControl size="small" fullWidth>
              <InputLabel id="demo-simple-select-label">
                {t('profilePage.changeLocale.language')}
              </InputLabel>
              <Select
                {...register('locale')}
                labelId="demo-simple-select-label"
                label={t('profilePage.changeLocale.labelLanguage')}
                defaultValue={accountInfo?.payload?.locale ?? 'pl'}>
                <MenuItem value="pl">{t('profilePage.changeLocale.polish')}</MenuItem>
                <MenuItem value="en">{t('profilePage.changeLocale.english')}</MenuItem>
              </Select>
            </FormControl>
          </div>

          <div className="mt-3 text-center">
            <Button variant="contained" type="submit" disabled={isLoading}>
              {t('profilePage.changeLocale.save')}
            </Button>
          </div>
        </form>
      </CardContent>
    </Card>
  );
};

export default ChangeLocale;
