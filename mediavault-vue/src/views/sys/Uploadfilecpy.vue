<template>
    <div>
        <input type="file" @change="handleFileChange" multiple />
        <button @click="uploadFiles" :disabled="!selectedFiles.length || uploading">上传文件</button>
        <p v-if="uploading">正在上传...</p>
        <p v-if="uploadSuccess" style="color: green;">文件上传成功</p>
        <p v-if="uploadError" style="color: red;">{{ uploadError }}</p>
    </div>
</template>

<script>
export default {
    data() {
        return {
            selectedFiles: [],
            uploading: false,
            uploadSuccess: false,
            uploadError: '',
        };
    },
    methods: {
        handleFileChange(event) {
            this.selectedFiles = Array.from(event.target.files);
            this.uploadSuccess = false;
            this.uploadError = '';
        },
        async uploadFiles() {
            if (!this.selectedFiles.length) {
                alert('请选择一个或多个文件');
                return;
            }

            this.uploading = true;
            this.uploadSuccess = false;
            this.uploadError = '';

            const formData = new FormData();
            this.selectedFiles.forEach((file) => {
                formData.append('file', file, file.name); // 使用相同的 key 'file'
            });

            try {
                const response = await fetch('/api/admin/common/upload', {
                    method: 'POST',
                    body: formData,
                });

                if (response.ok) {
                    const result = await response.json();
                    console.log('上传成功:', result);
                    this.uploadSuccess = true;
                    alert('文件上传成功');
                } else {
                    const errorData = await response.json();
                    console.error('上传失败:', errorData.message || response.statusText);
                    this.uploadError = '文件上传失败: ' + (errorData.message || response.statusText);
                }
            } catch (error) {
                console.error('请求出错:', error);
                this.uploadError = '请求出错，请重试';
            } finally {
                this.uploading = false;
            }
        },
    },
};
</script>

<style scoped>
/* 可以在这里添加样式 */
button:disabled {
    background-color: #ccc;
    cursor: not-allowed;
}
</style>
