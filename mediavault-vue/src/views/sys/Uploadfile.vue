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

<!--    <template #tip>-->
<!--      <div class="el-upload__tip">-->
<!--        jpg/png files with a size less than 500kb-->
<!--      </div>-->
<!--    </template>-->
  </el-upload>
</template>

<script lang="ts" setup>
import  { ref } from '@vue/runtime-core'
import type { UploadInstance, UploadFile } from 'element-plus'

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
    const response = await fetch('/api/admin/common/upload', {
      method: 'POST',
      headers: {
        'Content-Type': 'multipart/form-data'
      },
      body: formData,
    })

    if (response.ok) {
      const result = await response.json()
      console.log('上传成功:', result)
      alert('文件上传成功')
    } else {
      const errorData = await response.json()
      console.error('上传失败:', errorData.message || response.statusText)
      alert('文件上传失败: ' + (errorData.message || response.statusText))
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
