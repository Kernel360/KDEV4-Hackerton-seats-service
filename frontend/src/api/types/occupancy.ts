export type Occupancy = {
  seatId: number
  seatName: string
  startTime: string
  endTime: string
}

export type OccupancyResponse = {
  occupancyList: Occupancy[]
}
