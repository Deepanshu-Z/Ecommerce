import Home from "./Components/home/Home";
import Navbar from "./Components/home/Navbar";
import Products from "./Components/Products";
import { BrowserRouter as Router, Routes, Route} from "react-router-dom";
import About from './Components/About'
import Contact from './Components/Contact'
import React from "react";
import { Toaster } from "react-hot-toast";
import Cart from "./Components/cart/Cart";
import LogIn from "./Components/auth/Login";
import PrivateRoute from "./Components/PrivateRoute";
import Register from "./Components/auth/Register";
function App() {
  return (
    <div className="min-h-screen bg-white text-2xl font-bold">
      <React.Fragment>
      <Router>
        <Navbar />
        <Routes>
          <Route path="/" element= { <Home /> }/>
          <Route path="/products" element= { <Products /> }/>   
          <Route path='/about' element={ <About />}/>
          <Route path='/contact' element={ <Contact />}/>
          <Route path="/cart" element = {<Cart />}/>
          <Route path='/' element={<PrivateRoute publicPage />}></Route>
          <Route path='/login' element={ <LogIn />}/>
          <Route path='/register' element={ <Register />}/>
        </Routes>
      </Router>
      <Toaster position= 'bottom-center'/>
      </React.Fragment>
      
      
    </div>
  );
}

export default App;
