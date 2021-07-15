import React from "react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

import NavBar from "./components/NavBar";
import LogIn from "./components/LogIn";
import SignUp from "./components/SignUp";

function App() {
  return (
    <Router>
      <NavBar />
      <Switch>
        <Route path="/signup" exact component={SignUp} />
        <Route path="/login" exact component={LogIn} />
      </Switch>
    </Router>
  );
}

export default App;
