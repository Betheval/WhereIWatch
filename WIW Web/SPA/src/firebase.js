import firebase from 'firebase/app'
import 'firebase/firestore'
import 'firebase/auth'

const firebaseConfig = {
    apiKey: "AIzaSyCs4hrVLx2kE7o4csw7e6RC8myxFRQuCso",
    authDomain: "whereiwatch-ddf86.firebaseapp.com",
    projectId: "whereiwatch-ddf86",
    storageBucket: "whereiwatch-ddf86.appspot.com",
    messagingSenderId: "638714934951",
    appId: "1:638714934951:web:d9cf937f1d9c25b33acd82",
    measurementId: "G-1ZXYND3L57"
  };

  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);
  const db = firebase.firestore();
  const auth = firebase.auth();
  // firebase.analytics();
export { db, auth }