<template>
  <div id="collection">
     <van-nav-bar
        title="我的标签"
        left-text="返回"
        left-arrow
        @click-left="onClickLeft"
      />
      <div class="content">
          <van-cell-group>
            <van-field
              center
              clearable
              v-model="addForm.labelName"
              label="新标签"
              placeholder="请输入标签名"
             >
              <van-button slot="button" size="small" @click="save" type="primary">创建新标签</van-button>
            </van-field>
          </van-cell-group><br>
          <div v-for="item  in tagList">
            <van-swipe-cell>
                  <van-cell
                    @click="goToPage('choiseNote',item.id)"
                    :title="item.labelName"
                    :label="item.createTime"
                  />
                  <template slot="right">
                    <van-button square type="primary"
                      @click="handlerlabel(item)"
                      square
                      text="重命名"
                    />
                    <van-button square type="danger"
                      @click="removeTag(item.id)"
                      square
                      text="删除"
                    />
                  </template>
                </van-swipe-cell>
          </div>
      </div> 

     <van-popup v-model="show" class="model">
      <div>
        <br>
        <h3 style="margin-left: 36%">重命名</h3><br>
      <van-field 
        v-model="updateForm.labelName"
        autosize
        label="标签名:"
        type="textarea"
        maxlength="512"
        placeholder="请输入新标签名"
        show-word-limit
      />
    </van-cell-group>
      <van-row type="flex" class="padding"  justify="center">
        <van-button @click="show=false" style="width: 50%">取消</van-button>
        <van-button style="float: right;width: 50%" type="primary" @click="updateName">确定</van-button>
      </van-row>
      <br>
      </div>
    </van-popup>
    </div>
</template>
<script type="text/javascript">
  import { Dialog, Toast } from 'vant'
import { fetchData } from '../../api/tag';
import { deleteTag } from '../../api/tag';
import { insertLabel } from '../../api/tag';
import { updateLabel } from '../../api/tag';

export default {
  data () {
    return {
      tagList: [
      ],
      // 路由传递过来的数据 active
      active:'',
      itemsTitle:'',
      query:{},
      addForm:{
        labelName:''
      },
      show:false,
      updateForm:{
        labelName:'',
        id:''
      }
    }

  },
  created(){
    this.getData()
  },
  methods: {
    onClickLeft () {
      this.$router.back();
    },
    getData(){
       fetchData(this.query).then(resg => {
            if (resg.code==0) {
                this.tagList = resg.data
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
     goToPage (name,id) {
      localStorage.setItem('noteId',id);
      this.$router.push({ name });
    },
    removeTag(item){
      console.log(item)
      let param= {
          id: item}
        Dialog.confirm({
          title: '删除',
          message: '确定删除吗？'
        }).then(() => {
            deleteTag(param).then(resg => {
            if (resg.code==0) {
                Toast({
                  message: this.$t(resg.msg),
                  duration: 800
              });
                this.getData();
            }else{
              Toast({
                  message: this.$t(resg.msg),
                  duration: 800
              });
            }});
        }).catch(() => {
          // on cancel
        });
    },
    save(){
            if (this.addForm.labelName=='') {
             Toast({
                    message: this.$t('新标签名不能为空'),
                    duration: 800
                });  
                  return false 
              }
         insertLabel(this.addForm).then(resg => {
            if (resg.code==0) {
                Toast({
                  message: this.$t(resg.msg),
                  duration: 800
              });
                this.getData();
                this.addForm.labelName=''
            }else{
              Toast({
                  message: this.$t(resg.msg),
                  duration: 800
              });
            }});
    },
    handlerlabel(item){
        this.updateForm.labelName= item.labelName
        this.updateForm.id=item.id;
        this.show= true
    },
    updateName(){
       if (this.updateForm.labelName=='') {
             Toast({
                    message: this.$t('标签名不能为空'),
                    duration: 800
                });  
              return false 
          }
        updateLabel(this.updateForm).then(resg => {
            if (resg.code==0) {
                Toast({
                  message: this.$t(resg.msg),
                  duration: 800
              });
                this.getData();
                this.updateForm.labelName=''
                this.show=false
            }else{
              Toast({
                  message: this.$t(resg.msg),
                  duration: 800
              });
            }});

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
.model{
  height:180px%;
  width:70%;
}

</style>
