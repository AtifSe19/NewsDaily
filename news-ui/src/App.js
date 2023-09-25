import './lib/css/bootstrap.min.css';
// import './lib/js/bootstrap.min.js';

import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';

import Navbar from './components/navbar/Navbar';
import './App.css';
import AdminAndEditorPanel from './pages/adminAndEditor/panel/AdminAndEditorPanel';

function App() {
  return (
    <Router>
      <Navbar />
      <AdminAndEditorPanel />
    </Router>
  );
}

export default App;