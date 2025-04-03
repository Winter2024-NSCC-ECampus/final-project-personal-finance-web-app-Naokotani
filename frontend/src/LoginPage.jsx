import React, { useState } from 'react';
import axiosInstance from './axiosInstance';
import { useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie'

function LoginPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const history = useNavigate();

  const handleLogin = async () => {
    try {
      if (!email || !password) {
        setError('Please enter both email and password.');
        return;
      }

      const res = await axiosInstance.post(`/auth/signin`, { email, password });
      Cookies.set('authToken', res.data.jwt, { expires: 7 });
      Cookies.set('role', res.data.role[0]);

      if (res.data.role.includes("ROLE_admin"))
        history('/admin/panel');
      else
        history('/');

    } catch (error) {
      console.error('Login failed:', error.response ? error.response.data : error.message);
      setError('Invalid email or password.');
    }
  };

  return (
    <div className="container">
      <div className="box mt-6" >
        <div className="p-4">
          <h2 className="title is-4 has-text-centered mb-4">Login Page</h2>
          <div className="field">
            <label className="label" htmlFor="email">Email Address</label>
            <div className="control">
              <input
                className="input"
                id="email"
                type="email"
                placeholder="Email address"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
            </div>
          </div>
          <div className="field">
            <label className="label" htmlFor="password">Password</label>
            <div className="control">
              <input
                className="input"
                id="password"
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </div>
          </div>
          {error && <p className="has-text-danger">{error}</p>}
          <div className="field">
            <div className="control">
              <button
                className="button is-primary is-fullwidth"
                style={{ height: '50px' }}
                onClick={handleLogin}
              >
                Sign in
              </button>
            </div>
          </div>
          <div className="has-text-centered">
            <p>Not a member? <a href="/signup">Register</a></p>
          </div>
        </div>
      </div>
    </div>
  );
}

export default LoginPage;
