<template>
  <div class="app-container">
    <div class="button">
      <el-button type="success" @click="saveTemplate">保存草稿</el-button>
      <el-button @click="publish = true" type="primary" style="margin-left: 16px;">
        发布
      </el-button>
    </div>

    <el-drawer
      title="发布文章"
      :visible.sync="publish"
      direction="rtl"
      :before-close="handleClose">
      <div class="publish">
        <div class="label">文章标题:</div>
        <el-input class="label" v-model="article.title" placeholder="请输入标题"></el-input>
        <div class="label">添加标签</div>

        <el-autocomplete
          class="inline-input animate__animated animate__flipInX"
          v-model="input"
          style="width: 100%"
          prefix-icon="el-icon-search"
          :fetch-suggestions="querySearchAsync"
          :trigger-on-focus="false"
          @select="handleSelect"
          :popper-append-to-body="false"
          placeholder="请输入内容"
        >
        </el-autocomplete>
        <div v-show="article.tags !== []"
             style="margin-top: 20px; margin-bottom: 20px">
          <el-tag
            style="margin-left: 10px; margin-top: 5px; margin-bottom: 5px"
            v-for="tag in article.tags"
            :key="tag.id"
            closable
            @close="handleTagClose(tag)"
            :type="tag.type">
            {{tag.tagName}}
          </el-tag>
        </div>
        <el-button @click="publishArticle" type="primary" style="margin-top: 10px">
          发布
        </el-button>
      </div>
    </el-drawer>

    <el-input v-model="article.title" placeholder="文章标题" style="max-width: 70%; margin: 30px auto; display: flex"></el-input>
    <mavon-editor
      ref=md
      v-model="article.content"
      class="animate__animated animate__fadeInDown"
      boxShadowStyle="0 1px 20px -6px rgba(0,0,0,.5)"
      @imgAdd="imgAdd"
      @imgDel="imgDel"
      style="font-size: 20px; display: flex; max-width: 70%; margin: 0 auto; border-radius: 30px"
    />
  </div>
</template>

<script>
import {
  fileDelete,
  getArticleById,
  putArticle,
  saveTemplate,
  searchTags,
  uploadArticleImage
} from '@/api/methods'

export default {
  data() {
    return {
      input: '',
      article: {
        id: null,
        updateTime: '',
        createTime: '',
        thumbnailUrl: '',
        content: '',
        title: '',
        state: '',
        tags: [],
      },
      searchTags:[],
      publish: false,
    }
  },

  methods: {
    imgAdd(pos, $file){
      // 第一步.将图片上传到服务器.
      new Promise(((resolve, reject) => {
        const formData = new FormData()
        formData.append('file', $file)
        uploadArticleImage(formData).then(
          response => {
            const {data} = response
            // 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)
            /**
             * $vm 指为mavonEditor实例，可以通过如下两种方式获取
             * 1. 通过引入对象获取: `import {mavonEditor} from ...` 等方式引入后，`$vm`为`mavonEditor`
             * 2. 通过$refs获取: html声明ref : `<mavon-editor ref=md ></mavon-editor>，`$vm`为 `this.$refs.md`
             */
            this.$refs.md.$img2Url(pos, data.url);
            resolve()
        }).catch(error => {
          console.log(error.message)
          reject(error)
        })
      }))
    },
    imgDel(fileName){
      new Promise(((resolve, reject) => {
        fileDelete(fileName[0]).then(
          response => {
            resolve()
          }
        ).catch(error => {
          console.log(error.message)
          reject(error)
        })
      }))
    },
    getSrc(url){
      return url
    },

    onChange(file){
      this.image = file
    },
    onSuccess(response, file, fileList){
      console.log(file)
      const { data } = response
      this.article.thumbnailUrl = data.url
    },
    handleTagClose(tag){
      this.article.tags.splice(this.article.tags.indexOf(tag), 1)
    },
    querySearchAsync(val, callback) {
      // 过滤全是空格的情况，防止全部查询
      if (val.replaceAll(' ', '') === '') {
        callback([])
      } else {
        new Promise((resolve, reject) => {
          searchTags({tagName: val}).then(
            response => {
              this.searchTags = []
              response.data.forEach((value, key) => {
                const val = {"value": value.tagName, "id": value.id}
                this.searchTags.push(val)
              })
              callback(this.searchTags);
              resolve()
            }
          ).catch(error => {
            console.log(error.message)
            callback([])
            reject(error)
          })
        })
      }
    },
    handleSelect(item) {
      let tagIds = []
      this.article.tags.forEach((tag, key) => {
        tagIds.push(tag.id)
      })
      if (tagIds.indexOf(item.id) !== -1) {
        this.$notify.error({
          title: "失败",
          message: "该标签已存在",
          duration: 1000 * 3
        })
      } else {
        this.article.tags.push({"id" : item.id, "tagName": item.value})
        this.input = ''
      }

    },
    handleClose(done) {
      done()
    },
    publishArticle(){
      this.putArticle()
    },
    saveTemplate(){
      new Promise((resolve, reject) => {
        saveTemplate(this.article).then(
          response => {
            this.$notify.success({
              title: "成功",
              message: response.message || '创建成功',
              duration: 1000 * 3
            })
            resolve()
          }
        ).catch(error => {
          console.log(error.message)
          reject(error)
        })
      })
    },
    setArticle(){
      if (this.$route.query.id === undefined) {

      } else {
        new Promise(((resolve, reject) => {
          getArticleById(this.$route.query.id).then(
            response => {
              const { data } = response
              this.article = data
              resolve()
            }
          ).catch(error => {
            console.log(error.message)
            reject(error)
          })
        }))
      }
    },
    putArticle(){
      new Promise(((resolve, reject) => {
        this.article.state = 1
        putArticle(this.article).then(
          response => {
            this.$notify.success({
              title: "成功",
              message: response.message || '修改成功',
              duration: 1000 * 3
            })
            this.$router.push({path: 'allArticle'})
            resolve()
          }
        ).catch(error => {
          console.log(error.message)
          reject(error)
        })
      }))
    },
  },
  mounted() {
    this.setArticle()
  }
}
</script>

<style>
  .label{
    width: 100%;
    margin-top: 10px;
    margin-bottom: 10px;
  }
 .button{
   display: flex;
   justify-content: right;
   margin-right: 15%;
 }
 .publish{
   padding: 24px;
 }
</style>

