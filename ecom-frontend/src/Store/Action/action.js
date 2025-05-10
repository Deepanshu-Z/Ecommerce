import axios from "axios";
import api from "../../Api/api";

export const fetchProducts = () => async(dispatch) => {    
    try {
        const res = await api.get("/public/products");
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
    } catch (error) {
        console.log(error);
    }
}
