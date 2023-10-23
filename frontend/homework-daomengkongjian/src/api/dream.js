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
