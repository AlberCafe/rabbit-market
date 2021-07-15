import React, { useState, useEffect } from "react";
import axios from "axios";
import { Alert, Button, Modal, Form, FormControl, Navbar, Nav, Container, NavDropdown } from "react-bootstrap";

function SignUp(props) {
  const [id, setId] = useState("");
  const [pwd, setPwd] = useState("");
  const [show, setShow] = useState(false);
  const [validation, setValidation] = useState(false);
  const valid = id.includes("@" && ".") && pwd.length > 5;

  useEffect(() => {
    setValidation(valid);
  }, [id, pwd]);

  const signUp = () => {
    axios
      .post("http://localhost:8080/api/auth/signup", {
        email: id,
        password: pwd,
      })
      .then(res => {
        console.log("회원가입 성공:", res);
        setShow(true);
        setTimeout(function () {
          setShow(false);
        }, 3000);
      })
      .catch(err => {
        console.log(err);
      });
  };

  return (
    <Modal {...props} size="lg" centered>
      <Modal.Header>
        <Modal.Title id="contained-modal-title-vcenter">회원가입</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group className="mb-3" controlId="formBasicEmail">
            <Form.Label>Email address</Form.Label>
            <Form.Control
              type="email"
              placeholder="Enter email"
              onChange={e => {
                setId(e.target.value);
              }}
            />
            <Form.Text className="text-muted">We'll never share your email with anyone else.</Form.Text>
          </Form.Group>

          <Form.Group className="mb-3" controlId="formBasicPassword">
            <Form.Label>Password</Form.Label>
            <Form.Control
              type="password"
              placeholder="Password"
              onChange={e => {
                setPwd(e.target.value);
              }}
            />
          </Form.Group>
          <Form.Group className="mb-3" controlId="formBasicCheckbox">
            <Form.Check type="checkbox" label="Check me out" />
          </Form.Group>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        {validation ? (
          <Button variant="primary" type="submit" onClick={signUp}>
            가입
          </Button>
        ) : null}
        <Button variant="danger" onClick={props.onHide}>
          닫기
        </Button>
      </Modal.Footer>
      {show ? (
        <Alert variant="success" onClose={() => setShow(false)}>
          <Alert.Heading>회원 가입 성공</Alert.Heading>
          <p>로그인 하시고 토끼마켓을 이용하세요!</p>
        </Alert>
      ) : null}
    </Modal>
  );
}

export default SignUp;
