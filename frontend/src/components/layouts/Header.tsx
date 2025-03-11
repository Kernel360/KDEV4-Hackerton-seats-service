import { isLogin, logout } from '@/auth/auth'
import { AppBar, Button, Stack, Toolbar, Typography } from '@mui/material'
import Box from '@mui/material/Box'
import { useNavigate } from 'react-router-dom'

export default function Header() {
  const navigate = useNavigate()

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

          {isLogin() ? (
            <>
              <Button
                color="inherit"
                onClick={() => {
                  logout()
                  navigate('/')
                }}>
                로그아웃
              </Button>
            </>
          ) : (
            <>
              <Button
                color="inherit"
                onClick={() => navigate('/signin')}>
                로그인
              </Button>
              <Button
                color="inherit"
                onClick={() => navigate('/signup')}>
                회원가입
              </Button>
            </>
          )}
        </Toolbar>
      </AppBar>
    </Box>
  )
}
