<template>
  <div id="collection">
      <van-nav-bar
        title="导入 word"
        left-text="返回"
        left-arrow
        @click-left="onClickLeft"
      />
        <br>
        <br>
      <div>
      <van-uploader style="margin-left: 32%;margin-top: 30%" :after-read="afterRead" accept="file/*" >
        <van-button icon="photo" type="primary">导入 word</van-button>
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
    let formData = new FormData()
    formData.append('file', file.file)
    let config = {
      headers: {
          'Content-Type': 'multipart/form-data',
          'token': localStorage.getItem('token')
        }
    }; //添加请求头
    axios.post('http://localhost:8080/api/users/insertNoteByWord',formData,config)
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
