import * as BT from "./productTypes";
import axios from "axios";

export const saveBook = book => {
  return dispatch => {
    dispatch({
      type: BT.SAVE_BOOK_REQUEST,
    });

    axios
      .post("http://localhost:8080/api/products", book)
      .then(response => {
        console.log(response);
        dispatch(bookSuccess(response.data));
        console.log("디스패치 성공");
      })
      .catch(error => {
        dispatch(bookFailure(error));
        console.log("디스패치 실패");
      });
  };
};

export const fetchBook = bookId => {
  return dispatch => {
    dispatch({
      type: BT.FETCH_BOOK_REQUEST,
    });
    axios
      .get("http://localhost:8080/api/products/" + bookId)
      .then(response => {
        dispatch(bookSuccess(response.data));
      })
      .catch(error => {
        dispatch(bookFailure(error));
      });
  };
};

export const updateBook = book => {
  return dispatch => {
    dispatch({
      type: BT.UPDATE_BOOK_REQUEST,
    });
    axios
      .put("http://localhost:8080/api/products", book)
      .then(response => {
        dispatch(bookSuccess(response.data));
      })
      .catch(error => {
        dispatch(bookFailure(error));
      });
  };
};

export const deleteProduct = bookId => {
  return dispatch => {
    dispatch({
      type: BT.DELETE_BOOK_REQUEST,
    });
    axios
      .delete("http://localhost:8080/api/products" + bookId)
      .then(response => {
        dispatch(bookSuccess(response.data));
      })
      .catch(error => {
        dispatch(bookFailure(error));
      });
  };
};

const bookSuccess = book => {
  return {
    type: BT.BOOK_SUCCESS,
    payload: book,
  };
};

const bookFailure = error => {
  return {
    type: BT.BOOK_FAILURE,
    payload: error,
  };
};

export const fetchLanguages = () => {
  return dispatch => {
    dispatch({
      type: BT.FETCH_LANGUAGES_REQUEST,
    });
    axios
      .get("http://localhost:8081/rest/books/languages")
      .then(response => {
        dispatch({
          type: BT.LANGUAGES_SUCCESS,
          payload: response.data,
        });
      })
      .catch(error => {
        dispatch({
          type: BT.LANGUAGES_FAILURE,
          payload: error,
        });
      });
  };
};

export const fetchGenres = () => {
  return dispatch => {
    dispatch({
      type: BT.FETCH_GENRES_REQUEST,
    });
    axios
      .get("http://localhost:8081/rest/books/genres")
      .then(response => {
        dispatch({
          type: BT.GENRES_SUCCESS,
          payload: response.data,
        });
      })
      .catch(error => {
        dispatch({
          type: BT.GENRES_FAILURE,
          payload: error,
        });
      });
  };
};
