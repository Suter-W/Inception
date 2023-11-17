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

export const updateAvatarApi = (data) => {
  return request({
    url:'Upload/upload',
    method: 'POST',
    headers: {
      'Content-Type': 'multipart/form-data' // 设置请求头的 Content-Type 为 multipart/form-data
    },
    data:data
  })
}

export const getUserInfoApi = (data) => {
  return request({
    url: '/User/user',
    method: 'GET',
    params: data
  })
}
