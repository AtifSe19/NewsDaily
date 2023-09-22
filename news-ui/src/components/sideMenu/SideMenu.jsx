import React from 'react';
import { Link } from 'react-router-dom';
import './SideMenu.css';

// const SideMenu = () => {
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
//                         </ul>
//                     </div>
//                 </div>
//             </nav>
//         </div>
//     );
// };
const SideMenu = () => {
    return (
        <div>
            <nav className="navbar navbar-expand-lg navbar-dark sideMenu">
                <div className="container-fluid">
                    <div className="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul className="navbar-nav flex-column">
                            <li className="nav-item">
                                <Link className="nav-link active" to="/AddNews">
                                   Add news
                                </Link>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </div>
    );
};
export default SideMenu;
