<template>
  <div id="app">
    <router-view></router-view>
    <div v-show="$route.meta.showTabBar">
      <van-tabbar v-model="active">
          <van-tabbar-item to="/home" icon="home-o">最新</van-tabbar-item>
          <van-tabbar-item to="/edit" icon="edit">笔记</van-tabbar-item>
          <van-tabbar-item to="/file" icon="paid">文件夹</van-tabbar-item>
          <van-tabbar-item to="/my" icon="user-o">我的</van-tabbar-item>
        </van-tabbar>
      </div>
  </div>
</template>

<script>
// 禁止手机端扩大和缩小手势
window.onload = function () {
  document.addEventListener('touchstart', function (event) {
    if (event.touches.length > 1) {
      event.preventDefault()
    }
  })
  document.addEventListener('gesturestart', function (event) {
    event.preventDefault()
  })
}
export default {
  data () {
    return {
      active:''
    }
  },
  methods: {
    onChange(){
      Toast({
          message: this.$t('登录成功'),
          duration: 800
      });
    }
  },
}

</script>
<style lang="less">
#app {
  width: 100%;
  height: 100%;
}
</style>
