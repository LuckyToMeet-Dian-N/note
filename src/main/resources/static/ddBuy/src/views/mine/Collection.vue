<template>
  <div id="collection">
     <van-nav-bar
        title="收藏列表"
        left-text="返回"
        left-arrow
        @click-left="onClickLeft"
      />
      哈哈哈啊 
    </div>
</template>
<script type="text/javascript">
  import { Dialog, Toast } from 'vant'

export default {
  data () {
    return {
      typeArray: [],
      // 路由传递过来的数据 active
      active:'',
      itemsTitle:'',
    }
  },
  created(){
    this.getData()
  },
  methods: {
    onClickLeft() {
      Toast('返回');
    },

    onClickLeft () {
      this.$router.back();
    },
    getData(){
      console.log(1111)
    },
     back() {
      this.$router.go(-1);
    },
  }
}
</script>

<style lang="less" scoped>
#myOrder {
  position: fixed;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  z-index: 100;
  background-color: #f5f5f5;
  .van-icon {
    color: #dedede;
  }
}
</style>
