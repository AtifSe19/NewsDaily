import React, { useState, useEffect } from 'react'
import { Routes, Route } from 'react-router-dom'
import axios from 'axios'

import './UserDashboard.css'

import SideMenu from '../../../components/sideMenu/SideMenu'
import Welcome from '../../../components/welcome/Welcome'
import AddUser from '../addUser/AddUser'
import EditUser from '../editUser/EditUser'
import SearchUser from '../searchUser/SearchUser'

const UserDashboard = () => {

  const [role, setRole] = useState(null);
  const [targetUser, setTargetUser] = useState(null);
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
    <div className='userDashBoard'>
      <SideMenu />
      <Routes>
        <Route path="/" element={<Welcome role={role} target={targetUser} />} />
        <Route path="/addUser" element={<AddUser role={role} target={targetUser} />} />
        <Route path="/editUser/:userId" element={<EditUser role={role} target={targetUser} />} />
        <Route path="/searchUser" element={<SearchUser target = {targetUser} />} />
      </Routes>
    </div>
  )
}

export default UserDashboard