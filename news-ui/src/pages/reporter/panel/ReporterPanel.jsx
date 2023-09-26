import React, { useState, useEffect } from 'react'
import { Routes, Route } from 'react-router-dom'
import axios from 'axios'

import SideMenu from '../../../components/sideMenu/SideMenu'
import Welcome from '../../../components/welcome/Welcome'
import UploadNews from '../uploadNews/UploadNews'
import PendingNews from '../pendingNews/PendingNews'

const ReporterPanel = () => {

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
    <div className='panel'>
      <div style={{ width: '15%' }}>
        <SideMenu role={role} target = {targetUser}/>
      </div>
      <div style={{ width: '85%', display: 'flex', justifyContent: 'center'}} className='my-5'>
        <Routes>
          <Route path="/" element={<Welcome role={role} target={targetUser} />} />
          <Route path="/uploadNews" element={<UploadNews />} />
          <Route path="/pendingNews" element={<PendingNews />} />
        </Routes>
      </div>
    </div>
  )
}

export default ReporterPanel