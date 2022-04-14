<template>
  <div>
    <el-menu @select="handleSelect" class="el-menu-demo" mode="horizontal" :default-active="searchForTag ? 'tag' : 'title'">
      <el-menu-item index="tag" class="item">按标签搜索</el-menu-item>
<!--      <el-menu-item index="title" class="item">按标题搜索</el-menu-item>-->
    </el-menu>

    <div v-if="searchForTag">
      <by-tag/>
    </div>

    <div v-else>
      <by-title/>
    </div>
  </div>
</template>

<script>
import ByTag from "@/views/backDesk/search/ByTag/index";
import ByTitle from "@/views/backDesk/search/ByTitle/index";
export default {
  components: {ByTitle, ByTag},
  data() {
    return{
      searchForTag: true
    }
  },
  methods: {
    handleSelect(key, keyPath) {
      this.searchForTag = !this.searchForTag
    },
  },
}
</script>
<style scoped>
.el-menu-demo{
  margin-top: 1.9%;
  display: flex;
  justify-content: center;
}
.item{
  font-size: 20px;
  font-weight: bold;
}
</style>
