<template>
  <div id="myOrder">
     <van-nav-bar
        title="我的订单"
        left-text="返回"
        left-arrow
        @click-left="onClickLeft"
      />
    <van-tabs active="a">
      <van-tab title="待支付" name="a">
        <div v-for="item in orderListByNotPay">
           <van-cell title="一年期会员" value="内容" size="large" :label="item.orderNumber" >
            <van-button type="warning" @click="pay(item)" >支付</van-button>
          </van-cell>
        </div>
      </van-tab>
      <van-tab title="已支付" name="b">
        <div v-for="item in orderListBypay">
           <van-cell title="一年期会员" value="内容" size="large" :label="item.orderNumber" >
            <van-button disabled  type="warning">已支付</van-button>
          </van-cell>
        </div>
      </van-tab>
    </van-tabs>

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
    </div>

</template>
<script type="text/javascript">
  import { Dialog, Toast } from 'vant'
import { updateUserInfo } from '../../api/userInfo';
import { getUserInfo } from '../../api/login';
import { fetchData } from '../../api/orders';
import { updateOrderStatus } from '../../api/orders';

export default {
  data () {
    return {
      show: false,
      typeArray: [],
      orderListBypay:[],
      orderListByNotPay:[],
      value:'',
      showKeyboard: false,
      win: false,
      errorInfo: '',
      // 路由传递过来的数据 active
      active:'',
      itemsTitle:'',
      userInfo:{
        email:'22',
        userName:'222',
        age:'',
        region:'',
        balances:'1',
      },
      tempInfo:{}
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
        this.orderListBypay=[]
        this.orderListByNotPay=[]
        fetchData(this.userInfo).then(resg => {
            if (resg.code==0) {
                    var list =resg.data;
                  for (var i =0 ; i <list.length; i++) {
                      if (list[i].payState==0) {
                          this.orderListByNotPay.push(list[i])

                      }else{
                          this.orderListBypay.push(list[i])
                      }
                  }
            }else{
              Toast({
                  message: this.$t(resg.msg),
                  duration: 800
              });
            }});

    },
    save(){
       
    },
     back() {
      this.$router.go(-1);
    },
    onInput(key) {
      console.log(key)
      this.value = (this.value + key).slice(0, 6);
      if (this.value.length == 6) {
            let param = {
                password: this.value,
                orderNumber: this.tempInfo.orderNumber
            }
          updateOrderStatus(param).then(resg => {
            if (resg.code==0) {
                 Toast('支付成功');
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
    chongzhi(){
      this.win= true;
    },
    goToPage (name) {
      this.$router.push({ name });
    },
    pay(item){
      this.show=true;
      this.tempInfo =item
    }
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
.van-popup {
            transform: none;
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
</style>
