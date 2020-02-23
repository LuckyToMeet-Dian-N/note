<template>
  <div id="collection">
      <van-nav-bar
        title="上传视频"
        left-text="返回"
        left-arrow
        @click-left="onClickLeft"
      />
        <br>
        <br>
      <div>
      <van-uploader style="margin-left: 32%;margin-top: 30%" :after-read="afterRead" accept="video/*" >
        <van-button icon="photo" type="primary">上传视频</van-button>
      </van-uploader>
       <van-uploader style="margin-left: 32%;margin-top: 20px"  :after-read="afterRead" capture="camera"  accept="video/*">
        <van-button icon="photo" type="primary">录制上传</van-button>
      </van-uploader>
      </div>

    </div>
</template>
<script type="text/javascript">
import { Dialog, Toast } from 'vant'
import { fetchData } from '../../api/collection';
import { deleteCollection } from '../../api/collection';
import { usersUploadFile } from '../../api/note';
import axios from 'axios';
import { Notify } from 'vant'

export default {
  data () {
    return {
      fileList:[ { url: 'https://img.yzcdn.cn/vant/leaf.jpg', name: '图片1' },],
      collectionList: [
      ],
      // 路由传递过来的数据 active
      active:'',
      itemsTitle:'',
    }
  },
  created(){
    this.getData()
  },
  methods: {
    afterRead (file) {
      
      var info = JSON.parse(localStorage.getItem('userInfo'))

      if (file.file.size>1 * 1024 * 1024*1024 &&info.userType==0) {
           Notify( {type: 'danger', message: '非会员视频上传不能超过 10M'} );
        return false;
      }

    let formData = new FormData()
    formData.append('file', file.file)
    formData.append('noteType', 2)
    let config = {
      headers: {
          'Content-Type': 'multipart/form-data',
          'token': localStorage.getItem('token')
        }
    }; //添加请求头
     
    axios.post('http://localhost:8080/api/users/usersUploadFile',formData,config)
    .then(response=>{
      console.log(response)
          if (response.data.code==0) {
                Notify( {type: 'primary', message: '上传成功'} );
            }else{
                Notify({ type: 'danger', message: '上传失败'} );
            }
     }).catch(err => {
              Toast({
                  message: this.$t('客户端错误，请重试'),
                    duration: 800
                });
      });
 
      },
    getData(){
       fetchData(this.query).then(resg => {
            if (resg.code==0) {
                this.collectionList = resg.data
            }else{
              Toast({
                  message: this.$t(resg.msg),
                  duration: 800
              });
            }});
    },
     onClickLeft() {
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
