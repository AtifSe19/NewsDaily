import React, { useState, useEffect } from 'react'
import { Routes, Route } from 'react-router-dom'
import axios from 'axios'


import SideMenu from '../../../components/sideMenu/SideMenu'
import AddNews from '../../reporter/home/AddNews'

const User = () => {

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
    <div className='user'>
      <SideMenu />
      <Routes>
        <Route path="/AddNews" element={<AddNews role={role} target={targetUser} />} />
      </Routes>
    </div>
  )
}

export default User
