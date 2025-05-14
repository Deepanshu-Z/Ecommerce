import { configureStore } from "@reduxjs/toolkit";
import { productReducer } from "./Reducer/productReducer";
import { errorReducer } from "./Reducer/errorReducer";
import {cartReducer} from "./Reducer/cartReducer"
const cartItems = localStorage.getItem("cartItems")
                ?JSON.parse(localStorage.getItem("cartItems"))
                :[];

const initialState = {
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
    },
    preloadedState: initialState,
}) 
