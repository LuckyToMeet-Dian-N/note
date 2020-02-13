<template>
  <div id="login">
    <div class="loginBox"
         @click="changeImage(3)">
      <div class="auth-form">
        <div class="panfish">
          <img :src="imageURL"
               alt="">
        </div>
        <!-- 关闭按钮 -->
        <van-icon name="close"
                  class="closeButton"
                  @click="close" />
        <van-tabs v-model="active"
                  animated>
          <van-tab :title="$t('login.title')">
            <!-- 账号密码登录 -->
            <van-cell-group >
              <van-field v-model="loginInfo.email"
                         required
                         clearable
                         label="邮箱"
                         maxlength="50"
                         @click.stop="
                         changeImage(0)"
                         placeholder="请输入邮箱" />
              <van-field v-model="loginInfo.password"
                         type="password"
                         :label="$t('login.pass')"
                         :placeholder="$t('login.passTip')"
                         required
                         @click.stop="changeImage(1)" />
              </van-field>
            </van-cell-group>
              <!-- 手机号快捷登录 -->
            <van-button type="info"
                        size="large"
                        style="margin-top:1rem"
                        @click='login'>{{$t('login.login')}}</van-button>
                  <br />
          </van-tab>
          <!-- 注册 -->
          <van-tab :title="$t('login.resgin')">
            <van-cell-group>
              <van-field v-model="registInfo.userName"
                         clearable
                         maxlength="50"
                         label="用户名"
                         placeholder="请输入用户名"
                         required />
              <van-field v-model="registInfo.email"
                         clearable
                         maxlength="50"
                         label="邮箱"
                         placeholder="请输入邮箱"
                         required />
              <van-field v-model="registInfo.password"
                         type="password"
                         label="密码"
                         placeholder="请输入密码"
                         required />
              <van-field v-model="registInfo.againPassword"
                         type="password"
                         label="再次输入密码"
                         placeholder="请再次输入密码"
                         required />
              <van-field v-model="registInfo.security"
                         type="password"
                         label="安全码"
                         placeholder="请输入安全码"
                         required />
            </van-cell-group>
            <van-button type="info"
                        size="large"
                        style="margin-top:1rem"
                        @click='register'>{{$t('login.resgin')}}</van-button>
          </van-tab>
        </van-tabs>

        <p class="agreement"> {{$t('login.tipTile')}}<br>{{$t('login.tipContent')}}<a @click.stop="agreement(0)"
             class="agreement-box">{{$t('login.tip')}}</a>、<a @click.stop=agreement(1)
             class="agreement-box">{{$t('login.tipProcy')}}</a></p>
      </div>
    </div>
    <transition name="router-slider"
                mode="out-in">
      <router-view></router-view>
    </transition>
  </div>
</template>

<script type="text/javascript">
// 引入Vant的组件
import { Toast, Dialog } from 'vant'
// 引入API调用接口
// import { getPhoneCaptcha, phoneCaptchaLogin, pwdLogin } from '../../serve/api/index.js'
// 引入vuex
// import { mapState, mapActions, mapMutations } from 'vuex'
import { fetchData } from '../../api/login';
import { regist } from '../../api/login';
import { mapState, mapActions, mapMutations } from 'vuex'
import { getUserInfo } from '../../api/login';
// import {
//     getLocalStore,
//     setLocalStore
// } from './../config/global'

