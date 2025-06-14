<template>
  <div>
  <div style="margin-bottom: 15px">
    <el-input v-model="params.name" style="width: 180px;  font-size: 18px" placeholder="请输入比赛名称"></el-input>
<!--    <el-input v-model="params.riqi" style="width: 180px;margin-left: 6px;  font-size: 18px" placeholder="请输入比赛日期"></el-input>-->
    <el-button type="warning" style="margin-left: 10px ; font-size: 15px"@click="findBySearch()">查询</el-button>
    <el-button type="warning" style="margin-left: 10px ; font-size: 15px"@click="reset()">清空</el-button>
    <el-button type="primary" style="margin-left: 10px ; font-size: 15px" @click="add()" v-show="user.role ==='管理员'">新增</el-button>
    <el-button type="success" style="margin-left: 10px; font-size: 15px" @click="exp_1()">导出报表</el-button>
  </div>

  <div>
  <el-table
      :data="tableData" style="width: 100%; font-size: 20px">
    <el-table-column prop="name" label="比赛名称" width="180"></el-table-column>
    <el-table-column prop="img" label="封面图" width="180">
      <template v-slot="scope">
        <el-image
            style="width: 70px; height: 70px; border-radius:50%"
            :src="'http://localhost:8080/api/files/' +scope.row.img"
            :preview-src-list="['http://localhost:8080/api/files/' +scope.row.img]">
        </el-image>
      </template>
    </el-table-column>
    <el-table-column prop="riqi" label="比赛日期"></el-table-column>
    <el-table-column prop="didian" label="参赛地点" width="150"></el-table-column>
    <el-table-column prop="renshu" label="比赛人数" width="150"></el-table-column>
    <el-table-column prop="num" label="剩余容量" width="150"></el-table-column>
    <el-table-column prop="typeName" label="比赛分类" width="150"></el-table-column>
    <el-table-column  label="操作">
      <tempate slot-scope="scope">
        <el-button type="primary" @click="edit(scope.row)"style="font-size: 17px" v-show="user.role ==='管理员'">编辑</el-button>
        <el-button type="primary" @click="baoming(scope.row)"style="font-size: 17px" v-show="user.role !=='管理员'">报名</el-button>
        <el-button type="primary"style="font-size: 17px"><a href="https://mp.weixin.q" target="_blank" class="custom-link">详情</a></el-button>
<!--        <el-button type="primary" @click="down(scope.row.img) "style="font-size: 17px">下载</el-button>-->
        <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.id)">
          <el-button slot="reference" type="danger" style="margin-left: 5px;font-size: 17px "v-if="user.role ==='管理员'">删除</el-button>
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
          :page-sizes="[6, 12, 18, 24]"
          :page-size="params.pageSize"
          layout="total, sizes, prev, pager, next"
          :total="total">
      </el-pagination>
    </div>
    <div>
      <el-dialog title="请填写信息" :visible.sync="dialogFormVisible" width="30%">
        <el-form :model="form">
          <el-form-item label="比赛名称" label-width="15%">
            <el-input v-model="form.name" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="封面图" label-width="15%">
            <el-upload
                action="http://localhost:8080/api/files/upload" :on-success="successUpload">
              <el-button size="small" type="primary">点击上传</el-button>
            </el-upload>
          </el-form-item>
          <el-form-item label="比赛日期" label-width="15%">
            <el-date-picker v-model="form.riqi" type="date" style="width:90%" placeholder="选择日期" value-format="yyyy-MM-dd">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="参赛地点" label-width="15%">
            <el-input v-model="form.didian" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="比赛人数" label-width="15%">
            <el-input v-model="form.renshu" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="剩余容量" label-width="15%">
            <el-input v-model="form.num" autocomplete="off" style="width: 90%"></el-input>
            </el-form-item>
            <el-form-item label="分类" label-width="15%">
              <el-select v-model="form.typeId" placeholder="请选择">
                <el-option v-for="item in typeObjs" :key="item.id" :label="item.name" :value="item.id">
                </el-option>
              </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="submit()">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>


<style>
.custom-link {
  color: blue; /* 默认颜色为蓝色 */
}

.custom-link:hover {
  color: red; /* 悬停时颜色变为红色 */
}

.custom-link:active {
  color: green; /* 激活时颜色变为绿色 */
}
</style>
<script>


import request from "@/utils/request";

export default {

  data() {
    return {
     params:{
       name:'',
       phone:'',
       pageNum:1,
       pageSize:6,

     },
      tableData: [ ],
      total:0,
      dialogFormVisible:false,
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
    findTypes(){
      request.get("/gonggao").then(res => {
        if (res.code === '0'){
            this.typeObjs = res.data;

        }else{
          this.$message.error(res.msg)
        }
      })

    },
    findBySearch() {
      request.get("/bisai/search", {
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
    add() {
      this.form = {};
      this.dialogFormVisible = true;
    },
    edit(obj){
      this.form = obj;
      this.dialogFormVisible = true;
    },
    reset() {
      this.params = {
        pageNum: 1,
        pageSize: 5,
        name: '',
        riqi: ''
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
      request.post("/bisai", this.form).then(res => {
        if (res.code === '0') {
          this.$message.success("操作成功");
          this.dialogFormVisible = false;
          this.findBySearch();
        } else {
          this.$message.error(res.msg)
        }

      })
    },
    del(id){
      request.delete("/bisai/" + id).then(res =>{
        if (res.code === '0'){
          this.$message.success("操作成功");
          this.findBySearch();
        }else{
          this.$message.error(res.msg)
        }
      })
    },
    successUpload(res){
      this.form.img = res.data;
    },
    // down(flag){
    //   location.href = 'http://localhost:8080/api/files/' +flag
    // },
    baoming(row){
      let param = {bisaiId: row.id, userId: this.user.id}
      request.post("/baoming",param).then(res => {
        if (res.code === '0'){
          this.$message("报名成功！")
          this.findBySearch()
        }else{
          this.$message.error(res.msg)
        }

      })
    },
    exp_1(){
      let user = localStorage.getItem("user");
      location.href = 'http://localhost:8080/api/bisai/exp_1?token='+JSON.parse(user).token
    },
  }
}
</script>

