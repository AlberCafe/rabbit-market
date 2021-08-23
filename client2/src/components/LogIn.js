import React, { useEffect, useState } from "react";

import { Button, Form, Container } from "react-bootstrap";

import axios from "axios";

const URL = "http://ec2-3-35-167-70.ap-northeast-2.compute.amazonaws.com:8080";

export default function LogIn() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleLogIn = () => {
    const body = {
      email: email,
      password: password,
    };

    axios
      .post(`${URL}/api/auth/login`, body)
      .then((res) => {
        console.log("로그인 성공");
        localStorage.setItem("token", res.data.data.authenticationToken);
      })
      .catch((err) => console.log("로그인 에러", err));
  };

  const handleAuthenticated = () => {
    const token = localStorage.getItem("token");

    axios
      .get(`${URL}/api/auth/accountVerification/`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => console.log("인증 성공", res))
      .catch((err) => console.log("인증 에러", err));
  };

  return (
    <Container>
      <Form>
        <h1>로그인 페이지</h1>
        <Form.Group className="mb-3" controlId="formBasicEmail">
          <Form.Label>Email address</Form.Label>
          <Form.Control type="email" placeholder="Enter email" onChange={(e) => setEmail(e.target.value)} />
          <Form.Text>We'll never share your email with anyone else.</Form.Text>
        </Form.Group>

        <Form.Group className="mb-3" controlId="formBasicPassword">
          <Form.Label>Password</Form.Label>
          <Form.Control type="password" placeholder="Password" onChange={(e) => setPassword(e.target.value)} />
        </Form.Group>
        <Button variant="primary" onClick={handleLogIn}>
          로그인
        </Button>
      </Form>
      <Button variant="primary" onClick={handleAuthenticated}>
        인증
      </Button>
    </Container>
  );
}
