import axios from "axios";
import api from "../../Api/api";

export const fetchProducts = (queryString) => async(dispatch) => {    
    try {
        dispatch({
            type: "IS_FETCHING"
        });
        const res = await api.get(`/public/products?${queryString}`);
        const data = res.data;
        dispatch({
            type: "FETCH_PRODUCTS",
            payload: data.content,
            pageNumber: data.pageNumber,
            pageSize : data.pageSize,
            totalElements : data.totalElements,
            totalPages : data.totalPages,
            lastPage : data.lastPage,
        })
        dispatch({
            type: "IS_SUCCESS"
        });
    } catch (error) {
        dispatch({
            type: "IS_ERROR",
            payload: error?.response?.data?.message || "Failed to Fetch Products",
        });
        console.log(error);
    }
}
