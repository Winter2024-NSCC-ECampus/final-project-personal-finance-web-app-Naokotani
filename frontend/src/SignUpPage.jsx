import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom'; // Import useHistory hook

function SignupPage() {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [error, setError] = useState(''); // State to manage error messages
  const history = useNavigate(); // Get the history object for redirection
  const apiUrl = import.meta.env.VITE_DEV_API_URL;

  const handleSignup = async () => {
    try {
      // Check for empty fields
      if (!firstName || !lastName || !email || !password || !confirmPassword) {
        setError('Please fill in all fields.');
        return;
      }

      if (password !== confirmPassword) {
        throw new Error('Passwords do not match');
      }

      const response = await axios.post(`${apiUrl}auth/signup`, {
        firstName,
        lastName,
        email,
        password,
      });

      console.log(response.data);
      history('/');
    } catch (error) {
      console.error('Signup failed:', error.response ? error.response.data : error.message);
      setError(error.response ? error.response.data : error.message);
    }
  };

  return (
    <div className="container">
      <div className="box mt-6">
        <div className="p-4">
          <h2 className="title is-4 has-text-centered mb-4">Sign Up Page</h2>
          {error && <p className="has-text-danger">{error}</p>}

          <div className="field">
            <label className="label" htmlFor="firstName">First Name</label>
            <div className="control">
              <input
                className="input"
                id="firstName"
                type="text"
                placeholder="First Name"
                value={firstName}
                onChange={(e) => setFirstName(e.target.value)}
              />
            </div>
          </div>

          <div className="field">
            <label className="label" htmlFor="lastName">Last Name</label>
            <div className="control">
              <input
                className="input"
                id="lastName"
                type="text"
                placeholder="Last Name"
                value={lastName}
                onChange={(e) => setLastName(e.target.value)}
              />
            </div>
          </div>

          <div className="field">
            <label className="label" htmlFor="email">Email Address</label>
            <div className="control">
              <input
                className="input"
                id="email"
                type="email"
                placeholder="Email Address"
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

          <div className="field">
            <label className="label" htmlFor="confirmPassword">Confirm Password</label>
            <div className="control">
              <input
                className="input"
                id="confirmPassword"
                type="password"
                placeholder="Confirm Password"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
              />
            </div>
          </div>

          <div className="field">
            <div className="control">
              <button
                className="button is-primary is-fullwidth"
                style={{ height: '40px' }}
                onClick={handleSignup}
              >
                Sign Up
              </button>
            </div>
          </div>

          <div className="has-text-centered">
            <p>Already Registered? <a href="/">Login</a></p>
          </div>
        </div>
      </div>
    </div>
  );
}

export default SignupPage;
