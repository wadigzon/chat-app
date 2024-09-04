import React, { useState } from 'react';
import axios from 'axios';

const Register = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [message, setMessage] = useState('');

    const handleRegister = async () => {
        // Basic validation to check if passwords match
        if (password !== confirmPassword) {
            setMessage("Passwords do not match");
            return;
        }

        try {
            const response = await axios.post('/api/register', { username, password });
            setMessage(response.data.message); // Display backend response message

        } catch (error) {
            setMessage('Registration failed');
        }
    };

    return (
        <div>
            <h2>Register</h2>
            <input
                type="text"
                placeholder="Username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
            />
            <input
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <input
                type="password"
                placeholder="Confirm Password"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
            />
            <button onClick={handleRegister}>Register</button>

            {/* Display any messages */}
            {message && <p>{message}</p>}
        </div>
    );
};

export default Register;
