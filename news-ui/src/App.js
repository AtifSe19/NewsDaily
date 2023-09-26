import './lib/css/bootstrap.min.css';
// import './lib/js/bootstrap.min.js';

import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import axios from 'axios';

import Navbar from './components/navbar/Navbar';
import './App.css';
import AdminAndEditorPanel from './pages/adminAndEditor/panel/AdminAndEditorPanel';
import ReporterPanel from './pages/reporter/panel/ReporterPanel';

function App() {
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
          setRole(response.data.toUpperCase());
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
      {(role === 'ADMIN' || role === 'EDITOR') && <AdminAndEditorPanel role = {role} target = {targetUser}/>}
      {role === 'REPORTER' && <ReporterPanel role = {role}/>}
      {/* {role === 'USER' && <UserPanel />} */}
    </Router>
  );
}

export default App;