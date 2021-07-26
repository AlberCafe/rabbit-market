// import React from "react";
// import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

// import NavBar from "./components/NavBar";
// import LogIn from "./components/LogIn";
// import SignUp from "./components/SignUp";

// function App() {
//   return (
//     <Router>
//       <NavBar />
//       <Switch>
//         <Route path="/signup" exact component={SignUp} />
//         <Route path="/login" exact component={LogIn} />
//       </Switch>
//     </Router>
//   );
// }

// export default App;
import React from "react";
import "./App.css";

import { Container, Row, Col } from "react-bootstrap";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

import NavBar from "./components/NavBar";
import LogIn from "./components/LogIn";
import SignUp from "./components/SignUp";

import NavigationBar from "./components/NavigationBar";
import Welcome from "./components/Welcome";
import Product from "./components/Product/Product";
import ProductList from "./components/Product/ProductList";
import UserList from "./components/User/UserList";
import Register from "./components/User/Register";
import Login from "./components/User/Login";
import Footer from "./components/Footer";
import Home from "./components/Home";

const App = () => {
  window.onbeforeunload = event => {
    const e = event || window.event;
    e.preventDefault();
    if (e) {
      e.returnValue = "";
    }
    return "";
  };

  return (
    <Router>
      <NavBar />
      <NavigationBar />
      <Container>
        <Switch>
          {/* <Route path="/signup" exact component={SignUp} />
              <Route path="/login" exact component={LogIn} /> */}

          <Route path="/" exact component={Welcome} />
          <Route path="/home" exact component={Home} />
          <Route path="/add" exact component={Product} />
          <Route path="/edit/:id" exact component={Product} />
          <Route path="/list" exact component={ProductList} />
          <Route path="/users" exact component={UserList} />
          <Route path="/register" exact component={Register} />
          <Route path="/login" exact component={Login} />
          <Route path="/logout" exact component={() => <Login message="로그아웃 되었습니다." />} />
        </Switch>
      </Container>
      <Footer />
    </Router>
  );
};

export default App;
