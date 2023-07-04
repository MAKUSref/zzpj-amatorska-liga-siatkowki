import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import translationPL from './config/translation/pl/translation.json';
import translationEN from './config/translation/en/translation.json';

export enum LANGS {
  // eslint-disable-next-line no-unused-vars
  EN = 'en',
  // eslint-disable-next-line no-unused-vars
  PL = 'pl'
}

export const resources = {
  pl: {
    translation: translationPL
  },
  en: {
    translation: translationEN
  }
};

const lang = /^en\b/.test(navigator.language) ? LANGS.EN : LANGS.PL;

i18n.use(initReactI18next).init({
  resources,
  lng: lang,
  returnNull: false,
  interpolation: {
    escapeValue: false
  }
});

export default i18n;
