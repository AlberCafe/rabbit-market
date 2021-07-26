import React, { useState, useEffect } from "react";

import { Navbar, Container, Col } from "react-bootstrap";

const Footer = () => {
  const [fullYear, setFullYear] = useState();

  useEffect(() => {
    setFullYear(new Date().getFullYear());
  }, [fullYear]);

  return (
    <Navbar fixed="bottom" bg="light">
      <Container>
        <Col lg={12} className="text-center text-muted">
          <div>Copyright © {fullYear} All Rights Reserved by Kim & Shin</div>
        </Col>
      </Container>
    </Navbar>
  );
};

export default Footer;
