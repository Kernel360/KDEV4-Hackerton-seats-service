import dayjs from 'dayjs'

export const formatDate = (dateString: string) => {
  return dayjs(dateString).format('M월 D일 h:mm A')
}
