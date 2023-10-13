const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave:false,

  devServer: {
    // 代理配置
     proxy: {
         '/api': {
             target: 'https://likede2-admin.itheima.net/likede/', // 我们要代理的地址
             changeOrigin: true, // 是否跨域 需要设置此值为true 才可以让本地服务代理我们发出请求
           }
       }
   }
})
