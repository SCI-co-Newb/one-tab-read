import {useState} from "react";

export default function Signup() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [message, setMessage] = useState("");

    async function handleSubmit(event) {
        event.preventDefault();

        const userData = {username, password};

        try {
            const response = await fetch("http://localhost:8080/users", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(userData),
            });

            if (response.ok) {
                setMessage("User successfully created");
            } else {
                const errorData = await response.json();
                setMessage("Error: " + errorData.message);
            }
        } catch (error) {
            setMessage("Error: " + error.message);
        }
    }

    return (
        <div className="SignUp">
            <form onSubmit={handleSubmit}>
                <label>
                    Username: <br/>
                    <input type="text" id="username" name="username" value={username} onChange={(e) => setUsername(e.target.value)} />
                </label> <br/>
                <label>
                    Password: <br/>
                    <input type="password" id="password" name="password" value={password} onChange={(e) => setPassword(e.target.value)} />
                </label> <br/>
                <input type="submit" value="Sign Up" />
            </form>
            {message && <p>{message}</p>}
        </div>
    );
}