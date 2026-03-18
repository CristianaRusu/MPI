import React, { useState } from 'react';

const RegisterPage = () => {
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

            setSuccess(true);

        } catch (err) {
            setError(err.message || 'A apărut o eroare la înregistrare.');
        }
    };

    return (
        <div style={styles.container}>
            <h2>Creare Cont Nou</h2>

            <form onSubmit={handleSubmit} style={styles.form}>
                <div style={styles.inputGroup}>
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

                <div style={styles.inputGroup}>
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

                <div style={styles.inputGroup}>
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

                {}
                {error && <p style={styles.errorMessage}>{error}</p>}
                {success && <p style={styles.successMessage}>Cont creat cu succes!</p>}

                <button type="submit" style={styles.submitButton}>Înregistrează-te</button>
            </form>

            {}
            <div style={styles.loginLinkContainer}>
                <p>Ai deja un cont?</p>
                {}
                <a href="/login" style={styles.loginButton}>Mergi la pagina de Logare</a>
            </div>
        </div>
    );
};


const styles = {
    container: { maxWidth: '400px', margin: '50px auto', fontFamily: 'Arial, sans-serif' },
    form: { display: 'flex', flexDirection: 'column', gap: '15px' },
    inputGroup: { display: 'flex', flexDirection: 'column', gap: '5px' },
    submitButton: { padding: '10px', backgroundColor: '#0052CC', color: 'white', border: 'none', cursor: 'pointer', borderRadius: '4px' },
    errorMessage: { color: 'red', fontSize: '14px' },
    successMessage: { color: 'green', fontSize: '14px' },
    loginLinkContainer: { marginTop: '20px', textAlign: 'center', borderTop: '1px solid #eee', paddingTop: '15px' },
    loginButton: { display: 'inline-block', padding: '8px 16px', backgroundColor: '#f4f5f7', color: '#172b4d', textDecoration: 'none', borderRadius: '4px', fontWeight: 'bold' }
};

export default RegisterPage;