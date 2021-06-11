import React from "react";
import { NavLink } from "react-router-dom";
import { auth } from "../../firebase";
import "./appbar.css";
import Logoimg from "./logo.png";
import {withRouter} from 'react-router-dom'

const Appbar = (props) => {


  const cerrarSesion = () => {
    auth.signOut()
    .then(
      props.history.push('/login')
    )
  }


  return (
    <div>
      <nav className="navbar" >
          <img className="logo" src={Logoimg} alt="" />
          <ul class="navbar-wrapper">
          <li class="navlink">
            <NavLink style={{ textDecoration: 'none' }} to={"/"} >
                <p className='button'>Inicio</p>
              </NavLink>
            </li>
            <li class="navlink">
              {props.firebaseUser !== null ? <p className='button' onClick={() =>cerrarSesion()}>Cerrar sesión</p> :
               <NavLink style={{ textDecoration: 'none' }} to={"/login"} >
                <p className='button'> Inicia sesión</p>
              </NavLink> }
            </li>
          </ul>
      </nav>
    </div>
  );
};


export default withRouter(Appbar)
