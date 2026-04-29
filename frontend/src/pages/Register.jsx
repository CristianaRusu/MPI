import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Register.css';

const Register = () => {
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        username: '',
        email: '',
        password: ''
    });

    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(false);

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);
        setSuccess(false);

        try {
            const response = await fetch('http://localhost:8080/api/users/create/user', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(formData)
            });

            if (!response.ok) {
                const data = await response.json().catch(() => ({}));
                throw new Error(data.message || 'Acest email sau username este deja folosit.');
            }

            setSuccess(true);
            alert('Cont creat cu succes! Te poți loga acum.');

            navigate('/');

        } catch (err) {
            setError(err.message || 'A apărut o eroare la conectarea cu serverul.');
        }
    };

    return (
        <div className="register-container">
            <div className="register-header">
                <h1>RUN TRACKER</h1>
                <p>Alătură-te comunității noastre</p>
            </div>

            <div className="register-content">
                <h2>Creare Cont Nou</h2>

                <form onSubmit={handleSubmit} className="register-form">
                    <input
                        type="text"
                        name="username"
                        placeholder="Username-ul tău"
                        value={formData.username}
                        onChange={handleChange}
                        required
                    />
                    <input
                        type="email"
                        name="email"
                        placeholder="Adresa de email"
                        value={formData.email}
                        onChange={handleChange}
                        required
                    />
                    <input
                        type="password"
                        name="password"
                        placeholder="Parolă"
                        value={formData.password}
                        onChange={handleChange}
                        required
                    />

                    {error && <p className="error-message">{error}</p>}
                    {success && <p className="success-message">Cont creat cu succes!</p>}

                    <button type="submit" className="btn-neon green">Înregistrează-te</button>
                </form>

                <div className="login-link-container">
                    <p>Ai deja un cont?</p>
                    <button className="btn-outline gray" onClick={() => navigate('/')}>
                        Mergi la Login
                    </button>
                </div>
            </div>
        </div>
    );
};

export default Register;