import React from "react";
import { NavLink } from "react-router-dom";
import './sidebar.css';

export const Sidebar = () => {
  return (
    <div  className='lista'>
      <ul >
        <li className="list-item" >
          <NavLink style={{ textDecoration: 'none' }} to={"/admin/peliculas"}>
            <p>Peliculas</p>
            </NavLink>
        </li>
        <li className="list-item" >
          <NavLink style={{ textDecoration: 'none' }} to={"/admin/usuarios"}>
            <p>Usuarios</p>
            </NavLink>
        </li>
        <li className="list-item">

          <NavLink style={{ textDecoration: 'none' }} to={"/admin/noticias"}>
            <p>Noticias</p>
            </NavLink>

          
          </li>
      </ul>
    </div>
  );
};
