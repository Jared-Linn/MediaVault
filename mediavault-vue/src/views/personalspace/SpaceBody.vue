

<template>
    <ul v-infinite-scroll="load" class="infinite-list" style="overflow: auto">
        <video controls>
            <source src="https://mediavault.oss-cn-beijing.aliyuncs.com/fd0650fa-7a5f-4abe-9b2c-0488c8d66d0d.mp4" type="video/mp4">
        </video>
        <li v-for="i in count" :key="i" class="infinite-list-item">{{ i }}</li>

    </ul>
    <div class="demo-i
    mage__lazy">
        <el-image v-for="url in urls" :key="url" :src="url" lazy />
    </div>

</template>

<script lang="ts" setup>
import { ref } from '@vue/runtime-core'
const count = ref(0)
const load = () => {
    count.value += 2
}

const urls = [
    'https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg',
    'https://fuss10.elemecdn.com/1/34/19aa98b1fcb2781c4fba33d850549jpeg.jpeg',
    'https://fuss10.elemecdn.com/0/6f/e35ff375812e6b0020b6b4e8f9583jpeg.jpeg',
    'https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg',
    'https://fuss10.elemecdn.com/d/e6/c4d93a3805b3ce3f323f7974e6f78jpeg.jpeg',
    'https://fuss10.elemecdn.com/3/28/bbf893f792f03a54408b3b7a7ebf0jpeg.jpeg',
    'https://fuss10.elemecdn.com/2/11/6535bcfb26e4c79b48ddde44f4b6fjpeg.jpeg',
]
</script>

<style>
.infinite-list {
    /*height: 300px;*/
    height: 50vh;
    padding: 0;
    margin: 0;
    list-style: none;
}
.infinite-list .infinite-list-item {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 50px;
    background: var(--el-color-primary-light-9);
    margin: 10px;
    color: var(--el-color-primary);
}
.infinite-list .infinite-list-item + .list-item {
    margin-top: 10px;
}


.demo-image__lazy {
    height: 900px;
    overflow-y: auto;
}
.demo-image__lazy .el-image {
    display: block;
    min-height: 200px;
    margin-bottom: 10px;
}
.demo-image__lazy .el-image:last-child {
    margin-bottom: 0;
}
</style>
