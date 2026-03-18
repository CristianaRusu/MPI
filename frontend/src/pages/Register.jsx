import React, { useState } from 'react';
import './Register.css';

const Register = () => {
    const [formData, setFormData] = useState({
        username: '',
        email: '',
        password: ''
    });

    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(false);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);
        setSuccess(false);

        try {
            if (formData.email === 'test@email.com' || formData.username === 'admin') {
                throw new Error('Acest email sau username este deja folosit. Te rugăm să încerci altul.');
            }

            // Dacă totul este ok
            setSuccess(true);

        } catch (err) {
            setError(err.message || 'A apărut o eroare la înregistrare.');
        }
    };

    return (
        <div className="register-container">
            <h2>Creare Cont Nou</h2>

            <form onSubmit={handleSubmit} className="register-form">
                <div className="input-group">
                    <label htmlFor="username">Username:</label>
                    <input
                        type="text"
                        id="username"
                        name="username"
                        value={formData.username}
                        onChange={handleChange}
                        required
                    />
                </div>

                <div className="input-group">
                    <label htmlFor="email">Email:</label>
                    <input
                        type="email"
                        id="email"
                        name="email"
                        value={formData.email}
                        onChange={handleChange}
                        required
                    />
                </div>

                <div className="input-group">
                    <label htmlFor="password">Parolă:</label>
                    <input
                        type="password"
                        id="password"
                        name="password"
                        value={formData.password}
                        onChange={handleChange}
                        required
                    />
                </div>

                {error && <p className="error-message">{error}</p>}
                {success && <p className="success-message">Cont creat cu succes!</p>}

                <button type="submit" className="submit-button">Înregistrează-te</button>
            </form>

            <div className="login-link-container">
                <p>Ai deja un cont?</p>
                <a href="/login" className="login-button">Mergi la pagina de Logare</a>
            </div>
        </div>
    );
};

export default Register;