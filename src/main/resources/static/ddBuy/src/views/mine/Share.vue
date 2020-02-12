<template>
  <div id="myOrder">
     <van-nav-bar
        title="分享列表"
        left-text="返回"
        left-arrow
        @click-left="onClickLeft"
      /><br>
     <p> &nbsp; &nbsp;我的分享</p>
     <br>
   <div class="content">
          <van-cell  title="1" value="s" />
           <van-cell  title="1" value="s" />
            <van-cell  title="1" value="s" />
             <van-cell  title="1" value="s" />
  </div>
  <br>
  <p> &nbsp; &nbsp;其他分享</p>
     <br>
  <div class="content">
          <van-cell  title="1" value="s" />
           <van-cell  title="1" value="s" />
            <van-cell  title="1" value="s" />
             <van-cell  title="1" value="s" />
              <van-cell  title="1" value="s" />
           <van-cell  title="1" value="s" />
            <van-cell  title="1" value="s" />
             <van-cell  title="1" value="s" />
              <van-cell  title="1" value="s" />
           <van-cell  title="1" value="s" />
            <van-cell  title="1" value="s" />
             <van-cell  title="1" value="s" /> <van-cell  title="1" value="s" />
           <van-cell  title="1" value="s" />
            <van-cell  title="1" value="s" />
             <van-cell  title="1" value="s" />
  </div>
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
