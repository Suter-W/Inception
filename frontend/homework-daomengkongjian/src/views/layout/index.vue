<template>

  <el-container class="layout-page">


    <el-container>
      <el-header>
        <div class="user">

          <!-- <img src="../../assets/帝可得3.png"
           > -->
          <el-link :underline="false">欢迎您{{name}}</el-link>
        </div>

        <div class="logout">
          <el-popconfirm title="您确认退出吗？" @confirm="logout">
            <span slot="reference" :style="[{fontSize:'15px'},{padding:'2px'}]">退出</span>
            <i slot="reference" title="logout" class="el-icon-arrow-down" :style="[{fontSize:'15px'}]"></i>
          </el-popconfirm>
        </div>
      </el-header>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>

</template>

<script>
// import { getUserInfoApi } from '@/api/user'
import { mapMutations } from 'vuex'

export default {
  name: 'layoutdad',

  data () {
    return {
      name: ''
      // avatar: '',

    }
  },
  computed: {

  },

  mounted () {

  },
  created () {
    // this.getUserInfo()
  },

  methods: {
    ...mapMutations('user', ['logout']),
    //  async getUserInfo () {
    //   try {
    //     const { data: { name, avatar } } = await getUserInfoApi()
    //     this.name = name
    //     this.avatar = avatar
    //   } catch (e) {}
    // },

    handleOpen (key, keyPath) {
      console.log(key, keyPath)
    },
    handleClose (key, keyPath) {
      console.log(key, keyPath)
    }
  }

}
</script>

<style lang="scss" scoped>
@import '../../styles/index.scss';


.layout-page {
  height: 100vh;
  .el-aside {
    background: #fff;
     .img1 {
          width: 150px;
          height: 64px;
          margin: 10px auto;
           img{
              width: 100%;
              height: 100%;
            }
        }

    .el-menu {
      border-right: none;
      margin-top: 20px;
      &-item {
        background-color: transparent !important;
        > span, i {
          padding-left: 5px;
        }
      }
    }
  }
  .el-header {
    box-shadow: 0px 0px 35px 0px rgba(154, 161, 171, 0.15);
    background: $--color-primary;
    display: flex;
    justify-content: flex-end;
    align-items: center;
    z-index: 999;
         .imgdkd{
           width: 100px;
           height: 40px;
           position: absolute;
           left: 30px;
         }

    .user {
      display: flex;
      align-items: center;
      background:  $--color-primary;
      height: 60px;
      padding: 0 15px;
        img{
          width: 50px;
          height: 40px;
          object-fit: cover;
        }
      .el-avatar {
        margin-right: 15px;
      }
      .el-link.el-link--default{
        color: #fff;
      }
    }
    .logout {
      font-size: 20px;
       color: #fff;
      cursor: pointer;
      padding: 0 15px;
    }
  }
  .el-footer {
    display: flex;
    justify-content: space-between;
    align-items: center;
    color: #aaa;
    border-top: 1px solid rgba(152, 166, 173, 0.2);
    font-size: 14px;

  }

}
</style>
