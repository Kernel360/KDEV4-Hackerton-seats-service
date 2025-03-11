import MyOccupancy from '@/components/seat-occupancy/MyOccupancy'
import OccupancyTable from '@/components/seat-occupancy/OccupancyTable'
import { Button, Stack } from '@mui/material'
import { useState } from 'react'
import { OccupancyCardProps } from '@/components/seat-occupancy/OccupancyCard'

const MAX_OCCUPANCY = 2

export default function SeatOccupancy() {
  const [occupancyCards, setOccupancyCards] = useState<OccupancyCardProps[]>([])

  const handleDelete = (id: number) => {
    setOccupancyCards(prev => prev.filter(card => card.id !== id))
  }

  const handleAddOccupancy = (
    newOccupancy: Omit<OccupancyCardProps, 'onDelete'>
  ) => {
    setOccupancyCards(prev => {
      if (prev.length >= MAX_OCCUPANCY) {
        alert(`최대 ${MAX_OCCUPANCY}개의 예약만 가능합니다.`)
        return prev
      }
      return [...prev, { ...newOccupancy, onDelete: handleDelete }]
    })
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
        <OccupancyTable onOccupancy={handleAddOccupancy} />
      </Stack>
      <Stack
        direction="row"
        spacing={2}
        width={'35%'}>
        <MyOccupancy
          occupancyCards={occupancyCards}
          onDelete={handleDelete}
        />
      </Stack>
    </Stack>
  )
}
