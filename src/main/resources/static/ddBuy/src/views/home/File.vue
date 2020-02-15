<template>
  <div id="files">
      <van-nav-bar title="文件夹" left-arrow>
          <van-icon name="shop-collect-o" slot="left" />
       <van-icon name="shop-collect-o" slot="right" >
          <van-dropdown-menu>
            <van-dropdown-item >
                <van-cell title="创建文件夹" @click="show=true">
                </van-cell>
                
            </van-dropdown-item>
          </van-dropdown-menu>
        </van-icon>
        </van-nav-bar>
        
        <van-collapse v-model="activeName">
      <van-collapse-item title="所有文件夹" name="1">
        <van-collapse v-model="activeName1" accordion>
          <div
            v-for="(item,index) in fileList"
            :key="index"
            @touchstart="gtouchstart(item,index)"
            @touchmove="gtouchmove()"
            @touchend="gtouchend()"
          >
            <van-collapse-item :title="item.name" :name="index+1">
              <div v-for="(itemI,indexI) in item.noteList" :key="indexI">
                <van-swipe-cell>
                  <van-cell
                    @click="gotoNote(itemI)"
                    :title="itemI.noteTitle"
                    :label="itemI.createTime"
                  />
                  <template slot="right">
                    <van-button @click="removeNote(itemI.id)" square type="danger" text="删除" />
                    <van-button square type="info" text="邮件" @click="mails(itemI.id)" />
                    <van-button square type="warning" text="分享" @click="shares(itemI.id)" />
                    <van-button square type="primary" text="收藏" @click="collection(itemI.id)" />
                  </template>
                </van-swipe-cell>
              </div>
            </van-collapse-item>
          </div>
        </van-collapse>
      </van-collapse-item>
    </van-collapse>

   <van-popup v-model="show" class="model">
    <br>
    <h3 style="margin-left: 30%">创建文件夹</h3>
      <van-field v-model="file.folderName" maxlength="10" placeholder="输入文件夹名称" />
      <br>
      <span >
          <van-button class="center" style="width: 50%;float: left;"  @click="show=false">取消</van-button>
          <van-button class="center" style="width: 50%;float:right;"  type="primary"  @click="createFiles">创建</van-button>
      </span>
    </van-popup>
    <van-popup v-model="update" class="model">
        <br>
        <h3 style="margin-left: 30%">重命名</h3>
          <van-field v-model="updateForm.name" maxlength="10" placeholder="输入文件夹名称" />
          <br>
          <span >
              <van-button class="center" style="width: 50%;float: left;"  @click="update=false">取消</van-button>
              <van-button class="center" style="width: 50%;float:right;"  type="primary"  @click="updateFiles">确定</van-button>
          </span>
    </van-popup>


    <van-popup v-model="mailDiago" class="model">
        <br>
        <h3 style="margin-left: 30%">收件人邮箱</h3>
          <van-field v-model="send.eMail"  placeholder="请输入收件人邮箱" />
          <br>
          <span >
              <van-button class="center" style="width: 50%;float: left;"  @click="mailDiago=false">取消</van-button>
              <van-button class="center" style="width: 50%;float:right;"  type="primary"  @click="sendMails">发送</van-button>
          </span>
    </van-popup>

        <van-action-sheet
          v-model="showAction"
          :actions="actions"
          cancel-text="取消" 
          @select="onSelect"
        />

    </div>
</template>
<script type="text/javascript">
  import { Dialog, Toast } from 'vant'
import { fetchData } from '../../api/note';
  import { ActionSheet } from 'vant'
import { deleteFile } from '../../api/note';
import { updateFile } from '../../api/note';
import { createFile } from '../../api/note';
import { deleteNote } from '../../api/note';
import { insertShare } from '../../api/share';
import { insertCollection } from '../../api/collection';
import { sendEmail } from '../../api/note';

