<template>
  <div>
  <div style="margin-bottom: 15px">
    <el-input v-model="params.name" style="width: 180px;  font-size: 20px" placeholder="请输入比赛名称"></el-input>
    <el-button type="warning" style="margin-left: 10px;  font-size: 18px"@click="findBySearch()">查询</el-button>
    <el-button type="warning" style="margin-left: 10px;  font-size: 18px"@click="reset()">清空</el-button>
  </div>

  <div>
  <el-table
      :data="tableData" style="width: 100%;  font-size: 21px">
    <el-table-column prop="bisaiName" label="比赛名称" width="180"></el-table-column>
    <el-table-column prop="userName" label="参赛人员" width="180"></el-table-column>
    <el-table-column prop="time" label="报名时间"></el-table-column>
    <el-table-column prop="status" label="审核状态" width="180"></el-table-column>
    <el-table-column  label="操作">
      <tempate slot-scope="scope">
        <el-button type="success" @click="audit(scope.row)" style="font-size: 20px" v-show="user.role === '管理员'" >审核</el-button>
        <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.id)">
          <el-button slot="reference" type="danger" style="margin-left: 5px;font-size: 20px" v-show="user.role ==='管理员'">删除</el-button>
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
      <el-dialog title="请审核" :visible.sync="baomingVisible" width="30%">
        <el-form :model="form">
          <el-form-item label="审核状态" label-width="15%">
            <el-radio v-model="form.status" label="审核通过">审核通过</el-radio>
            <el-radio v-model="form.status" label="审核不通过">审核不通过</el-radio>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="baomingVisible = false">取 消</el-button>
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
      baomingVisible : false,
      form:{},
      typeObjs:[],
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  //页面加载的时候，再created中做一些事情
   created() {
    this. findBySearch();
    this.findTypes();
   },
  //定义一些页面控件触发的事件调用方法
  methods: {

    findBySearch() {
      request.get("/baoming/search", {
        params: this.params
      }).then(res => {
        if (res.code === '0') {
          this.tableData = res.data.list;
          this.total = res.data.total;

        } else {
          this.$message.error(res.msg)
        }
      })

    },

    audit(obj){
      this.form = obj;
      this.baomingVisible = true;
    },
    reset() {
      this.params = {
        pageNum: 1,
        pageSize: 5,
        name: ''
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
      request.post("/baoming/shuju", this.form).then(res => {
        if (res.code === '0') {
          this.$message({
            message: '操作成功！',
            type: 'success'
          });
          this.dialogFormVisible = false;
          this.baomingVisible = false;
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
      request.delete("/baoming/" + id).then(res =>{
        if (res.code === '0'){
          this.$message.success("操作成功");
          this.findBySearch();
        }else{
          this.$message.error(res.msg)
        }
      })
    },
  }
}
</script>