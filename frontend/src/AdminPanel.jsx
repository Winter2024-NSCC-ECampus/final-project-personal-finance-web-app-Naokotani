import Layout from "./Layout";
import { Link } from "react-router-dom";

const AdminPanel = () => {

  return (
    <Layout>
      <div className="container mt-5">
        <div className="columns is-multiline">
          <div className="column is-one-third">
            <div className="box">
              <h3 className="title is-4">Categories</h3>
              <p>
                Manage your product categories here. You can add, remove, and
                edit categories.
              </p>
              <Link to="/admin/categories" className="button is-link">
                Manage Categories
              </Link>
            </div>
          </div>
          <div className="column is-one-third">
            <div className="box">
              <h3 className="title is-4">Products</h3>
              <p>
                Manage your products here. Add new products, edit existing
                ones, or remove products.
              </p>
              <Link to="/admin/products" className="button is-link">
                Manage Products
              </Link>
            </div>
          </div>
        </div>
      </div>
    </Layout >
  );
};

export default AdminPanel;
