<template>
  <LoggedInContent v-if="isLoggedIn" />
  <GuestContent v-else />
  <Uploadfile />

  <button @click="logout">退出登录</button>
<!-- 登录状态切换组件 -->
    <button @click="router().push('/login')">前往登录</button>
  <br>
  <Decryption />
  <div>
      <button @click="getData">跨越请求</button>
      <button @click="getData2">跨越请求22</button>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import LoggedInContent from '@/views/role/LoggedInContent.vue';
import GuestContent from '@/views/role/GuestContent.vue';
import Decryption from '@/views/sys/Decryption.vue';
import Uploadfile from '@/views/sys/Uploadfile.vue';
import router from "@/router";



export default {
  name: 'Home',
    methods: {
        router() {
            return router
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
    const isLoggedIn = ref(false);

    const checkToken = () => {
      const token = localStorage.getItem('token');
      if (token) {
        isLoggedIn.value = true;
      } else {
        isLoggedIn.value = false;
      }
    };

    onMounted(() => {
      checkToken();
    });

    const getData = async () => {
            const res = await axios.get('/api/user/2');
    };

      const getData2 = async () => {
          for (let i = 0; i < 100000; i++) {
              const res = await axios.get('/api/user/2');
              // console.log(res);
          }
      };

    const logout = () => {
      localStorage.removeItem('token');
      isLoggedIn.value = false;
      // router.push('/login');
      console.log('已退出登录');
    };

    return {
      isLoggedIn,
      getData,
      getData2,
      logout
    };
  },
  beforeCreate() {},
  created() {},
  beforeMount() {}
}
</script>

<style>
/* 你的样式代码 */
</style>