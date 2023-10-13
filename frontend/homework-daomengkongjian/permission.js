// 登录权限
// 根据token来判断 用户是否登录
// 结合路由全局前置守卫
// router.beforeEach((to, from, next) => {
//   // to 目标
//   // from 源头
//   // next 回调函数
// })

// token 存在 放行
// token 不存在 去登录
// 在白名单吗 ==> 放行
// 去登录
import router from '@/router'
import store from '@/store'
const whiteList = ['/login','/register']

router.beforeEach((to, from, next) => {
  console.log(to)
  const token = store.state.user.token
  if (token) {
    next()
  } else {
    if (whiteList.includes(to.path)) {
      next()
    } else {
      next('/login')
    }
  }
})
