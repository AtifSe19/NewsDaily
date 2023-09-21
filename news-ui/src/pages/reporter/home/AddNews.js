import { useNavigate } from "react-router-dom";
import React, { useState } from "react";
import axios from "axios";

const AddAccountHolder = () => {
  let navigate = useNavigate();
  const { userId } = useParams(); //the reporter id 
  const [formData, setFormData] = useState({
    user: userId,
    title: "",
    description: "",
    postedAt: new Date().toISOString().slice(0, 16),
    isApproved: false,
    isDisabled: false,
    isAd: false
  });

  const handleChange = e => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

  const handleSubmit = async e => {
    e.preventDefault();
    try {
      const dataToSend = {
        ...formData
      };

      const response = await axios.post("/api/v1/news", dataToSend, {
        withCredentials: true,
        headers: {
          Authorization: "Basic " + btoa("admin:admin")
        }
      });
      navigate("/"); // assuming this is the home page
    } catch (error) {
      // Handle the error
    }
  };
  useEffect(() => {
    setFormData({
      ...formData,
      postedAt: new Date().toISOString().slice(0, 16)
    });
  }, []);

  return (
    <div className="container mt-5 my-5 accHoldContainer">
      <div className="row">
        <div className="col-md-12 text-center">
          <h1>Report a News</h1>
          <div
            style={{
              background: "black",
              height: "1px",
              width: "21%",
              margin: "auto",
              marginBottom: "8px"
            }}
          ></div>
          <p>Add News Details</p>
        </div>
      </div>
      <div className="row">
        <div className="col-md-7 mx-auto">
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label htmlFor="title" className="form-label">
                Title
              </label>
              <input
                type="text"
                className="form-control"
                id="title"
                name="title"
                value={formData.title}
                onChange={handleChange}
                required
              />
            </div>

            <div className="mb-3">
              <label htmlFor="description" className="form-label">
                Description
              </label>
              <textarea
                className="form-control"
                id="description"
                name="description"
                value={formData.description}
                onChange={handleChange}
                required
              />
            </div>

            <div className="mb-3">
              <label htmlFor="postedAt" className="form-label">
                Posted At
              </label>
              <input
                type="datetime-local"
                className="form-control"
                id="postedAt"
                name="postedAt"
                value={formData.postedAt}
                onChange={handleChange}
                required
              />
            </div>
            <button type="submit" className="btn btn-primary">
              Add Reporter
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default AddAccountHolder;
