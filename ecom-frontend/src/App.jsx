import Home from "./Components/home/Home";
import Navbar from "./Components/home/Navbar";
import Products from "./Components/Products";
import { BrowserRouter as Router, Routes, Route} from "react-router-dom";
import About from './Components/About'
import Contact from './Components/Contact'

function App() {
  return (
    <div className="min-h-screen bg-white text-2xl font-bold">
      <Router>
        <Navbar />
        <Routes>
          <Route path="/" element= { <Home /> }/>
          <Route path="/products" element= { <Products /> }/>   
          <Route path='/about' element={ <About />}/>
          <Route path='/contact' element={ <Contact />}/>
        </Routes>
      </Router>
      
      
    </div>
  );
}

export default App;
