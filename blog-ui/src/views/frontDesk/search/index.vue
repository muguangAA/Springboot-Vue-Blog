<template>
  <div>
<!--    <el-input-->
<!--      v-model="input"-->
<!--      class="label animate__animated animate__flipInX"-->
<!--      placeholder="请输入内容"-->
<!--      prefix-icon="el-icon-search"-->
<!--    />-->
<!--    <div class="article">-->
<!--      <div v-for="article in articles" :key="article.id" class="animate__animated animate__bounceIn">-->
<!--        <span class="boll" />-->
<!--        <span class="high-light" />-->
<!--        <router-link class="content" :to="getArticle(article.id)">-->
<!--          <span>{{ article.title }}</span>-->
<!--          <span class="time">{{ article.createTime.substring(0, 10) }}</span>-->
<!--        </router-link>-->
<!--      </div>-->
<!--    </div>-->
<!--    <el-empty v-show="isEmpty" description="未找到文章"></el-empty>-->
    <SearchArticles/>
  </div>

</template>

<script>
  import SearchArticles from '@/components/SearchArticles'
import 'animate.css'
import {getPublishedArticleByTitle} from '@/api/methods'
export default {
  name: 'Search',
  components: {
    SearchArticles
  },
  data() {
    return {
      input: '',
      articles: [],
      isEmpty: true
    }
  },
  computed: {
  },
  watch:{
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
        getPublishedArticleByTitle(val).then(
          response => {
            this.isEmpty = response.data.list.length === 0
            this.articles = response.data.list
            console.log(this.articles)
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
    }
  }
}
</script>

<style scoped>
.label{
  width: 1000px;
  display: flex;
  margin: 0 auto;
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
