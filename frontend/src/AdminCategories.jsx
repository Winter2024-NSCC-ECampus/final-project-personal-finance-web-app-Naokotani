import { useState, useEffect } from "react";
import axiosInstance from "./axiosInstance";
import Layout from "./Layout";
import { Link } from "react-router-dom";

const AdminCategories = () => {
  const [categories, setCategories] = useState([]);
  const [error, setError] = useState("");
  const [newCategory, setNewCategory] = useState({
    name: "",
    description: "",
  });

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await axiosInstance.get("/admin/category");
        setCategories(response.data);
      } catch (error) {
        console.error("Error fetching categories:", error);
        setError("Error fetching categories.");
      }
    };

    fetchCategories();
  }, []);

  const handleDeleteCategory = async (id) => {
    try {
      await axiosInstance.delete(`/admin/category`, {
        params: { id: id }
      });
      setCategories(categories.filter((category) => category.id !== id));
    } catch (error) {
      console.error("Error deleting category:", error);
      setError("Error deleting category.");
    }
  };

  const handleCreateCategory = async () => {
    try {
      const response = await axiosInstance.post("/admin/category", newCategory);
      setCategories([...categories, response.data]);
      setNewCategory({
        name: "",
        description: "",
      });
    } catch (error) {
      console.error("Error creating category:", error);
      setError("Error creating category.");
    }
  };

  return (
    <Layout>
      <div className="container mt-5">
        {error && <Error erro={error} setError={setError} />}

        <h3 className="title is-3">Manage Categories</h3>
        <div className="mb-4">
          <h4 className="title is-4">Create New Category</h4>
          <div className="field">
            <label className="label">Category Name</label>
            <input
              className="input"
              type="text"
              value={newCategory.name}
              onChange={(e) => setNewCategory({ ...newCategory, name: e.target.value })}
            />
          </div>
          <label className="label">Category Description</label>
          <input
            className="input"
            type="text"
            value={newCategory.description}
            onChange={(e) => setNewCategory({ ...newCategory, description: e.target.value })}
          />
          <button className="button is-primary mt-3" onClick={handleCreateCategory}>
            Create Category
          </button>
        </div>

        <h4 className="title is-4">Category List</h4>
        <ul>
          {categories.map((category) => (
            <li key={category.id} className="box mb-4">
              <h5 className="title is-5">{category.name}</h5>
              <div>
                <Link to={`/admin/categories/edit/${category.id}`} className="button is-link mr-2">
                  Edit Category
                </Link>
                <button
                  className="button is-danger"
                  onClick={() => handleDeleteCategory(category.id)}
                >
                  Delete Category
                </button>
              </div>
            </li>
          ))}
        </ul>
      </div>
    </Layout>
  );
};

export default AdminCategories;
