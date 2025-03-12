import { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { logout, isLogin } from '@/auth/auth'
import { AppBar, Button, Toolbar, Typography } from '@mui/material'
import Box from '@mui/material/Box'

const Header = () => {
  const navigate = useNavigate()

  const [loggedIn, setLoggedIn] = useState<boolean>(isLogin())

  useEffect(() => {
    const handleStorageChange = () => {
      setLoggedIn(isLogin())
    }

    window.addEventListener('storage', handleStorageChange)

    return () => {
      window.removeEventListener('storage', handleStorageChange)
    }
  }, [])

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar>
          <div style={{ flexGrow: 1, justifyContent: 'start' }}>
            <Button
              onClick={() => navigate('/')}
              variant="text"
              color="inherit">
              <Typography variant="h6">KERNEL360 회의실 예약</Typography>
            </Button>
          </div>

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
                  logout()
                  setLoggedIn(false)
                  navigate('/')
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
