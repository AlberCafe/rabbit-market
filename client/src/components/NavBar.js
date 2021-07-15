import React, { useState, useEffect } from "react";
import axios from "axios";

import { Button, Modal, Form, FormControl, Navbar, Nav, Container, NavDropdown, Alert } from "react-bootstrap";

import SignupModal from "./SignUp";
import LoginModal from "./LogIn";

function NavBar() {
  const [modalShow, setModalShow] = useState(false);
  const [modalShow2, setModalShow2] = useState(false);
  const [authenticated, setAuthenticated] = useState(false);

  return (
    <div>
      <Navbar expand="lg" bg="light" variant="light">
        <Container>
          <Navbar.Brand href="/" style={{ paddingTop: "10px", paddingRight: "30px" }}>
            <h1 style={{ color: "orange", fontWeight: "900" }}>토끼마켓</h1>
          </Navbar.Brand>
          <Navbar.Toggle aria-controls="responsive-navbar-nav" />
          <Navbar.Collapse id="responsive-navbar-nav">
            <Nav className="me-auto">
              <Nav.Link href="#features">글쓰기</Nav.Link>
              <Nav.Link href="#pricing">지역</Nav.Link>
              <Nav.Link href="#pricing">채팅</Nav.Link>
              <NavDropdown title="나의 토끼" id="collasible-nav-dropdown">
                <NavDropdown.Item href="#action/3.1">Action</NavDropdown.Item>
                <NavDropdown.Item href="#action/3.2">Another action</NavDropdown.Item>
                <NavDropdown.Item href="#action/3.3">Something</NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item href="#action/3.4">Separated link</NavDropdown.Item>
              </NavDropdown>
            </Nav>
            <Nav>
              <Form className="d-flex" style={{ display: "flex", alignItems: "center", marginRight: "30px" }}>
                <FormControl type="search" placeholder="" />
                <Button variant="success">🔍</Button>
              </Form>
              <Nav.Link href="#" onClick={() => setModalShow(true)} style={{ display: "flex", alignItems: "center" }}>
                {authenticated ? <div>마이 페이지</div> : <div>회원가입</div>}
              </Nav.Link>
              <Nav.Link onClick={() => setModalShow2(true)} style={{ display: "flex", alignItems: "center" }}>
                {authenticated ? <div>로그아웃</div> : <div>로그인</div>}
              </Nav.Link>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>

      <SignupModal show={modalShow} onHide={() => setModalShow(false)} />
      <LoginModal show={modalShow2} onHide={() => setModalShow2(false)} />
    </div>
  );
}

export default NavBar;
