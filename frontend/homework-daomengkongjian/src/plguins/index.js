import { registerEle } from './comp/element'

// import { registerMap } from './baidu'
// import { registerFilter } from './filter'xw
// import { registerXXXX } from './xxxx'

export default {
  install (Vue) {
    registerEle(Vue)
    // registerMap(Vue)
    // registerFilter(Vue)
  }
}
