import { Typography, Stack } from '@mui/material'
import OccupancyCard from './OccupancyCard'
import { OccupancyCardProps } from './OccupancyCard'

type MyOccupancyProps = {
  occupancyCards: OccupancyCardProps[]
  onDelete: (id: number) => void
}

export default function MyOccupancy({
  occupancyCards,
  onDelete
}: MyOccupancyProps) {
  return (
    <Stack
      spacing={2}
      width={'100%'}>
      <Typography variant="h5">내 예약 현황</Typography>
      <Stack
        direction="row"
        spacing={2}>
        {occupancyCards.map(card => (
          <OccupancyCard
            key={card.id}
            id={card.id}
            name={card.name}
            startTime={card.startTime}
            endTime={card.endTime}
            onDelete={onDelete}
          />
        ))}
      </Stack>
    </Stack>
  )
}
