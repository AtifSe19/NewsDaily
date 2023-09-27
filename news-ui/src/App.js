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

			<Navbar user = { user } />

			{/* <Routes>
				<Route path="/login" element={<LoginWrapper username={username} />} />
				{user === null ? (
					<Route path="/" element={<LoginWrapper username={username} />} />
				) : (
					<>
						{role === "ADMIN" || role === "EDITOR" ? (
							<Route path="/" element={<AdminAndEditorPanel role={role} target={targetUser} />} />
						) : role === "REPORTER" ? (
							<Route path="/" element={<ReporterPanel role={role} target={targetUser} />} />
						) : role === "USER" ? (
							<Route path="/" element={<UserPanel role={role} />} />
						) : null}
					</>
				)}
			</Routes> */}





			{/* <Routes>
				<Route path="/login" element={<LoginWrapper username={username} />} />
			</Routes> */}

			{user === null ? (
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
			)}

			{/* if I have authenticated user then go to module else go to login page */}
			{/* {user === null ? <LoginWrapper username={username} /> : <AdminAndEditorPanel role={role} target={targetUser} />} */}

			{/* if(user === null)
			{
				<LoginWrapper username={username} />
			}
			else
			{
				if (role === "admin" || role === "editor") {
					<AdminAndEditorPanel role={role} target={targetUser} />
				} else if (role === "reporter") {
					<ReporterPanel role={role} target={targetUser} />
				} else if (role === "USER") {
					<UserPanel />
				}
			} */}


			{/* {(role === 'ADMIN' || role === 'EDITOR') && <AdminAndEditorPanel role={role} target={targetUser} />}
				{role === 'REPORTER' && <ReporterPanel role={role} />}
				{role === 'USER' && <UserPanel />} */}

		</Router>
	);
}

export default App;

