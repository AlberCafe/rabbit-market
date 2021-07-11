import React, { useState } from "react";

function LogIn() {
  const [id, setId] = React.useState("");
  const [pwd, setPwd] = React.useState("");

  const login = () => {
    if (id === "" || pwd === "") {
      window.alert("아이디와 비밀번호를 입력해주세요.");
      return;
    }
  };
  return <div></div>;
}

export default LogIn;
