import React, { useEffect, useState } from "react";
import { Link, useLocation } from "react-router-dom";
import { getCart } from "./cart";
import Cookies from "js-cookie";

const Navbar = () => {
  const cart = getCart();
  const [user, setUser] = useState(false);

  const location = useLocation();


  const handleLogout = () => {
    Cookies.remove('authToken');
    setUser(null);
  }

  useEffect(() => {
    const token = Cookies.get("authToken");
    if (token)
      setUser(true)
    else
      setUser(false)

  }, [location]);


  const totalItemsInCart = cart.reduce((total, item) => total + item.quantity, 0);
  return (
    <nav className="navbar is-primary" role="navigation" aria-label="main navigation">
      <div className="navbar-brand">
        <Link to="/" className="navbar-item">
          <h1 className="title">STORE</h1>
        </Link>
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" />
        <a
          role="button"
          className="navbar-burger burger"
          aria-label="menu"
          aria-expanded="false"
          data-target="navbarBasicExample"
        >
          <span aria-hidden="true"></span>
          <span aria-hidden="true"></span>
          <span aria-hidden="true"></span>
        </a>
      </div>

      <div id="navbarBasicExample" className="navbar-menu">

        <div className="navbar-end">
          {user ?
            <Link to="/" className="navbar-item" onClick={handleLogout}>
              Sign Out
            </Link> :
            <Link to="/login" className="navbar-item">
              Sign In
            </Link>
          }

          <Link to="/cart" className="navbar-item">
            <span className="icon">
              <i className="material-icons">shopping_cart</i>
            </span>
            {totalItemsInCart > 0 && (
              <span className="badge">{totalItemsInCart}</span>
            )}
          </Link>
        </div>
      </div>
    </nav>
  );
};

const Layout = ({ children }) => {
  return (
    <div>
      <Navbar />
      <div>{children}</div>
    </div>
  );
};

export default Layout;
