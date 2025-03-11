import { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { logout, isLogin } from '@/auth/auth' // 기존 auth에서 가져오기
import { AppBar, Button, Toolbar, Typography } from '@mui/material'
import Box from '@mui/material/Box'

const Header = () => {
  const navigate = useNavigate()

  // 로그인 상태를 관리하는 상태 변수
  const [loggedIn, setLoggedIn] = useState<boolean>(isLogin())

  // 로그인 상태가 변경될 때마다 UI를 업데이트합니다.
  useEffect(() => {
    // localStorage의 변화 감지
    const handleStorageChange = () => {
      setLoggedIn(isLogin()) // 로그인 상태가 변경되면 UI를 업데이트
    }

    // 로컬스토리지 변경을 감지하는 이벤트 리스너 등록
    window.addEventListener('storage', handleStorageChange)

    // 컴포넌트 언마운트 시 이벤트 리스너 제거
    return () => {
      window.removeEventListener('storage', handleStorageChange)
    }
  }, [])

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar>
          <Button
            onClick={() => navigate('/')}
            variant="text"
            color="inherit"
            sx={{ flexGrow: 1, justifyContent: 'start' }}>
            <Typography variant="h6">KERNEL360 회의실 예약</Typography>
          </Button>

          {loggedIn ? (
            <>
              <Button
                color="inherit"
                onClick={() => navigate('/occupancy')}
                size="large">
                예약하기
              </Button>
              <Button
                color="inherit"
                onClick={() => {
                  logout() // 로그아웃
                  setLoggedIn(false) // 로그아웃 후 상태 변경
                  navigate('/') // 홈으로 이동
                }}
                size="large">
                로그아웃
              </Button>
            </>
          ) : (
            <>
              <Button
                color="inherit"
                onClick={() => navigate('/signin')}
                size="large">
                로그인
              </Button>
              <Button
                color="inherit"
                onClick={() => navigate('/signup')}
                size="large">
                회원가입
              </Button>
            </>
          )}
        </Toolbar>
      </AppBar>
    </Box>
  )
}

export default Header
