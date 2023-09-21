// import React, { useState } from 'react';
// import axios from 'axios';
// import { toast } from 'react-toastify';
// import { Link } from 'react-router-dom';

// import './SideMenu.css';

// const SideMenu = () => {

//     const [sectionType, setSectionType] = useState('news');
//     const handleChange = async (e) => {
//         e.preventDefault();

//         const apiUrl =
//             sectionType === 'news'
//                 ? '/api/v1/news/pending'
//                 : '/api/v1/comments/pending';

//         try {
//             const response = await axios.get(apiUrl);

//             if (response.status === 200) {
//                 toast.success(`Successfully get`);
//             } else {
//                 toast.error(`Failed to get`);
//             }
//         } catch (error) {
//             toast.error(`Failed to get`)
//         }
//     };
//     return (
//         <div>
//             <nav className="navbar navbar-expand-lg navbar-dark sideMenu">
//                 <div className="container-fluid">
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
//                             <div className="form-group my-2">
//                                 <select
//                                     id="sectionType"
//                                     className="form-control"
//                                     value={sectionType}
//                                     onChange={(e) => setSectionType(e.target.value)}
//                                 >
//                                     <option value="news" onClick={<Link to = "/searchUser">News</Link>}>News</option>
//                                     <option value="comment"><Link to = "/searchUser">Comment</Link></option>
//                                 </select>
//                             </div>
//                         </ul>





//                     </div>
//                 </div>
//             </nav>
//         </div>
//     );
// };

// export default SideMenu;

import React, { useState } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';
import { Link } from 'react-router-dom';

import { useNavigate } from 'react-router-dom';

import './SideMenu.css';

const SideMenu = () => {
    const [sectionType, setSectionType] = useState('news');
    const navigate = useNavigate();

    const handleChange = async (type) => {
        // e.preventDefault();
        console.log(type)

        setSectionType(type);

        // const apiUrl =
        //     sectionType === 'news'
        //         ? '/api/v1/news/pending'
        //         : '/api/v1/comments/pending';

        // try {
        //     const response = await axios.get(apiUrl);

        //     if (response.status === 200) {
        // toast.success(`Successfully get`);
        // Navigate to the appropriate page based on selection
        if (sectionType === 'news') {
            navigate('/searchUser'); // Update the route path as needed
        } else if (sectionType === 'comment') {
            navigate('/searchUser'); // Update the route path as needed
        }
        //     } else {
        //         toast.error(`Failed to get`);
        //     }
        // } catch (error) {
        //     toast.error(`Failed to get`);
    }


    return (
        <div>
            <nav className="navbar navbar-expand-lg navbar-dark sideMenu">
                <div className="container-fluid">
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
                            <div className="form-group my-2">
                                <select
                                    id="sectionType"
                                    className="form-control"
                                    value={sectionType}
                                    onChange={(e) => handleChange(e.target.value)}
                                >
                                    <option value="select" selected>Select Section Type</option>
                                    <option value="news">News</option>
                                    <option value="comment">Comment</option>
                                </select>
                            </div>
                        </ul>
                    </div>
                </div>
            </nav>
        </div>
    );
};

export default SideMenu;

