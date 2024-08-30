import React, { useState, useEffect } from 'react';
import axios from 'axios';

const Chat = () => {
    const [message, setMessage] = useState('');
    const [messages, setMessages] = useState([]);

    useEffect(() => {
        fetchMessages();
    }, []);

    const fetchMessages = async () => {
        const response = await axios.get('/api/messages');
        setMessages(response.data);
    };

    const sendMessage = async () => {
        await axios.post('/api/messages', { content: message });
        setMessage('');
        fetchMessages();
    };

    return (
        <div>
            <h2>Chat</h2>
            <div>
                <input
                    type="text"
                    placeholder="Type a message"
                    value={message}
                    onChange={(e) => setMessage(e.target.value)}
                />
                <button onClick={sendMessage}>Send</button>
            </div>
            <ul>
                {messages.map((msg, index) => (
                    <li key={index}>{msg.content}</li>
                ))}
            </ul>
        </div>
    );
};

export default Chat;
