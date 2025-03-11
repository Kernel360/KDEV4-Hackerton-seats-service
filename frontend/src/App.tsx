import { Navigate, Route, Routes } from 'react-router-dom'
import Main from './pages/Main'
import SignUp from './pages/SignUp'
import SignIn from './pages/SignIn'
import SeatOccupancy from './pages/SeatOccupancy'
import Mypage from './pages/MyPage'
import { JSX } from '@emotion/react/jsx-runtime'
import { useState } from 'react'

// 로그인 상태를 확인하는 유틸리티 함수
const isAuthenticated = () => {
  return !!localStorage.getItem('accessToken') // 로컬스토리지에서 JWT 토큰이 있으면 로그인된 상태로 간주
}

// 로그인된 사용자만 접근할 수 있도록 제한하는 컴포넌트
const AuthRoute = ({ element }: { element: JSX.Element }) => {
  return isAuthenticated() ? element : <Navigate to="/signin" /> // 인증되지 않은 사용자는 로그인 페이지로 리다이렉트
}

export default function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false)

  return (
    <Routes>
      {/* 메인 페이지는 누구나 접근 가능 */}
      <Route
        path="/"
        element={<Main />}
      />

      {/* 로그인 페이지와 회원가입 페이지는 누구나 접근 가능 */}
      <Route
        path="/signup"
        element={<SignUp />}
      />
      <Route
        path="/signin"
        element={<SignIn />}
      />

      {/* 로그인된 사용자만 접근 가능한 페이지 */}
      <Route
        path="/occupancy"
        element={<AuthRoute element={<SeatOccupancy />} />}
      />

      {/* 마이 페이지도 로그인된 사용자만 접근 */}
      <Route
        path="/mypage"
        element={<AuthRoute element={<Mypage />} />}
      />
    </Routes>
  )
}
