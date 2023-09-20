import React, { useState } from 'react';
import axios from 'axios';
import {toast} from 'react-toastify'
import './UpdateUser.css';

const UpdateUser = (user) => {
  const [username, setUsername] = useState('');
  const [User, setUser] = useState({
    username: '',
    password: '',
    email: '',
  });

  const handleChange = (e) => {
    setUsername(e.target.value);
  };

  const handleSearch = async () => {
    try {
      // Send a request to search for the account holder by username
      const response = await axios.get(`/api/v1/users/search/${username}`);

      if (response.status === 200) {
        setUser(response.data.content);
        toast.success(`${user.target}: @_${username} information retrieved successfully!`)
      } else {
        toast.error(`${user.target}: @_${username} does not exist!`);
        setUser({
          username: '',
          password: '',
          email: '',
        });
      }
    } catch (error) {
      toast.error(`${user.target}: @_${username} does not exist!`);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.put(`/api/v1/users/${username}`, User);
      if (response.status === 200) {
        toast.success(`${user.target}: @_${username} updated successfully!`)
      } else {
        toast.error('Something went wrong');
      }
    } catch (error) {
      toast.error("Something went wrong")
    }
  };

  return (
    <div className='container mt-5 my-5 updAccHoldContainer'>
      <div className="row">
        <div className="col-md-12 text-center">
          <h1>Update {user.target}</h1>
        </div>
      </div>
      <div className="row">
        <div className="col-md-7 mx-auto">
          <div className="mb-3">
            <label htmlFor="searchUsername" className="form-label">Search by Username</label>
            <input
              type="text"
              className="form-control"
              id="searchUsername"
              name="searchUsername"
              value={username}
              onChange={handleChange}
            />
            <button className="btn btn-primary mt-2" onClick={handleSearch}>Search</button>
          </div>
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label htmlFor="username" className="form-label">Username</label>
              <input
                type="text"
                className="form-control"
                id="username"
                name="username"
                value={User.username}
                onChange={(e) => setUser({ ...User, username: e.target.value })}
                required
              />
            </div>
            <div className="mb-3">
              <label htmlFor="email" className="form-label">Email address</label>
              <input
                type="email"
                className="form-control"
                id="email"
                name="email"
                value={User.email}
                onChange={(e) => setUser({ ...User, email: e.target.value })}
                required
              />
            </div>
            <div className="mb-3">
              <label htmlFor="password" className="form-label">Username</label>
              <input
                type="password"
                className="form-control"
                id="password"
                name="password"
                value={User.password}
                onChange={(e) => setUser({ ...User, password: e.target.value })}
                required
              />
            </div>
            <button type="submit" className="btn btn-primary btns">Update</button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default UpdateUser;