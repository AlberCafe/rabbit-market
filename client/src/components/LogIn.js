import React, { useState, useEffect } from "react";
import axios from "axios";

import { Button, Modal, Form, FormControl, Navbar, Nav, Container, NavDropdown, Alert } from "react-bootstrap";

function LogIn(props) {
  const [id, setId] = useState("");
  const [pwd, setPwd] = useState("");

  const login = () => {
    axios
      .post("http://localhost:8080/api/auth/login", {
        email: id,
        password: pwd,
      })
      .then(res => {
        console.log("로그인 성공:", res);
      })
      .catch(err => {
        console.log(err);
      });
  };

  return (
    <Modal {...props} size="lg" centered>
      <Modal.Header>
        <Modal.Title id="contained-modal-title-vcenter">로그인</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group className="mb-3" controlId="formBasicEmail">
            <Form.Label>Email address</Form.Label>
            <Form.Control type="email" placeholder="Enter email" onChange={e => setId(e.target.value)} />
            <Form.Text className="text-muted">We'll never share your email with anyone else.</Form.Text>
          </Form.Group>

          <Form.Group className="mb-3" controlId="formBasicPassword">
            <Form.Label>Password</Form.Label>
            <Form.Control type="password" placeholder="Password" onChange={e => setPwd(e.target.value)} />
          </Form.Group>
          <Form.Group className="mb-3" controlId="formBasicCheckbox">
            <Form.Check type="checkbox" label="Check me out" />
          </Form.Group>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="primary" type="submit" onClick={login}>
          로그인
        </Button>
        <Button variant="danger" onClick={props.onHide}>
          닫기
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

export default LogIn;
