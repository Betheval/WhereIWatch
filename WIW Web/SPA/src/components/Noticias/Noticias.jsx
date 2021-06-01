import React from 'react'
import {db , auth} from '../../firebase'
import {withRouter} from 'react-router-dom'
import { Sidebar } from '../Sidebar/Sidebar'
import './noticias.css'





const Noticias = (props) => {




  const [refreshCounter, setRefreshCounter] = React.useState(0)
  const [user, setUser] = React.useState(null)

  React.useEffect(() => {
    if (auth.currentUser) {
      setUser(auth.currentUser)
    }else{
      console.log('No existe usuario')
      props.history.push('/login')
    }

  },[props])




    const [esEdicion, setEsEdicion] = React.useState(false)
    const [noticias, setNoticias] = React.useState([])
    const [iddoc, setIdDoc] = React.useState('')
    const [id,setId] = React.useState(0)
    const[titulo,setTitulo] = React.useState('')
    const[subtitulo,setSubtitulo] = React.useState('')
    const[descripcion,setDescripcion] = React.useState('')
    const [imagen, setImagen] = React.useState('')
    const [newId, setNewId] = React.useState(0)


    /**
     * Setea los estados como se encontraban inicialmente.
     */
    const limpiarEstados = () =>{
      setEsEdicion(false)
      setIdDoc('')
      setId('')
      setTitulo('')
      setSubtitulo('')
      setDescripcion('')
      setImagen('')
      setNewId(newId+1)

    }


    /**
     * Elimina la noticia
     */
    const eliminarNoticia = (noticia) =>{
        try {
            db.collection('noticias').doc(noticia.iddoc).delete()
            setNoticias(noticias.filter(not => not.iddoc !== noticia.iddoc))
            limpiarEstados()
        } catch (error) {
            console.log(error);
            
        }

    }

    /**
     * Manda la noticia al formulario
     * 
     */
    const editarNoticia = (noticia) => {
        setEsEdicion(true)
        setIdDoc(noticia.iddoc)
        setId(noticia.id)
        setImagen(noticia.imagen)
        setTitulo(noticia.titulo)
        setSubtitulo(noticia.subtitulo)
        setDescripcion(noticia.descripcion)
        

    }

    /**
     * Procesa los datos del formulario
     */
    const procesarDatos = (e) =>{
        e.preventDefault()
        if (esEdicion) {
          try {
            db.collection('noticias').doc(iddoc).set({
              id:id,
              titulo:titulo,
              subtitulo:subtitulo,
              imagen:imagen,
              descripcion:descripcion
            })

            setRefreshCounter(refreshCounter + 1 )
            limpiarEstados()
            
          } catch (error) {
            console.log(error);
          }
            
        }else{
          try {
            db.collection('noticias').add({
              id:newId,
              titulo:titulo,
              subtitulo:subtitulo,
              imagen:imagen,
              descripcion:descripcion,
            })
            setRefreshCounter(refreshCounter + 1)
            limpiarEstados()
            
          } catch (error) {
            console.log(error);
          }

        }
        setEsEdicion(false)
    }

    let noticiasfromfireb = []
    React.useEffect(() =>{

        /**
         * Obtiene las Noticias de firebase
         */
         const fetchNoticias = async() =>{
             try {
                 const data = await db.collection('noticias').get()
                 const arrayData = data.docs.map(doc => (
                     {
                      iddoc: doc.id,
                      ...doc.data()
                 }))
                 console.log('otro'+arrayData);
                 setNoticias(arrayData)
                 noticiasfromfireb = arrayData
                 calculaNuevaId()
             } catch (error) {
                 console.log(error);
                 
             }
         }

         /* 
         * Calcula la id de la nueva noticia
         */
          const calculaNuevaId = () =>{
            let newId = 0;
            console.log('este'+ noticiasfromfireb);
            for (let index = 0; index < noticiasfromfireb.length; index++) {
              if (noticiasfromfireb[index].id > newId) {
                newId = noticiasfromfireb[index].id;
              }
            }
            setNewId(newId + 1)
          }

         fetchNoticias()
     },[refreshCounter])




    return (
        <div className='container-noticias'>
            <div className='sidebar-noticias'>
                <Sidebar />
            </div>


            <div className = 'table-usuarios-container'>
        <table>
          <tr>
            <th>Títular</th>
          </tr>
          {noticias.map((noticia) => (
            <tr key={noticia.iddoc}>
              <td>{noticia.titulo}</td>
              <td>
                <button onClick={() => editarNoticia(noticia)}>Editar</button>
                <button onClick={() => eliminarNoticia(noticia)}>Eliminar</button>
              </td>
            </tr>
          ))}
        </table>
      </div>
            <form className='form-noticias'>
                <h3>{esEdicion ? 'Editar notica':'Crear noticia'}</h3>
                <input type="text" onChange={(e) =>setTitulo(e.target.value) } placeholder="Introduce Titulo" value={titulo} />
                <input type="text" onChange={(e) =>setSubtitulo(e.target.value) } placeholder="Introduce Subtitulo" value={subtitulo} />
                <input type="text" onChange={(e) =>setImagen(e.target.value) } placeholder="Introduce enlace de imagen" value={imagen} />
                <input type="text" onChange={(e) =>setDescripcion(e.target.value) } placeholder="Introduce Descripcion" value={descripcion} />

                <button type="submit" onClick={(e) => procesarDatos(e)} >
                    {esEdicion ? 'Editar':'Crear'}

                </button>
            </form>
            
        </div>
    )
}
export default withRouter(Noticias);
