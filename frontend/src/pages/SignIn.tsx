import SignForm from '@/components/form/sign-form'
import { Stack } from '@mui/material'
import { postSignIn } from '@/api/api'
export default function SignIn() {
  return (
    <Stack
      p={3}
      gap={2}
      direction="row"
      justifyContent="center">
      <SignForm
        type="signin"
        api={postSignIn}
      />
    </Stack>
  )
}
