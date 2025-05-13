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


export const fetchCategories = () => async(dispatch) => {    
    try {
        dispatch({
            type: "CATEGORY_FETCHING"
        });
        const res = await api.get(`/public/categories`);
        const data = res.data;
        dispatch({
            type: "FETCH_CATEGORIES",
            payload: data.categoryResponse,
            pageNumber: data.pageNumber,
            pageSize : data.pageSize,
            totalElements : data.totalElements,
            totalPages : data.totalPages,
            lastPage : data.lastPage,
        })
        dispatch({
            type: "CATEGORY_SUCCESS"
        });
    } catch (error) {
        dispatch({
            type: "IS_ERROR",
            payload: error?.response?.data?.message || "Failed to Fetch Categories",
        });
        console.log(error);
    }
}


export const addToCart = (data, qty = 1, toast) => 
    (dispatch, getState) => {
        //Find the Product
        const {products} = getState().products;
        const getProduct = products.find(
            (item) => item.productId === data.productId ); 
        
        //Check if quantity exist
        const isQuantityExist = getProduct.quantity >= qty;

        //If yes, then add to cart
        if(isQuantityExist){
            dispatch({
                type : "ADD_CART",
                payload : {...data, quantity : qty}
            })
            localStorage.setItem("cartItems", JSON.stringify(getState().cart.cart))
            toast.success(`${data.productName} added to cart`)
        }else{
            toast.error(`${data.productName} out of stock!`)
        }

}