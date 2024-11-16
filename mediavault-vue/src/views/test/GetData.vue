<script setup lang="ts">
import axios from 'axios';

    const getData = async (): Promise<void> => {
      try {
        const res = await axios.get('/api/sys/user/2');
        console.log(res);
      } catch (error) {
        console.error('请求失败:', error);
      }
    };

    const getData2 = async (): Promise<void> => {
      for (let i = 0; i < 100000; i++) {
        try {
          const res = await axios.get('/api/sys/user/2');
          // console.log(res);
        } catch (error) {
          console.error('请求失败:', error);
        }
      }
    };
</script>

<template>
  <div>
    <button @click="getData">跨越请求</button>
    <button @click="getData2">跨越请求22</button>
  </div>
</template>

<style scoped>

</style>