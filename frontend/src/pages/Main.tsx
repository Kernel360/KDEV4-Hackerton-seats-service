import { getSeats } from '@/api/api'
import { Rectangle } from '@/components/main/rectangle'
import { SeatInfo } from '@/components/main/seat-info'
import { useEffect } from 'react'
import { Button, Card, CardContent, Stack } from '@mui/material'
import { useState } from 'react'
import { Seat } from '@/api/types/seat'

const DEFAULT_SEATS = [
  {
    id: '1',
    name: '좌석 A',
    isUsed: false
  },
  {
    id: '2',
    name: '좌석 B',
    isUsed: false
  },
  {
    id: '3',
    name: '좌석 C',
    isUsed: false
  },
  {
    id: '4',
    name: '좌석 D',
    isUsed: false
  }
]

export default function Main() {
  const [seats, setSeats] = useState<Seat[]>([])
  useEffect(() => {
    const fetchSeats = async () => {
      const seats = await getSeats()
      setSeats(seats.seats)
    }
    fetchSeats()
  }, [])

  function door() {
    return (
      <Stack
        direction="column"
        spacing={2}
        alignItems={'center'}
        width={'33%'}>
        <Rectangle
          width={'150px'}
          height={'150px'}>
          <button>출입문</button>
        </Rectangle>
      </Stack>
    )
  }

  function seatCD() {
    return (
      <Stack
        direction="column"
        spacing={2}
        width={'33%'}
        gap={7}>
        <Rectangle
          width={'400px'}
          height={'80px'}>
          <button>옷걸이</button>
        </Rectangle>
        <SeatInfo
          seatName="좌석 C"
          isUsed={false}
        />
        <SeatInfo
          seatName="좌석 D"
          isUsed={true}
        />
      </Stack>
    )
  }

  function seatAB() {
    return (
      <Stack
        direction="column"
        spacing={2}
        width={'33%'}
        alignItems={'center'}
        gap={7}>
        <SeatInfo
          seatName="좌석 A"
          isUsed={true}
        />
        <SeatInfo
          seatName="좌석 B"
          isUsed={false}
        />
        <Rectangle
          width={'250px'}
          height={'150px'}>
          <button>디렉터님</button>
        </Rectangle>
      </Stack>
    )
  }

  return (
    <Stack
      direction="row"
      spacing={2}
      p={5}>
      {door()}
      {seatCD()}
      {seatAB()}
    </Stack>
  )
}
