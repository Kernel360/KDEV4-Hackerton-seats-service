import axios from 'axios'
import { DEFAULT_SEATS } from './util/SeatUtil'
import { useEffect } from 'react'
import { Seat } from '@/api/types/seat'

const API_URL = import.meta.env.VITE_REST_SERVER

// 예약된 좌석 정보 타입 정의
interface Occupancy {
  seatId: number
  userId: number
  seatName: string
  startTime: string
}

export default function NowOccupanyList({
  setSeats
}: {
  setSeats: React.Dispatch<React.SetStateAction<Seat[]>>
}) {
  const handleNowOccupanyList = async () => {
    try {
      const response = await axios.get(`${API_URL}/seats/occupancy/now`)
      const occupancyData: Occupancy[] = response.data

      // 좌석 사용 여부 업데이트
      const updatedSeats = DEFAULT_SEATS.map(seat => {
        const isUsed = occupancyData.some(
          occupancy => occupancy.seatId === parseInt(seat.id)
        )
        return {
          ...seat,
          isUsed
        }
      })

      setSeats(updatedSeats)
    } catch (error) {
      console.error('현재 예약 현황 ERORR : ', error)
    }
  }

  useEffect(() => {
    handleNowOccupanyList()
  }, [])

  return <></>
}
