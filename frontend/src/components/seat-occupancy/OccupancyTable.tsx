import {
  TableBody,
  Table,
  TableHead,
  TableRow,
  Card,
  Button
} from '@mui/material'
import { TableCell } from '@mui/material'
import { OccupancyCardProps } from './OccupancyCard'
import { useEffect, useState } from 'react'
import { getOccupancyList } from '@/api/api'
import { Occupancy } from '@/api/types/occupancy'

type OccupancyTableProps = {
  onOccupancy: (newOccupancy: Omit<OccupancyCardProps, 'onDelete'>) => void
  existingReservations?: OccupancyCardProps[]
}

const TABLES = ['테이블 A', '테이블 B', '테이블 C', '테이블 D'] as const
type TableName = (typeof TABLES)[number]

const TIME_SLOTS = [
  { startTime: '9:00', endTime: '10:00' },
  { startTime: '10:00', endTime: '11:00' },
  { startTime: '11:00', endTime: '12:00' },
  { startTime: '12:00', endTime: '13:00' },
  { startTime: '13:00', endTime: '14:00' },
  { startTime: '14:00', endTime: '15:00' },
  { startTime: '15:00', endTime: '16:00' },
  { startTime: '16:00', endTime: '17:00' },
  { startTime: '17:00', endTime: '18:00' },
  { startTime: '18:00', endTime: '19:00' },
  { startTime: '19:00', endTime: '20:00' }
] as const

export default function OccupancyTable({
  onOccupancy,
  existingReservations = []
}: OccupancyTableProps) {
  const [occupancyList, setOccupancyList] = useState<Occupancy[]>([])

  useEffect(() => {
    const fetchOccupancyList = async () => {
      try {
        const response = await getOccupancyList()
        setOccupancyList(response.occupancyList)
      } catch (error) {
        console.error('Failed to fetch occupancy list:', error)
      }
    }

    fetchOccupancyList()
  }, [])

  function isTimeSlotReserved(tableName: TableName, startTime: string) {
    // API에서 받은 예약 데이터 확인
    const isOccupied = occupancyList.some(
      occupancy =>
        occupancy.seatName === tableName &&
        occupancy.startTime.includes(startTime)
    )

    // 사용자의 예약 데이터 확인
    const isUserReserved = existingReservations.some(
      res => res.name === tableName && res.startTime === startTime
    )

    return isOccupied || isUserReserved
  }

  function handleOccupancy(
    tableName: TableName,
    startTime: string,
    endTime: string
  ) {
    if (isTimeSlotReserved(tableName, startTime)) {
      alert('이미 예약된 시간입니다.')
      return
    }

    onOccupancy({
      id: Date.now(),
      name: tableName,
      startTime,
      endTime
    })
  }

  const tableHeader = () => (
    <TableHead>
      <TableRow>
        <TableCell>시간</TableCell>
        {TABLES.map(tableName => (
          <TableCell key={tableName}>{tableName}</TableCell>
        ))}
      </TableRow>
    </TableHead>
  )

  const tableBody = () => (
    <TableBody>
      {TIME_SLOTS.map((row, index) => (
        <TableRow key={index}>
          <TableCell>
            {row.startTime} ~ {row.endTime}
          </TableCell>
          {TABLES.map(tableName => (
            <TableCell key={`${tableName}-${row.startTime}`}>
              <Button
                sx={{ width: '80px' }}
                variant="outlined"
                disabled={isTimeSlotReserved(tableName, row.startTime)}
                onClick={() =>
                  handleOccupancy(tableName, row.startTime, row.endTime)
                }>
                {isTimeSlotReserved(tableName, row.startTime)
                  ? '예약중'
                  : '예약'}
              </Button>
            </TableCell>
          ))}
        </TableRow>
      ))}
    </TableBody>
  )

  return (
    <Card>
      <Table size="medium">
        {tableHeader()}
        {tableBody()}
      </Table>
    </Card>
  )
}
