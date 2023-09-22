import axios from "axios";
import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";

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
    isAd: false,
    tags: []
  });
  //console.log(userId);
  const handleChange = e => {
    const { name, value } = e.target;

    if (name === "tags") {
      const selectedTags = Array.from(
        e.target.selectedOptions,
        option => option.value
      );

      setFormData({
        ...formData,
        [name]: selectedTags
      });
    } else {
      setFormData({
        ...formData,
        [name]: value
      });
    }
  };

  const handleSubmit = async e => {
    e.preventDefault();
    try {
      const dataToSend = {
        ...formData
      };

      // Create a comma-separated string of selected tag IDs
      const tagsParam = formData.tags.join(",");

      const response = await axios.post(
        `/api/v1/news?tags=${tagsParam}`,
        dataToSend,
        {
          withCredentials: true,
          headers: {
            Authorization: "Basic " + btoa("admin:admin")
          }
        }
      );
      navigate("/"); // Assuming this is the home page
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
              <label htmlFor="tags" className="form-label">
                Tags
              </label>
              <div>
                <label>
                  <input
                    type="checkbox"
                    name="tags"
                    value="1"
                    checked={formData.tags.includes("1")} // Check if "1" is in formData.tags
                    onChange={handleChange}
                  />
                  Sports
                </label>
                <br />
                <label>
                  <input
                    type="checkbox"
                    name="tags"
                    value="2"
                    checked={formData.tags.includes("2")} // Check if "2" is in formData.tags
                    onChange={handleChange}
                  />
                  Crime
                </label>
                <br />
                <label>
                  <input
                    type="checkbox"
                    name="tags"
                    value="3"
                    checked={formData.tags.includes("3")} // Check if "3" is in formData.tags
                    onChange={handleChange}
                  />
                  Fashion
                </label>
                <br />
                <label>
                  <input
                    type="checkbox"
                    name="tags"
                    value="4"
                    checked={formData.tags.includes("4")} // Check if "4" is in formData.tags
                    onChange={handleChange}
                  />
                  Politics
                </label>
              </div>
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
              Request Approval
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default AddAccountHolder;
