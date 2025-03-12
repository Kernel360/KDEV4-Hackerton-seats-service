import { Stack, Typography } from '@mui/material'
import { Rectangle } from './Rectangle'

type SeatInfoProps = {
  seatName: string
  isUsed: boolean
}

export function SeatInfo({ seatName, isUsed }: SeatInfoProps) {
  return (
    <Rectangle
      width={'250px'}
      height={'150px'}
      sx={{
        backgroundColor: isUsed ? 'rgb(255, 160, 160)' : 'rgb(152, 251, 152)'
      }}>
      <Stack
        alignItems={'center'}
        justifyContent={'center'}>
        <Typography variant="h6">{seatName}</Typography>
        <Typography
          variant="h6"
          color={isUsed ? 'error' : 'success'}>
          {isUsed ? '사용중' : '예약 가능'}
        </Typography>
      </Stack>
    </Rectangle>
  )
}
