import React, { useEffect, useState } from "react";
import axios from "axios";

import { toast } from "react-toastify";

const Welcome = user => {
  const [username, setUsername] = useState(null);
  const [text1, setText1] = useState("");
  const [text2, setText2] = useState("");

  const handleWelcome = () => {
    toast.success(`Welcome, @_${username}! to the ${user.role} dashboard`, {
      position: toast.POSITION.TOP_CENTER
    });
  };
  const fullText1 = <h1>Welcome, @_{username}!</h1>;
  const fullText2 = "This is the {user.role} dashboard of the NewsDaily Web Application.";
  const styles1 = {
    fontSize: '36px',
    marginBottom: '20px',
    fontWeight: 'bold', 
    color: '#0074d9', 
    fontFamily: 'Arial, sans-serif' 
  };
  const styles2 = {
    fontSize: '18px', 
    color: 'white', 
    fontFamily: 'Arial, sans-serif' 
  };
  useEffect(() => {
    let currentIndex1 = 0;

    const interval1 = setInterval(() => {
      if (currentIndex1 <= fullText1.length) {
        setText1(fullText1.slice(0, currentIndex1));
        currentIndex1 += 1;
      } else {
        clearInterval(interval1);

        let currentIndex2 = 0;
        const interval2 = setInterval(() => {
          if (currentIndex2 <= fullText2.length) {
            setText2(fullText2.slice(0, currentIndex2));
            currentIndex2 += 1;
          } else {
            clearInterval(interval2);
          }
        }, 50);
      }
    }, 100);

    return () => clearInterval(interval1);
  }, []);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const usernameResponse = await axios.get(`/api/v1/users/getUsername`);

        if (usernameResponse.status === 200) {
          const username = usernameResponse.data;
          setUsername(username);
        } else {
          console.error("Failed to fetch username");
        }
      } catch (error) {
        console.error("Error:", error);
      }
    };

    fetchData();
  }, []);
  return (
    <div className="container mt-5">
      <div className="row">
        <div className="col-md-6 offset-md-3 text-center">
          <h1 style={styles1}>{text1}</h1>
          <p style={styles2}>{text2}</p>
          <button
            className="btn btn-primary"
            onClick={handleWelcome}
            style={{ border: "1px solid #008080" }}
          >
            Welcome!
          </button>
        </div>
      </div>
    </div>
  );
};

export default Welcome;
