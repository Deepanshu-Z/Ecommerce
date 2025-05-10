import { FormControl, IconButton, InputLabel, MenuItem, Select, Tooltip, Button} from "@mui/material";
import { useEffect, useState } from "react";
import { FiArrowDown, FiArrowUp, FiRefreshCw, FiSearch } from "react-icons/fi";
import { useLocation, useNavigate, useSearchParams } from "react-router-dom";

export function Filter() {
  const categories = [
    { categoryId: 1, categoryName: "Elecs" },
    { categoryId: 2, categoryName: "HOWS U" },
    { categoryId: 3, categoryName: "BAD WITCH" }
  ];

  const [searchParams] = useSearchParams();
  const params = new URLSearchParams(searchParams);
  const pathName = useLocation();
  const navigate = useNavigate();

  const [category, setCategory] = useState("all");
   const [sortOrder, setSortOrder] = useState("asc");
   const [searchTerm, setSearchTerm] = useState("");

   useEffect(() => {
        const currentCategory = searchParams.get("category") || "all";
        const currentSortOrder = searchParams.get("sortby") || "asc";
        const currentSearchItem = searchParams.get("key") || "";
        setCategory(currentCategory);
        setSortOrder(currentSortOrder);
        setSearchTerm(currentSearchItem);
   }, [searchParams])

  function handleCategoryChange(e) {
    const selectedCategory = e.target.value;
    if (selectedCategory == "all") {
        params.delete("category")
    }else {
        params.set("category", selectedCategory)
    }
    navigate(`${pathName}?${params}`)

    setCategory(e.target.value);
  }

  const toggleSortOrder = () => {
        setSortOrder((prevOrder) => {
            const newOrder = (prevOrder == "asc")?"desc":"asc";
            params.set("sortby", newOrder)
            navigate(`${pathName}?${params}`)
            return newOrder
        })
  }
  
  const handleClearFilters = () => {
    navigate({pathName : window.location.pathname });

  }

  return (
    <div className="flex lg:flex-row flex-col-reverse lg:justify-between justify-center items-center gap-4">
      {/* SEARCH BAR */}
      <div className="relative flex items-center 2xl:w-[450px] sm:w-[420px] w-full">
        <input
          type="text"
          placeholder="Search Products"
          className="border border-gray-400 text-slate-800 rounded-md py-2 pl-10 pr-4 w-full focus:outline-none focus:ring-2 focus:ring-[#1976d2]"
        />
        <FiSearch className="absolute left-3 text-slate-800" size={20} />
      </div>

      {/* CATEGORY DROPDOWN */}
      <div className="flex sm:flex-row flex-col items-center gap-4">
        <FormControl variant="outlined" size="small" className="min-w-[150px]">
          <InputLabel id="category-select-label">Category</InputLabel>
          <Select
            labelId="category-select-label"
            value={category}
            onChange={handleCategoryChange}
            label="Category"
          >
            <MenuItem value="all">All</MenuItem>
            {categories.map((item) => (
              <MenuItem key={item.categoryId} value={item.categoryName}>
                {item.categoryName}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
        {/* SORT BUTTON AND CLEAR BUTTON */}
      <Tooltip title="Sorted by price:asc">
            <Button variant = "contained" color = "primary"
            onClick={toggleSortOrder}
            className="flex items-center gap-2 h-15">
                SortBy
                {sortOrder == "asc"?(
                    <FiArrowUp size = {20} />
                ):(
                    <FiArrowDown size = {20} />
                )}
            </Button>
      </Tooltip>

      <button onClick={handleClearFilters}
      className="flex items-center gap-2 bg-rose-900 text-white px-3 py-2 rounded-md transition duration-300 ease-in shadow-md focus:outline-none" > 
        <FiRefreshCw className="font-semibold size={16}"/>
        <span className="font-semibold ">Clear Filter</span>
      </button>
      </div>
    </div>
  );
}
