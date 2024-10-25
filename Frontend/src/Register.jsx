import React, { useState } from "react";
import "./Register.css";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { FaUser, FaLock, FaPhone} from "react-icons/fa";
import { MdEmail } from "react-icons/md";
// import ErrorModal from "../../Modals/ErrorModal/ErrorModal";
import axios from "axios";

export default function Register() {

  const navigate = useNavigate();
  const [error, setError] = useState(null);
  const [userDetails, setUserDetails] = useState({
    email: "",
    password: "",
    firstName: "",
    mobileNo: "",
    
  });
  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    const inputValue =
      type === "checkbox" || type === "radio" ? checked : value;
    setUserDetails({ ...userDetails, [name]: inputValue });
  };



  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post(
        "http://localhost:8082/user/register",
        userDetails
      );
      console.log(response.data);
      console.log(response.status);
      if (response.status === 200) {
        const authToken = {
          accessToken: response.data.accessToken,
          tokenType: response.data.tokenType,
          userId: response.data.userId
        };
        window.localStorage.setItem("authToken", JSON.stringify(authToken));
        navigate("/home");
      }
    } catch (error) {
      if (error.response.status === 409) {
        console.log(error.response.status);
        setError({ title: "Error", message: " Email already exist." });
      } else {
        console.error("Error posting data:", error);
      }
    }
  };
//   const closeModal = () => {
//     setError(null);
//   };
  return (
    <div className="login-wrapper">
      <div className="wrapper">
        <form onSubmit={handleSubmit}>
        <h1>{"Welcome to Smart Cabs"}</h1>
            <div className="input-box">
              <input
                type="text"
                className="form-control"
                placeholder={"Name"}
                name="firstName"
                value={userDetails.firstName}
                onChange={handleChange}
                required
              />
              <FaUser className="icon" />
            </div>
            
            <div className="input-box">
              <input
                type="email"
                className="form-control"
                placeholder={"abc@xyz.com"}
                name="email"
                value={userDetails.email}
                onChange={handleChange}
                required
              />
              <MdEmail className="icon" />
            </div>
            <div className="input-box">
              <input
                type="tel"
                className="form-control"
                placeholder={"XXXXXXXXXX"}
                name="mobileNo"
                value={userDetails.mobileNo}
                onChange={handleChange}
                required
              />
              <FaPhone className="icon" />
            </div>

            <div className="input-box">
              <input
                type="password"
                className="form-control"
                placeholder={"Password"}
                name="password"
                value={userDetails.password}
                onChange={handleChange}
                required
              />
              <FaLock className="icon" />
            </div>
          
          <button type="submit" className="btn-design">
            {"Register"}
          </button>
          <div className="register-link">
            <p>
              {"Already have an account?"} <Link to="/login">{"Login"}</Link>
            </p>
          </div>
          {/* {error && (
            <ErrorModal
              title={error.title}
              message={error.message}
              onClose={closeModal}
            />
          )} */}
        </form>
      </div>
    </div>
  );
}