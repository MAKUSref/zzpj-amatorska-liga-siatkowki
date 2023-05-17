import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import translationPL from './config/translation/pl/translation.json';
import translationEN from './config/translation/en/translation.json';

export const resources = {
  pl: {
    translation: translationPL
  },
  en: {
    translation: translationEN
  }
};

i18n.use(initReactI18next).init({
  resources,
  lng: 'pl',
  returnNull: false,
  interpolation: {
    escapeValue: false
  }
});

export default i18n;
