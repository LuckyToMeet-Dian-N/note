<template>
  <div id="myOrder">
     <van-nav-bar
        title="修改密码"
        left-text="返回"
        right-text="保存"
        left-arrow
        @click-left="onClickLeft"
        @click-right="save"
      />
     <!-- 输入任意文本 -->
          <van-field v-model="userInfo.password"  placeholder="请输入密码" type="email" label="旧密码" />
          <van-field v-model="userInfo.newPassword"  placeholder="请输入新密码" type="text" label="新密码" />
          <van-field v-model="userInfo.againNewPassword"  placeholder="请再次输入新密码"  label="再次输入新密码" />
    </div>
</template>
<script type="text/javascript">
  import { Dialog, Toast } from 'vant'
import { updatePassword } from '../../api/userInfo';
import { getUserInfo } from '../../api/login';

export default {
  data () {
    return {
      typeArray: [],
      // 路由传递过来的数据 active
      active:'',
      itemsTitle:'',
      userInfo:{
        password: '',
        newPassword:'',
        againNewPassword:''
      }
    }
  },
  created(){
    this.getData()
  },
  methods: {
    onClickLeft() {
      Toast('返回');
    },
    onClickRight(){
        Toast('哈哈哈');
    },
    onClickLeft () {
      this.$router.back();
    },
    getData(){

    },
    save(){
         if (this.userInfo.password=='') {
         Toast({
                message: this.$t('旧密码不能为空'),
                duration: 800
            }); 
            return false 
          }
       if (this.userInfo.newPassword=='') {
             Toast({
                    message: this.$t('新密码不能为空'),
                    duration: 800
                });  
                  return false 
              }
        if (this.userInfo.againNewPassword=='') {
             Toast({
                    message: this.$t('再次输入的新密码不能为空'),
                    duration: 800
                });
                 return false   
          }
          if (this.userInfo.againNewPassword!=this.userInfo.newPassword) {
             Toast({
                    message: this.$t('两次密码不一致'),
                    duration: 800
                });
                 return false   
          }
        updatePassword(this.userInfo).then(resg => {
            if (resg.code==0) {
              Toast({
                  message: this.$t('更新成功'),
                  duration: 800
              });
               this.userInfo.password= ''
               this.userInfo.newPassword=''
                this.userInfo.againNewPassword=''
            }else{
              Toast({
                  message: this.$t(resg.msg),
                  duration: 800
              });
            }});
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
