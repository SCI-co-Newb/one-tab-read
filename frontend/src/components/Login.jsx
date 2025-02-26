import {useState} from "react";

export default function Login() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [message, setMessage] = useState("");

    async function handleSubmit(event) {
        event.preventDefault();

        const userData = {username, password};

        try {
            const response = await fetch(
                "http://localhost:8080/users/findByUsernameAndPassword",
                {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(userData),
                }
            );

            if (response.ok) {
                const data = await response.json();
                setMessage(data.message || `Welcome back ${username}!`);
            } else {
                // Check if response has JSON or is empty
                let errorMessage = "Invalid credentials";
                try {
                    const errorData = await response.json();
                    if (errorData.error) {
                        errorMessage = errorData.error;
                    }
                } catch (e) {
                    console.warn("Response body was empty or not JSON");
                }
                setMessage("Error: " + errorMessage);
            }
        } catch (error) {
            setMessage("Error: " + error.message);
        }
    }

    return (
        <div className="Login">
            <form onSubmit={handleSubmit}>
                <label>
                    Username: <br/>
                    <input type="text" id="username" name="username" value={username} onChange={(e) => setUsername(e.target.value)} />
                </label> <br/>
                <label>
                    Password: <br/>
                    <input type="password" id="password" name="password" value={password} onChange={(e) => setPassword(e.target.value)} />
                </label> <br/>
                <input type="submit" value="Login" />
                {message && <p>{message}</p>}
            </form>
        </div>
    );
}