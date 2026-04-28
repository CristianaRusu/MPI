import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './AccountPage.css';

const AccountPage = () => {
    const navigate = useNavigate();
    const [userData, setUserData] = useState(null);
    const [stats, setStats] = useState({ totalRuns: 0, totalKm: 0 });

    useEffect(() => {
        const savedUser = JSON.parse(localStorage.getItem('loggedInUser'));
        if (savedUser) {
            setUserData(savedUser);
        } else {
            navigate('/');
        }

        fetch('http://localhost:8080/api/activities/get/all/activities')
            .then(res => res.json())
            .then(data => {
                const totalKm = data.reduce((sum, act) => sum + act.distanceKm, 0);
                setStats({
                    totalRuns: data.length,
                    totalKm: totalKm.toFixed(2)
                });
            })
            .catch(err => console.error("Eroare stats:", err));
    }, [navigate]);

    const handleLogout = () => {
        localStorage.removeItem('user');
        navigate('/');
    };

    if (!userData) return null;

    return (
        <div className="account-container">
            <header className="account-header">
                <button className="back-btn" onClick={() => navigate('/home')}>← Dashboard</button>
                <h1>Profilul Meu</h1>
            </header>

            <div className="profile-card">
                <div className="profile-avatar">
                    {userData.name ? userData.name.charAt(0).toUpperCase() : 'U'}
                </div>
                <div className="profile-info">
                    <h2>{userData.name || 'Utilizator'}</h2>
                    <p className="email">{userData.email}</p>
                    <p className="joined">Sesiune activă</p>
                </div>
            </div>

            <div className="stats-grid">
                <div className="stat-box">
                    <label>Alergări totale</label>
                    <p>{stats.totalRuns}</p>
                </div>
                <div className="stat-box">
                    <label>Kilometri totali</label>
                    <p>{stats.totalKm} km</p>
                </div>
            </div>

            <button className="logout-btn" onClick={handleLogout}>
                Deconectare
            </button>
        </div>
    );
};

export default AccountPage;