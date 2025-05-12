import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { useSearchParams } from "react-router-dom";
import { fetchProducts } from "../Store/Action/action";

const useProductFilter = () => {
  const [searchParams] = useSearchParams();
  const dispatch = useDispatch();

  useEffect(() => {
    const params = new URLSearchParams();

    const currentPage = Number(searchParams.get("page") || 1);
    const sortOrder = searchParams.get("sortby") || "asc";
    const categoryParams = searchParams.get("category") || null;
    const key = searchParams.get("key") || null;

    params.set("page", currentPage);
    params.set("sortBy", "price");
    params.set("sortOrder", sortOrder);

    if (categoryParams) {
      params.set("category", categoryParams);
    }

    if (key) {
      params.set("key", key);
    }

    const queryString = params.toString();
    dispatch(fetchProducts(queryString));
  }, [searchParams, dispatch]);
};

export default useProductFilter;
