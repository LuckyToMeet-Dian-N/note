<template>
    <div class="">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-copy"></i> 个人信息</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
         <el-form ref="form" :model="form" label-width="80px">
              <el-form-item label="用户名">
                <el-input v-model="form.userName" style="width: 200px"></el-input>
            </el-form-item>
             <el-form-item label="账户类型">
               <!--  <el-input v-model="form.userType" style="width: 200px"></el-input> -->
               <i v-if="form.userType==0">
                    <el-tag type="success">非会员</el-tag>
               </i>
                <i v-if="form.userType==1">
                    <el-tag type="success">会员</el-tag>
               </i>
            </el-form-item>
             <el-form-item label="邮箱">
                <el-input v-model="form.email" :disabled="true" style="width: 200px"></el-input>
            </el-form-item>
             <el-form-item label="年龄">
                <el-input v-model="form.age" style="width: 200px"></el-input>
            </el-form-item>
             <el-form-item label="账户余额">
                <el-input v-model="form.balances"  :disabled="true" style="width: 200px"></el-input>
            </el-form-item>
            <el-form-item label="所在地区">
                <el-input v-model="form.region" style="width: 200px"></el-input>
            </el-form-item>
             <el-form-item label="注册时间">
                <el-input v-model="form.createTime" :disabled="true" style="width: 200px"></el-input>
            </el-form-item>
            <br>
            <span style="float: left;">
               <el-button type="primary" @click="updateUserInfo">保 存</el-button>
            </span>
            <br />
</el-form>
        </div>
</div>
</template>

<script>
import { updateData } from '../../api/userInfo';
import { fetchData } from '../../api/userInfo';
   
   export default {
    name: 'basetable',
    data() {
        return {
        form:{} 
        };
    },
    created() {
        this.getData();
    },
    methods: {
        // 获取 easy-mock 的模拟数据
        getData() {
            let a={}
            fetchData(a).then(res => {
                    this.form = res.data;
            });
        },
        updateUserInfo(){
            var update = this.form;
            if (update.region=='') {
                this.$message.success('地区不能为空');
                return false
            }
            if (update.userName=='') {
                this.$message.success('用户名不能为空');
                return false
            }
            if (update.region=='') {
                this.$message.success('年龄不能为空');
                return false
            }
            let param = {
                region: update.region,
                userName: update.userName,
                id: update.id,
                age: update.age
            }

            updateData(param).then(res => {
                        if (res.code == 0) {
                          this.$message.success('修改成功');
                          this.getData();
                        }else{
                          this.$message.error(res.msg);
                        }
                    }).catch(res=>{
                        console.log(res)
                    });
        }
    }
}
</script>

<style>
.message-title{
    cursor: pointer;
}
.handle-row{
    margin-top: 30px;
}
</style>

