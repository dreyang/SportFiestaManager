<template>
  <div>
      <div style="margin-bottom: 18px; font-size: 22px">
        <el-select v-model="selectedValue"  @change="handleSelectChange" placeholder="选择比赛项目">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
          </el-option>
        </el-select>
        <el-input v-model="params.name" style="margin-left: 10px; width: 195px ;  font-size: 18px" placeholder="请输入参赛人员姓名"></el-input>

        <el-button type="warning" style="margin-left: 10px ; font-size: 15px"@click="findBySearch()">查询</el-button>
        <el-button type="warning" style="margin-left: 10px ; font-size: 15px"@click="reset()">清空</el-button>
        <el-button type="primary" style="margin-left: 10px ; font-size: 15px"@click="add()" v-show="user.role ==='裁判'">录入</el-button>
        <el-button type="primary" style="margin-left: 10px ; font-size: 15px">    <a href="https://mp.weixin.qq.com/s?__biz=MzI5NzQ3MzYyNA==&mid=2247505843&idx=2&sn=b5db910a29800ccd45a272b2b26f96c2&chksm=ecb61484dbc19d9272318eeeea05a3b7e306bd92f5dc4f4a45fd3cfc82dd7837717ba33c622d&mpshare=1&scene=23&srcid=0524c28q35LnAXUl2obRSVot&sharer_shareinfo=16e24824838bb2538897ee9ba91d588f&sharer_shareinfo_first=16e24824838bb2538897ee9ba91d588f#rd" target="_blank" class="custom-link">成绩核算说明</a></el-button>
      </div>


    <div>
      <el-table
          :data="tableData" style="width: 100% ; font-size: 20px">
        <el-table-column prop="name" label="姓名" width="380"></el-table-column>
<!--        <el-table-column prop="xiangmu" label="比赛名称" width="180"></el-table-column>-->
        <el-table-column prop="grade" label="成绩" width="380"></el-table-column>
        <el-table-column  label="操作">
          <tempate slot-scope="scope">
            <el-button type="primary" @click="edit(scope.row)"  style="font-size: 20px" v-show="user.role ==='裁判'">编辑</el-button>
            <el-popconfirm  title="确定删除吗？" @confirm="del(scope.row.id)">
              <el-button slot="reference" type="danger" style="margin-left: 5px ; font-size: 20px" v-show="user.role ==='裁判'">删除</el-button>
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
          :page-sizes="[10, 20, 30, 40]"
          :page-size="params.pageSize"
          layout="total, sizes, prev, pager, next"
          :total="total">
      </el-pagination>
    </div>
    <div>
      <el-dialog title="录入成绩信息" :visible.sync="dialogFormVisible" width="30%">
        <el-form :model="form">
          <el-form-item label="姓名" label-width="15%">
            <el-input v-model="form.name" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
<!--          <el-form-item label="比赛项目" label-width="15%">-->
<!--            <el-input v-model="form.xiangmu" autocomplete="off" style="width: 90%"></el-input>-->
<!--          </el-form-item>-->
          <el-form-item label="成绩" label-width="15%">
            <el-input v-model="form.grade" autocomplete="off" style="width: 90%"></el-input>
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

<script>


import request from "@/utils/request";

export default {

  data() {
    return {
      selectedValue: '',
      options: [{
        value: 'option1',
        label: '50米短跑赛（学生女子组）'
      }, {
        value: '选项2',
        label: '100米短跑赛（学生女子组）'
      }, {
        value: '选项3',
        label: '200米短跑赛（学生女子组）'
      }, {
        value: '选项4',
        label: '800米长跑赛（学生女子组）'
      }, {
        value: '选项5',
        label: '4*100米接力（学生女子组）'
      },
        {
          value: '选项6',
          label: '4*400米接力（学生女子组）'
        },
        {
          value: '选项7',
          label: '跳高（学生女子组）'
        },
        {
          value: '选项8',
          label: '跳远（学生女子组）'
        },
        {
          value: '选项9',
          label: '100米短跑赛（教工女子组）'
        },
        {
          value: '选项10',
          label: '60米赶球跑（教工女子组）'
        },
        {
          value: '选项11',
          label: '一分钟跳绳（教工女子组）'
        },
        {
          value: '选项12',
          label: '球王争霸（教工女子组）'
        },
        {
          value: '选项13',
          label: '100米短跑赛（教工男子组）'
        },
        {
          value: '选项14',
          label: '定点投篮（教工男子组）'
        },
        {
          value: '选项15',
          label: '1500长跑赛（学生男子组）'
        },
        {
          value: '选项16',
          label: '铅球（学生男子组）'
        },],
   params:{
        name:'',
        pageNum:1,
        pageSize:10,

      },
      tableData: [ ],
      total:0,
      dialogFormVisible:false,
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
    handleSelectChange(value) {
      // 根据选中的值跳转到不同的页面
      switch (value) {
        case 'option1':
          this.$router.push('/views/BisaiView');
          break;
        case 'option2':
          this.$router.push('/page2');
          break;
        case 'option3':
          this.$router.push('/page3');
          break;
          // 针对其他选项的case...
      }
    },

    findBySearch() {
      request.get("/eenter/search", {
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
      request.post("/eenter", this.form).then(res => {
        if (res.code === '0') {
          this.$message({
            message: '操作成功！',
            type: 'success'
          });
          this.dialogFormVisible = false;
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
      request.delete("/eenter/" + id).then(res =>{
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