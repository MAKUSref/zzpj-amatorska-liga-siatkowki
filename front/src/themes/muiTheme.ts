import { createTheme } from '@mui/material/styles';

declare module '@mui/material/Button' {
  // eslint-disable-next-line no-unused-vars
  interface ButtonPropsVariantOverrides {
    light: true;
  }
}

const theme = createTheme({
  palette: {
    primary: {
      light: '#8284C0',
      main: '#5255A3',
      dark: '#30385F',
      contrastText: '#fff'
    }
  }
});

export default theme;
