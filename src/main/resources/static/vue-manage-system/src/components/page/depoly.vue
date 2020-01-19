<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 发布管理
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div style="margin: auto; height: 30%;width: 30%">
                <el-form  :model="aaa">
                   <el-form-item label="版本描述">
                    <el-input v-model="aaa.description"></el-input>
                </el-form-item>
                <el-form-item label="版本号">
                    <el-input v-model="aaa.version"></el-input>
                </el-form-item
                 <el-form-item label="版本文件">
                    <el-upload
                          class="upload-demo"
                          ref="upload"
                          drag
                          :data="aaa" 
                           :on-preview="handlePreview"
                           :onhandleChange="handleChange"
                           :file-list="fileList"
                           :on-success="successMethod"
                           :on-error="errorMethod"
                          action="http://localhost:8080/api/admin/deployVersions"
                          :multiple="false"
                          :auto-upload="false"
                          :limit="1"
                        >
                          <i class="el-icon-upload"></i>
                          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                          <div class="el-upload__tip" slot="tip">只能上传apk文件</div>
                    </el-upload>
                </el-form-item>
                    <br/>
                    <br />
                    <el-button style="margin-left: 70%;" size="small" type="success" @click="submitUpload">开始发布</el-button>
                </el-form>
                
            </div>
          
        </div>
 </div>
</template>

<script>
import { fetchData } from '../../api/versions';
import { deleteData } from '../../api/versions';

export default {
    name: 'basetable',
    data() {
        return {
            aaa:{
                description: '',
                version: '',
            },
            fileList:[]
        };
    },
  
    methods: {
       submitUpload() {
        if (this.aaa.description=='') {
            this.$message.error('文件描述不能为空');
            return false;
        }
        if (this.aaa.version=='') {
            this.$message.error('版本号不能为空！');
            return false;
        }

        var file =this.$refs.upload.uploadFiles[0];
        var a =(file.name+'').split(".");
        var length = a.length;
        if (this.$refs.upload.uploadFiles.length>1) {
            this.$message.error('不支持多个文件');
            return false;
        }
       
        // if (a[length-1]!='apk') {
        //     this.$message.error('文件类型不支持');
        //     return false;
        // }
        this.$refs.upload.submit();
      },
      handlePreview(file) {
        console.log(file.name);
      },
      handleChange(file, fileList) {
        this.fileList = fileList.slice(-1);
      },
      successMethod(){
        this.$message.success('发布成功');
        this.$refs.upload.uploadFiles=[];
        this.aaa.description=''
        this.aaa.version=''
      },
      errorMethod(err, file, fileList){
        this.$message.error(err);
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