<template>
  <div>
    <h1>生成签名 URL</h1>
    <input v-model="fileKey" placeholder="输入文件 key" />
    <button @click="generateSignedUrl">生成 URL</button>
    <p v-if="signedUrl">签名 URL: {{ signedUrl }}</p>
    <p v-if="error" style="color: red;">{{ error }}</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      fileKey: '',
      signedUrl: '',
      error: ''
    };
  },
  methods: {
    async generateSignedUrl() {
      try {
        const response = await axios.get(`api/generate-signed-url?key=${this.fileKey}`);
        this.signedUrl = response.data;
        this.error = '';
      } catch (error) {
        if (error.response && error.response.data) {
          this.error = `生成上传 URL 失败: ${error.response.data}`;
        } else {
          this.error = `生成上传 URL 失败: ${error.message}`;
        }
        this.signedUrl = '';
      }
    }
  }
};
</script>

<style scoped>
input {
  margin-bottom: 10px;
  padding: 5px;
}

button {
  padding: 10px 20px;
  background-color: #4CAF50;
  color: white;
  border: none;
  cursor: pointer;
}

button:hover {
  background-color: #45a049;
}
</style>