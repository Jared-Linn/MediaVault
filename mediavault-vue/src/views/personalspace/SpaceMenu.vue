<template>
    <el-menu
            :default-active="activeIndex"
            class="el-menu-demo"
            mode="horizontal"
            @select="handleSelect"
    >
        <el-menu-item index="1">Processing Center</el-menu-item>
        <el-sub-menu index="2">
            <template #title>Workspace</template>
            <el-menu-item index="2-1">item one</el-menu-item>
            <el-menu-item index="2-2">item two</el-menu-item>
            <el-menu-item index="2-3">item three</el-menu-item>
            <el-sub-menu index="2-4">
                <template #title>item four</template>
                <el-menu-item index="2-4-1">item one</el-menu-item>
                <el-menu-item index="2-4-2">item two</el-menu-item>
                <el-menu-item index="2-4-3">item three</el-menu-item>
            </el-sub-menu>
        </el-sub-menu>
        <el-menu-item index="3" disabled>Info</el-menu-item>
        <el-menu-item index="4">Orders</el-menu-item>
    </el-menu>
    <div class="h-6" />
<!--    <el-menu-->
<!--            :default-active="activeIndex2"-->
<!--            class="el-menu-demo"-->
<!--            mode="horizontal"-->
<!--            background-color="#545c64"-->
<!--            text-color="#fff"-->
<!--            active-text-color="#ffd04b"-->
<!--            @select="handleSelect"-->
<!--    >-->
<!--        <el-menu-item index="1">Processing Center</el-menu-item>-->
<!--        <el-sub-menu index="2">-->
<!--            <template #title>Workspace</template>-->
<!--            <el-menu-item index="2-1">item one</el-menu-item>-->
<!--            <el-menu-item index="2-2">item two</el-menu-item>-->
<!--            <el-menu-item index="2-3">item three</el-menu-item>-->
<!--            <el-sub-menu index="2-4">-->
<!--                <template #title>item four</template>-->
<!--                <el-menu-item index="2-4-1">item one</el-menu-item>-->
<!--                <el-menu-item index="2-4-2">item two</el-menu-item>-->
<!--                <el-menu-item index="2-4-3">item three</el-menu-item>-->
<!--            </el-sub-menu>-->
<!--        </el-sub-menu>-->
<!--        <el-menu-item index="3" disabled>Info</el-menu-item>-->
<!--        <el-menu-item index="4">Orders</el-menu-item>-->
<!--    </el-menu>-->

  Scroll down to see the bottom-right button.
    <el-backtop :right="100" :bottom="100" />



</template>
<script lang="ts" setup>
import { ref } from '@vue/runtime-core'

const activeIndex = ref('1')
const activeIndex2 = ref('1')
const handleSelect = (key: string, keyPath: string[]) => {
    console.log(key, keyPath)
}


</script>

<style scoped>
/*覆盖el-menu的默认高度*/
.el-menu--horizontal {
    --el-menu-horizontal-height: 60px;
}

</style>