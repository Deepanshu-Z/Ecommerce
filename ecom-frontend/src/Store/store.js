import { configureStore } from "@reduxjs/toolkit";
import { productReducer } from "./Reducer/productReducer";
import Products from "../Components/Products";

export const store = configureStore({
    reducer: {products: productReducer},
    preloadedState: {},
}) 