export default {
  data () {
    return {
      active: 0,
      loginInfo:{
        email: '',
        password: ''
      },
      registInfo:{
          userName:'',
          email:'',
          password:'',
          againPassword:'',
          security:'',
      },
      isShowSMSLogin: false,         // 是否短信登录
      imageURL: require('./../../images/login/normal.png'),
      smsCaptchaResult: null,
      userInfo: null
    };
  },
  computed: {
  },
  created(){
  },
  methods: {
    ...mapActions(['syncuserInfo']),

    // 2.切换萌猫图片
    changeImage (index) {
      if (index == 0) {
        this.imageURL = require('./../../images/login/greeting.png')
      } else if (index == 1) {
        this.imageURL = require('./../../images/login/blindfold.png')
      } else {
        this.imageURL = require('./../../images/login/normal.png')
      }
    },
    // 5.登录
     login () {
      if (this.loginInfo.email=='') {
         Toast({
                message: this.$t('邮箱不能为空'),
                duration: 800
            });  
      }
      if (this.loginInfo.password=='') {
         Toast({
                message: this.$t('密码不能为空'),
                duration: 800
            });  
      }
      fetchData(this.loginInfo).then(res => {
              if (res.code==0) {
                 localStorage.setItem("token",res.data);
                  getUserInfo(this.loginInfo).then(resg => {
                        if (resg.code==0) {
                          Toast({
                              message: this.$t('登录成功'),
                              duration: 800
                          });
                          resg.data.token = true;
                          localStorage.setItem('userInfo',JSON.stringify(resg.data))
                          this.$router.back()
                        }else{
                          Toast({
                              message: this.$t(resg.msg),
                              duration: 800
                          });
                        }});
              }else{
                Toast({
                    message: this.$t(res.msg),
                    duration: 800
                });
              }
          }).catch(res=>{
            Toast({
            message: this.$t('客户端异常，请检查重试'),
            duration: 800
            });
          });
    },
    // 6.注册
     register () {
        if (this.registInfo.email=='') {
         Toast({
                message: this.$t('邮箱不能为空'),
                duration: 800
            });  
           return false;
        }
        if (this.registInfo.password=='') {
           Toast({
                  message: this.$t('密码不能为空'),
                  duration: 800});  
            return false;
        }
        if (this.registInfo.userName=='') {
           Toast({
                  message: this.$t('用户名不能为空'),
                  duration: 800});  
            return false;
        }
        if (this.registInfo.security=='') {
           Toast({
                  message: this.$t('安全码不能为空'),
                  duration: 800});  
            return false;
        }
        if (this.registInfo.againPassword=='') {
           Toast({
                  message: this.$t('再次输入的密码不能为空'),
                  duration: 800});  
            return false;
        }
        if (this.registInfo.againPassword!=this.registInfo.password) {
           Toast({
                  message: this.$t('两次密码不一致'),
                  duration: 800});  
            return false;
        }
        regist(this.registInfo).then(res => {
              if (res.code==0) {
                Toast({
                    message: this.$t('注册成功成功'),
                    duration: 800
                });
                this.registInfo.userName=''
                this.registInfo.email=''
                this.registInfo.password=''
                this.registInfo.againPassword=''
                this.registInfo.security=''
              }else{
                Toast({
                    message: this.$t(res.msg),
                    duration: 800
                });
              }
          }).catch(res=>{
            Toast({
            message: this.$t('客户端异常，请检查重试'),
            duration: 800
            });
          });
    },
    // 7.用户协议
    agreement (index) {
      if (index == 0) {
        Toast({
          message: this.$t('login.tip'),
          duration: 800
        })
      } else {
        Toast({
          message: this.$t('login.tipProcy'),
          duration: 800
        })
      }
    },
    // 8.关闭
    close () {
      this.$router.back();
    },
  }
}
</script>
<style lang="less" scoped>
#login {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: url("../../images/login/back2.jpg");
  background-repeat: no-repeat;
  background-size: 100% 100%;
  background-attachment: fixed;
  .loginBox {
    display: flex;
    align-items: center;
    justify-content: center;
    position: fixed;
    opacity: 0.95;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    z-index: 500;
    .auth-form {
      position: relative;
      padding: 2rem;
      width: 26.5rem;
      max-width: 90%;
      font-size: 1.167rem;
      background-color: #fff;
      border-radius: 8px;
      box-sizing: border-box;
    }
    img {
      position: absolute;
      top: 1rem;
      left: 50%;
      width: 4rem;
      transform: translate(-50%, -70%);
      z-index: 1;
      @media screen and (max-width: 320px) {
        top: 0.5rem;
        transform: translate(-50%, -40%);
        width: 5rem;
      }
    }
    .closeButton {
      position: absolute;
      right: 1rem;
      top: 0.4rem;
    }
    .verificationImage {
      position: absolute;
      right: 0;
      width: 8rem;
      height: 3rem;
      margin-left: 3rem;
    }
    .switchLogin {
      margin-top: 1rem;
      font-size: 0.78rem;
    }
  }
}
.title {
  padding: 0.5rem;
  font-size: 0.5rem;
  color: grey;
}
.agreement {
  // margin-top: 1.667rem;
  line-height: 1rem;
  color: #767676;
  font-size: 0.867rem;
  .agreement-box {
    color: blue;
  }
}
</style>
