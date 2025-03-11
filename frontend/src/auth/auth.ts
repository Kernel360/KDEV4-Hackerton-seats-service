export const isLogin = () => {
  const accessToken = getAccessToken()
  return accessToken !== null
}

export const logout = () => {
  localStorage.removeItem('accessToken')
}

function getAccessToken() {
  return localStorage.getItem('accessToken')
}
