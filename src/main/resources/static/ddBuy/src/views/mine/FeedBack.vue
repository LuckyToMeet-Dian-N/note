<template>
  <div id="collection">
     <van-nav-bar
        title="反馈信息"
        left-text="返回"
        left-arrow
        @click-left="onClickLeft"
      />
      <br>
      <van-field
          v-model="message"
          rows="10"
          autosize
          type="textarea"
          maxlength="100"
          placeholder="请输入反馈信息"
          show-word-limit
        /> 
      <br>
        <van-button type="primary" @click="save" size="large">立即反馈</van-button>
    </div>
</template>
<script type="text/javascript">
  import { Dialog, Toast } from 'vant'
import { insertFeedBack } from '../../api/feedBack';

export default {
  data () {
    return {
      typeArray: [],
      // 路由传递过来的数据 active
      active:'',
      itemsTitle:'',
      message:''
    }
  },
  created(){
    this.getData()
  },
  methods: {
    save() {
      
      if (this.message=='') {
          Toast({
                  message: this.$t('反馈消息不能为空'),
                  duration: 800
              });
          return false;
      }
      let param ={
        message: this.message
      }
      insertFeedBack(param).then(resg => {
            if (resg.code==0) {
              Toast({
                  message: this.$t('反馈成功'),
                  duration: 800
              });
               this.message= ''
             
            }else{
              Toast({
                  message: this.$t(resg.msg),
                  duration: 800
              });
            }});
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
