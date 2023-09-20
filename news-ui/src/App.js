import React, { useState, useEffect } from 'react';
import './lib/css/bootstrap.min.css';
import Navbar from './components/navbar/Navbar';
import UserHomePage from './pages/user/home/Home';
import AddUser from './pages/user/addUser/AddUser';
import DeleteUser from './pages/user/deleteUser/DeleteUser';
import UpdateUser from './pages/user/updateUser/UpdateUser';
// import SearchUser from './pages/user/searchUser/SearchUser';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import axios from 'axios';
import './App.css';

function App() {
  const [role, setRole] = useState(null);
  const [targetUser, setTargetUser] = useState(null);
  useEffect(() => {

    const fetchUserRoles = async () => {
      try {
        const response = await axios.get('/api/v1/users/getRole');

        if (response.status === 200) {
          if(response.data.toUpperCase() === 'ADMIN') {
            setTargetUser('EDITOR');
          }
          else if(response.data.toUpperCase() === 'EDITOR') {
            setTargetUser('REPORTER');
          }
          setRole(response.data);
        } else {
          console.error('Failed to fetch user role');
        }
      } catch (error) {
        console.error('Error:', error);
      }
    };

    fetchUserRoles();
  }, []);

  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/" element={<UserHomePage role={role} target = {targetUser}/>} />
        <Route path="/addUser" element={<AddUser role={role} target = {targetUser} />} />
        <Route path="/deleteUser" element={<DeleteUser role={role} target = {targetUser} />} />
        <Route path="/updateUser" element={<UpdateUser role={role} target = {targetUser} />} />
        {/* <Route exact path="/searchUser" element={<SearchUser user={user} />} /> */}
      </Routes>
    </Router>
  );
}

export default App;