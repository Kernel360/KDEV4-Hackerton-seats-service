import { Rectangle } from '@/components/main/Rectangle'
import { SeatInfo } from '@/components/main/SeatInfo'
import { useState } from 'react'
import { Seat } from '@/api/types/seat'
import NowOccupanyList from '@/components/main/NowOccupancyList'
import { DEFAULT_SEATS } from '@/components/main/util/SeatUtil'
import { Stack } from '@mui/material'

export default function Main() {
  const [seats, setSeats] = useState<Seat[]>(DEFAULT_SEATS)

  const door = () => (
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

  const seatCD = () => (
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
      {seats.map(seat =>
        seat.name === '좌석 C' ? (
          <SeatInfo
            key={seat.id}
            seatName={seat.name}
            isUsed={seat.isUsed}
          />
        ) : null
      )}
      {seats.map(seat =>
        seat.name === '좌석 D' ? (
          <SeatInfo
            key={seat.id}
            seatName={seat.name}
            isUsed={seat.isUsed}
          />
        ) : null
      )}
    </Stack>
  )

  const seatAB = () => (
    <Stack
      direction="column"
      spacing={2}
      width={'33%'}
      alignItems={'center'}
      gap={7}>
      {seats.map(seat =>
        seat.name === '좌석 A' ? (
          <SeatInfo
            key={seat.id}
            seatName={seat.name}
            isUsed={seat.isUsed}
          />
        ) : null
      )}
      {seats.map(seat =>
        seat.name === '좌석 B' ? (
          <SeatInfo
            key={seat.id}
            seatName={seat.name}
            isUsed={seat.isUsed}
          />
        ) : null
      )}
      <Rectangle
        width={'250px'}
        height={'150px'}>
        <button>디렉터님</button>
      </Rectangle>
    </Stack>
  )

  return (
    <>
      <Stack
        direction="row"
        spacing={2}
        p={5}>
        {door()}
        {seatCD()}
        {seatAB()}
      </Stack>
      <NowOccupanyList setSeats={setSeats} />
    </>
  )
}
