import { useState } from "react";
import { FaShoppingCart } from "react-icons/fa";
import ProductViewModal from "./ProductViewModal";
import { addToCart } from "../Store/Action/action";
import { useDispatch } from "react-redux";
import toast from "react-hot-toast";

export default function ProductCard({ value: product, about }) {
  if (!product) {
    return null;
  }

  const [openProductViewModal, setOpenProductViewModal] = useState(false);
  const [selectedViewProduct, setSelectedViewProduct] = useState("");
  const btnLoader = false;

  const isAvailable = product.quantity && Number(product.quantity) > 0;

  function handleProductView(product) {
    if (!about) {
      setSelectedViewProduct(product);
      setOpenProductViewModal(true);
    }
  }
  const dispatch = useDispatch();
  function addToCartHandler(cartItems) {
    dispatch(addToCart(cartItems, 1, toast));
  }

  return (
    <div className="border rounded-lg shadow-xl overflow-hidden transition-shadow duration-300">
      <div
        onClick={() => handleProductView(product)}
        className="w-full overflow-hidden aspect-[3/2]"
      >
        <img
          className="w-full h-full cursor-pointer transition-transform duration-300 transform hover:scale-105"
          src={product.image || "/default-product.jpg"}
          alt={product.productName || "Product"}
        />
      </div>

      <div className="p-4">
        <h2
          onClick={() => handleProductView(product)}
          className="text-lg font-semibold mb-2 cursor-pointer"
        >
          {product.productName || "No Name"}
        </h2>

        <div className="min-h-20 max-h-20">
          <p className="text-gray-600 text-sm">
            {product.description || "No description available."}
          </p>
        </div>

        {!about && (
          <div className="flex items-center justify-between">
            {product.specialPrice ? (
              <div className="flex flex-col">
                <span className="text-gray-400 line-through">
                  ${Number(product.price).toFixed(2)}
                </span>
                <span className="text-xl font-bold text-slate-700">
                  ${Number(product.specialPrice).toFixed(2)}
                </span>
              </div>
            ) : (
              <span className="text-xl font-bold text-slate-700">
                ${Number(product.price).toFixed(2)}
              </span>
            )}

            <button
              disabled={!isAvailable || btnLoader}
              onClick={() =>
                addToCartHandler({
                  productId: product.productId,
                  image: product.image,
                  productName: product.productName,
                  description: product.description,
                  specialPrice: product.specialPrice,
                  price: product.price,
                  quantity: product.quantity,
                })
              }
              className={`bg-blue-500 ${
                isAvailable ? "opacity-100 hover:bg-blue-600" : "opacity-70"
              }
                        text-white py-2 px-3 rounded-lg items-center transition-colors duration-300 w-36 flex justify-center`}
            >
              <FaShoppingCart className="mr-2" />
              {isAvailable ? "Add to Cart" : "Stock Out"}
            </button>
          </div>
        )}
      </div>

      <ProductViewModal
        open={openProductViewModal}
        setOpen={setOpenProductViewModal}
        product={selectedViewProduct}
        isAvailable={isAvailable}
      />
    </div>
  );
}
