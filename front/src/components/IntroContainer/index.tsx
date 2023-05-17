import React from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import bgImage from '../../imgs/volleyball.svg';
import blobImage from '../../imgs/blob.svg';
import './style.scss';

interface IntroContainerProps {
  image?: string;
  leftSideSize?: 2 | 3 | 4 | 6;
}

const IntroContainer = ({
  children,
  image = bgImage,
  leftSideSize = 3
}: React.PropsWithChildren<IntroContainerProps>) => {
  return (
    <Container className="intro-container">
      <Row className="intro-container-row justify-content-between">
        <Col
          className="intro-container-col d-flex justify-content-center align-items-center ms-md-5"
          md={leftSideSize}>
          {children}
        </Col>
        <Col
          className="intro-container-col justify-content-center align-items-center d-none d-md-flex"
          md={6}>
          <img src={image} alt="Volleyball" />
        </Col>
      </Row>
      <img
        className="intro-container-blob-img position-absolute top-0 end-0"
        src={blobImage}
        alt=" "
      />
    </Container>
  );
};

export default IntroContainer;
