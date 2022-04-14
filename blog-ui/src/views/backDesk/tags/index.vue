<template>
  <div>
    <div style="text-align: center; margin-top: 20px">按下回车添加标签</div>
    <el-input
      v-model="input"
      class="label animate__animated animate__flipInX"
      placeholder="请输入内容"
      @keyup.enter.native="addTag"
    />
    <el-empty v-if="tags.length === 0" description="未找到标签"></el-empty>
    <div class="tags" v-else>标签列表:</div>
    <div class="tags" v-for="tag in tags" @click="getTagArticles(tag.tagName)">
      <el-tag
        class="animate__animated animate__bounceIn"
        style="margin-left: 10px; margin-top: 5px; margin-bottom: 5px; cursor: pointer"
        :key="tag.id"
        closable
        @close="handleTagClose(tag)">
        {{tag.tagName}}
      </el-tag>
      :{{tag.count}}篇文章
    </div>
  </div>
</template>

<script>
  import {addTag, deleteTag, getPerTagArticleNums} from '@/api/methods'
  export default {
    name: 'index',
    data() {
      return {
        input: '',
        tags: [],
      }
    },
    methods: {
      getTagArticles(tagName){
        this.$router.push({
          path: '/article/search',
          query: {
            tagName: tagName
          }
        })
      },
      handleTagClose(tag){
        new Promise((resolve, reject) => {
          deleteTag(tag).then(
            response => {
              this.$message.success({
                type: 'success',
                message: response.message || '删除成功!'
              })
              location.reload()
              resolve()
            }
          ).catch(error => {
            console.log(error.message)
            reject(error)
          })
        })
      },

      getPerTagArticleNums(tagName){
        new Promise((resolve, reject) => {
          getPerTagArticleNums(tagName).then(
            response => {
              const { data } = response
              this.tags = data
              resolve()
            }
          ).catch(error => {
            console.log(error.message)
            reject()
          })
        })
      },

      addTag(){
        new Promise((resolve, reject) => {
          addTag({tagName: this.input}).then(
            response => {
              this.$notify.success({
                title: "成功",
                message: response.message || '添加成功',
                duration: 1000 * 3
              })
              location.reload()
              resolve()
            }
          ).catch(error => {
            console.log(error.message)
            reject(error)
          })
        })
      }
    },
    watch: {
      input(val){
        if (val === ''){
          this.getPerTagArticleNums()
        } else {
          this.getPerTagArticleNums(val)
        }
      }
    },
    mounted() {
      this.getPerTagArticleNums()
    }
  }
</script>

<style scoped>
  .label{
    width: 1000px;
    display: flex;
    margin: 20px auto;
  }
  .tags{
    max-width: 1000px;
    margin: 20px auto;
  }
</style>
