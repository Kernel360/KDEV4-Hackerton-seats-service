import { useEffect, useState } from 'react'
import { Typography, Stack } from '@mui/material'
import OccupancyCard from './OccupancyCard'
import api from '@/api/api'
import dayjs from 'dayjs'

export type OccupancyCardProps = {
  id: number
  seatName: string
  startTime: string
  formattedStartTime: any
  formattedDate?: string
  formattedTimeRange?: string
  onDelete: (id: number) => void
}

export default function MyOccupancy() {
  const [occupancyCards, setOccupancyCards] = useState<OccupancyCardProps[]>([])

  const getUserOccupancy = async () => {
    const response = await api.get(`/seats/occupancy/my`)
    setOccupancyCards(response.data)
  }

  const onDelete = async (seatId: number) => {
    try {
      await api.delete(`/seats/occupancy/${seatId}`)
      setOccupancyCards(prev => prev.filter(card => card.id !== seatId))
    } catch (error) {
      console.error('삭제 실패:', error)
    }
  }

  useEffect(() => {
    getUserOccupancy()
  }, [])

  return (
    <Stack
      spacing={3}
      width={'100%'}>
      <Typography variant="h5">내 예약 현황</Typography>
      <Stack
        direction="row"
        spacing={2}
        flexWrap="wrap"
        justifyContent="space-between">
        {occupancyCards.map(card => {
          const start = dayjs(card.startTime)
          const end = start.add(1, 'hour')

          const formattedDate = start.format('M월 D일')
          const formattedTimeRange = `${start.format('h:mm A')} ~ ${end.format('h:mm A')}`

          return (
            <div
              key={card.id}
              style={{ width: '45%', margin: '10px' }}>
              <OccupancyCard
                id={card.id}
                seatName={card.seatName}
                startTime={card.startTime}
                formattedStartTime={
                  <span style={{ whiteSpace: 'pre-line' }}>
                    {formattedDate}
                    {'\n'}
                    {formattedTimeRange}
                  </span>
                }
                onDelete={onDelete}
              />
            </div>
          )
        })}
      </Stack>
    </Stack>
  )
}
