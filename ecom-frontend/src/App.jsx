import Home from "./Components/home/Home";
import Navbar from "./Components/home/Navbar";
import Products from "./Components/Products";
import { BrowserRouter as Router, Routes, Route} from "react-router-dom";

function App() {
  return (
    <div className="min-h-screen bg-white text-2xl font-bold">
      <Router>
        <Navbar />
        <Routes>
          <Route path="/" element= { <Home /> }/>
          <Route path="/products" element= { <Products /> }/>   
        </Routes>
      </Router>
      
      
    </div>
  );
}

export default App;
