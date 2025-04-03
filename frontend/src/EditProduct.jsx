import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import axiosInstance from "./axiosInstance";
import Layout from "./Layout";

const EditProduct = () => {
  const { productId } = useParams();
  const [product, setProduct] = useState({
    name: "",
    description: "",
    price: "",
    stock: "",
    categoryId: "",
  });
  const [categories, setCategories] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchProduct = async () => {
      try {
        const response = await axiosInstance.get(`/products/${productId}`);
        setProduct(response.data);
      } catch (error) {
        console.error("Error fetching product:", error);
        setError("Error fetching product.");
      }
    };

    const fetchCategories = async () => {
      try {
        const response = await axiosInstance.get("/admin/category");
        setCategories(response.data);
      } catch (error) {
        console.error("Error fetching categories:", error);
        setError("Error fetching categories.");
      }
    };

    fetchProduct();
    fetchCategories();
  }, [productId]);

  const handleUpdateProduct = async () => {
    try {
      const requestBody = {
        name: product.name,
        description: product.description,
        categoryId: product.categoryId,
        price: product.price
      };

      const response = await axiosInstance.put(`/admin/products`, requestBody, {
        params: { productId: productId }
      });

      history('/admin/products');
    } catch (error) {
      console.error("Error updating product:", error);
      setError("Error updating product.");
    }
  };

  const handleChange = (e) => {
    setProduct({
      ...product,
      [e.target.name]: e.target.value,
    });
  };

  return (
    <Layout>
      <div className="container mt-5">
        {error && <div className="notification is-danger">{error}</div>}

        <h3 className="title is-3">Edit Product</h3>
        <div className="mb-4">
          <div className="field">
            <label className="label">Product Name</label>
            <input
              className="input"
              type="text"
              name="name"
              value={product.name}
              onChange={handleChange}
            />
          </div>
          <div className="field">
            <label className="label">Description</label>
            <input
              className="input"
              type="text"
              name="description"
              value={product.description}
              onChange={handleChange}
            />
          </div>
          <div className="field">
            <label className="label">Price</label>
            <input
              className="input"
              type="number"
              name="price"
              value={product.price}
              onChange={handleChange}
            />
          </div>
          <div className="field">
            <label className="label">Stock</label>
            <input
              className="input"
              type="number"
              name="stock"
              value={product.stock}
              onChange={handleChange}
            />
          </div>
          <div className="field">
            <label className="label">Category</label>
            <select
              className="input"
              name="categoryId"
              value={product.categoryId}
              onChange={handleChange}
            >
              <option value="" disabled>Select a category</option>
              {categories.map((c) => (
                <option key={c.id} value={c.id}>
                  {c.name}
                </option>
              ))}
            </select>
          </div>
          <button className="button is-primary" onClick={handleUpdateProduct}>
            Update Product
          </button>
        </div>
      </div>
    </Layout>
  );
};

export default EditProduct;
