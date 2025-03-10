import { AppBar, Button, Toolbar, Typography } from '@mui/material'
import Box from '@mui/material/Box'

export default function Header() {
  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar>
          <Typography
            variant="h6"
            component="div"
            sx={{ flexGrow: 1 }}>
            KERNEL360 회의실 예약
          </Typography>

          {/* 로그인 여부에 따라 달라짐 */}
          <Button color="inherit">로그인</Button>
          <Button color="inherit">회원가입</Button>
        </Toolbar>
      </AppBar>
    </Box>
  )
}
