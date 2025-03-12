import { Button } from '@mui/material'
import { Card, CardContent, Stack, Typography } from '@mui/material'
import { OccupancyCardProps } from './MyOccupancy'
export default function OccupancyCard({
  id,
  seatName,
  formattedStartTime,
  onDelete
}: OccupancyCardProps) {
  const handleDelete = () => {
    onDelete(id)
  }

  return (
    <Card sx={{ width: '100%' }}>
      <CardContent sx={{ display: 'flex', flexDirection: 'column', gap: 1.5 }}>
        <Typography variant="h6">{seatName}</Typography>
        <Typography variant="body2">{formattedStartTime}</Typography>
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
