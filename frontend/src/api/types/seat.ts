export type SeatResponse = {
  seats: Seat[]
}

export type Seat = {
  id: string
  name: string
  isUsed: boolean
}
