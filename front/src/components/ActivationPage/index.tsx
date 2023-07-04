import { useNavigate } from 'react-router';
import { useSearchParams } from 'react-router-dom';
import { useLazyActivateQuery } from '../../features/api/apiSlice';
import { useEffect } from 'react';
import { toast } from 'react-toastify';
import { Pathnames } from '../../routes/pathnames';
import { useTranslation } from 'react-i18next';

const ActivationPage = () => {
  const [searchParams] = useSearchParams();
  const [requestActivation] = useLazyActivateQuery();
  const navigate = useNavigate();
  const { t } = useTranslation();

  useEffect(() => {
    const token: string | null = searchParams.get('token');
    if (!token) {
      toast.error(t('activationPage.noToken'));
      navigate(Pathnames.home.fullPath);
      return;
    }

    requestActivation(token)
      .unwrap()
      .then(() => {
        toast.success(t('activationPage.activateSuccess'));
      })
      .catch(() => {
        toast.error(t('exceptions.exceptionLinkNotValid'));
      });

    navigate(Pathnames.home.fullPath);
  }, []);
  return <></>;
};

export default ActivationPage;
