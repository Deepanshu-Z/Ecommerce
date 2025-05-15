import api from "../../Api/api";
import useProductFilter from "../../Components/useProductFilter";

export const fetchProducts = (queryString) => async (dispatch) => {
  try {
    dispatch({
      type: "IS_FETCHING",
    });
    const res = await api.get(`/public/products?${queryString}`);
    const data = res.data;
    dispatch({
      type: "FETCH_PRODUCTS",
      payload: data.content,
      pageNumber: data.pageNumber,
      pageSize: data.pageSize,
      totalElements: data.totalElements,
      totalPages: data.totalPages,
      lastPage: data.lastPage,
    });
    dispatch({
      type: "IS_SUCCESS",
    });
  } catch (error) {
    dispatch({
      type: "IS_ERROR",
      payload: error?.response?.data?.message || "Failed to Fetch Products",
    });
    console.log(error);
  }
};

export const fetchCategories = () => async (dispatch) => {
  try {
    dispatch({
      type: "CATEGORY_FETCHING",
    });
    const res = await api.get(`/public/categories`);
    const data = res.data;
    dispatch({
      type: "FETCH_CATEGORIES",
      payload: data.categoryResponse,
      pageNumber: data.pageNumber,
      pageSize: data.pageSize,
      totalElements: data.totalElements,
      totalPages: data.totalPages,
      lastPage: data.lastPage,
    });
    dispatch({
      type: "CATEGORY_SUCCESS",
    });
  } catch (error) {
    dispatch({
      type: "IS_ERROR",
      payload: error?.response?.data?.message || "Failed to Fetch Categories",
    });
    console.log(error);
  }
};

export const addToCart = (data, qty = 1, toast) =>
  (dispatch, getState) => {
    const { products } = getState().products;

    const getProduct = products.find(
      (item) => item.productId === data.productId
    );

    const isQuantityExist = getProduct?.quantity >= qty;

    if (isQuantityExist) {
      dispatch({
        type: "ADD_CART",
        payload: {
          ...data,
          quantity: qty,
          stockQuantity: getProduct.quantity, // ✅ ADD THIS
        },
      });

      localStorage.setItem("cartItems", JSON.stringify(getState().cart.cart));
      toast.success(`${data.productName} added to cart`);
    } else {
      toast.error(`${data.productName} out of stock!`);
    }
  };

export const increaseCartQuantity = (data, toast, currentQuantity, setCurrentQuantity) =>
  (dispatch, getState) => {
    const { products } = getState().products;

    const getProduct = products.find(
      (item) => item.productId === data.productId
    );

    const isQuantityExist = getProduct.quantity >= currentQuantity + 1;

    if (isQuantityExist) {
      const newQuantity = currentQuantity + 1;
      setCurrentQuantity(newQuantity);

      dispatch({
        type: "ADD_CART",
        payload: {
          ...data,
          quantity: newQuantity,
          stockQuantity: data.stockQuantity,
        },
      });

      localStorage.setItem("cartItems", JSON.stringify(getState().cart.cart));
    } else {
      toast.error("Quantity Reached to Limit");
    }
  };

export const decreaseCartQuantity = (data, newQuantity) => (dispatch, getState) => {
    dispatch({
      type: "ADD_CART",
      payload: { ...data, quantity: newQuantity },
    });
    localStorage.setItem("cartItems", JSON.stringify(getState().cart.cart));
  };

export const removeFromCart = (data, toast) => (dispatch, getState) => {
  dispatch({ type: "REMOVE_CART", payload: data });
  toast.success(`${data.productName} removed from cart`);
  localStorage.setItem("cartItems", JSON.stringify(getState().carts.cart));
};


export const authenticateSignInUser  = (sendData, toast, reset, navigate, setLoader) => async (dispatch) => {
        try {
            setLoader(true);
            const { data } = await api.post("/auth/signin", sendData);
            dispatch({ type: "LOGIN_USER", payload: data });
            localStorage.setItem("auth", JSON.stringify(data));
            reset();
            toast.success("Login Success");
            navigate("/");
        } catch (error) {
            console.log(error);
            toast.error(error?.response?.data?.message || "Internal Server Error");
        } finally {
            setLoader(false);
        }
}

export const registerNewUser  = (sendData, toast, reset, navigate, setLoader) => async (dispatch) => {
        try {
            setLoader(true);
            const { data } = await api.post("/auth/signup", sendData);
            reset();
            toast.success(data?.message || "User Registered Successfully");
            navigate("/login");
        } catch (error) {
            console.log(error);
            toast.error(error?.response?.data?.message || error?.response?.data?.password || "Internal Server Error");
        } finally {
            setLoader(false);
        }
}

export const logOutUser = (navigate) => (dispatch) => {
    dispatch({ type:"LOG_OUT" });
    localStorage.removeItem("auth");
    navigate("/login");
};