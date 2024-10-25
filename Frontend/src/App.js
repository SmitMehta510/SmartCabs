import Login from "./Login";
import Home from "./Home";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Register from "./Register";

function App() {
  return(
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/login" element={<Login />} />
        <Route path="/home" element={<Home />} />
      </Routes>
    </Router>
  );
}

export default App;
