import { useDispatch, useSelector } from "react-redux";
import HeroBanner from "./HeroBanner.jsx"
import { useEffect } from "react";
import ProductCard from "../ProductCard.jsx";
import { fetchCategories, fetchProducts } from "../../Store/Action/action.js";    

const Home = () => {
  const dispatch = useDispatch()
  useEffect(() => {
    dispatch(fetchProducts())
  }, [dispatch])
  const {products} = useSelector(
        (state) => state.products
  );

  const productArr = products.slice(0,4).map((item, i) => (
      <ProductCard key={i} value={item} />
    ));


  return (
    <div className="lg:px-14 sm:px-8 px-4" >
      <div className="py-6">
        <HeroBanner /> 
      </div>

      <div className="flex flex-col justify-center items-center space-y-2">
            <h1 className="text-slate-800 text-4xl ">
                <span>Discover out handpicked selection of top-rated products</span>
            </h1>
      </div>

      <div className="pb-6 pt-14 grid 2xl:grid-cols-4 lg:grid-cols-3 sm:grid-cols-2 gap-y-6 gap-x-6">
        {productArr}
      </div>
      
    </div>
  );
};
export default Home;
