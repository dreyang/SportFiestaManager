<template>
  <div>
  <div style="margin-bottom: 20px">
    <el-input v-model="params.name" style="width: 160px;  font-size: 18px" placeholder="请输入请假原由"></el-input>
    <el-button type="warning" style="margin-left: 10px; font-size: 15px"@click="findBySearch()">查询</el-button>
    <el-button type="warning" style="margin-left: 10px; font-size: 15px"@click="reset()">清空</el-button>
    <el-button type="primary" style="margin-left: 10px; font-size: 15px" @click="add()" v-show="user.role !== '管理员'">新增</el-button>
  </div>

  <div>
  <el-table
      :data="tableData" style="width: 100%; font-size: 20px">
    <el-table-column prop="name" label="请假原由" width="180"></el-table-column>
    <el-table-column prop="time" label="请假日期" width="180"></el-table-column>
    <el-table-column prop="day" label="请假天数" width="180"></el-table-column>
    <el-table-column prop="userName" label="请假用户" width="180"></el-table-column>
    <el-table-column prop="status" label="审核状态" width="180"></el-table-column>
    <el-table-column prop="reason" label="审核意见" width="180"></el-table-column>
    <el-table-column  label="操作">
      <tempate slot-scope="scope">
        <el-button type="primary" style="font-size: 20px" @click="edit(scope.row)" v-if="user.role === '管理员'">编辑</el-button>
        <el-button type="success" style="font-size: 20px" @click="audit(scope.row)" v-if="user.role === '管理员'" >审核</el-button>
        <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.id)">
          <el-button slot="reference" type="danger" style="margin-left: 5px ;font-size: 20px" v-if="user.role === '管理员'">删除</el-button>
        </el-popconfirm>
      </tempate>
    </el-table-column>
  </el-table>
  </div>
    <div style="margin-top: 10px">
      <span class="demonstration"></span>
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="params.pageNum"
          :page-sizes="[8, 16, 24, 32]"
          :page-size="params.pageSize"
          layout="total, sizes, prev, pager, next"
          :total="total">
      </el-pagination>
    </div>
    <div>
      <el-dialog title="请填写信息" :visible.sync="dialogFormVisible" width="30%">
        <el-form :model="form">
          <el-form-item label="请假原由" label-width="15%">
            <el-input v-model="form.name" autocomplete="off" style="width:90%"></el-input>
          </el-form-item>
          <el-form-item label="请假日期" label-width="15%">
            <el-date-picker v-model="form.time" type="date" style="width:90%" placeholder="选择日期" value-format="yyyy-MM-dd">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="请假天数" label-width="15%">
            <el-input v-model="form.day" autocomplete="off" style="width:90%"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="submit()">确 定</el-button>
        </div>
      </el-dialog>
    </div>
        <div>
          <el-dialog title="请审核" :visible.sync="auditVisible" width="30%">
            <el-form :model="form">
              <el-form-item label="审核状态" label-width="15%">
                <el-radio v-model="form.status" label="审核通过">审核通过</el-radio>
                <el-radio v-model="form.status" label="审核不通过">审核不通过</el-radio>
              </el-form-item>
              <el-form-item label="请假意见" label-width="15%">
                <el-input v-model="form.reason" autocomplete="off" style="width:90%"></el-input>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="auditVisible = false">取 消</el-button>
              <el-button type="primary" @click="submit()">确 定</el-button>
            </div>
      </el-dialog>
    </div>

  </div>
</template>

<script>


import request from "@/utils/request";

export default {

  data() {
    return {
     params:{
       name:'',
       phone:'',
       pageNum:1,
       pageSize:8,

     },
      tableData: [ ],
      total:0,
      dialogFormVisible:false,
      auditVisible : false,
      form:{},
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  //页面加载的时候，再created中做一些事情
   created() {
    this. findBySearch();
   },
  //定义一些页面控件触发的事件调用方法
  methods: {

    findBySearch() {
      request.get("/audit/search", {
        params: this.params
      }).then(res => {
        if (res.code === '0') {
          this.tableData = res.data.list;
          this.total = res.data.total;

        } else {
          this.$message({
            message: res.msg,
            type: 'success'
          });
        }
      })

    },
    add() {
      this.form = {};
      this.form.userId = this.user.id;
      this.dialogFormVisible = true;
    },
    edit(obj){
      this.form = obj;
      this.dialogFormVisible = true;
    },
    audit(obj){
      this.form = obj;
      this.auditVisible = true;
    },
    reset() {
      this.params = {
        pageNum: 1,
        pageSize: 5,
        name: '',
        phone: ''
      }
      this.findBySearch();
    },
    handleSizeChange(pageSize) {
      this.params.pageSize = pageSize;
      this.findBySearch();
    },
    handleCurrentChange(pageNum) {
      this.params.pageNum = pageNum;
      this.findBySearch();
    },
    submit() {
      request.post("/audit", this.form).then(res => {
        if (res.code === '0') {
          this.$message({
            message: '操作成功！',
            type: 'success'
          });
          this.dialogFormVisible = false;
          this.auditVisible = false;
          this.findBySearch();
        } else {
          this.$message({
            message: res.msg,
            type: 'error'
          });
        }

      })
    },
    del(id){
      request.delete("/audit/" + id).then(res =>{
        if (res.code === '0'){
          this.$message({
            message: '删除成功！',
            type: 'success'
          })
          this.findBySearch();
        }else{
          this.$message({
            message: res.msg,
            type: 'success'
          });
        }
      })
    }
  }
}
</script>