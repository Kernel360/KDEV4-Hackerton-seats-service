import { Stack, StackProps } from '@mui/material'

export function Rectangle({ children, ...props }: StackProps) {
  return (
    <Stack
      {...props}
      border={'1px solid black'}
      justifyContent={'center'}
      alignItems={'center'}>
      {children}
    </Stack>
  )
}
