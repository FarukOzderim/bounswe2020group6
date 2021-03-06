import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { authLogoutAction } from "../src/redux/auth/actions";
import { Switch, Route, Redirect, useHistory } from "react-router-dom";
import Landing from "./pages/Landing";
import About from "./pages/About";
import Home from "./pages/Home";
import Events from "./pages/Events";
import SignUp from "./pages/SignUp";
import ForgotPassword from "./pages/ForgotPassword";
import Search from "./pages/Search";
import CreateProject from "./pages/CreateProject";
import EditProject from "./pages/EditProject";
import ProjectDetails from "./pages/ProjectDetails";
import EventDetails from "./pages/EventDetails";
import FileEditor from "./pages/FileEditor";
import Profile from "./pages/Profile";
import ListFollowers from "./pages/ListFollow";
import api from "./axios";
import Spinner from "./components/Spinner";
import { Row, Col } from "antd";
import CreateEvent from "./pages/CreateEvent";
import EditEvent from "./pages/EditEvent";
import SavedEvents from "./pages/SavedEvents";

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
        setLoading(false);
      });
    // eslint-disable-next-line
  }, []);

  const storageToken = localStorage.getItem("token");

  let routes = (
    <Switch>
      <Route path="/forgotPassword/newPassword">
        <ForgotPassword step={2} />
      </Route>
      <Route path="/forgotPassword/code">
        <ForgotPassword step={1} />
      </Route>
      <Route path="/forgotPassword">
        <ForgotPassword step={0} />
      </Route>
      <Route path="/join">
        <SignUp />
      </Route>
      <Route path="/about">
        <About />
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
        <Route path="/events">
          <Events />
        </Route>
        <Route path="/event/saved">
          <SavedEvents />
        </Route>
        <Route path="/search">
          <Search />
        </Route>
        <Route path="/project/details/:projectId">
          <ProjectDetails />
        </Route>
        <Route path="/project/edit/:projectId">
          <EditProject />
        </Route>
        <Route path="/project/editfiles/:projectId">
          <FileEditor />
        </Route>
        <Route path="/project">
          <CreateProject />
        </Route>
        <Route path="/event/details/:eventId">
          <EventDetails />
        </Route>
        <Route path="/profile/:id">
          <Profile />
        </Route>
        <Route path="/list/:type">
          <ListFollowers />
        </Route>
        <Route path="/event/create">
          <CreateEvent />
        </Route>
        <Route path="/event/edit/:eventId">
          <EditEvent />
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
