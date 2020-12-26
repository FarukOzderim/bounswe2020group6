import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { authLogoutAction } from "../src/redux/auth/actions";
import { Switch, Route, Redirect, useHistory } from "react-router-dom";
import Landing from "./pages/Landing";
import Home from "./pages/Home";
import SignUp from "./pages/SignUp";
import Search from "./pages/Search";
import Project from "./pages/Project";
import ProjectDetails from "./pages/ProjectDetails";
import api from "./axios";
import Spinner from "./components/Spinner";
import { Row, Col } from "antd";

export default function App() {
  const [loading, setLoading] = useState(false);
  const dispatch = useDispatch();
  const history = useHistory();
  const selector = useSelector;

  const user = selector((state) => state.auth.user);

  useEffect(() => {
    const jwtToken = localStorage.getItem("token");
    setLoading(true);
    api()
      .post("/auth/jwt", { token: jwtToken })
      .then(() => {
        setLoading(false);
      })
      .catch(() => {
        dispatch(authLogoutAction());
        history.push("/");
        setLoading(false);
      });
    // eslint-disable-next-line
  }, []);

  const storageToken = localStorage.getItem("token");

  let routes = (
    <Switch>
      <Route path="/join">
        <SignUp />
      </Route>
      <Route path="/">
        <Landing />
      </Route>
      <Redirect to="/" />
    </Switch>
  );

  if (storageToken && user) {
    routes = (
      <Switch>
        <Route path="/home">
          <Home />
        </Route>
        <Route path="/search">
          <Search />
        </Route>
        <Route path="/project">
          <Project />
        </Route>
        <Route path="/project-details/:projectId/">
          <ProjectDetails />
        </Route>
        <Redirect to="/home" />
      </Switch>
    );
  }

  return loading ? (
    <Row style={{ height: "100vh" }} justify="center" align="middle">
      <Col>
        <Spinner size={200} />
      </Col>
    </Row>
  ) : (
    routes
  );
}
