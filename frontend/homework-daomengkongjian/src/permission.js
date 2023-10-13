import router from '@/router'
import store from '@/store'

const whiteList = ['login', '404','register']

router.beforeEach((to, from, next) => {
  const token = store.state.user.token
  console.log(token)
  if (token) {
    next(to.path)
  } else {
    if (whiteList.includes(to.path)) {
      next()
    } else {
      next('/login')
    }
  }
})
