interface SeatInfoProps {
  seatName: string
  isUsed: boolean
}

const SeatInfo: React.FC<SeatInfoProps> = ({ seatName, isUsed }) => {
  return (
    <div
      style={{
        backgroundColor: isUsed ? 'red' : 'green', // 예약된 좌석은 빨간색, 아닌 좌석은 초록색
        padding: '20px',
        margin: '10px',
        color: 'white',
        textAlign: 'center'
      }}>
      {seatName}
    </div>
  )
}

export default SeatInfo
