import React, { useState, useEffect } from 'react';
import axios from 'axios';

const App = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');
    const [messages, setMessages] = useState([]);

    useEffect(() => {
        fetchMessages();
    }, []);

    const register = async () => {
        await axios.post('/api/register', { username, password });
    };

    const login = async () => {
        await axios.post('/api/login', { username, password });
    };

    const sendMessage = async () => {
        await axios.post('/api/messages', { message });
        fetchMessages();
    };

    const fetchMessages = async () => {
        const response = await axios.get('/api/messages');
        setMessages(response.data);
    };

    return (
        <div>
            <h1>Chat Application</h1>
            <div>
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
                <button onClick={register}>Register</button>
                <button onClick={login}>Login</button>
            </div>
            <div>
                <input
                    type="text"
                    placeholder="Type a message"
                    value={message}
                    onChange={(e) => setMessage(e.target.value)}
                />
                <button onClick={sendMessage}>Send</button>
            </div>
            <div>
                <h2>Messages</h2>
                <ul>
                    {messages.map((msg, index) => (
                        <li key={index}>{msg.content}</li>
                    ))}
                </ul>
            </div>
        </div>
    );
};

export default App;
