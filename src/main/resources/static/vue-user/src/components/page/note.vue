<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 文稿
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container" style="width:18%;height:600px;float: left;">
                <el-menu  class="el-menu-demo" mode="horizontal">
                      <el-submenu index="2">
                        <template slot="title"><i class="el-icon-plus"></i>新文稿</template>
                        <el-menu-item index="2-1" @click="addNote">图文文稿</el-menu-item>
                        <el-menu-item index="2-2" @click="uploadImagesDialog=true">上传图片</el-menu-item>
                        <el-menu-item index="2-3" @click="uploadWordDialog=true">导入word</el-menu-item>
                        <el-menu-item index="2-4" @click="uploadShiPinDialog=true">上传视频</el-menu-item>
                        <el-menu-item index="2-4" @click="createFolderDialog=true">新建文件夹</el-menu-item>
                      </el-submenu>
                    </el-menu>
                    <br />
            <el-collapse v-model="pageTotal" accordion>
                  <el-collapse-item title="我的资源">
                        <el-collapse v-for="item in tableData" accordion>
                            <el-collapse-item  style="margin-left: 15px;">
                                    <template slot="title">
                                        {{item.name}}
                                        <span style="margin-left: 50%">
                                            <el-dropdown @command="handleCommand">
                                                <i  class="el-icon-circle-plus-outline"></i>
                                               <el-dropdown-menu slot="dropdown">
                                                <el-dropdown-item :command="beforeHandleCommand(1,item)">重命名</el-dropdown-item>
                                                <el-dropdown-item :command="beforeHandleCommand(2,item)">删除</el-dropdown-item>
                                              </el-dropdown-menu>
                                            </el-dropdown>
                                        </span>
                                    </template>
                                    <br>
                                    <div v-for="test in item.noteList">
                                        <i @click="noteUpdate(test)">{{test.noteTitle}}</i>
                                        <span style="float: right;">
                                        <el-dropdown @command="handleNoteCommand">
                                                <i  class="el-icon-circle-plus-outline"></i>
                                               <el-dropdown-menu slot="dropdown">
                                                    <el-dropdown-item :command="test">删除</el-dropdown-item>
                                              </el-dropdown-menu> 
                                          </el-dropdown>
                                          </span>
                                         <el-divider></el-divider>
                                    </div>

                              </el-collapse-item>
                        </el-collapse>
                  </el-collapse-item>
            </el-collapse>
        </div>
        <div class="container" style="width: 60%;height: 100%;float: left;">
            <el-form ref="form" :model="form" label-width="80px">
                <el-input v-model="noteForm.noteTitle" placeholder="请输入标题"></el-input><br><br>
                <i v-if="noteForm.noteType==0">
                <quill-editor ref="myTextEditor" v-model="noteForm.noteContent" :options="editorOption"></quill-editor>
                <br>
                <el-button class="editor-btn" type="primary" @click="sumbitNoteData">提交</el-button>
                 </i>
                 <i v-if="noteForm.noteType==1">
                     <img :src="noteForm.filePath" />
                 </i>
                 <i v-if="noteForm.noteType == 2"> 
                            <video  style="width: 100%;height: 400px"      
                                     class="video-js vjs-default-skin vjs-big-play-centered" 
                                     controls 
                                     preload="auto"  
                                     autoplay= "muted"  
                                    >          
                                <source :src="noteForm.filePath" type="video/mp4" />  
                            </video>     
                        </i> 
                 <br>
                  <br>
                   <br>
            </el-form>
        </div>
        <!-- 上传图片 -->
        <el-dialog title="上传图片" :visible.sync="uploadImagesDialog" width="50%">
           <el-form :model="images">
                    <el-upload
                          class="upload-demo"
                          ref="uploadImages"
                          drag
                          :data="images" 
                           :on-preview="handlePreview"
                           :onhandleChange="handleChange"
                           :on-success="successMethod"
                           :on-error="errorMethod"
                          action="http://localhost:8080/api/users/usersUploadFile"
                          :multiple="false"
                          :auto-upload="false"
                          :limit="1"
                        >
                          <i class="el-icon-upload"></i>
                          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                          <div class="el-upload__tip" slot="tip">只能上传jpg,png文件</div>
                    </el-upload>
                    <br/>
                    <br />
                    <el-button style="margin-left: 70%;" size="small" type="success" @click="uploadImagess">开始上传</el-button>
                </el-form>
        </el-dialog>

        <el-dialog title="上传视频" :visible.sync="uploadShiPinDialog" width="50%">
           <el-form :model="shipin">
                    <el-upload
                          class="upload-demo"
                          ref="uploadShiPin"
                          drag
                          :data="shipin" 
                           :on-preview="handlePreview"
                           :onhandleChange="handleChange"
                           :on-success="successMethod"
                           :on-error="errorMethod"
                          action="http://localhost:8080/api/users/usersUploadFile"
                          :multiple="false"
                          :auto-upload="false"
                          :limit="1"
                        >
                          <i class="el-icon-upload"></i>
                          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                          <div class="el-upload__tip" slot="tip">只能上传MP4文件</div>
                    </el-upload>
                    <br/>
                    <br />
                    <el-button style="margin-left: 70%;" size="small" type="success" @click="uploadShiPins">开始上传</el-button>
                </el-form>
        </el-dialog>

        <!-- WORD 转换 -->
        <el-dialog :visible.sync="uploadWordDialog" width="30%">
                <el-form>
                    <el-upload
                          class="upload-demo"
                          ref="upload"
                          drag
                           :on-preview="handlePreview"
                           :onhandleChange="handleChange"
                           :on-success="successMethod"
                           :on-error="errorMethod"
                          action="http://localhost:8080/api/users/insertNoteByWord"
                          :multiple="false"
                          :auto-upload="false"
                          :limit="1"
                        >
                          <i class="el-icon-upload"></i>
                          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                          <div class="el-upload__tip" slot="tip">只能上传doc,docx文件</div>
                    </el-upload>
                    <br/>
                    <br />
                    <el-button style="margin-left: 70%;" size="small" type="success" @click="submitUpload">开始上传</el-button>
                </el-form>
        </el-dialog>
        <el-dialog title="新建文件夹" :visible.sync="createFolderDialog" width="30%">
            <el-form :model="file">
                <el-form-item label="文件夹名">
                    <el-input v-model="file.folderName"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="createFolder">创 建</el-button>
            </span> 
             </el-form>
        </el-dialog> 

        <el-dialog title="编辑文件夹" :visible.sync="updateFolderDialog" width="30%">
            <el-form :model="updateFileForm">
                <el-form-item label="文件夹名">
                    <el-input v-model="updateFileForm.name"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="updateFolder">更 新</el-button>
                 <el-button @click="updateFolderDialog = false">关 闭</el-button>
            </span> 
             </el-form>
        </el-dialog>   
    </div>
