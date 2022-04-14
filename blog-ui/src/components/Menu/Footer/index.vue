<template>
  <div>
    <el-popover
      placement="top-start"
      title=""
      width="200"
      trigger="hover"
      content="点我回到顶部"
    >
    </el-popover>
  </div>
</template>

<script>
export default {
  data() {
    return {
    }
  },
  methods: {
    returnTop() {
      let top = document.documentElement.scrollTop || document.body.scrollTop
      // 实现滚动效果
      const timeTop = setInterval(() => {
        document.body.scrollTop = document.documentElement.scrollTop = top -= 50
        if (top <= 0) {
          clearInterval(timeTop)
        }
      }, 10)
    }
  }
}
</script>

<style scoped>
.img{
  bottom: 0;
  right: 0;
  width: 15%;
  position: fixed;
}
</style>
