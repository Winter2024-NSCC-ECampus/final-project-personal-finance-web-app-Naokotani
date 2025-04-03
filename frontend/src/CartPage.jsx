import React, { useState, useEffect } from "react";
import axiosInstance from "./axiosInstance";
import Layout from "./Layout";
import { getCart, addToCart, removeFromCart, saveCart, clearCart } from "./cart";
import Cookies from "js-cookie";

const Cart = () => {
  const [products, setProducts] = useState([]);
  const [cart, setCart] = useState([]);
  const [error, setError] = useState(null);
  const [orderError, setOrderError] = useState(null);
  const [orderSuccess, setOrderSuccess] = useState(false);
  const [user, setUser] = useState(false);

  const fetchProductById = async (productId) => {
    try {
      const response = await axiosInstance.get(`/products/${productId}`);
      setProducts((prevProducts) => [
        ...prevProducts,
        response.data,
      ]);
    } catch (error) {
      setError("Error fetching product details.");
      console.error("Error fetching product:", error);
    }
  };

  useEffect(() => {
    const cartItems = getCart();
    setCart(cartItems);

    cartItems.forEach((item) => {
      fetchProductById(item.productId);
    });

    const token = Cookies.get('role');
    if (token)
      setUser(true);
    else
      setUser(false);

  }, []);

  const handleRemoveFromCart = (productId) => {
    removeFromCart(productId);
    setCart(getCart());
  };

  const handleQuantityChange = (productId, newQuantity) => {
    if (newQuantity < 1) return;
    let updatedCart = getCart();

    updatedCart = updatedCart.map(item =>
      item.productId === productId ? { ...item, quantity: newQuantity } : item
    );

    saveCart(updatedCart);
    setCart(updatedCart);
  };

  const handlePlaceOrder = async () => {
    try {
      const orderData = {
        purchases: cart.map(item => ({
          productId: item.productId,
          quantity: item.quantity,
        })),
      };

      const response = await axiosInstance.post("/user/orders", orderData);
      if (response.status === 200) {
        setOrderSuccess(true);
        setOrderError(null);
        clearCart();
        setProducts([])
        setCart([])
      }
    } catch (error) {
      setOrderError("Error placing order.");
      console.error("Error placing order:", error);
    }
  };

  return (
    <Layout>
      <div className="container mt-5">
        <h1 className="title has-text-centered">Your Cart</h1>

        {error && <div className="notification is-danger">{error}</div>}
        {orderError && <div className="notification is-danger">{orderError}</div>}
        {orderSuccess && <div className="notification is-success">Order placed successfully!</div>}

        <div className="cart-details mb-5">
          <h2 className="title is-4">Cart Details</h2>
          <ul>
            {cart.length === 0 ? (
              <p>Your cart is empty!</p>
            ) : (
              cart.map((item) => {
                const product = products.find((prod) => prod.id === item.productId);
                if (!product) return null;

                return (
                  <li key={item.productId} className="box mb-4">
                    <h3 className="title is-5">{product.name}</h3>
                    <p>
                      <strong>Price: </strong>${product.price.toFixed(2)}
                    </p>
                    <div className="field is-horizontal">
                      <div className="field-body">
                        <div className="field">
                          <input
                            className="input is-narrow"
                            type="number"
                            min="1"
                            value={item.quantity}
                            onChange={(e) => handleQuantityChange(item.productId, parseInt(e.target.value))}
                          />
                        </div>
                      </div>
                    </div>
                    <p>
                      <strong>Total: </strong>${(product.price * item.quantity).toFixed(2)}
                    </p>
                    <button
                      className="button is-danger is-small"
                      onClick={() => handleRemoveFromCart(item.productId)}
                    >
                      Remove
                    </button>
                  </li>
                );
              })
            )}
          </ul>
        </div>

        {cart.length > 0 && (
          <div className="mt-5">
            <button
              disabled={user ? false : true}
              className="button is-primary"
              onClick={handlePlaceOrder}
            >
              Place Order {"foo"}
            </button>
          </div>
        )}
      </div>
    </Layout>
  );
};

export default Cart;
