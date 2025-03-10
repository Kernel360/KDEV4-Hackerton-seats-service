import { Route, Routes } from 'react-router-dom'
import Main from './pages/Main'
import SignUp from './pages/SignUp'
import SignIn from './pages/SignIn'
import SeatOccupancy from './pages/SeatOccupancy'

export default function App() {
  return (
    <Routes>
      <Route
        path="/"
        element={<Main />}
      />
      <Route
        path="/signup"
        element={<SignUp />}
      />
      <Route
        path="/signin"
        element={<SignIn />}
      />
      <Route
        path="/occupancy"
        element={<SeatOccupancy />}
      />
    </Routes>
  )
}
