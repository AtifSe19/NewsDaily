// import axios from "axios";
// import React, { useState, useEffect } from "react";
// import { useNavigate, useParams } from "react-router-dom";

// const AddNews = () => {
//   let navigate = useNavigate();
//   const { userId } = useParams(); 
//   const [formData, setFormData] = useState({
//     user: userId,
//     title: "",
//     content: "",
//     isApproved: false,
//     isDisabled: false,
//     isAd: false,
//     tags: [] 
//   });

 
//   const [topNewsTags, setTopNewsTags] = useState([]);

//   const fetchTopNewsTags = async () => {
//     try {
//       const response = await axios.get("/api/v1/tags", {
//         withCredentials: true,
//         headers: {
//           Authorization: "Basic " + btoa("admin:admin")
//         }
//       });
//       if (Array.isArray(response.data)) {
//         setTopNewsTags(response.data);
//       } else {
//         console.error("Invalid data format received from the server.");
//       }
//     } catch (error) {
//       console.error(error);
//     }
//   };

//   useEffect(() => {
//     fetchTopNewsTags(); 
//   }, []);

//   const handleChange = (e) => {
//     const { name, value, checked } = e.target;

//     if (name === "tags") {
//       const selectedTags = [...formData.tags]; 

//       if (checked) {
        
//         selectedTags.push(value);
//       } else {
       
//         const index = selectedTags.indexOf(value);
//         if (index !== -1) {
//           selectedTags.splice(index, 1);
//         }
//       }

//       setFormData({
//         ...formData,
//         [name]: selectedTags
//       });
//     } else {
//       setFormData({
//         ...formData,
//         [name]: value
//       });
//     }
//   };

//   const handleSubmit = async (e) => {
//     e.preventDefault();
//     try {
//       const dataToSend = {
//         ...formData
//       };

//       const tagsParam = formData.tags.join(",");
//       console.log(tagsParam);
//       const response = await axios.post(
//         `/api/v1/news?tags=${tagsParam}`,
//         dataToSend,
//         {
//           withCredentials: true,
//           headers: {
//             Authorization: "Basic " + btoa("admin:admin")
//           }
//         }
//       );
//       navigate("/"); 
//     } catch (error) {
//       // Handle the error
//       console.error(error);
//     }
//   };

//   return (
//     <div className="container mt-5 my-5 accHoldContainer">
//       <div className="row">
//         <div className="col-md-12 text-center">
//           <h1>Report a News</h1>
//           <div
//             style={{
//               background: "black",
//               height: "1px",
//               width: "21%",
//               margin: "auto",
//               marginBottom: "8px"
//             }}
//           ></div>
//           <p>Add News Details</p>
//         </div>
//       </div>
//       <div className="row">
//         <div className="col-md-7 mx-auto">
//           <form onSubmit={handleSubmit}>
//             <div className="mb-3">
//               <label htmlFor="title" className="form-label">
//                 Title
//               </label>
//               <input
//                 type="text"
//                 className="form-control"
//                 id="title"
//                 name="title"
//                 value={formData.title}
//                 onChange={handleChange}
//                 required
//               />
//             </div>

//             <div className="mb-3">
//               <label htmlFor="description" className="form-label">
//                 Description
//               </label>
//               <textarea
//                 className="form-control"
//                 id="content"
//                 name="content"
//                 value={formData.content}
//                 onChange={handleChange}
//                 required
//               />
//             </div>
//             <div className="mb-3">
//               <label className="form-label">Tags</label>
//               <div>
//                 {topNewsTags.map((tag) => (
//                   <label key={tag.id}>
//                     <input
//                       type="checkbox"
//                       name="tags"
//                       value={tag.id}
//                       checked={formData.tags.includes(tag.id)}
//                       onChange={handleChange}
//                     />
//                     {tag.name}
//                   </label>
//                 ))}
//               </div>
//             </div>
//             <button type="submit" className="btn btn-primary">
//               Request Approval
//             </button>
//           </form>
//         </div>
//       </div>
//     </div>
//   );
// };

// export default AddNews;

import axios from "axios";
import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";

const AddNews = () => {
  let navigate = useNavigate();
  const { userId } = useParams(); // the reporter id
  const [formData, setFormData] = useState({
    user: userId,
    title: "",
    content: "",
    isApproved: false,
    isDisabled: false,
    isAd: false,
    selectedTags: [] 
  });

  const [allTags, setAllTags] = useState([]); 

  
  const fetchAllTags = async () => {
    try {
      const response = await axios.get("/api/v1/tags", {
       
        withCredentials: true,
        headers: {
          Authorization: "Basic " + btoa("admin:admin")
        }
      });
      
      if (Array.isArray(response.data)) {
        setAllTags(response.data);
        
      } else {
        console.error("Invalid data format received from the server.");
        console.log(response.data)
      }
    } catch (error) {
      // Handle the error
      console.error(error);
    }
  };

  useEffect(() => {
    fetchAllTags();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;

    if (name === "tags") {
      const selectedTags = [...formData.selectedTags]; 

      if (selectedTags.includes(value)) {
        
        const index = selectedTags.indexOf(value);
        if (index !== -1) {
          selectedTags.splice(index, 1);
        }
      } else {
        
        selectedTags.push(value);
      }

      setFormData({
        ...formData,
        selectedTags
      });
    } else {
      setFormData({
        ...formData,
        [name]: value
      });
    }
  };

const handleSubmit = async (e) => {
  e.preventDefault();
  try {
    const tagsParam = formData.selectedTags.join(","); // Convert selected tags to a comma-separated string
    const response = await axios.post(
      `/api/v1/news?tags=${tagsParam}`, // Send tags as a query parameter
      {
        ...formData,
      },
      {
        withCredentials: true,
        headers: {
          Authorization: "Basic " + btoa("admin:admin")
        }
      }
    );
    navigate("/");
  } catch (error) {
    console.error(error);
  }
};

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
                id="content"
                name="content"
                value={formData.content}
                onChange={handleChange}
                required
              />
            </div>
            <div className="mb-3">
              <label className="form-label">Tags</label>
              <div>
                {allTags.map((tag) => (
                  <label key={tag.id}>
                    <input
                      type="checkbox"
                      name="tags"
                      value={tag.id.toString()}
                      checked={formData.selectedTags.includes(tag.id.toString())}
                      onChange={handleChange}
                    />
                    {tag.name}
                  </label>
                ))}
              </div>
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

export default AddNews;
