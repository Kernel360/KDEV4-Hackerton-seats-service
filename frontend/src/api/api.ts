import { OccupancyResponse } from '@/api/types/occupancy'
import { SignInResponse, SignUpResponse } from './types/user'
import axios from 'axios'

const API_BASE_URL = 'http://localhost:8080'

export interface ErrorResponse {
  message: string
}

export const getOccupancyList = async (): Promise<OccupancyResponse> => {
  const data: OccupancyResponse = {
    occupancyList: [
      {
        seatId: 1,
        seatName: '테이블 A',
        startTime: '2024-03-20 10:00',
        endTime: '2024-03-20 12:00'
      },
      {
        seatId: 2,
        seatName: '테이블 B',
        startTime: '2024-03-20 14:00',
        endTime: '2024-03-20 16:00'
      }
    ]
  }
  // 실제 API 호출을 시뮬레이션하기 위한 지연
  await new Promise(resolve => setTimeout(resolve, 500))
  return data
}

export const postSignUp = async (
  id: string,
  password: string
): Promise<SignUpResponse> => {
  const data: SignUpResponse = {
    accessToken: '1234567890'
  }
  return data
}

export const postSignIn = async (
  id: string,
  password: string
): Promise<SignInResponse> => {
  try {
    const response = await axios.post<SignInResponse>(
      `${API_BASE_URL}/auth/signin`,
      {
        id,
        password
      }
    )

    // 토큰을 로컬 스토리지에 저장
    localStorage.setItem('accessToken', response.data.accessToken)

    return response.data
  } catch (error) {
    if (axios.isAxiosError(error) && error.response) {
      const errorData = error.response.data as ErrorResponse
      throw new Error(errorData.message || '로그인에 실패했습니다.')
    }
    throw new Error('서버와의 통신에 실패했습니다.')
  }
}
