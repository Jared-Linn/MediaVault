// @/utils/mySwitch.ts
import { ref } from 'vue'

let flag = ref<boolean>(true)

const mySwitch = (): void => {
  const pre_box = document.querySelector<HTMLElement>('.pre-box')
  const img = document.querySelector<HTMLImageElement>('#avatar')

  if (pre_box && img) {
    if (flag.value) {
      pre_box.style.transform = 'translateX(100%)'
      pre_box.style.backgroundColor = '#c9e0ed'
      img.src = require('@/assets/img/wuwu.jpeg')
    } else {
      pre_box.style.transform = 'translateX(0%)'
      pre_box.style.backgroundColor = '#edd4dc'
      img.src = require('@/assets/img/waoku.jpg')
    }
    flag.value = !flag.value
  } else {
    console.error('预滑动盒子或图片元素未找到')
  }
}

export default mySwitch
