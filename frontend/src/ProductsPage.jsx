import { useState, useEffect } from "react";
import axiosInstance from "./axiosInstance";
import Error from "./Error";
import Layout from "./Layout";
import { addToCart, removeFromCart, getCart } from "./cart"; // Import cart utility functions

const ProductsPage = ({ getProducts }) => {
  const [products, setProducts] = useState([]);
  const [error, setError] = useState("");
  const [cart, setCart] = useState([]); // Local state to track the cart
  const [selectedCategory, setSelectedCategory] = useState(""); // Track the selected category

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const response = await axiosInstance.get("products");
        setProducts(response.data);
      } catch (error) {
        console.error("Error fetching products:", error);
        setError("Error fetching products.");
      }
    };

    fetchProducts();
  }, [getProducts]);

  useEffect(() => {
    setCart(getCart());
  }, []);

  const handleAddToCart = (productId) => {
    addToCart(productId, 1);
    setCart(getCart());
  };

  const handleRemoveFromCart = (productId) => {
    removeFromCart(productId);
    setCart(getCart());
  };

  const handleCategoryChange = (category) => {
    setSelectedCategory(category); // Set the selected category
  };

  // Filter products based on selected category
  const filteredProducts = selectedCategory
    ? products.filter((product) => product.category === selectedCategory)
    : products;

  // Get unique categories from the products
  const categories = [...new Set(products.map((product) => product.category))];

  return (
    <Layout>
      <div className="container mt-5">
        {error && <Error erro={error} setError={setError} />}

        {/* Category Filter Buttons */}
        <div className="mb-4">
          <button
            className="button is-link"
            onClick={() => handleCategoryChange("")}
          >
            All Categories
          </button>
          {categories.map((category) => (
            <button
              key={category}
              className="button is-link ml-2"
              onClick={() => handleCategoryChange(category)}
            >
              {category}
            </button>
          ))}
        </div>

        {/* Product List */}
        <ul>
          {filteredProducts.map((product) => {
            const isInCart = cart.some((item) => item.productId === product.id);

            return (
              <li key={product.id} className="box mb-4">
                <h3 className="title is-4">{product.name}</h3>
                <p>{product.description}</p>
                <p><strong>Category:</strong> {product.category}</p>
                <p><strong>Price:</strong> ${product.price.toFixed(2)}</p>

                {!isInCart ? (
                  <button
                    className="button is-primary"
                    onClick={() => handleAddToCart(product.id)}
                  >
                    Add to Cart
                  </button>
                ) : (
                  <button
                    className="button is-danger"
                    onClick={() => handleRemoveFromCart(product.id)}
                  >
                    Remove from Cart
                  </button>
                )}
              </li>
            );
          })}
        </ul>
      </div>
    </Layout>
  );
};

export default ProductsPage;
