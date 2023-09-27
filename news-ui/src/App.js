import './lib/css/bootstrap.min.css';
// import './lib/js/bootstrap.min.js';

import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import axios from 'axios';

// import { Route } from 'react-router-dom';

import Navbar from './components/navbar/Navbar';
import './App.css';
import AdminAndEditorPanel from './pages/adminAndEditor/panel/AdminAndEditorPanel';
import ReporterPanel from './pages/reporter/panel/ReporterPanel';
import LoginWrapper from './components/login/LoginWrapper';
import Logout from './components/login/Logout';
import UserPanel from './pages/user/panel/UserPanel';

function App() {
	const [role, setRole] = useState(null);
	const [targetUser, setTargetUser] = useState(null);

	const [user, setUser] = useState(null);
	const [username, setUserName] = useState(null);


	useEffect(() => {
		const fetchUserRoles = async () => {
			try {
				const response = await axios.get('/api/v1/users/getRole');

				if (response.status === 200) {
					if (response.data.toUpperCase() === 'ADMIN') {
						setTargetUser('EDITOR');
					}
					else if (response.data.toUpperCase() === 'EDITOR') {
						setTargetUser('REPORTER');
					}
					setRole(response.data.toUpperCase());
				} else {
					console.error('Failed to fetch user role');
				}
			} catch (error) {
				console.error('Error:', error);
			}
		};

		const fetchAuthenticatedUserRoles = async () => {
			try {
				const response = await axios.get('/api/v1/users/getAuthenticatedUser');

				if (response.status === 200) {
					setUser(response.data);

					setUserName(response.data.name);
				} else {
					console.error('Failed to fetch authenticated user');
				}
				// console.log(response);
				// console.log(user);
			} catch (error) {
				console.error('Error:', error);
			}
		};
		fetchUserRoles();
		fetchAuthenticatedUserRoles();
	}, []);

	return (
		<Router>
            <Navbar showLogout={user !== null} /> {/* Show "Logout" when user is logged in */}
            
            <Routes>
				<Route path="/login" element={<LoginWrapper /> } />
                <Route path="/logout" element={<Logout /> } /> {/* Use element prop for the /logout route */}
                <Route path="/" element={user === null ? (
                    <LoginWrapper username={username} />
                ) : (
                    <>
                        {role === "ADMIN" || role === "EDITOR" ? (
                            <AdminAndEditorPanel role={role} target={targetUser} />
                        ) : role === "REPORTER" ? (
                            <ReporterPanel role={role} target={targetUser} />
                        ) : role === "USER" ? (
                            <UserPanel role={role} />
                        ) : null}
                    </>
                )} />
            </Routes>
        </Router>
	);
}

export default App;

