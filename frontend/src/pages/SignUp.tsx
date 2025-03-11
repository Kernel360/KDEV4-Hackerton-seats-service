import { postSignUp } from '@/api/api'
import SignForm from '@/components/form/sign-form'
import { Stack } from '@mui/material'
import { useNavigate } from 'react-router-dom'

export default function SignUp() {
  const navigate = useNavigate()

  return (
    <Stack
      p={3}
      gap={2}
      direction="row"
      justifyContent="center">
      <SignForm
        type="signup"
        api={postSignUp}
      />
    </Stack>
  )
}
