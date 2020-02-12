<template>
  <div id="myOrder">
     <van-nav-bar
        title="个人信息"
        left-text="返回"
        right-text="保存"
        left-arrow
        @click-left="onClickLeft"
        @click-right="save"
      />
     <!-- 输入任意文本 -->
          <van-field v-model="userInfo.email" disabled  type="email" label="邮箱" />
          <van-field v-model="userInfo.userName" type="text" label="整数" />
          <van-field v-model="userInfo.age"  label="年龄" />
          <van-field v-model="userInfo.region" type="text" label="地区" />
           <van-field v-model="userInfo.balances"  type="number" disabled  label="账户余额" >
             <van-button slot="button" size="small"  type="primary">充值</van-button>
           </van-field>
    </div>
</template>
<script type="text/javascript">
  import { Dialog, Toast } from 'vant'
import { updateUserInfo } from '../../api/userInfo';
import { getUserInfo } from '../../api/login';

export default {
  data () {
    return {
      typeArray: [],
      // 路由传递过来的数据 active
      active:'',
      itemsTitle:'',
      userInfo:{
        email:'22',
        userName:'222',
        age:'',
        region:'',
        balances:'1',
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
      var info = JSON.parse(localStorage.getItem('userInfo'))
      
        getUserInfo(this.userInfo).then(resg => {
            if (resg.code==0) {
                  var info =resg.data
                  this.userInfo.email= info.email
                  this.userInfo.userName= info.userName
                  this.userInfo.age= info.age
                  this.userInfo.balances= info.balances
                  this.userInfo.region= info.region
                  this.userInfo.balances= info.balances
                  this.userInfo.id= info.id
            }else{
              Toast({
                  message: this.$t(resg.msg),
                  duration: 800
              });
            }});

    },
    save(){
         if (this.userInfo.userName=='') {
         Toast({
                message: this.$t('用户名不能为空'),
                duration: 800
            }); 
            return false 
          }
       if (this.userInfo.age=='') {
             Toast({
                    message: this.$t('年龄不能为空'),
                    duration: 800
                });  
                  return false 
              }
        if (this.userInfo.age>=100) {
             Toast({
                    message: this.$t('年龄不能超过100岁'),
                    duration: 800
                });
                 return false   
          }
        updateUserInfo(this.userInfo).then(resg => {
            if (resg.code==0) {
              Toast({
                  message: this.$t('更新成功'),
                  duration: 800
              });
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
