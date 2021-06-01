import React from 'react'
import { db, auth } from '../../firebase'
import { Sidebar } from '../Sidebar/Sidebar'
import { Bar } from 'react-chartjs-2';
import {withRouter} from 'react-router-dom'
import './peliculas.css'



/// CHART
const data = {
  labels: ['Mayo', 'Junio', 'Julio'],
  datasets: [
    {
      label:'Peliculas creadas',
      data: [12, 19, 3],
      backgroundColor: [
        'rgba(255, 99, 132, 0.2)',
        'rgba(54, 162, 235, 0.2)',
        'rgba(255, 206, 86, 0.2)',
      ],
      borderColor: [
        'rgba(255, 99, 132, 1)',
        'rgba(54, 162, 235, 1)',
        'rgba(255, 206, 86, 1)',
      ],
      borderWidth: 1,
    },
  ],
};



///END CHART





const Peliculas = (props) => {
  const [user, setUser] = React.useState(null)
  const [refreshCounter,setRefreshCounter] = React.useState(0)
  React.useEffect(() => {
    if (auth.currentUser) {
      setUser(auth.currentUser)
    }else{
      console.log('No existe usuario')
      props.history.push('/login')
    }

  },[props])

    const [esEdicion, setEsEdicion] = React.useState(false)
    const [peliculas, setPeliculas] = React.useState([])
    const [newId, setNewId] = React.useState(0)

    let pelisfromfireb =[]

    /**
     *
     * States del formulario
     */

    const [titulo, setTitulo] = React.useState('')
    const [idDoc, setIdDoc] = React.useState('')
    const [id,setId] = React.useState(0)
    const [anio, setAnio] = React.useState('')
    const [director, setDirector] = React.useState('')
    const [portada, setPortada] = React.useState('')
    const [rating, setRating] = React.useState(100)
    const [sinopsis, setSinopsis] = React.useState('')
    const [sitios, setSitios] = React.useState([])

    const [check1, setCheck1] = React.useState(false)
    const [check2, setCheck2] = React.useState(false)
    const [check3, setCheck3] = React.useState(false)




    /**
     *
     * Limpia todos los estados
     */
const limpiarEstados = () =>{
    setEsEdicion(false)
    setTitulo("")
    setAnio("")
    setDirector("")
    setPortada("")
    setRating(100)
    setSinopsis("")
    setSitios([]);
    setCheck1(false)
    setCheck2(false)
    setCheck3(false)
  }

    const handleClick = (id) =>{
        if (id === 0) {
            setCheck1(!check1)

            if (!check1) {
                sitios.push(0)
            }else{
                var i = sitios.indexOf(0)
                sitios.splice(i,1)
            }
        }else if (id === 1){
            setCheck2(!check2)
            if (!check2) {
                sitios.push(1)
            }else{
                var i2 = sitios.indexOf(1)
                sitios.splice(i2,1)
            }
        }else if (id === 2){
          setCheck3(!check3)
          if (!check3) {
              sitios.push(2)
          }else{
              var i3 = sitios.indexOf(1)
              sitios.splice(i3,1)
          }
      }
    }





    /**
     *
     * Procesa la película del formulario
     */

    const procesarPelicula = async (e) => {
      e.preventDefault();
      if (esEdicion) {
        try {
          await  db.collection("peliculas").doc(idDoc).set({
            id: id,
            portada: portada,
            nombre: titulo,
            anio: anio,
            director: director,
            sinopsis: sinopsis,
            ratingPoints: rating,
            sitios: sitios
          });
          setRefreshCounter(refreshCounter + 1)
          limpiarEstados()

        } catch (error) {
          console.log(error);
        }
      } else {
        try {
        await  db.collection("peliculas").add({
            id: newId,
            portada: portada,
            nombre: titulo,
            anio: anio,
            director: director,
            sinopsis: sinopsis,
            ratingPoints: rating,
            sitios: sitios
          });
          setRefreshCounter( refreshCounter + 1 )
          limpiarEstados()
        } catch (error) {
          console.log(error);
        }
      }
    }

    /**
     * Manda al formulario la pelicula
     * @param pelicula
     */
    const editarPelicula = (pelicula) =>{

      settingBoxes()
        setEsEdicion(true)
        setId(pelicula.id)
        setIdDoc(pelicula.iddoc)
        setPortada(pelicula.portada)
        setTitulo(pelicula.nombre)
        setDirector(pelicula.director)
        setRating(pelicula.ratingPoints)
        setSinopsis(pelicula.sinopsis)
        setAnio(pelicula.anio)
        setSitios(pelicula.sitios)




    }

    const settingBoxes = () =>{
        if (sitios.includes(0)) {
            setCheck1(true)
        } else{
          setCheck1(false)
        }
        if (sitios.includes(1)) {
            setCheck2(true)
        }else{
          setCheck2(false)
        }

    }


    /**
     * Elimina la pelicula de firebase
     * @param pelicula
     */
    const eliminarPelicula = async (pelicula) =>{
        try {
           await db.collection('peliculas').doc(pelicula.iddoc).delete()
            setRefreshCounter(refreshCounter + 1 )
        } catch (error) {
            console.log('No se ha podido borrar.');

        }

    }



     React.useEffect(() =>{


        /**
         * Obtiene las peliculas de firebase
         */
         const fetchPeliculas = async() =>{
             try {
                 const data = await db.collection('peliculas').get()
                 const arrayData = data.docs.map(doc => (
                     {
                      iddoc: doc.id,
                      ...doc.data()
                 }))
                 console.log(arrayData);
                 setPeliculas(arrayData)
                 pelisfromfireb = arrayData
                 calculaNuevaId()
             } catch (error) {
                 console.log(error);

             }
         }
             /**
     *
     * Calcula la id de la nueva pelicula
     */
      const calculaNuevaId = () =>{
        let newId = 0;
        console.log('este'+pelisfromfireb);
        for (let index = 0; index < pelisfromfireb.length; index++) {
          if (pelisfromfireb[index].id > newId) {
            newId = pelisfromfireb[index].id;
          }
        }
        setNewId(newId + 1)
      }



         fetchPeliculas()
     },[refreshCounter])




    return (
      <div className='container-peliculas'>
        <div className='sidebar-peliculas'>
          <Sidebar />
        </div>

        <div className='chart'>
        <Bar data={data} />
        </div>
<div className='row-buttom'>
        <div className='table-peliculas'>
        <table>
          <tr>
            <th>Nombre</th>
            <th>Director</th>
            <th>Año</th>
            <th>Rating</th>
          </tr>
          {peliculas.map((pelicula) => (
            <tr key={pelicula.iddoc}>
              <td>{pelicula.nombre}</td>
              <td>{pelicula.director}</td>
              <td>{pelicula.anio}</td>
              <td>{pelicula.ratingPoints}</td>
              <td>
                <button onClick={() => editarPelicula(pelicula)}>Editar</button>
                <button onClick={() => eliminarPelicula(pelicula)}>
                  Eliminar
                </button>
              </td>
            </tr>
          ))}
        </table>
        </div>
        <div>
          <form className="form-peliculas" >
            <h3>{esEdicion ? "Editar Pelicula" : "Crear Pelicula"}</h3>
            <input
            className='input'
              type="text"
              placeholder="Introduce el título"
              onChange={(e) => setTitulo(e.target.value)}
              value={titulo}
            />

            <input
            className='input'
              type="text"
              placeholder="Introduce el director"
              onChange={(e) => setDirector(e.target.value)}
              value={director}
            />

            <input
            className='input'
              type="text"
              placeholder="Introduce año"
              onChange={(e) => setAnio(e.target.value)}
              value={anio}
            />
            <input
            className='input'
              type="text"
              placeholder="Introduce portada"
              onChange={(e) => setPortada(e.target.value)}
              value={portada}
            />
            <input
            className='input'
              type="text"
              placeholder="Introduce sinopsis"
              onChange={(e) => setSinopsis(e.target.value)}
              value={sinopsis}
            />

            <input
            className='input'
              type="number"
              placeholder="Introduce rating"
              onChange={(e) => setRating(parseInt(e.target.value))}
              value={rating}
            />

            <label>
              <input
                onClick={() => handleClick(0)}
                checked={check1}
                value={check1}
                type="checkbox"
              />{" "}
              Netflix
            </label>

            <label>
              <input
                onClick={() => handleClick(1)}
                checked={check2}
                value ={check2}
                type="checkbox"
              />{" "}
              Amazon Prime
            </label>
            <label>
              <input
                onClick={() => handleClick(2)}
                checked={check3}
                value={check3}
                type="checkbox"
              />{" "}
              HBO
            </label>

            <button onClick={(e) => procesarPelicula(e)} type="submit">
              {esEdicion ? "Editar" : "Crear"}
            </button>
          </form>
        </div>
        </div>
      </div>
    );
}

export default withRouter(Peliculas)
