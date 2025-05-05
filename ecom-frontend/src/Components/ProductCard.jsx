import { useState } from "react"

export default function ProductCard(props){
    const [openProductViewModal, setOpenProductViewModal] = useState(false);
    const btnLoader = false;
    const [selectedViewProduct, setSelectedViewProduct] = useState("");
    const isAvailable = props.value.quantity && Number(props.value.quantity) > 0;
    return(
        <div className="border rounded-lg shadow-xl overflow-hidden transition-shadow duration-300">
            <div onClick={() => {}} 
            className="w-full overflow-hidden aspect-[3/2]">
                <img 
                className= "w-full h-full cursor-pointer transition-transform duration-300 transform hover:scale-105"
                src={props.value.image} alt={props.value.productName} />
            </div>
            <div className="p-4">
                <h2 onClick={() => {}}
                    className="text-lg font-semibold mb-2 cursor-pointer">
                    {props.value.productName}
                </h2>
            </div>           
        </div>
    )
}