import React from "react";
import { Sidebar } from "../Sidebar/Sidebar";
import {auth} from '../../firebase'
import {withRouter} from 'react-router-dom'
import './bodyadmin.css'

const Bodyadmin = (props) => {
  const [user, setUser] = React.useState(null)

  React.useEffect(() => {
    if (auth.currentUser) {
      setUser(auth.currentUser)
    }else{
      console.log('No existe usuario')
      props.history.push('/login')
    }

  },[props])


  return (
      <div className = 'container'>
        <div className ='sidebar' >
        <Sidebar/>
        </div>
          
      </div>
  );
};

export default withRouter(Bodyadmin)
