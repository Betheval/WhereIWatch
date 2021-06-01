import React from 'react'
import './registro.css'
import {auth, db} from '../../firebase'
import {withRouter} from 'react-router-dom'

const Registro = (props) => {
  const [usuarios,setUsuarios] = React.useState([])
  const [esRegistro, setEsRegistro] = React.useState(false)
  const [nombre,setNombre] = React.useState("")
  const [email,setEmail] = React.useState("")
  const [pass,setPass] = React.useState("")
  const [error,setError] = React.useState(null)

/**
 * 
 *  
 * 
 */
 React.useEffect(() =>{

  /**
   * Obtiene las peliculas de firebase
   */
   const fetchUsuarios = async() =>{
       try {
           const data = await db.collection('usuarios').get()
           const arrayData = data.docs.map(doc => (
               {
                iddoc: doc.id,
                ...doc.data()
           }))
           console.log(arrayData);
           setUsuarios(arrayData)
       } catch (error) {
           console.log(error);
           
       }
   }

   fetchUsuarios()
},[])

/**
 * 
 * Valida el usuario administrador 
 * 
 */
const isValid = (ema) =>{
  let email = ema.trim() 
  console.log('Email trimed'+email);
 let usertryingauth = usuarios.filter( user => user.correo === email )
 console.log(usertryingauth)
 if (usertryingauth[0].esAdmin){
   return true
 }
 return false
}

/**
 * Comprueba la validez de los datos introducidos en el formulario de registro.
 */
  const procesarDatos = e =>{
    e.preventDefault()

    if(!email.trim()){
      setError('Ingrese Email')
      return
    }

    if(!pass.trim()){
      setError('Ingrese Contraseña')
      return
    }

    if(esRegistro && !nombre.trim()){
      setError('Ingrese Nombre')
      return
    }
    if(pass.length < 6){
  
      setError('La contraseña debe contener 6 carácteres o más')
      return
    }
    setError(null)

    if(esRegistro){
      registrar()
    } else{
      login()
    }
  }
/**
 * Registra al usuario haciendo uso del hook useCallback,
 * tambien guarda al usuario en la base de datos.
 */
  const registrar = React.useCallback(async () => {
    try {
      const res = await auth.createUserWithEmailAndPassword(email,pass)
      await db.collection('usuarios').doc(res.user.uid).set({
        nombre : nombre,
        correo: res.user.email,
        pendiente: [],
        visto:[]
      })

      setEmail('')
      setPass('')
      setError(null)

    } catch (error) {
      if(error.code === "auth/invalid-email"){
        setError('Email no valido')
      }else if (error.code === "auth/email-already-in-use"){
        setError('El email ya está en uso')
      }
      
    }
  },[nombre,email,pass])


  /**
   * Procesa el login del usuario
   */
  const login = React.useCallback(async () => {
    try {
      if (isValid(email)) {
        await auth.signInWithEmailAndPassword(email, pass)
        setEmail('')
        setPass('')
        props.history.push('/admin/peliculas')
      }else{
      setError('No posee los permisos suficientes')
      }
    } catch (error) {
      if (error.code === 'auth/user-not-found') {
        setError('Usuario/contraseña no coinciden')
      }
      if (error.code === 'auth/wrong-password') {
        setError('Usuario/contraseña no coinciden')
        
      }
      console.log(error.message);
    }

  },[email,pass,props])



    return (
      <div className="container">
        <div className="container-login">
          <div className="form-container">
            <h3 className='h3-title-login'> {esRegistro ? "Registro" : "Login"}</h3>

            <form className="form-login" onSubmit={procesarDatos}>
              {error && (
                <div>
                  <p className='danger-message'>{error}</p>
                </div>
              )}

              {esRegistro ? (
                <input
                  className="input"
                  type="text"
                  placeholder="Ingrese nombre de usuario"
                  onChange={(e) => setNombre(e.target.value)}
                  value={nombre}
                />
              ) : null}

              <input
                className="input"
                type="email"
                placeholder="Ingrese email"
                onChange={(e) => setEmail(e.target.value)}
                value={email}
              />
              <input
                className="input"
                type="password"
                placeholder="Ingrese contraseña"
                onChange={(e) => setPass(e.target.value)}
                value={pass}
              />
              <button className="button-login" type="submit">
                {esRegistro ? "Registrase" : "Entrar"}
              </button>
              <button
                onClick={() => setEsRegistro(!esRegistro)}
                className="button-login"
                type="button"
              >
                {esRegistro ? "Ir al login" : "¿No tienes cuenta?"}
              </button>
            </form>
          </div>
        </div>
      </div>
    );
}

export default withRouter(Registro)
