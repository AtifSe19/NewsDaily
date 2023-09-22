import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

import './SideMenu.css';

const SideMenu = (user) => {
    const [sectionType, setSectionType] = useState('select');
    const navigate = useNavigate();

    const handleChange = (type) => {
        setSectionType(type);
        navigate(`/newscom/${type}`);
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
                            {user && user.role === 'EDITOR' && (
                                <div className="form-group my-2">
                                    <select
                                        id="sectionType"
                                        className="form-control"
                                        value={sectionType}
                                        onChange={(e) => handleChange(e.target.value)}
                                    >
                                        <option value="select">Select Section Type</option>
                                        <option value="news">News</option>
                                        <option value="comments">Comment</option>
                                    </select>
                                </div>
                            )}
                        </ul>
                    </div>
                </div>
            </nav>
        </div>
    );
};

export default SideMenu;


