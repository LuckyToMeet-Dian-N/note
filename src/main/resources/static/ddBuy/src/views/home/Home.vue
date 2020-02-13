<template>
  <div id="home">
    <div>
        HAHAHHA
  </div>
</div>
</template>

<script type="text/javascript">
import { Dialog, Toast } from 'vant'

// 引入页面组件
import Loading from '../../components/loading/LoadingGif'

export default {
  computed: {
  },
  created () {
    // 0.数据初始化
  },
  mounted () {

  },
  data () {
    return {
      active:''
    }
  },
  components: {

    Loading,
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

<style lang="less" scoped>
#home {
  background-color: "#f5f5f5";
  padding-bottom: 3rem;
  .head {
    margin-top: -3rem;
    width: 100%;
    background-image: url("http://518taole.7-orange.cn/backImage.png");
  }
}
.van-divider {
  background-color: #f5f5f5;
  margin: 0;
}
</style>
