import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

// 路由配置 5+2
// 2 ==> 确定 路由规则 + 路由懒加载
//   ==> 确定出口 router-view

// 1 login layout
// 2 article dashboard

const originalPush = VueRouter.prototype.push

VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

const routes = [
  {
    path: '/login',
    component: () => import('@/views/login')
  },
  {
    path: '/register',
    component: () => import('@/views/register')
  },
  {
    path: '/',
    component: () => import('@/views/layout'),
    redirect: '/home',
    children: [
      {
        path: '/home',
        name:'home',
        component: () => import('@/views/home')
      },
      {
        path: '/addlog',
        name:'addlog',
        component: () => import('@/views/addlog')
      },
      {
        path: '/log',
        name:'log',
        component: () => import('@/views/log')
      },
      {
        path: '/collect',
        name:'collect',
        component: () => import('@/views/collect')
      },
      {
        path: '/setting',
        name:'setting',
        component: () => import('@/views/setting'),
        redirect: '/setting/mine',
        children: [
          {
            path: '/setting/infoset',
            name:'infoset',
            component: () => import('@/views/setting/children/infoset')
          },
          {
            path: '/setting/message',
            name:'message',
            component: () => import('@/views/setting/children/message')
          },
          {
            path: '/setting/mine',
            name:'mine',
            component: () => import('@/views/setting/children/mine')
          },
        ]
      }
    ]
  }
]

const router = new VueRouter({
  routes
})

export default router
