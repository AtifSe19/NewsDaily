import './lib/css/bootstrap.min.css';

import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';

import Navbar from './components/navbar/Navbar';
import User from './pages/user/userDashboard/User'
import './App.css';

function App() {
  return (
    <Router>
      <Navbar />
      {/* <UserDashboard /> */}
      <User/>
    </Router>
  );
}

export default App;