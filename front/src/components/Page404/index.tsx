import { Row } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import svg404 from '../../imgs/page404.svg';

const Page404 = () => {
  const { t } = useTranslation();

  return (
    <>
      <Row>
        <div className="mx-auto mt-5 px-0" style={{ maxWidth: '600px' }}>
          <img src={svg404} alt="Page bot found image" />
        </div>
      </Row>
      <p className="text-center">
        <span className="fw-bold text-primary">{t('errorPage.error')}</span> {t('errorPage.info')}
      </p>
    </>
  );
};

export default Page404;