</template>

<script>
import 'quill/dist/quill.core.css';
import 'quill/dist/quill.snow.css';
import 'quill/dist/quill.bubble.css';
import { quillEditor } from 'vue-quill-editor';
import { fetchData } from '../../api/note';
import { updateData } from '../../api/note';
import { deleteData } from '../../api/note';
import { addData } from '../../api/note';
import { deleteNote } from '../../api/note';
import { updateNote } from '../../api/note';

export default {
    name: 'basetable',
    data() {
        return {
            query: {
                noteType: '',
            },
            editorOption: {
                placeholder: 'Hello World'
            },
            shipin:{
                noteType: 2
            },
            images:{
                noteType: 1
            },
            file:{
                folderName: ''
            },
            updateFileForm:{},
            tableData: [],
            multipleSelection: [],
            delList: [],
            updateFolderDialog: false,
            editVisible: false,
            addUser: false,
            uploadShiPinDialog: false,
            uploadImagesDialog: false,
            uploadWordDialog: false,
            createFolderDialog: false,
            pageTotal: 0,
            noteForm:{
                noteTitle: '',
                noteContent: '',
                noteType: '',
            },
            payName: '',
            updateForm:{},
            form: {
                note:{}   
            },
        };
    },
    components: {
        quillEditor
    },
    created() {
        this.getData();
    },
    methods: {
        // 获取 easy-mock 的模拟数据
        getData() {
            fetchData(this.query).then(res => {
                this.tableData = res.data;
                this.pageTotal = res.data.length;
            });
        },
        noteUpdate(row){
            var forms = this.noteForm
            forms.noteContent= row.noteContent
            forms.noteType = row.noteType;
            forms.noteTitle = row.noteTitle;
            forms.filePath = row.filePath
            forms.id = row.id
        },
        addNote(){
            var forms = this.noteForm;
            forms.noteType= 0;
        },
        handleSearch(){
            this.getData();
        },
        selectState(state,payName){
            this.payName = payName;
            this.query.noteType =state;
        },
        reSet(){
            this.query.noteType = '';
            this.payName =''
        },
        // 编辑操作
        handleEdit(index, row) {
            this.idx = index;
            this.form = row;
            this.editVisible = true;
        },
        // 保存编辑
        saveEdit() {

        },
        // 分页导航
        handlePageChange(val) {
            this.$set(this.query, 'pageIndex', val);
            this.getData();
        },
        submitUpload() {
            var file =this.$refs.upload.uploadFiles[0];
            var a =(file.name+'').split(".");
            var length = a.length;
            if (this.$refs.upload.uploadFiles.length>1) {
                this.$message.error('不支持多个文件');
                return false;
            }
            if (a[length-1]!='docx' ) {
                this.$message.error('文件类型不支持');
                return false;
            }
            this.$refs.upload.submit();
            this.uploadWordDialog =false
        },
        handlePreview(file) {
            console.log(file.name);
          },
        handleChange(file, fileList) {
            this.fileList = fileList.slice(-1);
          },
        successMethod(){
            this.$message.success('上传成功!请刷新');
            this.$refs.upload.uploadFiles=[];
            this.aaa.description=''
            this.aaa.version=''
            this.getData();
          },
        errorMethod(err, file, fileList){
            this.$message.error(err);
        },
        uploadImagess() {
            console.log(this.$refs.uploadImages.uploadFiles[0])
            var file =this.$refs.uploadImages.uploadFiles[0];
            var a =(file.name+'').split(".");
            var length = a.length;
            if (this.$refs.uploadImages.uploadFiles.length>1) {
                this.$message.error('不支持多个文件');
                return false;
            }
            if (!(a[length-1]=='jpg'|| !a[length-1]=='png' )) {
                this.$message.error('文件类型不支持');
                return false;
            }
            this.$refs.uploadImages.submit();
            this.getData()
            this.uploadImagesDialog=false
        },
        uploadShiPins() {
            var file =this.$refs.uploadShiPin.uploadFiles[0];
            var a =(file.name+'').split(".");
            var length = a.length;
            if (this.$refs.uploadShiPin.uploadFiles.length>1) {
                this.$message.error('不支持多个文件');
                return false;
            }
            if (a[length-1]!='docx' ) {
                this.$message.error('文件类型不支持');
                return false;
            }
            this.$refs.uploadShiPin.submit();
            this.getData();
            this.uploadShiPinDialog= false
        },
        createFolder(){

            if (this.file.folderName=='') {
                this.$message.error('文件名不能为空');
                return false;
            }
             addData(this.file).then(res => {
                        if (res.code !=0) {
                             this.$message.error(res.msg);
                        }else{
                           this.$message.success(res.msg);
                           this.file.folderName=''
                           this.getData();
                           this.createFolderDialog=false
                    }
            });
        },
        beforeHandleCommand(index,row){
          return {
             'index': index,
             'row': row
          }
        },
        handleCommand(command){
             switch (command.index) {
                  case 1:
                    console.log("1231")
                  　　// 设备订单
                  　　const aa ={
                            id: command.row.id,
                            name: command.row.name
                        }
                        this.updateFileForm = aa
                        this.updateFolderDialog =true
                   　　break;
                   case 2:
                        this.deleteFolder(command.row.id)                 　　
                   　　break;
                 }
            
        },
        deleteFolder(ids){
             let param = {id: ids}
            // 二次确认删除
            this.$confirm('确定要删除吗？', '提示', {
                type: 'warning'
            })
                .then(() => {
                    deleteData(param).then(res => {
                        if (res.code !=0) {
                             this.$message.error(res.msg);
                        }else{
                           this.$message.success(res.msg);
                           this.getData();
                        }
                    });

                })
                .catch(() => {});
        },
        updateFolder(){
            if (this.updateFileForm.folderName=='') {
                this.$message.error('文件名不能为空');
                return false;
            }
             updateData(this.updateFileForm).then(res => {
                        if (res.code !=0) {
                             this.$message.error(res.msg);
                        }else{
                           this.$message.success(res.msg);
                           this.updateFileForm={}
                           this.getData();
                           this.updateFolderDialog=false
                    }
                });
        },
        handleNoteCommand(command){
            this.removeNote(command)
        },
        //删除笔记
        removeNote(row){
            let param = {
                id : row.id
            }
              this.$confirm('确定要删除吗？', '提示', {
                type: 'warning'
            })
                .then(() => {
            deleteNote(param).then(res => {
                        if (res.code !=0) {
                             this.$message.error(res.msg);
                        }else{
                           this.$message.success(res.msg);
                           this.getData();
                    }
                }); 
            }).catch(() => {});   
        },
        sumbitNoteData(){
            var forms = this.noteForm
            forms.noteType = 0
            if (forms.noteContent =='') {
                this.$message.error('内容不能为空');
                return false;
            }
             if (forms.noteTitle  =='') {
                this.$message.error('标题不能为空');
                return false;
            }
                
            updateNote(forms).then(res => {
                        if (res.code !=0) {
                             this.$message.error(res.msg);
                        }else{
                           this.$message.success(res.msg);
                            this.noteForm.noteType = 0
                           this.getData();
                    }
                }); 
        }
    }
};
</script>

<style scoped>
.handle-input {
    width: 150px;
}
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 120px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}
.table {
    width: 100%;
    font-size: 14px;
}
.red {
    color: #ff0000;
}
.mr10 {
    margin-right: 10px;
}
.table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
}
</style>
