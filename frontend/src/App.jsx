import React from 'react';
import CartPage from './CartPage'
import LoginPage from './LoginPage';
import SignupPage from './SignUpPage';
import ProductsPage from './ProductsPage';
import AdminPanel from './AdminPanel'
import EditProduct from './EditProduct';
import 'bulma/css/bulma.min.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import AdminCategories from './AdminCategories';
import AdminProducts from './AdminProducts';


function App() {
  return (
    <>
      <Router>
        <Routes>
          <Route path="/" element={<ProductsPage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/signup" element={<SignupPage />} />
          <Route path="/cart" element={<CartPage />} />
          <Route path="/admin/panel" element={<AdminPanel />} />
          <Route path="/admin/products" element={<AdminProducts />} />
          <Route path="/admin/categories" element={<AdminCategories />} />
          <Route path="/admin/products/edit/:productId" element={<EditProduct />} />
        </Routes>
      </Router>
    </>
  )
}

export default App
