// import React, { useState} from "react";
// import "./Login.css";
// import axios from "axios";
// import { Link } from "react-router-dom";
// import { FaLock } from "react-icons/fa";
// import { useNavigate } from "react-router-dom";
// import { MdEmail } from "react-icons/md";
// export default function Login() {
//   const navigate = useNavigate();
//   const [userLoginDetails, setUserLoginDetails] = useState({
//     email: "",
//     password: "",
//   });
  
//   const handleSubmit = async (e) => {
//     e.preventDefault();
//     try {
//       const response = await axios.post(
//         "http://localhost:8082/user/authenticate",
//         userLoginDetails
//       );
//       if (response.status === 200) {
//         console.log(response.data);
//         const authToken = {
//           accessToken: response.data.accessToken,
//           tokenType: response.data.tokenType,
//           userId: response.data.userId
//         };
//         window.localStorage.setItem("authToken", JSON.stringify(authToken));
//         navigate("/home");
//       }
//     }
//     catch (error) {
//       console.error("Error posting data:", error.response.status);
//       let errorStatus = error.response.status;
//       console.log(errorStatus);
//     }
//   };

//   const handleChange = (e) => {
//     const { name, value } = e.target;
//     setUserLoginDetails({ ...userLoginDetails, [name]: value });
//   };

//   return (
//     <div className="login-wrapper">
//       <div className="wrapper">
//         <form onSubmit={handleSubmit}>
//           <h1>Welcome to Smart Cabs</h1>
//           <div className="input-box">
//             <input
//               type="email"
//               placeholder={"abc@xyz.com"}
//               name="email"
//               value={userLoginDetails.email}
//               onChange={handleChange}
//               required
//               autoComplete="off"
//             />
//               <MdEmail className="icon" />
//           </div>
//           <div className="input-box">
//             <input
//               type="password"
//               placeholder={"Password"}
//               name="password"
//               value={userLoginDetails.password}
//               onChange={handleChange}
//               required
//             />
//             <FaLock className="icon" />
//           </div>
//           <button type="submit">{"Login"}</button>
//           <div className="register-link">
//             <p>
//               {"New Here?"} <Link to="/register">{"Register"}</Link>
//             </p>
//           </div>
//         </form>
//       </div>
//     </div>
//   );
// }

import React, { useState } from "react";
import "./Login.css";
import axios from "axios";
import { Link } from "react-router-dom";
import { FaLock } from "react-icons/fa";
import { useNavigate } from "react-router-dom";
import { MdEmail } from "react-icons/md";

export default function Login() {
  const navigate = useNavigate();
  const [userLoginDetails, setUserLoginDetails] = useState({
    email: "",
    password: "",
  });
  const [errorMessage, setErrorMessage] = useState(""); // State to manage error message

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        "http://localhost:8082/user/authenticate",
        userLoginDetails
      );
      if (response.status === 200) {
        console.log(response.data);
        const authToken = {
          accessToken: response.data.accessToken,
          tokenType: response.data.tokenType,
          userId: response.data.userId,
        };
        window.localStorage.setItem("authToken", JSON.stringify(authToken));
        setErrorMessage(""); // Clear any previous error message
        navigate("/home");
      }
    } catch (error) {
      console.error("Error posting data:", error.response.status);
      let errorStatus = error.response.status;
      if (errorStatus === 401) {
        setErrorMessage("Invalid credentials. Please try again."); // Set the error message
      } else {
        setErrorMessage("An error occurred. Please try again later."); // Generic error message for other status codes
      }
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUserLoginDetails({ ...userLoginDetails, [name]: value });
  };

  return (
    <div className="login-wrapper">
      <div className="wrapper">
        <form onSubmit={handleSubmit}>
          <h1>Welcome to Smart Cabs</h1>
          {errorMessage && <p className="error-message" style={{ textAlign: 'center' }}>{errorMessage}</p>} {/* Conditionally render the error message */}
          <div className="input-box">
            <input
              type="email"
              placeholder={"abc@xyz.com"}
              name="email"
              value={userLoginDetails.email}
              onChange={handleChange}
              required
              autoComplete="off"
            />
            <MdEmail className="icon" />
          </div>
          <div className="input-box">
            <input
              type="password"
              placeholder={"Password"}
              name="password"
              value={userLoginDetails.password}
              onChange={handleChange}
              required
            />
            <FaLock className="icon" />
          </div>
          <button type="submit">{"Login"}</button>
          <div className="register-link">
            <p>
              {"New Here?"} <Link to="/register">{"Register"}</Link>
            </p>
          </div>
        </form>
      </div>
    </div>
  );
}
