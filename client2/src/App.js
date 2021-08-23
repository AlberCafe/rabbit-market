import "./App.css";

import React, { useState } from "react";
import { BrowserRouter, Route, Switch } from "react-router-dom";

import Main from "./components/Main";
import SignUp from "./components/SignUp";
import LogIn from "./components/LogIn";
import Category from "./components/Category";
import Product from "./components/Product";

function App() {
  const [Images, setImages] = useState([]);
  const updateImages = (newImages) => {
    setImages(newImages);
  };

  return (
    <>
      <a href="/">
        <h1>홈</h1>
      </a>
      <Switch>
        <Route exact path="/" component={Main} />
        <Route exact path="/signup" component={SignUp} />
        <Route exact path="/login" component={LogIn} />
        <Route exact path="/category" component={Category} />
        <Route exact path="/product" component={Product}>
          <Product refreshFunction={updateImages} />
        </Route>
      </Switch>
    </>
  );
}

export default App;
