<template>
  <div class="about">
    <van-nav-bar
        title="版本信息"
        left-text="返回"
        @click-left="back"
      />
    <div class="col">
      <img
        class="logo"
        src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575201131698&di=7c065894cc54634602345e5f6c3f82ad&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01a36257c2f4aa0000018c1b9ee60b.JPG%401280w_1l_2o_100sh.jpg"
      />
      <span class="blod-text">记事本 Note</span>
      <span class="version-info">Version {{versionInfo.version}}</span>
      <span class="version-info">{{versionInfo.description}}</span>
      <span class="version-info">{{versionInfo.createTime}}</span>

      <div class="center-pay" style="margin-top:10px;">
        <van-button
          :disabled="!versionInfo.path"
          class="center"
          type="primary"
          size="small"
          @click="downloadApp(versionInfo.path)"
        >下载该版本</van-button>
      </div>
    </div>
    <div class="bottom">
      <span>Note 版权所有</span>
      <span>Copyright © 2019-2020 Note</span>
      <span>All Rights Reserved</span>
    </div>
  </div>
</template>
<script>
  import { getVersionInfo } from '../../api/tag';

export default {
  created() {
    this.getVersionInfos();
  },
  data() {
    return {
      versionInfo: {}
    };
  },
  methods: {
    back() {
      this.$router.go(-1);
    },
    downloadApp(url) {
      console.log(url);
      window.open( url, "_blank");
    },
    getVersionInfos() {
       getVersionInfo(this.query).then(resg => {
            if (resg.code==0) {
                this.versionInfo = resg.data
            }else{
              Toast({
                  message: this.$t(resg.msg),
                  duration: 800
              });
            }});
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
  margin-top: 170px;
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