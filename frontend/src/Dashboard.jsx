import { useState, useEffect } from "react";
import axiosInstance from "./axiosInstance";
import Error from "./Error";
import Layout from "./Layout";

const Dashboard = () => {
  const [error, setError] = useState("");
  //const [cart, setCart] = useState([]); // Local state to track the cart

  // useEffect(() => {
  //   const fetchProducts = async () => {
  //     try {
  //       const response = await axiosInstance.get("products");
  //       //      setProducts(response.data);
  //     } catch (error) {
  //       console.error("Error fetching products:", error);
  //       setError("Error fetching products.");
  //     }
  //   };
  //
  //   // fetchProducts();
  //   //  }, [getProducts]);
  // }, []);

  useEffect(() => {
    //    setCart(getCart());
  }, []);

  return (
    <Layout>
      <div className="container mt-5">
        {error && <Error erro={error} setError={setError} />}
        <h1>Hi</h1>
        <h1>Hi</h1>
        <h1>Hi</h1>
        <h1>Hi</h1>
        <h1>Hi</h1>
        <h1>Hi</h1>
        <h1>Hi</h1>
        <h1>Hi</h1>
        <h1>Hi</h1>
        <h1>Hi</h1>
        <h1>Hi</h1>
        <h1>Hi</h1>
        <h1>Hi</h1>
        <h1>Hi</h1>
        <h1>Hi</h1>
        <h1>Hi</h1>
        <h1>Hi</h1>
        <h1>Hi</h1>
        <h1>Hi</h1>
      </div>
    </Layout>
  );
};

export default Dashboard;
