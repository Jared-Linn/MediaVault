<template >
  <el-skeleton :loading="loading" animated>
    <template #template>
      <div class="skeleton-item">
        <el-skeleton-item variant="image" style="width: 200px; height: 200px;"/>
        <div style="padding: 14px;">
          <el-skeleton-item variant="h3" style="width: 50%;"/>
          <div style="display: flex; align-items: center; justify-content: space-between;">
            <el-skeleton-item variant="text" style="margin-right: 16px;"/>
            <el-skeleton-item variant="text" style="width: 32px;"/>
          </div>
        </div>
      </div>
    </template>
    <template #default>
      <!-- 实际内容 -->
      <div v-if="!loading">
        <img src="../../assets/img/1.jpg" alt="Image" style="width: 200px; height: 200px;"/>
        <div style="padding: 14px;">
          <h3>Title</h3>
          <div style="display: flex; align-items: center; justify-content: space-between;">
            <p>Description</p>
            <p>Price</p>
          </div>
        </div>
        <UploadFile/>
        <MediaDisplay/>

      </div>
    </template>
  </el-skeleton>
</template>

<script lang="ts">
import UploadFile from '@/views/sys/Uploadfile.vue';
import MediaDisplay from '@/views/role/file/MediaDisplay.vue';

export default {
  data() {
    return {
      loading: true
    };
  },
  components:{
    MediaDisplay,
    UploadFile
  },
  mounted() {
    // 模拟数据加载
    setTimeout(() => {
      this.loading = false;
    }, 2000);
  }
};
</script>

<style scoped>
.skeleton-item {
  border-radius: 4px;
  background-color: #f5f5f5;
  margin-bottom: 16px;
}
</style>