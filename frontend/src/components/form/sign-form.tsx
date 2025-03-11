import { useState } from 'react'
import {
  Button,
  Card,
  CardContent,
  CardHeader,
  Divider,
  TextField,
  Alert,
  Snackbar,
  Stack
} from '@mui/material'
import { postSignIn, postSignUp } from '@/api/api'
import { useNavigate } from 'react-router-dom'

type SignFormType = 'signin' | 'signup'

type SignFormProps = {
  type: SignFormType
  api: typeof postSignIn | typeof postSignUp
}

const SIGN_FORM_TEXT = {
  signin: '로그인',
  signup: '회원가입'
} as const

const getSignFormText = (type: SignFormType) => SIGN_FORM_TEXT[type]

export default function SignForm({ type, api }: SignFormProps) {
  const navigate = useNavigate()
  const [id, setId] = useState('')
  const [password, setPassword] = useState('')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const handleSubmit = async () => {
    if (!id || !password) {
      setError('아이디와 비밀번호를 입력해주세요.')
      return
    }

    try {
      setLoading(true)
      setError(null)

      await api(id, password)
      navigate('/')
    } catch (err) {
      setError(err instanceof Error ? err.message : '오류가 발생했습니다.')
    } finally {
      setLoading(false)
    }
  }

  const handleIdChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setId(e.target.value)
  }

  const handlePasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value)
  }

  const handleCloseError = () => {
    setError(null)
  }

  return (
    <Card>
      <CardHeader title={getSignFormText(type)} />
      <Divider />
      <CardContent
        sx={{
          display: 'flex',
          flexDirection: 'column',
          gap: 2,
          mt: 2,
          mx: 2
        }}>
        <TextField
          label="아이디"
          value={id}
          onChange={handleIdChange}
          disabled={loading}
        />
        <TextField
          label="비밀번호"
          type="password"
          value={password}
          onChange={handlePasswordChange}
          disabled={loading}
        />
        <Button
          onClick={handleSubmit}
          variant="contained"
          disabled={loading}
          sx={{ mt: 2 }}>
          {loading ? '처리중...' : getSignFormText(type)}
        </Button>
      </CardContent>
      <Stack>
        <Snackbar
          open={!!error}
          autoHideDuration={3000}
          onClose={handleCloseError}>
          <Alert
            onClose={handleCloseError}
            severity="error"
            sx={{ width: '100%' }}>
            {error}
          </Alert>
        </Snackbar>
      </Stack>
    </Card>
  )
}
