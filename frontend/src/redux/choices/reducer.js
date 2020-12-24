import * as actions from "../actionTypes";

const initialState = {
  tags: [],
  universities: [],
  departments: [],
};

// LOGIN

const getTagsSuccessReducer = (state, action) => {
    return {
        ...state,
        tags: action.payload,
    };
};

const getUniversitiesSuccessReducer = (state, action) => {
    return {
        ...state,
        universities: action.payload,
    };
};

const getDepartmentsSuccessReducer = (state, action) => {
    return {
        ...state,
        departments: action.payload,
    };
};


export default function reducer(state = initialState, action) {
  switch (action.type) {

    case actions.GET_TAGS_SUCCESS:
      return getTagsSuccessReducer(state, action);
    case actions.GET_DEPARTMENTS_SUCCESS:
      return getDepartmentsSuccessReducer(state, action);
    case actions.GET_UNIVERSITIES_SUCCESS:
      return getUniversitiesSuccessReducer(state, action);

    default:
      return state;
  }
}
