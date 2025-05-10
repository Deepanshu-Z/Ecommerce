import { configureStore } from "@reduxjs/toolkit";
import { productReducer } from "./Reducer/productReducer";
import { errorReducer } from "./Reducer/errorReducer";

export const store = configureStore({
    reducer: {
        products : productReducer,
        errors : errorReducer
    },
    preloadedState: {},
}) 