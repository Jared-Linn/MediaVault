<template>
  <div class="container">
    <LoggedInContent v-if="isLoggedIn"/>
    <GuestContent v-else/>

    <button @click="logout">退出登录</button>
    <!-- 登录状态切换组件 -->
    <button @click="router().push('/login')">前往登录</button>
    <br>
    <Decryption/>
  </div>
</template>

<script lang="ts">
import {ref, onMounted} from 'vue';
import {useRouter} from 'vue-router';
import LoggedInContent from '@/views/role/LoggedInContent.vue';
import GuestContent from '@/views/role/GuestContent.vue';
import Decryption from '@/views/sys/Decryption.vue';
import GetData from '@/views/test/GetData.vue';
import Navigation from '@/views/home/components/Navigation.vue';
import router from '@/router';


export default {
  methods: {
    router(): typeof router {
      return router;
    }
  },
  components: {
    Navigation,
    GetData,
    LoggedInContent,
    GuestContent,
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


    const logout = (): void => {
      localStorage.removeItem('token');
      isLoggedIn.value = false;
      console.log('已退出登录');
    };

    return {
      isLoggedIn,
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

<style scoped>
.container {
  text-align: center;
}
</style>
