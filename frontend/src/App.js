import React from 'react';
import './App.css';

import AuthForm from "./components/AuthForm";
import Books from "./components/Books";

function App() {
    const [user, setUser] = React.useState(null);
    const [isLoggedIn, setIsLoggedIn] = React.useState(false);

    function handleLogin(userData) {
        setUser(userData);
        setIsLoggedIn(true);
    }

    function handleLogout() {
        setUser(null);
        setIsLoggedIn(false);
    }

    return (
        <div className="App">
            {!isLoggedIn &&
                <AuthForm onLogin={handleLogin} />
            }
            {isLoggedIn &&
                <div className="logged-in">
                    <p>Welcome back, {user.username}!</p>
                    <Books user={user} />
                    <button className="logout" onClick={handleLogout}>Logout</button>
                </div>
            }
        </div>
    );
}

export default App;
