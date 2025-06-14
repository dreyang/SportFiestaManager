<template>
  <div class="background-image_1">
  <div style="width: 100%">
    <div style="margin-bottom: 15px; font-weight: bold; font-size: 42px;color: #c72525">系统公告 :</div>
    <el-collapse v-model="activeName" accordion>
      <el-collapse-item  v-for="item in data":key="item.id" :title="item.name" :name="item.id">
        <div style="padding: 0 40px; color: #008b8b; font-size: 25px ">{{item.content}}<a href="https://www.sdwu.edu.cn/info/1031/24318.htm" target="_blank">详情</a></div>
      </el-collapse-item>
    </el-collapse>
  </div>
<div style="margin-bottom: 10px; font-weight: bold;font-size: 42px;color: #c72525 ">往届精彩瞬间 :</div>
      <div class="homePage"  style="width: 100%;height: 300px;">
        <div style="background: linear-gradient(to right, red, blue); opacity: 0.5 ">
          <!-- 轮播图组件 -->
          <MySwiper :images="images"></MySwiper>
      </div>
    </div>

  </div>

</template>

<script>
import request from "@/utils/request";
import MySwiper from "@/views/MySwiper";
export default {
  name:'HomeView',
  components: {
    // 注册 vue-awesome-swiper 组件
    MySwiper,
  },
  data() {
    return {
      activeName: '1',
      data:[],
      images: [
        { img: require('/src/assets/diyi.mp4') },
        { img: require('/src/assets/dier.mp4') },
        { img: require('/src/assets/disan.mp4') },
      ],
    };
  },
  mounted() {
    this.findNotice();
  },

  el:'#vedio_1',
  methods: {
    showAllSlideImage() {
      const _this = this;
      //异步请求显示所有数据
      const params = {
        page: 1,
        size: 0,
      };
      this.$axios.findAllSlideImages(params).then((res) => {
        if (res.code === 200) {
          console.log("查询到轮播图信息:", res);
          this.tableDatas = res.data.result.slideshowList;
          var slideList = res.data.result.slideshowList;
          this.total = slideList.length;

          let imageRs = [];
          if (slideList.length > 0) {
            for (let item of slideList) {
              let imgurl = {};
              imgurl.img = item.url;
              imageRs.push(imgurl);
            }
          }
          _this.images = imageRs;
        } else {
          console.error(res);
        }
      });
    },
    findNotice(){
      request.get("/notice").then(res =>{
        if (res.code === '0'){
        this.data = res.data;
        this.activeName = res.data[0].id;
        }else{
          this.$message.error(res.msg)
        }
      })
    },
  },
  created() {
    this.showAllSlideImage();
  }
}
</script>
<style>
.background-image_1{
  background-image: url("@/assets/beijing_1.webp");
    background-size: cover;
    background-position: center;
}
</style>