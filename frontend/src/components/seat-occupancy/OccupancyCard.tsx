import { Button } from '@mui/material'

import { Card, CardContent, Stack, Typography } from '@mui/material'

export type OccupancyCardProps = {
  id: number
  name: string
  startTime: string
  endTime: string
  onDelete: (id: number) => void
}

export default function OccupancyCard({
  id,
  name,
  startTime,
  endTime,
  onDelete
}: OccupancyCardProps) {
  const handleDelete = () => {
    onDelete(id)
  }

  return (
    <Card sx={{ width: '50%' }}>
      <CardContent sx={{ display: 'flex', flexDirection: 'column', gap: 1.5 }}>
        <Typography variant="h6">{name}</Typography>
        <Typography variant="body2">
          예약 시간: {startTime} ~ {endTime}
        </Typography>
        <Stack
          direction="row"
          spacing={2}>
          <Button
            variant="outlined"
            onClick={handleDelete}
            fullWidth>
            예약 취소
          </Button>
        </Stack>
      </CardContent>
    </Card>
  )
}
