import { useState, useEffect } from "react";
import axiosInstance from "./axiosInstance";
import Layout from "./Layout";
import { Link } from "react-router-dom";

const AdminProducts = () => {
  const [products, setProducts] = useState([]);
  const [category, setCategory] = useState([]);
  const [newProduct, setNewProduct] = useState({
    name: "",
    description: "",
    price: "",
    categoryId: ""
  });
  const [error, setError] = useState("");

  const handleDeleteProduct = async (id) => {
    try {
      await axiosInstance.delete(`/admin/products`, {
        params: { id: id }
      });
      setProducts(products.filter((product) => product.id !== id));
    } catch (error) {
      console.error("Error deleting product:", error);
      setError("Error deleting product.");
    }
  };

  const handleCreateProduct = async () => {
    try {
      const response = await axiosInstance.post("/admin/products", newProduct);
      setProducts([...products, response.data]);
      setNewProduct({ name: "", description: "", price: "", category: "" });
    } catch (error) {
      console.error("Error creating product:", error);
      setError("Error creating product.");
    }
  };

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const response = await axiosInstance.get("/products");
        setProducts(response.data);
      } catch (error) {
        console.error("Error fetching products:", error);
        setError("Error fetching products.");
      }
    };

    const fetchCategories = async () => {
      try {
        const response = await axiosInstance.get("/admin/category");
        setCategory(response.data);
      } catch (error) {
        console.error("Error fetching categories:", error);
        setError("Error fetching products.");
      }
    };


    fetchCategories();
    fetchProducts();
  }, []);
  return (
    <Layout>
      <div className="container mt-5">
        {error && <Error erro={error} setError={setError} />}

        <h3 className="title is-3">Manage Products</h3>
        <div className="mb-4">
          <h4 className="title is-4">Create New Product</h4>
          <div className="field">
            <label className="label">Product Name</label>
            <input
              className="input"
              type="text"
              value={newProduct.name}
              onChange={(e) => setNewProduct({ ...newProduct, name: e.target.value })}
            />
          </div>
          <div className="field">
            <label className="label">Description</label>
            <input
              className="input"
              type="text"
              value={newProduct.description}
              onChange={(e) => setNewProduct({ ...newProduct, description: e.target.value })}
            />
          </div>
          <div className="field">
            <label className="label">Price</label>
            <input
              className="input"
              type="number"
              value={newProduct.price}
              onChange={(e) => setNewProduct({ ...newProduct, price: e.target.value })}
            />
          </div>
          <div className="field">
            <label className="label">Category</label>
            <select
              className="input"
              value={newProduct.categoryId}
              onChange={(e) => setNewProduct({ ...newProduct, categoryId: e.target.value })}
            >
              <option value="" disabled>Select a category</option>
              {category && category.map((c) => (
                <option key={c.id} value={c.id}>
                  {c.name}
                </option>
              ))}
            </select>
          </div>
          <button className="button is-primary" onClick={handleCreateProduct}>
            Create Product
          </button>
        </div>

        <h4 className="title is-4">Product List</h4>
        <ul>
          {products.map((product) => (
            <li key={product.id} className="box mb-4">
              <h5 className="title is-5">{product.name}</h5>
              <p>{product.description}</p>
              <p><strong>Price:</strong> ${product.price}</p>
              <p><strong>Category:</strong> {product.category}</p>
              <p><strong>Stock:</strong> {product.stock}</p>

              <div>
                <Link to={`/admin/products/edit/${product.id}`} className="button is-link mr-2">
                  Edit Product
                </Link>
                <button
                  className="button is-danger"
                  onClick={() => handleDeleteProduct(product.id)}
                >
                  Delete Product
                </button>
              </div>
            </li>
          ))}
        </ul>
      </div>
    </Layout>
  );
};

export default AdminProducts;
