import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './AccountPage.css';

const AccountPage = () => {
    const navigate = useNavigate();
    const [userData, setUserData] = useState(null);
    const [profileImage, setProfileImage] = useState(null);
    const [passwords, setPasswords] = useState({
        currentPassword: '',
        newPassword: '',
        confirmPassword: ''
    });

    useEffect(() => {
        const savedUser = JSON.parse(localStorage.getItem('loggedInUser'));
        if (savedUser) {
            setUserData(savedUser);
            if (savedUser.profileImage) {
                setProfileImage(savedUser.profileImage);
            }
        } else {
            navigate('/');
        }
    }, [navigate]);

    const handleLogout = () => {
        localStorage.removeItem('loggedInUser');
        navigate('/');
    };


    const handleImageUpload = async (e) => {
        const file = e.target.files[0];
        if (!file) return;

        const formData = new FormData();
        formData.append('image', file);

        try {
            const response = await fetch(`http://localhost:8080/api/users/upload/profile/image/${userData.id}`, {
                method: 'POST',
                body: formData
            });

            if (response.ok) {
                const reader = new FileReader();
                reader.onloadend = () => {
                    const base64String = reader.result;
                    setProfileImage(base64String);

                    const updatedUser = { ...userData, profileImage: base64String };
                    localStorage.setItem('loggedInUser', JSON.stringify(updatedUser));
                };
                reader.readAsDataURL(file);

                alert("Poza de profil a fost actualizată! 📸");
            } else {
                alert("Eroare la salvarea imaginii pe server.");
            }
        } catch (error) {
            console.error("Eroare upload:", error);
            alert("Eroare de conexiune la server.");
        }
    };

    const handlePasswordChange = async (e) => {
        e.preventDefault();

        if (passwords.newPassword !== passwords.confirmPassword) {
            alert("Parolele noi nu se potrivesc!");
            return;
        }

        try {
            const queryParams = new URLSearchParams({
                currentPassword: passwords.currentPassword,
                newPassword: passwords.newPassword
            }).toString();

            const response = await fetch(`http://localhost:8080/api/users/change/password/${userData.id}?${queryParams}`, {
                method: 'PUT'
            });

            if (response.ok) {
                alert("Parola a fost schimbată cu succes! 🔒");
                setPasswords({ currentPassword: '', newPassword: '', confirmPassword: '' });
            } else {
                const errorMsg = await response.text();
                alert(`Eroare: ${errorMsg || "Parola actuală este incorectă."}`);
            }
        } catch (err) {
            console.error("Eroare schimbare parolă:", err);
            alert("Eroare de conexiune la server.");
        }
    };

    const handleInput = (e) => {
        setPasswords({ ...passwords, [e.target.name]: e.target.value });
    };

    if (!userData) return null;

    return (
        <div className="account-page-wrapper">
            <div className="account-container">
                <header className="account-header">
                    <button className="back-btn" onClick={() => navigate('/home')}>← Dashboard</button>
                    <h1>Setări Cont</h1>
                </header>

                <section className="profile-section">
                    <div className="avatar-circle">
                        {profileImage ? (
                            <img src={profileImage} alt="Profile" />
                        ) : (
                            <span className="avatar-placeholder">
                                {userData.name ? userData.name.charAt(0).toUpperCase() : 'U'}
                            </span>
                        )}
                    </div>
                    <label className="image-upload-label">
                        Schimbă fotografia de profil
                        <input type="file" accept="image/*" onChange={handleImageUpload} hidden />
                    </label>
                    <p className="user-email-display" style={{marginTop: '10px', color: '#ccc'}}>{userData.email}</p>
                </section>

                <section className="password-section">
                    <h3>Securitate</h3>
                    <form onSubmit={handlePasswordChange} className="password-form">
                        <div className="input-group">
                            <label>Parola Actuală</label>
                            <input
                                type="password"
                                name="currentPassword"
                                value={passwords.currentPassword}
                                onChange={handleInput}
                                required
                            />
                        </div>
                        <div className="input-group">
                            <label>Parola Nouă</label>
                            <input
                                type="password"
                                name="newPassword"
                                value={passwords.newPassword}
                                onChange={handleInput}
                                required
                            />
                        </div>
                        <div className="input-group">
                            <label>Confirmă Parola Nouă</label>
                            <input
                                type="password"
                                name="confirmPassword"
                                value={passwords.confirmPassword}
                                onChange={handleInput}
                                required
                            />
                        </div>
                        <button type="submit" className="btn-save-password">
                            Actualizează Parola
                        </button>
                    </form>
                </section>

                <button className="btn-logout" onClick={handleLogout}>
                    Închide Sesiunea
                </button>
            </div>
        </div>
    );
};

export default AccountPage;