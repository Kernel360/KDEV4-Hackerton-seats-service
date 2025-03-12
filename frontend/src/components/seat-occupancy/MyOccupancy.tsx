import { useEffect, useState } from 'react'
import { Typography, Stack } from '@mui/material'
import OccupancyCard from './OccupancyCard'
import api from '@/api/api'
import dayjs from 'dayjs'

export type OccupancyCardProps = {
  seatId: number
  seatName: string
  startTime: string
  formattedStartTime: any
  formattedDate?: string
  formattedTimeRange?: string
  onDelete: (seatId: number, startTime: string) => void
}

export default function MyOccupancy({
  isReservationSuccess,
  setIsReservationSuccess
}: {
  isReservationSuccess: boolean
  setIsReservationSuccess: (isReservationSuccess: boolean) => void
}) {
  const [occupancyCards, setOccupancyCards] = useState<OccupancyCardProps[]>([])

  const getUserOccupancy = async () => {
    const response = await api.get(`/seats/occupancy/my`)
    setOccupancyCards(response.data)
  }

  // 초기 로딩 시 한 번만 실행
  useEffect(() => {
    getUserOccupancy()
  }, [])

  // isReservationSuccess가 변경될 때만 실행
  useEffect(() => {
    getUserOccupancy()
  }, [isReservationSuccess])

  const onDelete = async (seatId: number, startTime: string) => {
    try {
      await api.delete(`/seats/occupancy`, {
        data: {
          seatId,
          startTime
        }
      })

      setOccupancyCards(prev => prev.filter(card => card.seatId !== seatId))
      setIsReservationSuccess(!isReservationSuccess)
    } catch (error) {
      console.error('삭제 실패:', error)
    }
  }

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
              key={card.seatId}
              style={{ width: '45%', margin: '10px' }}>
              <OccupancyCard
                seatId={card.seatId}
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
