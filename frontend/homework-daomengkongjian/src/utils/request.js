// 下/引入包 axios
import axios from 'axios'
import store from '@/store'
import { Message } from 'element-ui'
// 创建 axios实例 baseURL
const request = axios.create({
  baseURL: 'http://localhost:8088',
  timeout: 5000
})
// axios实例 拦截器
// token失效  ==> 注入token
request.interceptors.request.use((config) => {
  const token = store.state.user.token
  // Authorization
  // Bearer {{token}}
  // console.log(config.headers);
  config.headers.Authorization = `${token}`

  return config
}, err => {
  return Promise.reject(err)
})

// 对响应错误做点什么  普通错误 + 401情况
// 401 怎么做
request.interceptors.response.use(response => {
  return response.data
}, err => {
  console.log(err) // 401
  if (err.response.status === 401) {
    Message.error('尊敬的用户，当前登录状态已过期！')
    store.commit('user/logout')
    // 401
    // 提示 token 失效
    // token 清空
    // 去login
  } else {
    Message.error(err.response.data.message)
  }

  return Promise.reject(err)
})
// 导出axios实例
export default request
