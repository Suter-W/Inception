import request from '@/utils/request'

export const loginApi = (data) => {
  return request({
    url: '/Login/login',
    method: 'POST',
    data: data
  })
}

export const registerApi = (data) => {
  return request({
    url: '/Login/register',
    method: 'POST',
    data: data
  })
}

export const getUserInfoApi = (data) => {
  return request({
    url: '/User/user',
    method: 'GET',
    params: data
  })
}
