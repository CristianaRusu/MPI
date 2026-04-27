import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import './ActivityDetails.css';

const ActivityDetails = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [activity, setActivity] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetch(`http://localhost:8080/api/activities/get/activity/by/${id}`)
            .then(res => {
                if (!res.ok) throw new Error("Activitatea nu a fost găsită");
                return res.json();
            })
            .then(data => {
                setActivity(data);
                setLoading(false);
            })
            .catch(err => {
                console.error("Eroare la preluare:", err);
                setLoading(false);
            });
    }, [id]);

    const calculatePace = () => {
        if (!activity || !activity.startTime || !activity.endTime || !activity.distanceKm || activity.distanceKm <= 0) {
            return "0.00";
        }
        const start = new Date(activity.startTime);
        const end = new Date(activity.endTime);
        const diffMinutes = (end - start) / 60000;
        return (diffMinutes / activity.distanceKm).toFixed(2);
    };

    const handleDelete = async () => {
        if (window.confirm("Ești sigur că vrei să ștergi această sesiune de alergare?")) {
            try {
                const response = await fetch(`http://localhost:8080/api/activities/delete/activity/by/${id}`, {
                    method: 'DELETE',
                });

                if (response.ok) {
                    alert("Activitatea a fost eliminată!");
                    navigate('/home');
                } else {
                    alert("Serverul a refuzat ștergerea.");
                }
            } catch (err) {
                console.error("Eroare la ștergere:", err);
                alert("Nu s-a putut comunica cu serverul.");
            }
        }
    };

    if (loading) return <div className="loading">Se încarcă detaliile...</div>;

    if (!activity) return (
        <div className="error-container">
            <p>Activitatea cu ID-ul {id} nu a fost găsită.</p>
            <button onClick={() => navigate('/home')}>Înapoi la Home</button>
        </div>
    );

    return (
        <div className="activity-details-container">
            <header className="details-header">
                <button className="back-btn" onClick={() => navigate('/activities')}>← Înapoi la listă</button>
                <h2>Detalii Sesiune # {activity.id}</h2>
            </header>

            <div className="details-card">
                <div className="detail-row">
                    <span>📅 Data:</span>
                    <strong>{new Date(activity.startTime).toLocaleDateString()}</strong>
                </div>

                <div className="detail-row">
                    <span>⏱️ Durată:</span>
                    <strong>
                        {Math.round((new Date(activity.endTime) - new Date(activity.startTime)) / 60000)} min
                    </strong>
                </div>

                <div className="detail-row">
                    <span>🏃 Distanță:</span>
                    <strong className="highlight">{activity.distanceKm} km</strong>
                </div>

                <div className="detail-row">
                    <span>⚡ Pace Mediu:</span>
                    <strong className="highlight">
                        {}
                        {activity.pace ? activity.pace.toFixed(2) : calculatePace()} min/km
                    </strong>
                </div>
            </div>

            <div className="actions">
                {}
                <button className="delete-btn" onClick={handleDelete} style={{ width: '100%' }}>
                    Șterge Activitatea
                </button>
            </div>
        </div>
    );
};

export default ActivityDetails;