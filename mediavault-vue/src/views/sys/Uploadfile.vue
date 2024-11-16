<template>
  <el-upload
      ref="uploadRef"
      :auto-upload="false"
      multiple
      @change="handleFileChange"
  >
    <template #trigger>
      <el-button type="primary">select file</el-button>
    </template>

    <el-button class="ml-3" type="success" @click="submitUpload">
      upload to server
    </el-button>
  </el-upload>
</template>

<script lang="ts" setup>
import { ref } from '@vue/runtime-core'
import type { UploadInstance, UploadFile } from 'element-plus'
import axios from 'axios'

const uploadRef = ref<UploadInstance>()
const fileList = ref<UploadFile[]>([])

const handleFileChange = (file: UploadFile, newFileList: UploadFile[]) => {
  fileList.value = newFileList
}

const submitUpload = async () => {
  const currentFileList = fileList.value

  if (!currentFileList || currentFileList.length === 0) {
    alert('请选择一个或多个文件')
    return
  }

  const formData = new FormData()
  currentFileList.forEach((file: UploadFile) => {
    if (file.raw) {
      formData.append('file', file.raw, file.name)
    }
  })

  try {
    const response = await axios.post('/api/admin/common/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })

    if (response.status === 200) {
      console.log('上传成功:', response.data)
      alert('文件上传成功')
    } else {
      console.error('上传失败:', response.data.message || response.statusText)
      alert('文件上传失败: ' + (response.data.message || response.statusText))
    }
  } catch (error) {
    console.error('请求出错:', error)
    alert('请求出错，请重试')
  }
}
</script>

<style scoped>
/* 可以在这里添加样式 */
.el-button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}
</style>
