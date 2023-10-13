import request from '@/utils/request'

export const loginApi = (data) => {
  return request({
    url: '/user-service/user/login',
    method: 'POST',
    data
  })
}
