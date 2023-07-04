import { useForm } from 'react-hook-form';
import IntroContainer from '../IntroContainer';
import { yupResolver } from '@hookform/resolvers/yup';
import KeyboardBackspaceIcon from '@mui/icons-material/KeyboardBackspace';
import * as Yup from 'yup';
import { TextField } from '@mui/material';
import { Button } from '@mui/material';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { useResetEmailMutation } from '../../features/api/apiSlice';
import { toast } from 'react-toastify';
import { Pathnames } from '../../routes/pathnames';
import { useTranslation } from 'react-i18next';

interface ResetEmailFormSchema {
  newEmail: string;
  repeatNewEmail: string;
}

const resetEmailFormSchema = Yup.object().shape({
  newEmail: Yup.string().required('Nie podano e-maila').email('Nieprawidłowy email'),
  repeatNewEmail: Yup.string()
    .required()
    .oneOf([Yup.ref('newEmail')], 'Podane e-maile nie zgadzają się')
});

const ResetEmail = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const [resetEmaildMutation] = useResetEmailMutation();
  const { t } = useTranslation();

  const {
    handleSubmit,
    register,
    formState: { errors }
  } = useForm<ResetEmailFormSchema>({
    resolver: yupResolver(resetEmailFormSchema)
  });

  const handleResetEmail = ({ newEmail }: ResetEmailFormSchema) => {
    console.log('submit');

    const token: string | null = searchParams.get('token');
    if (token) {
      resetEmaildMutation({ newEmail, token })
        .unwrap()
        .then(() => {
          toast.success('E-mail został zmieniony pomyślnie');
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
      <div className="reset-email-form card border-0">
        <form onSubmit={handleSubmit(handleResetEmail)}>
          <h3 className="mb-3">Resetuj email</h3>

          <p className="mb-4 text-muted fw-semibold">Amatorska liga siatkówki wita.</p>

          <div>
            <TextField
              {...register('newEmail')}
              size="small"
              variant="outlined"
              label="* Nowy E-mail"
              error={!!errors.newEmail}
              helperText={errors.newEmail?.message}
            />
          </div>
          <div className="mt-3">
            <TextField
              {...register('repeatNewEmail')}
              size="small"
              variant="outlined"
              label="* Potwierdź E-mail"
              error={!!errors.repeatNewEmail}
              helperText={errors.repeatNewEmail?.message}
            />
          </div>

          <div className="mt-3">
            <Button variant="contained" type="submit">
              Zatwierdź nowy email
            </Button>
          </div>
          <Button
            size="small"
            className="mt-3"
            variant="text"
            onClick={() => navigate(Pathnames.home.fullPath)}>
            <KeyboardBackspaceIcon /> <span className="ms-2">Strona główna</span>
          </Button>
        </form>
      </div>
    </IntroContainer>
  );
};

export default ResetEmail;
