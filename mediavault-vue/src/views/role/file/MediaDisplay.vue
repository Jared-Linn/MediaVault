<template>
  <div>
    <div v-for="(item, index) in mediaItems" :key="index" class="media-item">
      <div v-if="isImage(item)">
        <img :src="item.dataurl" :alt="item.fileName" />
      </div>
      <div v-else-if="isVideo(item)">
        <video controls :src="item.dataurl"></video>
      </div>
      <div class="media-details">
        <h3>{{ item.fileName }}</h3>
        <p>{{ item.description }}</p>
        <p>上传时间: {{ item.uploadTime }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const mediaItems = ref([]);

const fetchMediaItems = async () => {
  try {
    const response = await axios.get('/api/admin/common/file-info', {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    });
    if (response.data.code === 200) {
      mediaItems.value = response.data.data;
    } else {
      console.error('Failed to fetch media items:', response.data.message);
    }
  } catch (error) {
    console.error('Error fetching media items:', error);
  }
};

const isImage = (item) => item.contentType.startsWith('image/');
const isVideo = (item) => item.contentType.startsWith('video/');

onMounted(fetchMediaItems);
</script>

<style scoped>
.media-item {
  margin-bottom: 16px;
  border: 1px solid #ddd;
  padding: 16px;
  border-radius: 4px;
}

.media-item img {
  max-width: 100%;
  height: auto;
}

.media-item video {
  width: 100%;
  height: auto;
}

.media-details {
  margin-top: 8px;
}
</style>
