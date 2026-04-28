import React, { useState, useEffect } from 'react';
import './Home.css';
import { useNavigate } from 'react-router-dom';

const Home = () => {
    const navigate = useNavigate();

    const [isModalOpen, setIsModalOpen] = useState(false);
    const [streak, setStreak] = useState(null);

    const [activityData, setActivityData] = useState({
        distance: '',
        duration: '',
        date: ''
    });

    useEffect(() => {
        const userString = localStorage.getItem('loggedInUser');
        if (!userString) return;
        const userId = JSON.parse(userString).id;

        fetch(`http://localhost:8080/api/activities/streak/${userId}`)
            .then(res => res.ok ? res.json() : null)
            .then(data => setStreak(data))
            .catch(() => setStreak(null));
    }, []);

    const handleLogout = () => {
        localStorage.clear();
        alert('Te-ai delogat cu succes!');
        navigate('/');
    };

    const handleDeleteAccount = async () => {
        const confirmDelete = window.confirm("Ești sigur că vrei să îți ștergi definitiv contul?");
        if (confirmDelete) {
            const userString = localStorage.getItem('loggedInUser');
            if (!userString) return;
            const userId = JSON.parse(userString).id;

            try {
                const response = await fetch(`http://localhost:8080/api/users/delete/user/by/${userId}`, { method: 'DELETE' });
                if (response.ok) {
                    alert('Contul tău a fost șters!');
                    localStorage.clear();
                    navigate('/');
                }
            } catch (error) {
                alert("Eroare de conexiune cu serverul.");
            }
        }
    };

    const submitActivity = async (e) => {
        e.preventDefault();

        const userString = localStorage.getItem('loggedInUser');
        if (!userString) {
            alert("Eroare: Nu ești logat! Te rugăm să te reîntorci la pagina de Login.");
            navigate('/');
            return;
        }

        const loggedInUser = JSON.parse(userString);

        const startDate = new Date(`${activityData.date}T00:00:00`);

        const durationInMinutes = parseInt(activityData.duration, 10) || 0;

        const endDate = new Date(startDate.getTime() + durationInMinutes * 60000);

        const formatForJava = (d) => {
            const pad = (n) => n.toString().padStart(2, '0');
            return `${d.getFullYear()}-${pad(d.getMonth() + 1)}-${pad(d.getDate())}T${pad(d.getHours())}:${pad(d.getMinutes())}:00`;
        };

        const payload = {
            distanceKm: parseFloat(activityData.distance),
            startTime: formatForJava(startDate),
            endTime: formatForJava(endDate),
            userDto: {
                id: loggedInUser.id
            }
        };

        try {
            const response = await fetch('http://localhost:8080/api/activities/create/activity', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });

            if (response.ok) {
                alert('Alergarea a fost salvată cu succes! 🏃‍♂️');
                setIsModalOpen(false);
                setActivityData({ distance: '', duration: '', date: '' });
            } else {
                alert('Eroare la salvarea alergării. Verifică consola de backend.');
            }
        } catch (error) {
            console.error("Eroare:", error);
            alert("Nu s-a putut conecta la server.");
        }
    };

    const handleChange = (e) => {
        setActivityData({ ...activityData, [e.target.name]: e.target.value });
    };

    return (
        <div className="home-container">
            <div className="home-header">
                <h1>🏃‍♂️ RUN TRACKER</h1>
                <p>Dashboard-ul tău de alergare</p>
            </div>

            <div className="streak-section">
                <div className="streak-card">
                    <span className="streak-icon">🔥</span>
                    <span className="streak-value">{streak ? streak.currentStreak : '—'}</span>
                    <span className="streak-label">Streak curent</span>
                </div>
                <div className="streak-card">
                    <span className="streak-icon">🏆</span>
                    <span className="streak-value">{streak ? streak.longestStreak : '—'}</span>
                    <span className="streak-label">Cel mai lung streak</span>
                </div>
            </div>

            <div className="home-buttons-grid">
                <button className="btn-neon green" onClick={() => setIsModalOpen(true)}>
                    ➕ Add running session
                </button>
                <button className="btn-neon blue" onClick={() => navigate('/activities')}>
                    📋 See Running Sessions
                </button>
                <button className="btn-neon purple" onClick={() => navigate('/account')}>
                    👤 See Account
                </button>
                <button className="btn-outline red" onClick={handleDeleteAccount}>
                    🗑️ Delete Account
                </button>
                <button className="btn-outline gray" onClick={handleLogout}>
                    🚪 Logout
                </button>
            </div>

            {isModalOpen && (
                <div className="modal-overlay">
                    <div className="modal-content">
                        <h2>Adaugă Alergare Nouă</h2>
                        <form onSubmit={submitActivity}>
                            <input
                                type="number"
                                name="distance"
                                placeholder="Distanța (km)"
                                value={activityData.distance}
                                onChange={handleChange}
                                required
                            />
                            <input
                                type="number"
                                name="duration"
                                placeholder="Durata (minute)"
                                value={activityData.duration}
                                onChange={handleChange}
                                required
                            />
                            <input
                                type="date"
                                name="date"
                                value={activityData.date}
                                onChange={handleChange}
                                required
                            />
                            <div className="modal-actions">
                                <button type="submit" className="btn-neon green">Salvează</button>
                                <button type="button" className="btn-outline gray" onClick={() => setIsModalOpen(false)}>Anulează</button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
};

export default Home;