import { Container } from 'react-bootstrap';
import { Outlet } from 'react-router-dom';
import Navbar from '../Navbar';

const DefaultContainer = () => (
  <>
    <Navbar />
    <Container className="mt-4">
      <Outlet />
    </Container>
  </>
);

export default DefaultContainer;
