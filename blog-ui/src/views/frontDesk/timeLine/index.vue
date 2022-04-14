<template>
  <div class="timeline left">
    <el-timeline>
      <el-timeline-item v-for="(article, i) in articleList"
                        :key="article.id"
                        :color="article.state === 1 ? 'green' : article.state === 3 ? 'red' : ''"
                        placement="top"
                        hide-timestamp>
        <el-card style="cursor: pointer; width: 900px" @click.native="toArticle(article.id)">
          <h3>{{article.title}}</h3>
          <p>{{article.content}}</p>
          <p>
            <span>
              {{article.createTime.substring(0, 10)}}
            </span>
          </p>
        </el-card>
      </el-timeline-item>
    </el-timeline>
  </div>
</template>

<script>
  import {getPublishedArticlesByPage} from "@/api/methods";
  import {getDocumentHeight, getScrollTop, getWindowHeight} from '@/utils/page'

  export default {
    data(){
      return{
        articleList: [],
        total: 0,
        pageNum: 1,
        pageSize: 10,
        isLoadEnd: false,
        isLoading: false
      }
    },
    methods: {
      toArticle(id) {
        this.$router.push({path: '/guest/article/' + id})
      },
      getPublishedArticlesByPage() {
        new Promise(((resolve, reject) => {
          this.isLoading = true
          getPublishedArticlesByPage(this.pageNum, this.pageSize).then(
            response => {
              const { data } = response;
              data.list.forEach((value, key) => {
                this.articleList.push(value)
              })
              this.isLoading = false
              this.total = data.total
              this.pageNum++;
              if (this.total === this.articleList.size) {
                this.isLoadEnd = true;
              }
              resolve()
            }
          ).catch(error => {
            console.log(error)
            reject()
          })
        }))
      }
    },
    mounted() {
      this.getPublishedArticlesByPage()
      window.onscroll = () => {
        if (getScrollTop() + getWindowHeight() > getDocumentHeight() - 100) {
          // 如果不是已经没有数据了，都可以继续滚动加载
          if (this.isLoadEnd === false && this.isLoading === false) {
            this.getPublishedArticlesByPage();
          }
        }
      };
    }
  }
</script>

<style lang="less" scoped>
  .timeline {
    padding: 40px 0;
    .year {
      font-size: 34px;
      font-weight: bold;
      color: #000;
    }
    a {
      text-decoration: none;
    }

    .title {
      color: #333;
      &:hover {
        color: #1890ff;
      }
    }
  }
</style>
