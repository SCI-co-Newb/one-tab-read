import {useState} from "react";
import Login from "./Login";
import Signup from "./Signup";

export default function AuthForm() {
    const [isLoginVisible, setIsLoginVisible] = useState(false);
    const [isSignupVisible, setIsSignupVisible] = useState(false);

    const toggleLoginForm = () => setIsLoginVisible(!isLoginVisible);
    const toggleSignupForm = () => setIsSignupVisible(!isSignupVisible);

    return (
        <div className="authForm">
            <button onClick={toggleLoginForm}>Login</button>
            {isLoginVisible && (
                <Login />
            )}
            <button onClick={toggleSignupForm}>Signup</button>
            {isSignupVisible && (
                <Signup />
            )}
        </div>
    )

}