import React, { useState } from 'react';
import './Login.css';

const Login = () => {
    const [formData, setFormData] = useState({
        usernameOrEmail: '',
        password: ''
    });

    const [error, setError] = useState('');

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!formData.usernameOrEmail || !formData.password) {
            setError('Te rugăm să completezi toate câmpurile!');
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/api/users/get/user/by/username/email?name=${formData.usernameOrEmail}`);

            if (response.ok) {
                const user = await response.json();
                if (user && user.password === formData.password) {
                    setError('');
                    alert('Logare cu succes! Bine ai venit, ' + user.username + '!');
                } else {
                    setError('Parolă incorectă!');
                }
            } else {
                setError('Utilizatorul nu există! Te rugăm să îți creezi un cont.');
            }
        } catch (err) {
            console.error("Eroare de server:", err);
            setError('Nu ne-am putut conecta la server. Asigură-te că backend-ul este pornit!');
        }
    };

    return (
        <div className="login-wrapper">
            <div className="login-container">
                {}
                <h1 className="brand-logo">🏃‍♂️ RUN TRACKER</h1>

                <div className="login-box">
                    <h2 className="login-title">Log In</h2>

                    {}
                    <div className="social-buttons">
                        <button className="social-btn facebook-btn">Continuă cu Facebook</button>
                        <button className="social-btn google-btn">Continuă cu Google</button>
                        <button className="social-btn apple-btn">Continuă cu Apple</button>
                    </div>

                    <div className="divider">
                        <span>sau loghează-te cu email</span>
                    </div>

                    {error && <p className="error-message">{error}</p>}

                    <form onSubmit={handleSubmit}>
                        <div className="form-group">
                            <input
                                type="text"
                                name="usernameOrEmail"
                                value={formData.usernameOrEmail}
                                onChange={handleChange}
                                placeholder="Email sau Nume utilizator"
                            />
                        </div>

                        <div className="form-group">
                            <input
                                type="password"
                                name="password"
                                value={formData.password}
                                onChange={handleChange}
                                placeholder="Parolă"
                            />
                        </div>

                        <button type="submit" className="login-btn">Logare</button>
                    </form>

                    <div className="forgot-password">
                        <a href="#forgot">Ai uitat parola?</a>
                    </div>
                </div>

                <div className="register-section">
                    Nu ai cont? <a href="/register">Înregistrează-te aici</a>
                </div>
            </div>
        </div>
    );
};

export default Login;