<template>
  <LoggedInContent v-if="isLoggedIn"/>
  <GuestContent v-else/>
  <Uploadfile/>

  <button @click="logout">退出登录</button>
  <!-- 登录状态切换组件 -->
  <button @click="router().push('/login')">前往登录</button>
  <br>
  <Decryption/>
  <div>
    <button @click="getData">跨越请求</button>
    <button @click="getData2">跨越请求22</button>
  </div>
</template>

<script lang="ts">
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import LoggedInContent from '@/views/role/LoggedInContent.vue';
import GuestContent from '@/views/role/GuestContent.vue';
import Decryption from '@/views/sys/Decryption.vue';
import Uploadfile from '@/views/sys/Uploadfile.vue';
import router from '@/router';

export default {
  name: 'Home',
  methods: {
    router(): typeof router {
      return router;
    }
  },
  components: {
    LoggedInContent,
    GuestContent,
    Uploadfile,
    Decryption
  },
  setup() {
    const router = useRouter();
    const isLoggedIn = ref<boolean>(false);

    const checkToken = (): void => {
      const token = localStorage.getItem('token');
      isLoggedIn.value = !!token;
    };

    onMounted(() => {
      checkToken();
    });

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

    const logout = (): void => {
      localStorage.removeItem('token');
      isLoggedIn.value = false;
      console.log('已退出登录');
    };

    return {
      isLoggedIn,
      getData,
      getData2,
      logout
    };
  },
  beforeCreate() {
  },
  created() {
  },
  beforeMount() {
  }
};
</script>

<style>
/* 你的样式代码 */
</style>
