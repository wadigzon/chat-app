import React, { useState } from 'react';
import Login from './components/Login';
import Register from './components/Register';
import Chat from './components/Chat';

const App = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [showRegister, setShowRegister] = useState(false); // Toggle between login and register

    const handleLogin = () => {
        setIsLoggedIn(true);
    };

    const handleLogout = () => {
        localStorage.removeItem('jwtToken');
        setIsLoggedIn(false);
    };

    return (
        <div>
            <h1>Chat Application</h1>

            {!isLoggedIn ? (
                <div>
                    {showRegister ? (
                        <Register />
                    ) : (
                        <Login onLogin={handleLogin} />
                    )}

                    {/* Button to toggle between Login and Register */}
                    <button onClick={() => setShowRegister(!showRegister)}>
                        {showRegister ? 'Go to Login' : 'Go to Register'}
                    </button>
                </div>
            ) : (
                <div>
                    <button onClick={handleLogout}>Logout</button>
                    <Chat />
                </div>
            )}
        </div>
    );
};

export default App;
