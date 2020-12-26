import api from "../../axios";
import * as actions from "./actions";

export const getProfileInfo = (id) => {
  return (dispatch) => {
    dispatch(actions.getProfileInfoStartAction());
    api({ sendToken: true })
      .get("/profile/" + id)
      .then((response) => {
        dispatch(actions.getProfileInfoSuccessAction(response.data));
      })
      .catch((e) => {
        console.log(e);
      });
  };
};

export const getProfileInfoWithoutLoading = (id) => {
  return (dispatch) => {
    api({ sendToken: true })
      .get("/profile/" + id)
      .then((response) => {
        dispatch(actions.getProfileInfoSuccessAction(response.data));
      })
      .catch((e) => {
        console.log(e);
      });
  };
};

export const infoUpdate = (body, id) => {
  return (dispatch) => {
    api({ sendToken: true })
      .patch("/profile/update", body)
      .then((response) => {
        dispatch(getProfileInfo(id))
      })
      .catch((e) => {
        console.log(e.response.data)
      });
  };
};