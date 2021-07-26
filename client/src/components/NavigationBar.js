import React, { useState } from "react";
import { connect } from "react-redux";
import { Button, Modal, Form, FormControl, Navbar, Nav, Container, NavDropdown, Alert } from "react-bootstrap";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUserPlus, faSignInAlt, faSignOutAlt } from "@fortawesome/free-solid-svg-icons";
import { logoutUser } from "../services/index";

import SignupModal from "./SignUp";
import LoginModal from "./LogIn";

const NavigationBar = props => {
  const logout = () => {
    props.logoutUser();
  };

  const guestLinks = (
    <>
      <div className="mr-auto"></div>
      <Nav className="navbar-right">
        <Link to={"register"} className="nav-link">
          <FontAwesomeIcon icon={faUserPlus} /> 회원가입
        </Link>
        <Link to={"login"} className="nav-link">
          <FontAwesomeIcon icon={faSignInAlt} /> 로그인
        </Link>
      </Nav>
    </>
  );
  const userLinks = (
    <>
      <Nav className="mr-auto">
        <Link to={"add"} className="nav-link">
          물건 등록
        </Link>
        <Link to={"list"} className="nav-link">
          물건 목록
        </Link>
        <Link to={"users"} className="nav-link">
          사용자 목록
        </Link>
      </Nav>
      <Nav className="navbar-right">
        <Link to={"logout"} className="nav-link" onClick={logout}>
          <FontAwesomeIcon icon={faSignOutAlt} /> 로그아웃
        </Link>
      </Nav>
    </>
  );

  return (
    <div>
      <Navbar bg="light" variant="light">
        <Link to={props.auth.isLoggedIn ? "home" : ""} className="navbar-brand">
          <h1 style={{ color: "orange", fontWeight: "900" }}>토끼마켓</h1>
        </Link>
        {props.auth.isLoggedIn ? userLinks : guestLinks}
      </Navbar>
    </div>
  );
};

const mapStateToProps = state => {
  return {
    auth: state.auth,
  };
};

const mapDispatchToProps = dispatch => {
  return {
    logoutUser: () => dispatch(logoutUser()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(NavigationBar);
