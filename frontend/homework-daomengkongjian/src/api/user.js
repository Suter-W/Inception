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

export const updateNameApi = (data) => {
  return request({
    url:'Upload/uploadName',
    method: 'POST',
    data:data
  })
}

export const updateSignatureApi = (data) => {
  return request({
    url:'Upload/uploadSignature',
    method: 'POST',
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

export const isFollowApi = (data) => {
  return request({
    url: '/Follow/isFollow',
    method: 'GET',
    params: data
  })
}

export const followApi = (data) => {
  return request({
    url: '/Follow/follow',
    method: 'POST',
    params: data,
  })
}

export const deleteFollowApi = (data) => {
  return request({
    url: '/Follow/cancelFollow',
    method: 'POST',
    params: data,
  })
}

export const getFolloweesApi = (data) => {
  return request({
    url: 'Follow/getFollowList',
    method: 'POST',
    params: data,
  })
}

export const getFollowersApi = (data) => {
  return request({
    url: 'Follow/getFanList',
    method: 'POST',
    params: data,
  })
}

export const getShowUserLogDreamsApi = (data) => {
  return request({
    url:'World/getHostDreams',
    method:'POST',
    params:data,
  })
}