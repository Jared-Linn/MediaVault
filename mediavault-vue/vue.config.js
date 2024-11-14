const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  pages: {
    index: {
      entry: 'src/main.ts',

    },
  },

  devServer: {
    host: '0.0.0.0',
    port: 8000,
    proxy: {

      '/api': {
        // target: "http://103.207.68.161:6741",
        // target: "http://172.10.30.20:8081",
        target: "http://172.10.30.20:5000",
        changeOrigin: true,
        pathRewrite: {
          '^/api': ''
        }
      },


      '/aliyun': {
        target: 'https://mediavault.oss-cn-beijing.aliyuncs.com',
        changeOrigin: true,
        pathRewrite: {
          '^/aliyun': ''
        }
      }
    }
  }
})