export default {
  data () {
    return {
      fileList: [
      ],
      file:{
          folderName: ''
      },
      updateForm:{
        name:'',
        id:''
      },
      send:{
          eMail: '',
          noteId: ''
      },
      mailDiago: false,
      update: false,
      // 路由传递过来的数据 active
      active:'',
      itemsTitle:'',
      activeName: ['1'],
      activeName1:['1'],
      actions: [{ name: '重命名', color: '07c160' },{ name: '删除', color: '07c160' }],
      show: false,
      showAction: false,
      tempInfo:{}
    }
  },
  created(){
    this.getData()
  },
  methods: {
    gotoNote(item){
      localStorage.setItem('noteInfo',JSON.stringify(item))
      this.$router.push('zhanshi');
    },
    getData(){
         fetchData(this.query).then(resg => {
            if (resg.code==0) {
                 this.fileList = resg.data;
            }else{
              Toast({
                  message: this.$t(resg.msg),
                  duration: 800
              });
            }
          });
    },
     back() {
      this.$router.go(-1);
    },
     gtouchstart(item,index) {
      let param = {
          item :item,
          index: index
      }
      this.timeOutEvent = setTimeout(() => {
        this.longPress(param);
      }, 500);
      return false;
    }, 
    longPress(index) {
      this.timeOutEvent = 0;
      this.tempInfo= index
      console.log("长按事件", index);
      this.showAction = true;
    },
    //手释放，如果在500毫秒内就释放，则取消长按事件，此时可以执行onclick应该执行的事件
    gtouchend() {
      clearTimeout(this.timeOutEvent); //清除定时器
      if (this.timeOutEvent != 0) {
      }
      return false;
    },
    gtouchmove() {
      clearTimeout(this.timeOutEvent); //清除定时器
      this.timeOutEvent = 0;
    },
    onSelect(event){
      console.log(event)
      var a = this.tempInfo;
      if (event.name =='重命名') {
        this.updateForm.id= a.item.id
        this.updateForm.name= a.item.name
          this.showAction= false;
          this.update =true
      }else{
        if (a.index==0) {
            Toast('默认文件夹不能删除');
            this.showAction= false;
            return false
        }else{
            let param={
              id: a.item.id
            }
        Dialog.confirm({
          title: "警告",
          message: "您确定要删除吗？"
        })
          .then(() => {
              deleteFile(param).then(resg => {
                if (resg.code==0) {
                   Toast('删除成功');
                   this.getData();
                   this.showAction=false
                }else{
                  Toast(resg.msg);
                }
              });
          })
          .catch(() => {
            // on cancel
          });
        }
      }
    },
    createFiles(){
      if (this.file.folderName=='') {
          Toast('文件夹名不能为空');
          return false
        }
        createFile(this.file).then(resg => {
            if (resg.code==0) {
               Toast('新增成功');
               this.getData();
               this.show=false
            }else{
              Toast(resg.msg);
            }
          });
    },
    updateFiles(){
        if (this.updateForm.name=='') {
          Toast('文件夹名不能为空');
          return false
        }
         updateFile(this.updateForm).then(resg => {
            if (resg.code==0) {
               Toast('修改成功');
               this.getData();
               this.update=false
            }else{
              Toast(resg.msg);
            }
          });
    },
    removeNote(id){
          let param={
            id: id
          }
      Dialog.confirm({
          title: "警告",
          message: "您确定要删除吗？"
        })
          .then(() => {
                deleteNote(param).then(resg => {
                    if (resg.code==0) {
                        Toast({
                          message: this.$t(resg.msg),
                          duration: 800
                      });
                        this.listNote=[]
                        this.getData()
                    }else{
                      Toast({
                          message: this.$t(resg.msg),
                          duration: 800
                      });
                    }
                  });
            })
          .catch(() => {
            // on cancel
          });
    },
    shares(id){
       let param={
            noteId: id
          }
          Dialog.confirm({
            title: "提示",
            message: "您确定要分享笔记吗？"
          })
          .then(() => {
                insertShare(param).then(resg => {
                    if (resg.code==0) {
                        Toast('分享成功');
                    }else{
                      Toast({
                          message: this.$t(resg.msg),
                          duration: 800
                      });
                    }
                  });
            })
          .catch(() => {
            // on cancel
          });
    },
    collection(id){
         let param={
            noteId: id
          }
          Dialog.confirm({
            title: "提示",
            message: "您确定要收藏笔记吗？"
          })
          .then(() => {
                insertCollection(param).then(resg => {
                    if (resg.code==0) {
                        Toast('收藏成功');
                    }else{
                      Toast({
                          message: this.$t(resg.msg),
                          duration: 800
                      });
                    }
                  });
            })
          .catch(() => {
            // on cancel
          });
    },
    sendMails(){


        if (this.send.eMail=='') {
          Toast('发送邮箱不能为空');
          return false
        }
      var reg = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
      if (!reg.test(this.send.eMail)) {
        // Notify({ type: "danger", message: "" });
        Toast('邮箱格式错误');
        return false;
      }

          Dialog.confirm({
            title: "提示",
            message: "您确定要发送至邮箱吗？"
          })
          .then(() => {
                sendEmail(this.send).then(resg => {
                    if (resg.code==0) {
                        Toast('发送成功');
                        this.mailDiago=false;
                    }else{
                      Toast({
                          message: this.$t(resg.msg),
                          duration: 800
                      });
                    }
                  });
            })
          .catch(() => {
            // on cancel
          });
    },
    mails(id){
        this.send.noteId = id;
        this.mailDiago =true

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
.model {
  width: 70%;
  height: auto;
}

</style>
