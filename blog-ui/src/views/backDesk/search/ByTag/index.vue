<template>
  <div>
    <el-input
      v-model="input"
      class="label animate__animated animate__flipInX"
      placeholder="请输入内容"
      prefix-icon="el-icon-search"
    />
    <div class="article">
      <div v-for="article in articles" :key="article.id" class="animate__animated animate__bounceIn">
        <span class="boll" />
        <span class="high-light" />
        <router-link class="content" :to="getArticle(article.id)">
          <span>{{ article.title }}</span>
          <span class="time">{{ article.createTime.substring(0, 10) }}</span>
        </router-link>
      </div>
    </div>
    <el-empty v-show="isEmpty" description="未找到文章"></el-empty>
  </div>

</template>

<script>
import 'animate.css'
import {getArticlesByTag} from '@/api/methods'
export default {
  data() {
    return {
      input: '',
      articles: [],
      pageSize: 100,
      pageNum: 1,
      isEmpty: true
    }
  },
  watch:{
    $route(route) {
      this.input = route.query.tagName
    },
    input(val){
      if (val === '' || val === undefined) {
        this.isEmpty = true
        this.articles = []
      } else {
        // 过滤全是空格的情况，防止全部查询
        if (val.replaceAll(' ', '') === '') {

        } else {
          this.search(val)
        }
      }
    }
  },
  methods: {
    search(val) {
      new Promise((resolve, reject) => {
        getArticlesByTag(val, this.pageNum, this.pageSize).then(
          response => {
            const { data } = response
            this.isEmpty = data.length === 0
            this.articles = data.list
            resolve()
          }
        ).catch(error => {
          console.log(error.message)
          reject(error)
        })
      })
    },
    getArticle(id) {
      return '/guest/article/' + id
    },
  },
  mounted() {
    this.input = this.$route.query.tagName
  }
}
</script>

<style scoped>
.label{
  width: 1000px;
  display: flex;
  margin: 50px auto;
}
.article{
  width: 1000px;
  height: 100px;
  margin: 30px auto;
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
  padding: 20px;
  background: #f5f5f5;
  margin-bottom: 30px;
  display: block;
  text-decoration: none;
}
.time {
  float: right;
  color: #cacaca;
}
</style>
