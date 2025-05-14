import { configureStore } from "@reduxjs/toolkit";
import { productReducer } from "./Reducer/productReducer";
import { errorReducer } from "./Reducer/errorReducer";
import {cartReducer} from "./Reducer/cartReducer"
import { authReducer } from "./Reducer/authReducer";
const cartItems = localStorage.getItem("cartItems")
                ?JSON.parse(localStorage.getItem("cartItems"))
                :[];
const user = localStorage.getItem("auth")
            ?JSON.parse(localStorage.getItem("auth"))
            :[];

const initialState = {
  auth: {user : user},
  cart: {
    cart: cartItems,
    totalPrice: 0,
    cartId: null
  }
};


export const store = configureStore({
    reducer: {
        products : productReducer,
        errors : errorReducer,
        cart: cartReducer,
        auth: authReducer,
    },
    preloadedState: initialState,
}) 
