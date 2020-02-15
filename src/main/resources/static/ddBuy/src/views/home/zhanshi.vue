<template>
  <div class="content">
      <van-nav-bar  v-if="isMe==true"
        title="文稿"
        left-text="返回"
        right-text="保存"
        left-arrow
        @click-left="onClickLeft"
        @click-right="save"
      />
      <van-nav-bar  v-if="isMe==false"
        title="文稿"
        left-text="返回"
        left-arrow
        @click-left="onClickLeft"
      />
    
      <div v-if="noteInfo.noteType==0">
            <van-cell-group>
                <van-field v-if="isMe==true"
                  v-model="noteInfo.noteTitle"
                  placeholder="请输入标题"
                />
                <van-field v-if="isMe==false"
                  v-model="noteInfo.noteTitle"
                  placeholder="请输入标题" disabled
                />
              </van-cell-group>
            <br>
            <div style="width: 100%">
               <vue-html5-editor :content="noteInfo.noteContent" disabled :auto-height="true" :show-module-name="false" @change="updateData"></vue-html5-editor>
            <br>
            </div>
        </div>
        <div v-if="noteInfo.noteType==1">
           <img style="width: 100%;height: 50%px" :src="noteInfo.filePath">
        </div>

        <div v-if="noteInfo.noteType==3">
           
        </div>

        </div>
  </div>
</template>

<script>
import { createNote } from '../../api/note';
import { Dialog, Toast } from 'vant'
import { updateNote } from '../../api/note';

export default {
    created(){
        var info = JSON.parse(localStorage.getItem('noteInfo'))
        this.noteInfo.id=info.id;
        this.noteInfo.noteTitle=info.noteTitle;
        this.noteInfo.noteContent=info.noteContent;
        this.noteInfo.noteType=info.noteType;
        if (info.noteTitle!=0) {
           this.noteInfo.filePath=info.filePath;
        }
        console.log(info)
        var userInfo =JSON.parse(localStorage.getItem('userInfo'))
        console.log(info.usersId+"   "+userInfo.id)
        if (info.userId==undefined) {
             if (info.usersId==userInfo.id) {
              this.isMe=true
            }
        }else{
           if (info.userId==userInfo.id) {
              this.isMe=true
            }
        }
       
    },
    data() {
      return {
        isMe: false,
        messages: [],
        noteInfo:{
          noteTitle:'',
          noteContent:'请输入内容',
          noteType: 1,
          id:'',
          filePath:'',
        }
      }
    },
   
    methods: {
      onClickLeft () {
      this.$router.back();
    },
    updateData(e){  
      console.log(e)
        this.noteInfo.noteContent=e  
    },
    save(){
        if (this.noteInfo.noteContent=='') {
            Toast('内容不能为空');
            return false;
        }
        if (this.noteInfo.noteTitle=='') {
            Toast('标题不能为空');
            return false;
        }
        updateNote(this.noteInfo).then(resg => {
            if (resg.code==0) {
                Toast('保存成功');
            }else{
              Toast({
                  message: this.$t(resg.msg),
                  duration: 800
              });
            }});

    },


} 
}
</script>

<style lang="scss">


</style>
