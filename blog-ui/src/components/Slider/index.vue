<template>
  <div class="right slider">
    <div class="right-content"></div>
    <div class="tags">
      <div class="title">标签云</div>
      <div v-for="tag in tagList"
           @click="toSearch(tag.tagName)"
                   class="item"
                   :key="tag.tagName">
        <span :key="tag.tagName">{{tag.tagName}}({{tag.count}})</span>
      </div>
    </div>
  </div>
</template>

<script>
  import {getPerTagArticleNums} from "@/api/methods";

  export default {
    name: 'Slider',
    data() {
      return {
        tagList: []
      }
    },
    methods: {
      toSearch(tagName) {
        this.$router.push({
          path: '/guest/search',
          query: {
            tagName: tagName,
          }
        })
      }
    },
    mounted() {
      new Promise(((resolve, reject) => {
        getPerTagArticleNums().then(
          response => {
            const { data } = response
            this.tagList = data;
            resolve()
          }
        ).catch(error => {
          console.log(error)
          reject()
        })
      }))
    }
  }
</script>

<style scoped lang="less">
  .slider {
    padding-top: 50px;
    max-width: 300px;
  }
  .right {
    text-align: center;
    .right-logo {
      width: 130px;
      border-radius: 50%;
      animation: mylogo 3s;
      -moz-animation: mylogo 3s; /* Firefox */
      -webkit-animation: mylogo 3s; /* Safari and Chrome */
      -o-animation: mylogo 3s; /* Opera */
      animation-iteration-count: infinite;
    }
    .title {
      font-size: 25px;
      font-weight: bold;
    }
    .right-content {
      padding: 10px 0 20px 0;
      margin-bottom: 10px;
      border-bottom: 1px solid #eee;
      .item {
        display: inline-block;
        padding: 0 10px;
        color: #969696;
        border-right: 1px solid #eee;
        &:last-child {
          border-right: none;
        }
        .num {
          color: #333;
        }
      }
    }
    .introduce {
      margin-bottom: 10px;
      border-bottom: 1px solid #eee;
      .title {
        font-size: 14px;
        color: #969696;
      }
      .content {
        color: #333;
        line-height: 26px;
        text-align: left;
        padding: 20px 0;
      }
      .footer {
        padding-bottom: 10px;
      }
    }
    .tags {
      min-height: 200px;
      padding: 5px 0 20px 0;
      margin-bottom: 10px;
      border-bottom: 1px solid #eee;
      .title {
        font-size: 14px;
        color: #969696;
      }
      .item {
        display: inline-block;
        cursor: pointer;
        padding: 5px 10px;
        border-radius: 5px;
        background-color: #eee;
        color: #333;
        margin: 10px 10px 0 0;
        text-decoration: none;
        &:hover {
          color: #409eff;
        }
      }
    }
    .classification {
      padding: 5px 0 20px 0;
      .title {
        font-size: 14px;
        color: #969696;
      }
      .item {
        text-align: center;
        padding: 10px;
        border-bottom: 1px solid #eee;
        color: #666;
        margin: 10px 10px 0 0;
      }
    }
  }
  @keyframes mylogo {
    0% {
      transform: rotate(0deg) scale(0.8, 0.8);
      opacity: 1;
    }
    25% {
      transform: rotate(0deg) scale(1, 1);
      opacity: 0.8;
    }
    100% {
      transform: rotate(0deg) scale(0.8, 0.8);
      opacity: 1;
    }
  }

  @-moz-keyframes mylogo {
    0% {
      transform: rotate(0deg) scale(0.8, 0.8);
      opacity: 1;
    }
    25% {
      transform: rotate(0deg) scale(1, 1);
      opacity: 0.8;
    }
    100% {
      transform: rotate(0deg) scale(0.8, 0.8);
      opacity: 1;
    }
  }

  @-webkit-keyframes mylogo {
    0% {
      transform: rotate(0deg) scale(0.8, 0.8);
      opacity: 1;
    }
    25% {
      transform: rotate(0deg) scale(1, 1);
      opacity: 0.8;
    }
    100% {
      transform: rotate(0deg) scale(0.8, 0.8);
      opacity: 1;
    }
  }

  @-o-keyframes mylogo {
    0% {
      transform: rotate(0deg) scale(0.8, 0.8);
      opacity: 1;
    }
    25% {
      transform: rotate(0deg) scale(1, 1);
      opacity: 0.8;
    }
    100% {
      transform: rotate(0deg) scale(0.8, 0.8);
      opacity: 1;
    }
  }
</style>
