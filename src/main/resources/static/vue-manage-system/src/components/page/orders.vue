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
               用户id：&nbsp; <el-input v-model="query.userId" placeholder="用户id" style="width: 180px"></el-input>
               订单状态：&nbsp;
                <el-select v-model="query.payName" placeholder="请选择">
                    <el-option label="待支付" value="0" @click.native="selectState(0,'待支付')"></el-option>
                   <el-option label="已支付" value="1" @click.native="selectState(1,'已支付')" ></el-option>
                   <el-option label="取消订单" value="2" @click.native="selectState(2,'取消订单')" ></el-option>
                   <el-option label="订单超时" value="3" @click.native="selectState(3,'订单超时')" ></el-option>
                  </el-select>
                  &nbsp;&nbsp;
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
                &nbsp;&nbsp;<el-button type="primary" icon="el-icon-search" @click="reSet">重置</el-button>
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
                <el-table-column prop="address" label="订单号">
                        <template slot-scope="scope">
                        {{scope.row.orderNumber}}
                    </template>
                    </el-table-column>
                <el-table-column prop="name" label="用户id">
                    <template slot-scope="scope">
                        {{scope.row.userId}}
                    </template>
                </el-table-column>
                 <el-table-column prop="userName" label="用户名">
                    <template slot-scope="scope">
                        {{scope.row.userName}}
                    </template>
                </el-table-column>
                <!-- <el-table-column prop="payMoney" label="支付金额">
                        <template slot-scope="scope">
                        {{scope.row.payMoney}}
                    </template>
                    </el-table-column> -->
                <el-table-column prop="payState" label="状态">
                        <template slot-scope="scope">
                      
                        <i v-if="scope.row.payState==0">
                             <el-tag>待支付</el-tag>
                        </i>
                        <i v-if="scope.row.payState==1">
                             <el-tag>取消订单</el-tag>
                        </i>
                        <i v-if="scope.row.payState==2">
                             <el-tag>已支付</el-tag>
                        </i>
                        <i v-if="scope.row.payState==3">
                             <el-tag>已退款</el-tag>
                        </i>
                         <i v-if="scope.row.payState==4">
                             <el-tag>订单超时</el-tag>
                        </i>
                    </template>
                </el-table-column>
                <el-table-column label="创建时间">
                    <template slot-scope="scope">{{scope.row.createTime}}</template>
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
            <div class="pagination">
                <el-pagination
                    background
                    layout="total, prev, pager, next"
                    :current-page="query.pageIndex"
                    :page-size="query.pageSize"
                    :total="pageTotal"
                ></el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="50%">
            <el-form ref="form" :model="form" label-width="70px">
                <el-form-item label="订单id">
                    <el-input v-model="form.id" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="订单号">
                    <el-input v-model="form.orderNumber"  :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="支付状态">
                   <el-select v-model="payName" placeholder="请选择">
                        <el-option label="待支付" value="0" @click.native="selectStateByOrder(0,'待支付')"></el-option>
                       <el-option label="取消订单" value="1" @click.native="selectStateByOrder(1,'取消订单')" ></el-option>
                       <el-option label="已支付" value="2" @click.native="selectStateByOrder(2,'已支付')" ></el-option>
                       <el-option label="已退款" value="3" @click.native="selectStateByOrder(3,'已退款')" ></el-option>
                       <el-option label="订单超时" value="4" @click.native="selectStateByOrder(4,'订单超时')" ></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="支付金额">
                    <el-input v-model="form.payMoney"  :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="创建时间">
                    <el-input v-model="form.createTime"  :disabled="true"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import { fetchData } from '../../api/orders';
import { updateData } from '../../api/orders';
import { deleteData } from '../../api/orders';

export default {
    name: 'basetable',
    data() {
        return {
            query: {
                payState: '',
                payName:''

            },
            tableData: [],
            multipleSelection: [],
            delList: [],
            editVisible: false,
            addUser: false,
            pageTotal: 0,
            addForm:{

            },
            payName: '',
            updateForm:{},
            form: {
                
            },
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
            let param = {ordersId: row.id}

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
        handleSearch(){
            this.getData();
        },
        selectStateByOrder(state,payName){
            this.payName = payName;
            this.form.payState =state;
        },
        selectState(state,payName){
            this.query.payName = payName;
            this.query.payState =state;
        },
        reSet(){
            this.query.payName = '';
            this.query.payState = '';
            this.query.userId = '';
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
            if (this.form.payState ==0) {
                this.payName = '待支付';
            }
            if (this.form.payState ==1) {
                this.payName = '取消订单';
            }
            if (this.form.payState ==2) {
                this.payName = '已支付';
            }
            if (this.form.payState ==3) {
                this.payName = '已退款';
            }
            if (this.form.payState ==4) {
                this.payName = '订单超时';
            }

            this.editVisible = true;
        },
        // 保存编辑
        saveEdit() {
            this.form.createTime=null
            this.form.updateData=null
           var update =  this.updateForm
            update.id = this.form.id
            update.payState = this.form.payState
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
