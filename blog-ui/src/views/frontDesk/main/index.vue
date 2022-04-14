<template>
  <div>
    <div class="main">
      <h1 class="main-title">
        <i class="el-icon-document-copy">
          <b>最近的文章</b>
        </i>
      </h1>
      <div v-for="article in articles" :key="article.id" class="article animate__animated animate__fadeInDown" @click="routePush(article.id)">
        <div class="content">
          <div class="text">
            <h2>
              {{ article.title }}
            </h2>
            <div style="height: 150px">{{ article.content.substring(0, 400) }} .......</div>
            <h4>{{ article.createTime.substring(0,10) }}</h4>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {getRecentArticleByPage} from '@/api/methods'
import 'animate.css'
export default {
  name: 'Main',
  data() {
    return {
      articles: []
    }
  },
  computed: {
  },
  mounted() {
    new Promise((resolve, reject) => {
      getRecentArticleByPage(1, 8).then(
        response => {
          response.data.list.forEach((value, key) => {
            this.articles.push(value)
          })
          resolve()
        }
      ).catch(error => {
        console.log(error.message)
        reject(error)
      })
    })
  },
  methods: {
    routePush(id) {
      this.$router.push({ path: '/guest/article/' + id })
    }
  }
}
</script>

<style scoped>
.main{
}
h1.main-title {
  color: #666;
  font-size: 25px;
  top: 70px;
}
.article {
  width: 800px;
  height: 300px;
  margin-top: 50px;
  border-radius: 10px;
  box-shadow: 0 1px 20px -6px rgba(0,0,0,.5);
}
.img-container {
  float: right;
  width: 500px;
  height: 300px;
  overflow: hidden;
}
.content {
  float: left;
  width: 700px;
  height: 200px;
}
.text{
  margin-top: 30px;
  margin-left: 50px;
  opacity: 80%;
}
</style>
