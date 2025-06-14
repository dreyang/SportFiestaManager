<template>
  <div>
    <el-container>
      <el-header style="background: linear-gradient(to right, red, blue); opacity: 0.5 ">
        <img src="@/assets/logo.png" alt="" style="width: 40px; position: relative; top: 10px;">
        <span style="font-size: 25px; margin-left: 15px; color: #ffffff">校运会管理系统</span>
        <el-dropdown style="float: right; height: 80px; line-height: 80px">
        <span class="el-dropdown-link" style="color: #ffffff; font-size: 20px">
          {{ user.name }}
          <i class="el-icon-arrow-down el-icon--right"></i>
        </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>
              <div style="font-size: 25px" @click="logout">退出登录</div>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>

    </el-container>

    <el-container>
      <el-aside style="overflow: hidden; min-height: 100vh; background: linear-gradient(to bottom ,blue, red);opacity: 0.5; width: 250px">
        <el-menu default-active="$route.path" router background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
          <el-menu-item index="/">
            <i class="el-icon-s-home"></i>
            <span slot="title">系统首页</span>
          </el-menu-item>
<!--          只有管理员才能看到用户信息界面！-->
          <el-submenu index="2" v-show="user.role ==='管理员'">
            <template slot="title">
              <i class="el-icon-location"></i><span>用户管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/admin">用户信息</el-menu-item>

            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-location"></i><span>信息管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/bisai">比赛报名</el-menu-item>
              <el-menu-item index="/gonggao">赛事信息</el-menu-item>
              <el-menu-item index="/eter">成绩系统</el-menu-item>
              <el-menu-item index="/bm">报名审核</el-menu-item>
              <el-menu-item index="/audit" >请假审核</el-menu-item>
              <el-menu-item index="/log" v-show="user.role ==='管理员'">日志管理</el-menu-item>
              <el-menu-item index="/notice" v-show="user.role ==='管理员'">系统公告</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-main>
        <router-view/>
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "Layout",

  data(){
    return{
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  methods:{
    logout(){
      localStorage.removeItem("user");
      this.$router.push("/login");
    }
  }
}


</script>

<style>
.el-menu{
  border-right: none !important;
}
.el-main{
background-image: url("@/assets/beijing_1.webp");
  background-size: cover;
  background-position: center;
/*  background-color: #6492d5;*/
}
</style>