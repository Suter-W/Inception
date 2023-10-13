import router from '@/router'
export default {
  namespaced: true,
  state: {
    token: null
  },
  mutations: {
    updateToken (state, token) {
      state.token = token
    },
    logout (state) {
      state.token = null
      router.push('/login')
    }
  }
}
