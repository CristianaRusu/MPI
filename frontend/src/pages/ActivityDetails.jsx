import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import './ActivityList.css';

const ActivityDetails = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [activity, setActivity] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetch(`http://localhost:8080/api/activities/get/activity/by/${id}`)
            .then(response => {
                if (!response.ok) throw new Error("Nu am putut încărca detaliile");
                return response.json();
            })
            .then(data => {
                setActivity(data);
                setLoading(false);
            })
            .catch(error => {
                console.error("Eroare la fetch:", error);
                setLoading(false);
            });
    }, [id]);

    const calculateDuration = (startTime, endTime) => {
        if (!startTime || !endTime) return 0;
        const start = new Date(startTime);
        const end = new Date(endTime);
        const diffMs = end - start; // diferența în milisecunde
        return Math.round(diffMs / 60000); // transformăm în minute
    };

    const calculatePace = (startTime, endTime, distanceKm) => {
        if (!startTime || !endTime || !distanceKm || distanceKm <= 0) return "N/A";
        const start = new Date(startTime);
        const end = new Date(endTime);
        const diffMinutes = (end - start) / 60000;
        return (diffMinutes / distanceKm).toFixed(2);
    };

    if (loading) return <div style={{ color: 'white', textAlign: 'center', marginTop: '50px' }}>Se încarcă detaliile...</div>;

    if (!activity) return (
        <div style={{ color: 'white', textAlign: 'center', marginTop: '50px' }}>
            <h2>Nu am găsit această alergare.</h2>
            <button onClick={() => navigate('/activities')} className="back-home-btn">← Înapoi la listă</button>
        </div>
    );

    return (
        <div className="activity-list-container" style={{ maxWidth: '600px', margin: '0 auto' }}>
            <header className="list-header">
                <button className="back-home-btn" onClick={() => navigate('/activities')}>← Înapoi</button>
                <h2>Detalii Alergare</h2>
            </header>

            <div className="activity-card" style={{ marginTop: '30px', cursor: 'default' }}>
                <div className="card-header" style={{ justifyContent: 'center' }}>
                    <span className="activity-date" style={{ fontSize: '1.2rem' }}>
                        📅 {new Date(activity.startTime).toLocaleDateString()}
                    </span>
                </div>

                <div className="card-body" style={{ flexDirection: 'column', gap: '20px', alignItems: 'center', marginTop: '20px' }}>
                    <div className="stat" style={{ textAlign: 'center' }}>
                        <label style={{ fontSize: '1rem', color: '#aaa' }}>Distanță Totală</label>
                        <p style={{ fontSize: '2.5rem', margin: '10px 0', color: '#ccff00' }}>{activity.distanceKm} km</p>
                    </div>

                    {}
                    <div className="stat" style={{ textAlign: 'center' }}>
                        <label style={{ fontSize: '1rem', color: '#aaa' }}>Durată Activitate</label>
                        <p style={{ fontSize: '2.5rem', margin: '10px 0', color: '#fff' }}>
                            {calculateDuration(activity.startTime, activity.endTime)} <span style={{ fontSize: '1.2rem' }}>min</span>
                        </p>
                    </div>

                    <div className="stat" style={{ textAlign: 'center' }}>
                        <label style={{ fontSize: '1rem', color: '#aaa' }}>Pace Mediu</label>
                        <p style={{ fontSize: '2rem', margin: '10px 0' }}>
                            {calculatePace(activity.startTime, activity.endTime, activity.distanceKm)} <span style={{ fontSize: '1rem' }}>min/km</span>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ActivityDetails;