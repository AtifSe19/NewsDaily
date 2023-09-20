import React, { useState } from 'react';
import axios from 'axios';
import './DeleteUser.css';
import {toast} from 'react-toastify';

const DeleteUser = (user) => {
  const [username, setUsername] = useState('');

  const handleChange = (e) => {
    setUsername(e.target.value);
  };

  const handleDelete = async () => {
    try {
      const response = await axios.delete(`/api/v1/users/${username}`);

      if (response.status === 200) {
        toast.success(`${user.target} with @_${username} deleted successfully`);
      } else {
        toast.error('Something went wrong');
      }
    } catch (error) {
      toast.error(`${user.target} with username @_${username} does not exist!`);
    }
  };

  return (
    <div className='container mt-5 my-5 delAccHoldContainer'>
      <div className="row">
        <div className="col-md-12 text-center">
          <h1>Delete {user.target}</h1>
        </div>
      </div>
      <div className="row">
        <div className="col-md-7 mx-auto">

          <form onSubmit={handleDelete}>
            <div className="mb-3">
              <label htmlFor="username" className="form-label">{user.target}'s Username</label>
              <input type="text" className="form-control" id="username" value={username}
                onChange={handleChange} />
            </div>
            <button id='delBtn' type="submit" className="btn btn-danger">Delete</button>
          </form>

        </div>
      </div>
    </div>
  )
}

export default DeleteUser