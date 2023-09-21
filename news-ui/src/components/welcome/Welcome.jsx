
import React, { useEffect, useState } from 'react';
import axios from 'axios';

import { toast } from 'react-toastify';

const Welcome = (user) => {
  const [username, setUsername] = useState(null);

  const handleWelcome = () => {
    toast.success(`Welcome, @_${username}! to the ${user.role} dashboard`, {
      position: toast.POSITION.TOP_CENTER,
      });
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const usernameResponse = await axios.get(`/api/v1/users/getUsername`);

        if (usernameResponse.status === 200) {
          const username = usernameResponse.data; 
          setUsername(username);
        } else {
          console.error('Failed to fetch username');
        }
      } catch (error) {
        console.error('Error:', error);
      }
    };

    fetchData();
  }, []);
  return (
    <div className="container mt-5">
      <div className="row">
        <div className="col-md-6 offset-md-3 text-center">
          <h1>Welcome, @_{username}!</h1>
          <p>This is the {user.role} dashboard of the NewsDaily Web Application.</p>
          <button className="btn btn-primary" onClick={handleWelcome} style={{border: "1px solid #008080"}}>Welcome!</button>
        </div>
      </div>
    </div>
  );
};

export default Welcome;
