import React, { useEffect, useState } from "react";

import { Button, Container } from "react-bootstrap";
import { BrowserRouter, Switch, Route, Redirect, useHistory } from "react-router-dom";

export default function Main() {
  const history = useHistory();
  const [test, setTest] = useState(-1);

  return (
    <Container>
      <h1>메인 페이지 {test}</h1>
      <Button onClick={() => history.push("/signup")}>회원가입</Button>
      <Button onClick={() => history.push("/login")}>로그인</Button>
      <Button onClick={() => history.push("/category")}>카테고리</Button>
      <Button onClick={() => history.push("/product")}>물건</Button>
    </Container>
  );
}
