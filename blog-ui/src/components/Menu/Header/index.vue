<template>
  <div>
    <el-menu :default-active="$route.path.replace(prefixUrl + '/', '')" class="el-menu-demo" mode="horizontal" @select="handleSelect">
      <el-menu-item v-for="route in routes" :key="route.path" :index="route.path">
        <b style="font-size: 18px">{{route.meta.title}}</b>
      </el-menu-item>
      <el-menu-item v-if="getToken" index="/admin/main" class="login">进入后台</el-menu-item>
      <el-menu-item v-else index="/login" class="login">登录</el-menu-item>
    </el-menu>
  </div>
</template>

<script>
import {getToken} from "@/utils/auth";

export default {
  data() {
    return {
      prefixUrl: ''
    }
  },
  computed:{
    routes(){
      const allRoutes = this.$router.options.routes
      let result = []
      allRoutes.forEach(((value, index) => {
        if (value.guest === true) {
          this.prefixUrl = value.path
          value.children.forEach(children => {
            if (!children.hidden) {
              result.push(children)
            }
          })
        }
      }))
      return result
    },
    getToken() {
      return getToken() != null
    }
  },
  methods: {
    handleSelect(key, keyPath) {
      if (key === '/login' || key === '/admin/main') {
        this.$router.push({path: key})
      } else {
        this.$router.push({ path: this.prefixUrl + '/' + key })
      }
    },
  },
}
</script>

<style scoped>
  .el-menu-demo{
    display: flex;
    justify-content: center;
  }
  .login{
    position: absolute;
    margin-left: 93%;
    font-size: 30px;
    color: #000000;
  }
</style>
