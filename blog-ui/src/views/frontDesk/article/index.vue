<template>
  <div>
    <el-container>

      <el-header>
        <Header />
      </el-header>

      <el-main>
        <div class="title animate__animated animate__fadeInDown">{{article.title}}</div>
          <mavon-editor
            v-model="article.content"
            class="animate__animated animate__fadeInDown"
            :toolbars="toolbars"
            :editable="false"
            :subfield="false"
            default-open="preview"
            style="font-size: 20px; display: flex; width: 60%; margin: 0 auto; border-radius: 50px; box-shadow: 0 1px 20px -6px rgba(0,0,0,.5)"
          />
      </el-main>

      <el-footer>
        <Footer />
      </el-footer>
    </el-container>
  </div>
</template>

<script>
import {getArticleById} from '@/api/methods'
import Header from '@/components/Menu/Header'
import Footer from '@/components/Menu/Footer'
import 'animate.css'
export default {
  name: 'Article',
  components: {
    Header,
    Footer
  },
  data() {
    return {
      title: '',
      article: {
        title: '',
        id: 0,
        updateTime: '',
        createTime: '',
        content: '',
        thumbnailUrl: '',
        state: '',
        tags: ''
      },
      toolbars: {}
    }
  },
  mounted() {
    new Promise((resolve, reject) => {
      getArticleById(this.$route.params['id']).then(
        response => {
          const { data } = response
          this.article = data
          resolve()
        }
      ).catch(error => {
        console.log(error.message)
        reject(error)
      })
    })
  },
}
</script>

<style scoped>
  .title{
    font-size: 30px;
    text-align: center;
    margin-bottom: 40px;
    margin-top: 40px;
  }
</style>
