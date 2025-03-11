import axios from 'axios'

const API_URL = import.meta.env.VITE_REST_SERVER

const api = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json'
  },
  withCredentials: true
})

// 로그인
export const postSignIn = async (name: string, password: string) => {
  const response = await api.post('/user/signin', { name, password })
  const { accessToken } = response.data
  localStorage.setItem('accessToken', accessToken) // 로그인 후 JWT 토큰을 로컬스토리지에 저장
}

// 회원가입
export const postSignUp = async (name: string, password: string) => {
  const response = await api.post('/user/signup', { name, password })
  const { accessToken } = response.data
  localStorage.setItem('accessToken', accessToken) // 회원가입 후 JWT 토큰을 로컬스토리지에 저장
}

// JWT 토큰을 로컬스토리지에서 가져오는 함수
const getAuthToken = () => {
  return localStorage.getItem('accessToken') // 로컬스토리지에 JWT 토큰을 저장한다고 가정
}

// 로그인 여부 확인
export const isLogin = (): boolean => {
  const accessToken = localStorage.getItem('accessToken')
  return accessToken !== null && accessToken !== ''
}

// 로그아웃
export const logout = () => {
  localStorage.removeItem('accessToken')
}

// 요청을 보낼 때마다 토큰을 Authorization 헤더에 포함시킴
api.interceptors.request.use(
  config => {
    const accessToken = getAuthToken()
    if (accessToken) {
      config.headers['Authorization'] = `Bearer ${accessToken}`
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 로그인 확인을 위한 함수
export const checkLogin = () => {
  if (!isLogin()) {
    throw new Error('로그인 필요')
  }
}

export default api
