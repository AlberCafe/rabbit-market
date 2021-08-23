import React, { useState, useEffect } from "react";

import { Button, Card, Container } from "react-bootstrap";

import axios from "axios";

const URL = "http://ec2-3-35-167-70.ap-northeast-2.compute.amazonaws.com:8080/api/categories";

export default function Category() {
  const [categoryName, setCategoryName] = useState("임시카테고리2");

  const A = "http://ec2-3-35-167-70.ap-northeast-2.compute.amazonaws.com:8080/api​/categories";
  const B = "http://ec2-3-35-167-70.ap-northeast-2.compute.amazonaws.com:8080/api/categories";

  const handleRegister = () => {
    const token = localStorage.getItem("token");

    const body = {
      name: categoryName,
    };

    axios
      .post(URL, body, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => console.log("카테고리 등록 성공", res))
      .catch((err) => console.log("카테고리 등록 에러", err));
  };

  return (
    <Container>
      <h1>카테고리 페이지</h1>
      <Card style={{ width: "18rem" }}>
        <Card.Img variant="top" src="holder.js/100px180" />
        <Card.Body>
          <Card.Title>Card Title</Card.Title>
          <Card.Text>Some quick example text to build on the card title and make up the bulk of the card's content.</Card.Text>
          <Button variant="primary">Go somewhere</Button>
        </Card.Body>
      </Card>
      <Button variant="primary" onClick={handleRegister}>
        Submit
      </Button>
    </Container>
  );
}
