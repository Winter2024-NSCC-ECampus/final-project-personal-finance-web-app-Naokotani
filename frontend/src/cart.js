import Cookies from "js-cookie";

const CART_COOKIE_NAME = "cart";

export const getCart = () => {
  const cart = Cookies.get(CART_COOKIE_NAME);
  return cart ? JSON.parse(cart) : [];
};

export const saveCart = (cart) => {
  Cookies.set(CART_COOKIE_NAME, JSON.stringify(cart), { expires: 7 });
};

export const addToCart = (productId, quantity) => {
  let cart = getCart();

  const existingProductIndex = cart.findIndex(item => item.productId === productId);

  if (existingProductIndex >= 0) {
    cart[existingProductIndex].quantity += quantity;
  } else {
    cart.push({ productId, quantity });
  }

  saveCart(cart);
};

export const removeFromCart = (productId) => {
  let cart = getCart();

  cart = cart.filter(item => item.productId !== productId);

  saveCart(cart);
};

export const clearCart = () => {
  let cart = []
  saveCart(cart);
};
