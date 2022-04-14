<template>
  <div class="container">
    <el-table
      :data="tableData"
      border
      style="width: 100%">
      <el-table-column
        fixed
        align="center"
        prop="createTime"
        label="更新时间"
        width="150">
      </el-table-column>
      <el-table-column
        align="center"
        prop="title"
        label="标题"
        width="200">
      </el-table-column>
      <el-table-column
        align="center"
        prop="state"
        label="状态"
        width="100">
      </el-table-column>
      <el-table-column
        align="center"
        label="标签"
        prop="tags"
        width="350">
        <template slot-scope="scope">
          <el-tag v-for="tag in scope.row.tags" :key="tag.id" style="margin-left: 15px">
            <b>{{tag.tagName}}</b>
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        fixed="right"
        label="操作"
        width="180">
        <template slot-scope="scope">
          <el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      class="page"
      background
      :page-size="pageSize"
      layout="prev, pager, next"
      @current-change="changePage"
      :total="total">
    </el-pagination>
  </div>
</template>

<script>

  import {getPublishedArticlesByPage} from '@/api/methods'
  export default {
    name: 'ArticlesList',
    data() {
      return {
        pageNum: 1,
        total: 0,
        pageSize: 5,
        tableData: []
      }
    },
    created() {
      this.getArticle()
    },
    methods: {
      changePage(page){
        this.pageNum = page
        this.getArticle()
      },
      getArticle(){
        new Promise((resolve, reject) => {
          getPublishedArticlesByPage(this.pageNum, this.pageSize).then(
            response => {
              const { data } = response
              this.total = data.total
              data.list.forEach((item) => {
                item.createTime = item.createTime.substring(0, 10)
                let state = item.state
                if (state === 0) {
                  item.state = '未发布'
                } else {
                  item.state = '已发布'
                }
              })
              this.tableData = data.list
              resolve()
            }
          ).catch(error => {
            console.log(error.message)
            reject(error)
          })
        })
      },
      handleClick(row) {
        console.log(row)
        this.$router.push({path: '/guest/article/' + row.id})
      }
    },
  }
</script>

<style scoped>
  .container{
    width: 1000px;
  }
  .page{
    margin-top: 30px;
    text-align: center;
  }
</style>
