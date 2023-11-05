import request from '@/utils/request'

export const getDreamsApi = (data) => {
  return request({
    url: '/World/getDreamList',
    method: 'GET',
    params: data
  })
}

export const getDreamCommentListApi = (data) => {
  return request({
    url: '/World/getDreamCommentList',
    method: 'GET',
    params: data
  })
}

export const publishDreamApi = (data) => {
  return request({
    url: '/Publish/publishDream',
    method: 'POST',
    data: data
  })
}

export const getDreamLogApi = (data) => {
  return request({
    url: '/World/getDreamLog',
    method: 'GET',
    params: data
  })
}

export const getFavoriteDreamsApi = (data) => {
  return request({
    url: '/World/getFavoriteDreams',
    method: 'GET',
    params: data
  })
}

export const publishCommentApi = (data) => {
  return request({
    url: '/World/publishComment',
    method: 'POST',
    data: data
  })
}
