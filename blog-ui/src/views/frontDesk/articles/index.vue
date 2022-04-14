<template>
  <div>
    <h1 class="main-title">
      <i class="el-icon-document-copy">
        <b>文章列表</b>
      </i>
    </h1>
    <ArticleList/>
<!--      <div class="article">-->
<!--        <div v-for="article in articles" :key="articles.id" class="infinite-list-item animate__animated animate__zoomIn">-->
<!--            <span class="boll" />-->
<!--            <span class="high-light" />-->
<!--            <router-link class="content" :to="getArticleUrl(article.id)">-->
<!--              <span>{{ article.title }}</span>-->
<!--              <span class="time">{{ article.createTime.substring(0, 10) }}</span>-->
<!--            </router-link>-->
<!--        </div>-->
<!--      </div>-->
  </div>
</template>

<script>
  import ArticleList from '@/components/ArticlesList'
import 'animate.css'
import {getPublishedArticlesByPage} from '@/api/methods'
export default {
  name: 'Articles',
  components: {
    ArticleList
  },
  data() {
    return {
      pageNum: 1,
      articles: [],
      total: 0,
    }
  },
  mounted() {
    this.getArticle()
  },
  methods: {
    getArticle(){
      new Promise((resolve, reject) => {
        getPublishedArticlesByPage(this.pageNum, 100).then(
          response => {
            this.total= response.data.total
            response.data.list.forEach((value, key) => {
              this.articles.push(value)
              resolve()
            })
          }
        ).catch(error => {
          console.log(error.message)
          reject(error)
        })
      })
    },
    getArticleUrl(id) {
      return '/guest/article/' + id
    },
  }
}
</script>

<style scoped>
  h1.main-title {
    color: #666;
    font-size: 25px;
    top: 70px;
  }
.header {
  width: 100%;
  height: 300px;
  margin: 0 auto;
  overflow: hidden;
}
.img-container {
  width: 100%;
  height: 250px;
}
.header-title{
  color: #ffffff;
  position: absolute;
  font-size: 40px;
  top: 110px;
  left: 45%;
}
.article{
  max-width: 1000px;
  margin: 0 auto;
}
.boll {
  height: 15px;
  width: 15px;
  background: orange;
  position: absolute;
  margin-left: -35px;
  margin-top: 20px;
  border-radius: 100%;
}
.high-light {
  float: left;
  margin-top: 10px;
  border-left: 20px solid transparent;
  border-bottom: 20px solid #f5f5f5;
  margin-left: -20px;
}
.content {
  color: #7d7d7d;
  padding: 25px;
  background: #f5f5f5;
  margin-top: 30px;
  display: block;
  text-decoration: none;
}
.time {
  float: right;
  color: #cacaca;
}

</style>
