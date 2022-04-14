<template>
  <div>
    <el-container>

      <el-header>
        <Header />
      </el-header>

      <el-main style="display: flex; width: 1400px; margin: 0 auto">
        <router-view style="width: 1100px"/>
        <Slider v-if="isShowSlider" style="width: 350px"/>
      </el-main>

      <el-footer>
        <Footer />
      </el-footer>

    </el-container>
  </div>
</template>

<script>
import Header from './Header'
import Footer from './Footer'
import Slider from '@/components/Slider'
export default {
  name: 'Menu',
  data() {
    return{
      isShowSlider: true
    }
  },
  watch: {
    $route(route) {
      this.isShowSlider = route.path.indexOf('/article/') === -1
    }
  },
  components: {
    Header,
    Footer,
    Slider
  },
  mounted() {
    this.isShowSlider = this.$route.path.indexOf('/article/') === -1
  }
}
</script>

<style scoped>

</style>
