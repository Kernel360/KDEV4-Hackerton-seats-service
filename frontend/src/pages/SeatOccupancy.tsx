import MyOccupancy from '@/components/seat-occupancy/MyOccupancy'
import OccupancyTable from '@/components/seat-occupancy/OccupancyTable'
import { Button, Stack } from '@mui/material'
import { useState } from 'react'

export default function SeatOccupancy() {
  const [isReservationSuccess, setIsReservationSuccess] = useState(false)

  const handleReservationSuccess = () => {
    setIsReservationSuccess(true)
  }

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
        <OccupancyTable
          onReservationSuccess={handleReservationSuccess}
          setIsReservationSuccess={setIsReservationSuccess}
        />
      </Stack>
      <Stack
        direction="row"
        spacing={2}
        width={'35%'}>
        <MyOccupancy
          isReservationSuccess={isReservationSuccess}
          setIsReservationSuccess={setIsReservationSuccess}
        />
      </Stack>
    </Stack>
  )
}
