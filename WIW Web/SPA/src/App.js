import React from 'react';
import  Appbar  from './components/AppBar/Appbar';
import { Jumbotron } from './components/Jumbotron/Jumbotron';
import { Footer } from './components/Footer/Footer';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom'
import  Registro  from './components/Login/Registro';
import  Bodyadmin  from './components/Bodyadmin/Bodyadmin';
import Peliculas  from './components/Peliculas/Peliculas';
import {auth} from './firebase'
import Usuarios from './components/Usuarios/Usuarios';
import Noticias from './components/Noticias/Noticias';
import './App.css'
function App() {

  const [firebaseUser , setFirebaseUser] = React.useState(false)

  React.useEffect( () => {
    auth.onAuthStateChanged(user => {
      if (user) {
        setFirebaseUser(user)
      }else{
        setFirebaseUser(null)
      }
      console.log(user);
    })
  },[])

  return firebaseUser !== false ? (
    <Router>
      <div>
        <Appbar firebaseUser = {firebaseUser} />
        <Switch>
        <Route path="/admin/usuarios">
            <Usuarios />
          </Route>
        <Route path="/admin/noticias">
            <Noticias />
          </Route>
          <Route path="/admin/peliculas">
            <Peliculas/>
          </Route>
          <Route path="/admin">
            <Bodyadmin/>
          </Route>
          <Route path="/login">
            <Registro/>
          </Route>
          <Route path="/">
            <Jumbotron />
            <Footer />
          </Route>
        </Switch>
      </div>
    </Router>


  ): <p>Cargando...</p>;
}

export default App;
