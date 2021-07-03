import React, { useState } from "react";
import { Button, Modal, Form, FormControl, Navbar, Nav, Container, NavDropdown } from "react-bootstrap";

function CenteredModal(props) {
  return (
    <Modal {...props} size="lg" centered>
      <Modal.Header>
        <Modal.Title id="contained-modal-title-vcenter">회원가입</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group className="mb-3" controlId="formBasicEmail">
            <Form.Label>Email address</Form.Label>
            <Form.Control type="email" placeholder="Enter email" />
            <Form.Text className="text-muted">We'll never share your email with anyone else.</Form.Text>
          </Form.Group>

          <Form.Group className="mb-3" controlId="formBasicPassword">
            <Form.Label>Password</Form.Label>
            <Form.Control type="password" placeholder="Password" />
          </Form.Group>
          <Form.Group className="mb-3" controlId="formBasicCheckbox">
            <Form.Check type="checkbox" label="Check me out" />
          </Form.Group>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="primary" type="submit">
          가입
        </Button>
        <Button variant="danger" onClick={props.onHide}>
          닫기
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

function NavBar() {
  const [modalShow, setModalShow] = useState(false);

  return (
    <div>
      <Navbar expand="lg" bg="light" variant="light">
        <Container>
          <Navbar.Brand href="#home" style={{ paddingTop: "10px", paddingRight: "30px" }}>
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
              <Nav.Link href="#deets" style={{ display: "flex", alignItems: "center" }}>
                로그인
              </Nav.Link>
              <Nav.Link href="#memes" onClick={() => setModalShow(true)} style={{ display: "flex", alignItems: "center" }}>
                회원가입
              </Nav.Link>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>

      <CenteredModal show={modalShow} onHide={() => setModalShow(false)}></CenteredModal>
    </div>
  );
}

export default NavBar;
