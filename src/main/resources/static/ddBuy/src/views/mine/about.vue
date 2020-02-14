<template>
  <div class="about">
    <van-nav-bar
        title="关于我们"
        left-text="返回"
        left-arrow
        @click-left="onClickLeft"
      /><br>
    <div class="col">
      <img
        class="logo"
        src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575201131698&di=7c065894cc54634602345e5f6c3f82ad&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01a36257c2f4aa0000018c1b9ee60b.JPG%401280w_1l_2o_100sh.jpg"
      />
      <span class="blod-text">记事本 Note</span>
      <span class="version-info">Version bate</span>
      <span class="version-info">记事本-记录美好瞬间</span>
    </div>
    <div class="bottom">
      <span>Note 版权所有</span>
      <span>Copyright © 2018-2019 Note</span>
      <span>All Rights Reserved</span>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {};
  },
  methods: {
    onClickLeft() {
      this.$router.go(-1);
    }
  }
};
</script>>


<style scoped>
.topBg {
  background-color: #fff;
}
.col {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 60px;
}
.bottom {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 240px;
}
.blod-text {
  margin-top: 20px;
  font-weight: 700;
  font-size: 24px;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
    "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
}

.logo {
  width: 86px;
  height: 86px;
}

.left {
  position: absolute;
  left: 20px;
  color: black;
}

.version-info {
  margin-top: 10px;
  font-size: 14px;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
    "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
}
.bottom {
  color: #909399;
}
</style>