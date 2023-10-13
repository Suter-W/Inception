import Vue from 'vue'
import Vuex from 'vuex'
import user from './modules/user'

// 刷新页面 token 丢失问题
import createPersistedState from 'vuex-persistedstate'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    user
  },
  plugins: [createPersistedState({
    paths: ['user.token']
  })]
})
