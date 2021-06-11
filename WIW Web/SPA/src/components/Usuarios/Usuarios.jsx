import React from 'react'
import {db,auth} from '../../firebase'
import {withRouter} from 'react-router-dom'
import { Sidebar } from '../Sidebar/Sidebar'
import './usuarios.css'

const Usuarios = (props) => {
  const [nombre, setNombre] = React.useState("");
  const [email, setEmail] = React.useState("");
  const [idDoc,setIdDoc] = React.useState('')
  const [counter, setCounter] = React.useState(0)

  const [usuarios, setUsuarios] = React.useState([]);

  const [user, setUser] = React.useState(null)

  React.useEffect(() => {
    if (auth.currentUser) {
      setUser(auth.currentUser)
    }else{


      props.history.push('/login')
    }

  },[props])


  const limpiarEstados = () =>{
    setNombre('')
    setEmail('')
    setIdDoc('')
    setCounter('')
  }




  /**
   * Procesa los datos
   */
  const procesarDatos = (e) => {
      e.preventDefault()
      if (nombre !== '' && email !== '') {
          try {
              db.collection('usuarios').doc(idDoc).set({
                  nombre: nombre,
                  correo: email
              })
              limpiarEstados()
              setCounter( counter + 1)
          } catch (error) {
            console.log(error);

          }

      }
  };

  /**
   *
   * Manda los datos al formulario
   */
  const editarUsuario = (usuario) => {
    setIdDoc(usuario.iddoc)
    setNombre(usuario.nombre);
    setEmail(usuario.correo);
  };

  React.useEffect(() => {
    /**
     * Obtiene los usuarios de firebase
     */
    const fetchUsuarios = async () => {
      try {
        const data = await db.collection("usuarios").get();
        const arrayData = data.docs.map((doc) => ({
          iddoc: doc.id,
          ...doc.data(),
        }));


        setUsuarios(arrayData);
      } catch (error) {
        console.log(error);
      }
    };
    fetchUsuarios();
  }, [counter]);

  return (
    <div className="container-usuarios">
      <div className="sidebar-usuarios">
        <Sidebar />
      </div>

      <div className = 'table-usuarios-container'>
        <table>
          <tr>
            <th>Nombre</th>
            <th>Correo</th>
          </tr>
          {usuarios.map((usuario) => (
            <tr key={usuario.iddoc}>
              <td>{usuario.nombre}</td>
              <td>{usuario.correo}</td>
              <td>
                <button onClick={() => editarUsuario(usuario)}>Editar</button>
                <button>Eliminar</button>
              </td>
            </tr>
          ))}
        </table>
      </div>

      <form className='form-usuarios'>
        <h3>Editar usuario</h3>
        <input
          onChange={(e) => setNombre(e.target.value)}
          type="text"
          placeholder="Introduce Nombre"
          name="nombre"
          value={nombre}
        />

        <input
          onChange={(e) => setEmail(e.target.value)}
          type="text"
          placeholder="Introduce correo"
          name="email"
          value={email}
        />
        <button onClick={(e) => procesarDatos(e)} type="submit">
           Editar
        </button>
      </form>
    </div>
  );
}
export default withRouter(Usuarios)
