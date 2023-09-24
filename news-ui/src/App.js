import './lib/css/bootstrap.min.css';
// import './lib/js/bootstrap.min.js';

import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';

import Navbar from './components/navbar/Navbar';
import UserDashboard from './pages/user/userDashboard/UserDashboard'
import './App.css';

function App() {
  return (
    <Router>
      <Navbar />
      <UserDashboard />
    </Router>
  );
}

export default App;