import React, { useState, useEffect } from "react";

import { Button, Form, Container } from "react-bootstrap";

import axios from "axios";

const URL = "http://ec2-3-35-167-70.ap-northeast-2.compute.amazonaws.com:8080";

export default function SignUp() {
  const [name, setName] = useState("김다솔");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("01099980570");
  const [address, setAddress] = useState("경기도 용인시");

  axios.defaults.withCredentials = true;

  const handleSignUp = (e) => {
    e.preventDefault();

    const body = {
      username: name,
      email: email,
      password: password,
      phoneNumber: phoneNumber,
      address: address,
    };

    axios
      .post(`${URL}/api/auth/signup`, body)
      .then((res) => console.log("회원가입 성공", res))
      .catch((err) => console.log("회원가입 에러", err));
  };

  useEffect(() => {
    console.log(email);
    console.log(typeof password);
    console.log(password);
  });

  return (
    <Container>
      <h1>회원가입 페이지</h1>
      <Form>
        <Form.Group className="mb-3" controlId="formBasicEmail">
          <Form.Label>Email address</Form.Label>
          <Form.Control type="email" placeholder="Enter email" onChange={(e) => setEmail(e.target.value)} />
          <Form.Text>We'll never share your email with anyone else.</Form.Text>
        </Form.Group>

        <Form.Group className="mb-3" controlId="formBasicPassword">
          <Form.Label>Password</Form.Label>
          <Form.Control type="password" placeholder="Password" onChange={(e) => setPassword(e.target.value)} />
        </Form.Group>
        <Button variant="primary" type="submit" onClick={handleSignUp}>
          Submit
        </Button>
      </Form>
    </Container>
  );
}
