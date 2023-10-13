import Vue from 'vue'
import SvgIcon from '@/components/SvgIcon'// svg component

// register globally
Vue.component('svg-icon', SvgIcon)

const req = require.context('./svg', false, /\.svg$/)
// webpack帮我们提供了一个依赖管理包 放在svg下的图标可直接用
// require.context('读取文件的路径', '是否遍历文件的子目录', '匹配文件的正则') 
// 查找 svg目录下 所有符合.svg格式的图片，不查找子目录
// 创建一个上下文模块 ===> 包含 目录下的所有模块 的引用
// requireContext.keys() // ===> 找出所有文件的路径
// console.log(req.keys());
// requireContext(路径) // ===> 根据路径找到模块
// console.log(req('./dashboard.svg'));
const keys = req.keys() // 找出所有文件的路径
keys.map(key => {
  req(key) // 根据路径找到模块
  console.log(req(key));
})


// const requireAll = requireContext => requireContext.keys().map(requireContext)
// requireAll(req)
