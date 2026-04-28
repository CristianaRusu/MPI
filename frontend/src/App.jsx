import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './pages/Login';
import Home from './pages/Home';
import Register from './pages/Register';
import ActivityDetails from './pages/ActivityDetails';
import ActivityList from './pages/ActivityList';
import AccountPage from './pages/AccountPage';

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route path="/home" element={<Home />} />
                <Route path="/activity/:id" element={<ActivityDetails />} />
                <Route path="/activities" element={<ActivityList />} />
                <Route path="/account" element={<AccountPage />} />
            </Routes>
        </Router>
    );
}

export default App;