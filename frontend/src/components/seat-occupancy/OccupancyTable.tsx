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
//import { getOccupancyList } from '@/api/api'
import { Occupancy } from '@/api/types/occupancy'
import api from '@/api/api'

type Seat = {
  seatId: number
  seatName: string
}

type SeatStatus = {
  seatId: number
  seatName: string
  occupancy: boolean
}

type TableHeader = {
  time: string
  seatA: Seat
  seatB: Seat
  seatC: Seat
  seatD: Seat
}

type TableData = {
  date: string
  startTime: string
  endTime: string
  seatA: SeatStatus
  seatB: SeatStatus
  seatC: SeatStatus
  seatD: SeatStatus
}

type OccupancyTableProps = {
  reservationSuccess: boolean
  onReservationSuccess: () => void
}

export default function OccupancyTable({
  reservationSuccess,
  onReservationSuccess
}: OccupancyTableProps) {
  const [tableHeaderData, setTableHeaderData] = useState<TableHeader>()
  const [tableData, setTableData] = useState<TableData[]>([])

  const fetchOccupancyList = async () => {
    try {
      const response = await api.get('/seats/occupancy')
      setTableHeaderData(response.data.tableHeader)
      setTableData(response.data.tableData)
      //const response = await getOccupancyList()
      //setOccupancyList(response.occupancyList)
    } catch (error) {
      console.error(error)
    }
  }

  useEffect(() => {
    fetchOccupancyList()
  }, [])

  useEffect(() => {
    fetchOccupancyList()
  }, [reservationSuccess])

  const handleReserve = async (
    seatId: number,
    date: string,
    startTime: string
  ) => {
    try {
      const response = await api.post('/seats/occupancy', {
        seatId,
        startTime: `${date} ${startTime}`
      })

      // 예약 성공 시 onReservationSuccess 호출
      onReservationSuccess()
      fetchOccupancyList()
    } catch (error) {
      console.error(error)
    }
  }

  const tableHeader = () => (
    <TableHead>
      <TableRow>
        <TableCell>{tableHeaderData?.time}</TableCell>
        <TableCell>{tableHeaderData?.seatA.seatName}</TableCell>
        <TableCell>{tableHeaderData?.seatB.seatName}</TableCell>
        <TableCell>{tableHeaderData?.seatC.seatName}</TableCell>
        <TableCell>{tableHeaderData?.seatD.seatName}</TableCell>
      </TableRow>
    </TableHead>
  )

  const tableBody = () => (
    <TableBody>
      {tableData.map((row, index) => (
        <TableRow key={index}>
          <TableCell>
            {row.startTime} ~ {row.endTime}
          </TableCell>
          <TableCell>
            {reserveButton(row.seatA, row.date, row.startTime)}
          </TableCell>
          <TableCell>
            {reserveButton(row.seatB, row.date, row.startTime)}
          </TableCell>
          <TableCell>
            {reserveButton(row.seatC, row.date, row.startTime)}
          </TableCell>
          <TableCell>
            {reserveButton(row.seatD, row.date, row.startTime)}
          </TableCell>
        </TableRow>
      ))}
    </TableBody>
  )

  const reserveButton = (seat: SeatStatus, date: string, startTime: string) => (
    <Button
      variant="outlined"
      disabled={seat.occupancy}
      sx={{ width: '100px' }}
      onClick={() => handleReserve(seat.seatId, date, startTime)}>
      {seat.occupancy ? '예약 불가' : '예약'}
    </Button>
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
