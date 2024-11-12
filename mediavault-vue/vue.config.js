const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,

  devServer: {
    // host
    host: '0.0.0.0',
    // 端口
    port: 80,
    // 配置代理解决跨域
    proxy: {
      '/api': {
        target: 'http://172.10.30.20:8080',
        changeOrigin: true,
        pathRewrite: {
          '^/api': ''
        }
      }
    }
  }
  

})