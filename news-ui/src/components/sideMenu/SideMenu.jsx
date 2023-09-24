// import React, { useState } from "react";
// import { Link, useNavigate } from "react-router-dom";
// import "./SideMenu.css";

// const SideMenu = (user) => {
//     const [sectionType, setSectionType] = useState("select");
//     const navigate = useNavigate();

//     const handleChange = (type) => {
//         setSectionType(type);
//         navigate(`/newscom/${type}`);
//     };

//     return (
//         <div className="side-menu-container">
//             <nav className="navbar navbar-expand-lg navbar-light sideMenu">
//                 <div className="container-fluid">
//                     <button
//                         className="navbar-toggler"
//                         type="button"
//                         data-bs-toggle="collapse"
//                         data-bs-target="#navbarSupportedContent"
//                     >
//                         <span className="navbar-toggler-icon"></span>
//                     </button>
//                     <div className="collapse navbar-collapse" id="navbarSupportedContent">
//                         <ul className="navbar-nav flex-column">
//                             <li className="nav-item">
//                                 <Link className="nav-link active" to="/">
//                                     Home
//                                 </Link>
//                             </li>
//                             <li className="nav-item">
//                                 <Link className="nav-link" to="/addUser">
//                                     Add
//                                 </Link>
//                             </li>
//                             <li className="nav-item">
//                                 <Link className="nav-link" to="/searchUser">
//                                     Search
//                                 </Link>
//                             </li>

//                             {user && user.role === "EDITOR" && (
//                                 <>
//                                     <li className="nav-item">
//                                         <div className="form-group my-2">
//                                             <select
//                                                 id="sectionType"
//                                                 className="form-select"
//                                                 value={sectionType}
//                                                 onChange={(e) => handleChange(e.target.value)}
//                                             >
//                                                 <option value="select">Approve / Disapprove</option>
//                                                 <option value="news">News</option>
//                                                 <option value="comments">Comment</option>
//                                             </select>
//                                         </div>
//                                     </li>
//                                     <li className="nav-item">
//                                         <div className="form-group my-2">
//                                             <select
//                                                 id="newsOrComment"
//                                                 className="form-select"
//                                                 onChange={(e) => {
//                                                     const selectedOption = e.target.value;
//                                                     if (selectedOption === "news") {
//                                                         navigate("/news");
//                                                     } else if (selectedOption === "comments") {
//                                                         navigate("/comments");
//                                                     }
//                                                 }}
//                                             >
//                                                 <option value="">Enable / Disable</option>
//                                                 <option value="news">News</option>
//                                                 <option value="comments">Comment</option>
//                                             </select>
//                                         </div>
//                                     </li>
//                                 </>
//                             )}

//                         </ul>
//                     </div>
//                 </div>
//             </nav>
//         </div>
//     );
// };

// export default SideMenu;
import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './SideMenu.css';

const SideMenu = (user) => {
  const [sectionType, setSectionType] = useState('select');
  const navigate = useNavigate();

  const handleChange = (type) => {
    setSectionType(type);
    navigate(`/newscom/pending/${type}`);
  };

  return (
    <div className="side-menu-container">
      <nav className="navbar navbar-expand-lg navbar-light sideMenu">
        <div className="container-fluid">
          <button
            className="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarSupportedContent"
          >
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarSupportedContent">
            <ul className="navbar-nav flex-column">
              <li className="nav-item">
                <Link className="nav-link active" to="/">
                  Home
                </Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/addUser">
                  Add
                </Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/searchUser">
                  Search
                </Link>
              </li>

              {user && user.role === 'EDITOR' && (
                <>
                  <li className="nav-item">
                    <div className="form-group my-2">
                      <select
                        id="sectionType"
                        className="form-select"
                        value={sectionType}
                        onChange={(e) => handleChange(e.target.value)}
                      >
                        <option value="select">Approve / Disapprove</option>
                        <option value="news">News</option>
                        <option value="comments">Comment</option>
                      </select>
                    </div>
                  </li>
                  <li className="nav-item">
                    <div className="form-group my-2">
                      <select
                        id="newsOrComment"
                        className="form-select"
                        onChange={(e) => {
                          const selectedOption = e.target.value;
                          if (selectedOption === 'news') {
                            navigate(`/newscom/disable/${selectedOption}`);
                          } else if (selectedOption === 'comments') {
                            navigate(`/newscom/disable/${selectedOption}`);
                          }
                        }}
                      >
                        <option value="">Enable / Disable</option>
                        <option value="news">News</option>
                        <option value="comments">Comment</option>
                      </select>
                    </div>
                  </li>
                </>
              )}
            </ul>
          </div>
        </div>
      </nav>
    </div>
  );
};

export default SideMenu;
