import React, { useEffect, useState } from 'react';
import './Statistics.css';
import { useNavigate } from 'react-router-dom';

const Statistics = () => {
    const navigate = useNavigate();
    const [stats, setStats] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const userString = localStorage.getItem('loggedInUser');
        if (!userString) {
            navigate('/');
            return;
        }

        const userId = JSON.parse(userString).id;

        const fetchStats = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/activities/statistics/${userId}`);
                if (response.ok) {
                    const data = await response.json();
                    setStats(data);
                } else {
                    setError('Nu s-au putut încărca statisticile.');
                }
            } catch (err) {
                setError('Eroare de conexiune cu serverul.');
            } finally {
                setLoading(false);
            }
        };

        fetchStats();
    }, [navigate]);

    const formatTime = (totalSeconds) => {
        if (!totalSeconds) return '0h 0min';
        const hours = Math.floor(totalSeconds / 3600);
        const minutes = Math.floor((totalSeconds % 3600) / 60);
        return `${hours}h ${minutes}min`;
    };

    const formatPace = (pace) => {
        if (!pace) return '0:00 min/km';
        const minutes = Math.floor(pace);
        const seconds = Math.round((pace - minutes) * 60);
        return `${minutes}:${seconds.toString().padStart(2, '0')} min/km`;
    };

    return (
        <div className="statistics-container">
            <div className="statistics-header">
                <button className="btn-back" onClick={() => navigate('/home')}>← Înapoi</button>
                <h1>📊 Statisticile tale</h1>
                <p>Rezumatul activității tale de alergare</p>
            </div>

            {loading && <p className="statistics-loading">Se încarcă...</p>}
            {error && <p className="statistics-error">{error}</p>}

            {stats && !loading && (
                <div className="statistics-grid">
                    <div className="stat-card green">
                        <span className="stat-icon">🏃‍♂️</span>
                        <span className="stat-value">{stats.totalDistance?.toFixed(2) ?? '0'} km</span>
                        <span className="stat-label">Distanță totală</span>
                    </div>

                    <div className="stat-card blue">
                        <span className="stat-icon">📋</span>
                        <span className="stat-value">{stats.totalActivityCount ?? '0'}</span>
                        <span className="stat-label">Număr de alergări</span>
                    </div>

                    <div className="stat-card purple">
                        <span className="stat-icon">⚡</span>
                        <span className="stat-value">{formatPace(stats.mediumPace)}</span>
                        <span className="stat-label">Pace mediu</span>
                    </div>

                    <div className="stat-card orange">
                        <span className="stat-icon">⏱️</span>
                        <span className="stat-value">{formatTime(stats.totalTime)}</span>
                        <span className="stat-label">Timp total</span>
                    </div>
                </div>
            )}

            {stats && stats.totalActivityCount === 0 && !loading && (
                <p className="statistics-empty">Nu ai înregistrat nicio alergare încă. Hai să începem! 🚀</p>
            )}
        </div>
    );
};

export default Statistics;
