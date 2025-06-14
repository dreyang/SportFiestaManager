<template>
  <div class="swiper-container">
    <div class="swiper-wrapper">
      <div class="swiper-slide" v-for="(item, i) in images" :key="i">
        <!-- <img class="carousel-img" :src="item.img" alt="" /> -->
        <video
            class="carousel-img"
            controls
            muted
            autoplay
            fluid="false"
            preload="auto"
            :src="item.img"
        ></video>
      </div>
    </div>
    <!-- 如果需要分页器 -->
    <div class="swiper-pagination"></div>
    <!-- 如果需要导航按钮 -->
    <div class="swiper-button-prev"></div>
    <div class="swiper-button-next"></div>
  </div>
</template>

<script>
import Swiper from "swiper";
import "swiper/css/swiper.min.css";
export default {
  name: "MySwiper",
  //声明接收App传递过来的数据，其中todos是自己用的
  props: ["images"],
  data() {
    return {
      // images: [
      //   { img: "https://www.baidu.com/img/baidu_jgylogo3.gif" },
      //   { img: "http://localhost:8282/images/21667218837206.jpg" },
      //   { img: "http://localhost:8282/images/21667218837206.jpg" },
      // ],
    };
  },
  mounted() {
    var mySwiper = new Swiper(".swiper-container", {
      autoplay: {
        delay: 5000,
        disableOnInteraction: false,
      }, //可选选项，自动滑动

      loop: true, // 循环模式选项
      speed: 4000,
      observer: true, //修改swiper 自己或子元素时，自动初始话swiper
      observerParents: true, //修改swiper的父元素时，自动初筛话swiper

      // 如果需要分页器
      pagination: {
        el: ".swiper-pagination",
        clickable: true,
      },

      // 如果需要前进后退按钮
      navigation: {
        nextEl: ".swiper-button-next",
        prevEl: ".swiper-button-prev",
        disabledClass: "my-button-disabled",
      },
    });
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.carousel-img {
  width: 100%;
  height: 100%;
}

.swiper-container {
  height: 350px;
  width: 100%;
  text-align: center;
}
</style>