import { configureStore } from "@reduxjs/toolkit";
import { productReducer } from "./Reducer/productReducer";

export const store = configureStore({
    reducer: {products: productReducer},
    preloadedState: {},
}) 