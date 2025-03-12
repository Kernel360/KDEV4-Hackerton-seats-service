import MyOccupancy from '@/components/seat-occupancy/MyOccupancy'
import { Button, Stack } from '@mui/material'

export default function SeatOccupancy() {
  const dateFilter = () => {
    return (
      <Stack
        direction="row"
        spacing={2}>
        <Button variant="outlined">오늘</Button>
        <Button variant="outlined">내일</Button>
      </Stack>
    )
  }

  return (
    <Stack
      p={3}
      gap={2}
      direction="row">
      <Stack
        spacing={2}
        width={'65%'}>
        {dateFilter()}
        {/* <OccupancyTable /> */}
      </Stack>
      <Stack
        direction="row"
        spacing={2}
        width={'35%'}>
        <MyOccupancy />
      </Stack>
    </Stack>
  )
}
