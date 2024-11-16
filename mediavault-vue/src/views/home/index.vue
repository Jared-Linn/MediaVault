<template>
  <Heard/>
  <Navigation/>
  <Content/>
  <Foot/>

</template>

<script lang="ts">
import {useRouter} from 'vue-router';

import Navigation from '@/views/home/components/Navigation.vue';
import Heard from '@/views/home/components/Heard.vue';
import Content from '@/views/home/components/Content.vue';
import Foot from '@/views/home/components/Foot.vue';
import router from '@/router';
import {defineComponent} from "vue";

export default defineComponent({
  components: {Heard, Foot, Content, Navigation}
})



</script>

<style >
/* 你的样式代码 */
/* 全局居中 */
 /*
body {

  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  background-color: #f5f5f5;
  overflow: hidden;
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: Arial, Helvetica, sans-serif;
}

 */

</style>
