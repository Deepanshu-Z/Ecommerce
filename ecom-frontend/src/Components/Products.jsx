import { FaExclamation, FaExclamationTriangle } from "react-icons/fa";
import ProductCard from "./ProductCard";
import {  useDispatch, useSelector } from "react-redux";
import { useEffect } from "react";
import { fetchCategories, fetchProducts } from "../Store/Action/action";
import { Filter } from "./Filter";
import useProductFilter from "./useProductFilter";
export default function Products() {
  
  const {products, categories} = useSelector(
      (state) => state.products
  )
  const { errorMessage, isLoading } = useSelector(
      (state) => state.errors
  )
  
  useProductFilter();

  const productArray = products?.map((item, i) => (
    <ProductCard key={i} value={item} />
  ));


  return (
    <div className="lg:px-14 sm:px-8 px-4 py-14 2xl:w-[90%] 2xl:mx-auto">
      <Filter categories = {categories ? categories : []} />
      {isLoading ? (
        <div>Loading...</div>
      ) : errorMessage ? (
        <div className="flex justify-center items-center h-[200px]">
          <FaExclamationTriangle className="text-slate-800 text-3xl mr-2"/>
          <span className="text-slate-800 text-lg font-medium">
            {errorMessage}
          </span>
          </div>
      ) : (
        <div className="min-h-[700px]">
          <div className="pb-6 pt-14 grid 2xl:grid-cols-4 lg:grid-cols-3 sm:grid-cols-2 gap-y-6 gap-x-6">
            { productArray }
          </div>
        </div>
      )}
    </div>
  );
}
