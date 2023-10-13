import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import plguins from '@/plguins'

import '@/styles/index.scss'
import '../permission.js'
import { library } from '@fortawesome/fontawesome-svg-core'

/* import font awesome icon component */
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

import { fas } from '@fortawesome/free-solid-svg-icons'
import { far } from '@fortawesome/free-regular-svg-icons'
import { fab } from '@fortawesome/free-brands-svg-icons'
/* import specific icons */
import { faUserSecret } from '@fortawesome/free-solid-svg-icons'

/* add icons to the library */
library.add(faUserSecret,far,fab,fas)

/* add font awesome icon component */
Vue.component('font-awesome-icon', FontAwesomeIcon)

Vue.config.productionTip = false

Vue.use(plguins)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
