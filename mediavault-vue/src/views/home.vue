@ -1,70 +0,0 @@
<template>
  <LoggedInContent v-if="isLoggedIn" />
  <GuestContent v-else />
  <Uploadfile />
  <button @click="getData">跨越请求</button>
  <button @click="logout">退出登录</button>
</template>

<script>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import LoggedInContent from '@/views/role/LoggedInContent.vue';
import GuestContent from '@/views/role/GuestContent.vue';

import Uploadfile from '@/views/sys/Uploadfile.vue';



export default {
  name: 'Home',
  components: {
    LoggedInContent,
    GuestContent,
      Uploadfile
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
      const res = await axios.get('api/user/2');
      console.log(res);
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