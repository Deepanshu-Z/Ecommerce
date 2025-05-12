const initialState = {
    isLoading:false,
    errorMessage:null,    
    categoryLoader:false,
    categoryMessage:null,
}

export const errorReducer = (state = initialState, action) => {
    switch (action.type) {
        case "IS_FETCHING":
            return { 
                ...state,
                isLoading:true,
                errorMessage:null
            }

        case "IS_SUCCESS":
            return {
                ...state,
                isLoading:false,
                errorMessage:null
            }
        case "CATEGORY_FETCHING":
            return { 
                ...state,
                categoryLoader:true,
                categoryMessage:null
            }

        case "CATEGORY_SUCCESS":
            return {
                ...state,
                categoryLoader:false,
                categoryMessage:null
            }
        case "IS_ERROR":
            return {
                ...state,
                isLoading:false,
                errorMessage:action.payload
            }

        default:
            return state;
    }
};