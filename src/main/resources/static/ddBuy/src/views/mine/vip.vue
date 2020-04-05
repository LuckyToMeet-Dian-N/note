<template>
  <div id="collection">
     <van-nav-bar
        title="会员权益"
        left-text="返回"
        left-arrow
        @click-left="onClickLeft"
      />
      <br>
      <h3 style="margin-left: 5%">开通会员,权益享不挺</h3>
        <div id="vipPay">
          <div class="vipDesc">
            <van-grid :column-num="3"
                      :border=false>
              <van-grid-item icon="vip-card-o"
                             text="标签无上限"
                             style="color:#60b86a" />
              <van-grid-item icon="discount"
                             text="视频文件大于 10M"
                             style="color:#60b86a" />
              <van-grid-item icon="gem-o"
                             text="图片文稿大于 1M"
                             style="color:#60b86a" />
            </van-grid>
          </div>
        </div>
        <br>
        <br>
        <h3 style="margin-left: 5%">会员类型</h3>
        <br>
        <div class="vipType">
               <van-checkbox-group v-model="checks" bind:change="onChange">
                  <van-cell
                        @click=""
                        title="一年期会员"
                        label="￥120">
                    <van-checkbox name="a"  style="float: right;margin-right: 13%;margin-top: 10px" /></van-cell>
                </van-checkbox-group>
                
         </div>
         <div>
          <!-- <van-radio-group v-model="radio">
          <van-cell-group title="支付方式">
            <van-cell clickable
                      @click="radio = '1'">
              <template slot="title">
                <img src="./../../images/order/wx.png"
                     alt=""
                     width="25px"
                     height="25px"
                     style=" vertical-align: middle;padding-right:5px">
                <span>微信支付</span>
              </template>
              <van-radio slot="right-icon"
                         name="1"
                         checked-color="#07c160" />
            </van-cell>
            <van-cell clickable
                      @click="radio = '2'">
              <template slot="title">
                <img src="./../../images/order/zfb.png"
                     alt=""
                     width="25px"
                     height="25px"
                     style=" vertical-align: middle;padding-right:5px">
                <span>支付宝</span>
              </template>
              <van-radio slot="right-icon"
                         name="2"
                         checked-color="#07c160" />
            </van-cell>
            <van-cell clickable
                      @click="radio = '3'">
              <template slot="title">
                <img src="./../../images/order/hb.png"
                     alt=""
                     width="25px"
                     height="25px"
                     style=" vertical-align: middle;padding-right:5px">
                <span>花呗</span>
              </template>
              <van-radio slot="right-icon"
                         name="3"
                         checked-color="#07c160" />
            </van-cell>
          </van-cell-group>
        </van-radio-group> -->
        </div>
      <van-popup v-model="show">
        <div class="pay_password" >
            <!-- 密码输入框 -->
            <div class="password_input">
                <h3 style="text-align: center;margin-bottom: 30px">￥120</h3>
                <van-password-input
                        :value="value"
                />
            </div>
            <!--键盘-->
            <van-number-keyboard
                    :show="show"
                    @blur="show = false"
                    @input="onInput"
                    @delete="onDelete"
            />
        </div>
    </van-popup>

    <van-submit-bar
        :price="12000"
        button-text="立即支付"
        @submit ="save"
      />
  </div>
</template>
<script type="text/javascript">
  import { Dialog, Toast } from 'vant'
import { insertFeedBack } from '../../api/feedBack';
import { createOrders } from '../../api/orders';
import { updateOrderStatus } from '../../api/orders';


export default {
  data () {
    return {
      show:false,
      radio:'',
      typeArray: [],
      // 路由传递过来的数据 active
      active:'',
      value:'',
      itemsTitle:'',
      message:'',
      checks: ['a']
    }
  },
  created(){
    this.getData()
  },
  methods: {
    save() {
      // if (this.radio=='') {
      //   Toast('支付方式不能为空');
      //   return false;
      // }
        this.show=true;     
    },
    onClickLeft () {
      this.$router.back();
    },
    getData(){

    },
     back() {
      this.$router.go(-1);
    },
    onInput(key) {
      this.value = (this.value + key).slice(0, 6);
      if (this.value.length == 6) {
            let param = {
                password: this.value,
            }
          updateOrderStatus(param).then(resg => {
            if (resg.code==0) {
                 Toast('支付成功');
                 var info = JSON.parse(localStorage.getItem('userInfo'));
                 info.typeName = '会员'
                 info.userType= 1
                  localStorage.setItem('userInfo',JSON.stringify(info))
                 this.show=false;
                 this.value=''
                 this.getData()
            }else{
              Toast(resg.msg);
            }
          });
      }else {
        this.errorInfo = '';
      }
    },
    onDelete() {
      this.value = this.value.slice(0, this.value.length - 1);
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
.vipDesc {
    position: relative;
    padding-top: 1rem;
    width: 100%;
    background-color: #ffffff;
    p {
      text-align: center;
      font-size: 0.8rem;
      font-weight: bolder;
    }
  }
  .pay_password {
  background: #FAFAFA;
  position: fixed;
  left: 0;
  bottom: 0;
  width: 100%;
  height: 63%;
}

.password_input {
  position: fixed;
  left: 0;
  bottom: 250px;
  width: 100%;
}
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
.van-popup {
            transform: none;
        }
 
</style>
