import { useState } from "react";
import { FaShoppingCart } from "react-icons/fa";

export default function ProductCard(props) {
  const [openProductViewModal, setOpenProductViewModal] = useState(false);
  const btnLoader = false;
  const [selectedViewProduct, setSelectedViewProduct] = useState("");
  const isAvailable = props.value.quantity && Number(props.value.quantity) > 0;
  function handleProductView(product){
    setSelectedViewProduct(product);
    setOpenProductViewModal(true);
  }
  return (
    <div className="border rounded-lg shadow-xl overflow-hidden transition-shadow duration-300">
      <div onClick={handleProductView(props)} className="w-full overflow-hidden aspect-[3/2]">
        <img
          className="w-full h-full cursor-pointer transition-transform duration-300 transform hover:scale-105"
          src={props.value.image}
          alt={props.value.productName}
        />
      </div>
      <div className="p-4">
        <h2
          onClick={handleProductView(props)}
          className="text-lg font-semibold mb-2 cursor-pointer"
        >
          {props.value.productName}
        </h2>

        <div className=" min-h-20 max-h-20">
          <p className="text-gray-600 text-sm">{props.value.description}</p>
        </div>

        <div className="flex items-center justify-between">
          {props.value.specialPrice ? (
            <div className="flex flex-col">
              <span className="text-gray-200 line-through">
                ${Number(props.value.price).toFixed(2)}
              </span>
              <span className="text-xl font-bold text-slate-700">
                ${Number(props.value.specialPrice).toFixed(2)}
              </span>
            </div>
          ) : (
            <span className="text-xl font-bold text-slate-700">
              ${Number(props.value.price).toFixed(2)}
            </span>
          )}

          <button 
            disabled = {isAvailable || btnLoader}
            onClick={() => {}}
            className={`bg-blue-500 ${isAvailable ? "opacity-100 hover:bg-blue-600" : "opacity-70"}
          text-white py-1 px-2 rounded-lg items-center transition-colors duration-300 w-36 flex justify-center`}>
            <FaShoppingCart className="mr-2"/>
            {isAvailable? "Add to Cart" : "Stock Out"}
          </button>

        </div>
      </div>
    </div>
  );
}
