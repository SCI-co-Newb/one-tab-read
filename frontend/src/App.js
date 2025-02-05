import './App.css';
//import {  } from './components/';

import React, { useState, useEffect } from 'react';
import {Axios} from 'axios';

function App() {
    const [message, setMessage] = useState("");
    useEffect(() => {
        axios.get(/*put url for api in here*/)
    }, []);
    return (
    <div className="App">
      <header className="App-header">
          Hello.
          <button>
              Click me
          </button>
      </header>
    </div>
    );
}

export default App;
