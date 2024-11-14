<template>
  <div>
    <button @click="fetchAndDecryptFile">加载并解密文件</button>
    <img v-if="decryptedFileUrl" :src="decryptedFileUrl" alt="Decrypted File" />
  </div>
</template>

<script>
import CryptoJS from 'crypto-js';

export default {
  data() {
    return {
      decryptedFileUrl: null,
    };
  },
  methods: {
      async fetchAndDecryptFile() {
          // const fileUrl = '/aliyun/6054daac-ec6a-4952-9274-efa7a61fdf7f.png';
          const fileUrl = 'https://mediavault.oss-cn-beijing.aliyuncs.com/6054daac-ec6a-4952-9274-efa7a61fdf7f.png';

          try {
              // 获取文件内容
              const response = await fetch(fileUrl, {
                  method: 'GET',
                  mode: 'cors', // 确保启用 CORS
              });

              if (!response.ok) {
                  throw new Error(`请求失败: ${response.status} ${response.statusText}`);
              }

              const fileContent = await response.blob();

              // 创建 URL 对象
              this.decryptedFileUrl = URL.createObjectURL(fileContent);
          } catch (error) {
              console.error('请求或解密出错:', error);
              if (error instanceof TypeError && error.message.includes('Failed to fetch')) {
                  console.error('可能是 CORS 问题或网络问题');
              } else {
                  console.error('其他错误:', error);
              }
              alert('请求或解密出错，请重试');
          }
      },


      decryptData(encryptedData, secretKey) {
          try {
              // 使用 AES 解密
              const bytes = CryptoJS.AES.decrypt(encryptedData, secretKey);
              return bytes.toString(CryptoJS.enc.Utf8);
          } catch (error) {
              console.error('解密失败:', error);
              if (error instanceof SyntaxError) {
                  console.error('可能是密钥不匹配或文件内容格式问题');
              } else {
                  console.error('其他解密错误:', error);
              }
              throw new Error('解密失败');
          }
      },
  },
};
</script>

<style scoped>
img {
  width:100%;
  height:auto;
}
/* 可以在这里添加样式 */
</style>
