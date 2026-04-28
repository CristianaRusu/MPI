import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './ActivityList.css';

const ActivityList = () => {
    const [activities, setActivities] = useState([]);
    const [searchTerm, setSearchTerm] = useState("");
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        fetch('http://localhost:8080/api/activities/get/all/activities')
            .then(response => {
                if (!response.ok) throw new Error("Eroare la server");
                return response.json();
            })
            .then(data => {
                setActivities(data);
                setLoading(false);
            })
            .catch(error => {
                console.error('Error fetching activities:', error);
                setLoading(false);
            });
    }, []);

    const calculatePace = (startTime, endTime, distanceKm) => {
        if (!startTime || !endTime || !distanceKm || distanceKm <= 0) return "N/A";
        const start = new Date(startTime);
        const end = new Date(endTime);
        const diffMinutes = (end - start) / 60000;
        return (diffMinutes / distanceKm).toFixed(2);
    };

    const filteredActivities = activities.filter(activity => {
        const dateStr = new Date(activity.startTime).toLocaleDateString().toLowerCase();
        const distanceStr = activity.distanceKm.toString().toLowerCase();
        const paceVal = calculatePace(activity.startTime, activity.endTime, activity.distanceKm).toLowerCase();
        const term = searchTerm.toLowerCase();

        return dateStr.includes(term) || distanceStr.includes(term) || paceVal.includes(term);
    });

    if (loading) return <div className="loading">Se încarcă activitățile...</div>;

    return (
        <div className="activity-list-container">
            <header className="list-header">
                <button className="back-home-btn" onClick={() => navigate('/home')}>← Înapoi</button>
                <h2>Istoric Alergări</h2>

                <input
                    type="text"
                    className="filter-input"
                    placeholder="Caută după dată, km sau pace..."
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                />
            </header>
            <div className="activity-grid">
                {filteredActivities.length > 0 ? (
                    filteredActivities.map((activity) => (
                        <div
                            key={activity.id}
                            className="activity-card"
                            onClick={() => navigate(`/activity/${activity.id}`)}
                        >
                            <div className="card-header">
                                <span className="activity-date">
                                    {new Date(activity.startTime).toLocaleDateString()}
                                </span>
                                <span className="activity-type">🏃 Run</span>
                            </div>
                            <div className="card-body">
                                <div className="stat">
                                    <label>Distanță</label>
                                    <p>{activity.distanceKm} km</p>
                                </div>
                                <div className="stat">
                                    <label>Pace</label>
                                    <p>{calculatePace(activity.startTime, activity.endTime, activity.distanceKm)} min/km</p>
                                </div>
                            </div>
                            <button className="view-btn">Vezi detalii</button>
                        </div>
                    ))
                ) : (
                    <div className="no-data">
                        <p>Nu am găsit nicio activitate.</p>
                        <button className="btn-neon green" onClick={() => navigate('/home')}>
                            Adaugă prima alergare
                        </button>
                    </div>
                )}
            </div>
        </div>
    );
};

export default ActivityList;