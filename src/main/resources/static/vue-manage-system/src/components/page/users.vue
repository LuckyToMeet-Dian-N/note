<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 基础表格
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <span style="float: right;"><el-button type="primary" icon="el-icon-search" @click="addUser=true">新建用户</el-button></span>
            </div>
            <br><br>
            <el-table
                :data="tableData"
                border
                class="table"
                ref="multipleTable"
                header-cell-class-name="table-header"
            >
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column prop="id" label="ID" width="55" align="center">
                    <template slot-scope="scope">
                        {{scope.row.id}}
                    </template>
                </el-table-column>
                <el-table-column prop="name" label="用户名">
                    <template slot-scope="scope">
                        {{scope.row.userName}}
                    </template>
                </el-table-column>
                 <el-table-column prop="age" label="年龄">
                    <template slot-scope="scope">
                        {{scope.row.age}}
                    </template>
                </el-table-column>
                
               <!--  <el-table-column label="头像(查看大图)" align="center">
                    <template slot-scope="scope">
                        <el-image
                            class="table-td-thumb"
                            :src="scope.row.thumb"
                            :preview-src-list="[scope.row.thumb]"
                        ></el-image>
                    </template>
                </el-table-column> -->
                <el-table-column prop="address" label="地址">
                        <template slot-scope="scope">
                        {{scope.row.region}}
                    </template>
                    </el-table-column>
                <el-table-column label="用户类型" align="center">
                    <template slot-scope="scope">
                        <i v-if="scope.row.userType==0">
                             <el-tag>普通会员</el-tag>
                        </i>
                        <i v-if="scope.row.userType==1">
                             <el-tag>付费会员</el-tag>
                        </i>
                        
                    </template>
                </el-table-column>
                <el-table-column label="账户余额">
                    <template slot-scope="scope">￥{{scope.row.balances}}</template>
                </el-table-column>
                <el-table-column prop="date" label="注册时间">
                          <template slot-scope="scope">
                                {{scope.row.createTime}}
                              </template>
            </el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">
                        <el-button
                            type="text"
                            icon="el-icon-edit"
                            @click="handleEdit(scope.$index, scope.row)"
                        >编辑</el-button>
                        <el-button
                            type="text"
                            icon="el-icon-delete"
                            class="red"
                            @click="handleDelete(scope.$index, scope.row)"
                        >删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
           <!--  <div class="pagination">
                <el-pagination
                    background
                    layout="total, prev, pager, next"
                    :current-page="query.pageIndex"
                    :page-size="query.pageSize"
                    :total="pageTotal"
                ></el-pagination>
            </div> -->
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="50%">
            <el-form ref="form" :model="form" label-width="70px">
                <el-form-item label="用户名">
                    <el-input v-model="form.userName"></el-input>
                </el-form-item>
                <el-form-item label="email" >
                    <el-input v-model="form.email" :disabled="true"></el-input>
                </el-form-item>
                <!-- <el-form-item label="密码">
                    <el-input v-model="form.password"></el-input>
                </el-form-item> -->
                <el-form-item label="年龄">
                    <el-input v-model="form.age"></el-input>
                </el-form-item>
                <el-form-item label="地址">
                    <el-input v-model="form.region"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 新增弹出框 -->
        <el-dialog title="编辑" :visible.sync="addUser" width="50%">
            <el-form ref="addForm" :model="addForm" label-width="70px">
                <el-form-item label="用户名">
                    <el-input v-model="addForm.userName" placeholder="请输入用户名"></el-input>
                </el-form-item>
                <el-form-item label="email">
                    <el-input v-model="addForm.email" type="email" placeholder="请输入邮箱"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="addForm.password" placeholder="请输入密码"></el-input>
                </el-form-item>
                <el-form-item label="二次密码">
                    <el-input v-model="addForm.againPassword" placeholder="请再次输入密码"></el-input>
                </el-form-item>
                <el-form-item label="年龄">
                    <el-input v-model="addForm.age" type="number" placeholder="输入年龄"></el-input>
                </el-form-item>
                <el-form-item label="地址">
                    <el-input v-model="addForm.region" placeholder="请输入地区"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addUser = false">取 消</el-button>
                <el-button type="primary" @click="submitForm">确 定</el-button>
            </span>
        </el-dialog>

    </div>
</template>

<script>
import { fetchData } from '../../api/index';
import { updateData } from '../../api/index';
import { addData } from '../../api/index';
import { deleteData } from '../../api/index';

export default {
    name: 'basetable',
    data() {
        return {
            query: {
            },
            tableData: [],
            multipleSelection: [],
            delList: [],
            editVisible: false,
            addUser: false,
            pageTotal: 0,
            addForm:{

            },
            updateForm:{},
            form: {},
            userTypes:[
                {
                    typeNmae: '付费会员',
                    userType: 1
                },
                {
                    typeNmae: '普通会员',
                    userType: 0
                }
            ],
            idx: -1,
            id: -1
        };
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
        // 删除操作
        handleDelete(index, row) {
            let param = {userId: row.id}

            // 二次确认删除
            this.$confirm('确定要删除吗？', '提示', {
                type: 'warning'
            })
                .then(() => {
                    // this.$message.success('删除成功');
                    // this.tableData.splice(index, 1);
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
        submitForm(){
             var form=this.addForm
            if (form.userName=='') {
                  this.$message.error("名字不能为空");
                  return false
             }
              if (form.age=='') {

                  this.$message.error("年龄不能为空");
                  return false

             }else{
                if (form.age>=150) {
                  this.$message.error("年龄不能大于150");
                  return false
                }
             }
             if (form.region=='') {
                  this.$message.error("地区不能为空");
                  return false
             }
              if (form.password=='') {
                  this.$message.error("密码不能为空");
                  return false
             }
              if (form.email=='') {
                  this.$message.error("email不能为空");
                  return false
             }
             if (form.password != form.againPassword) {
                  this.$message.error("两次密码不一致");
                  return false
             }

              addData(this.addForm).then(res => {
                        if (res.code !=0) {
                             this.$message.error(res.msg);
                        }else{
                           this.$message.success(res.msg);
                           this.getData();
                           this.addUser=false;
                        }
                    });  
        },
        // 编辑操作
        handleEdit(index, row) {
            this.idx = index;
            this.form = row;
            this.editVisible = true;
        },
        // 保存编辑
        saveEdit() {

            this.form.createTime=null
            this.form.updateData=null
           var update =  this.updateForm
           update.id = this.form.id;
            update.email = this.form.email
            update.region = this.form.region
            update.age= this.form.age;
            update.userName = this.form.userName

            updateData(update).then(res => {
                if (res.code !=0) {
                     this.$message.error(res.msg);
                }else{
                   this.$message.success(res.msg);
                   this.editVisible = false;
                   this.getData();
                }
            });

            // this.$set(this.tableData, this.idx, this.form);
        },
        // 分页导航
        handlePageChange(val) {
            this.$set(this.query, 'pageIndex', val);
            this.getData();
        }
    }
};
</script>

<style scoped>
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
