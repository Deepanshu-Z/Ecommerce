import {
  Button,
  Dialog,
  DialogBackdrop,
  DialogPanel,
  DialogTitle,
} from "@headlessui/react";
import { useState } from "react";
import { MdClose, MdDone } from "react-icons/md";
import Status from "./Status";
export default function ProductViewModal({
  open,
  setOpen,
  product,
  isAvailable,
}) {
  function handleClose() {
    setOpen(false);
  }

  return (
    <>
      <Dialog
        open={open}
        as="div"
        className="relative z-10 focus:outline-none"
        onClose={handleClose}
      >
        <DialogBackdrop className="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity" />
        <div className="fixed inset-0 z-10 w-screen overflow-y-auto">
          <div className="flex min-h-full items-center justify-center p-4">
            <DialogPanel
              transition
              className="relative transform overlow-hidden rounded-lg bg-white shadow-xl transition-all md:max-w-[620px] md:min-w-[620px] w-full"
            >
              {product.image && (
                <div className="flex justify-center aspect-[3/2]">
                  <img
                    className="w-full h-full cursor-pointer transition-transform duration-300 transform hover:scale-105"
                    src={product.image}
                    alt={product.productName}
                  />
                </div>
              )}
              <div className="px-6 pt-10 pb-2">
                <div className="flex justify-between items-baseline">
                  <DialogTitle
                    as="h3"
                    className="lg:text-3xl sm:text-2 text-xl font-semibold leading-6 text-gray-800-800m"
                  >
                    {product.productName}
                  </DialogTitle>

                  <div>
                    {isAvailable ? (
                      <div>
                        <Status
                          text="In stock"
                          icon={MdDone}
                          bg="bg-teal-200"
                          color="text-teal-900"
                        />
                      </div>
                    ) : (
                      <div>
                        <Status
                          text="Out of stock!"
                          icon={MdClose}
                          bg="bg-rose-200"
                          color="text-rose-700"
                        />
                      </div>
                    )}
                  </div>
                </div>


                <hr className="my-2 border-slate-300" />


                <p className="mt-2 text-sm/6 text-slate-800">
                  {product.description}
                </p>
                {product.specialPrice ? (
                  <div className="flex flex-col">
                    <span className="text-gray-200 line-through">
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

                <div className="mt-4">
                  <Button
                    className="cursor-pointer inline-flex items-center gap-2 rounded-md bg-gray-700 px-3 py-1.5 text-sm/6 font-semibold text-white shadow-inner shadow-white/10"
                    onClick={handleClose}
                  >
                    CLOSE
                  </Button>
                </div>
              </div>
            </DialogPanel>
          </div>
        </div>
      </Dialog>
    </>
  );
}
