<template>
  <el-container class="layout-page">

    <el-container>
      <!-- <el-header>
        <div class="user">
          <el-avatar class="weibo-avatar" :src="yourAvatarUrl" size="small"></el-avatar>
          <el-link :underline="false">{{name}} 欢迎您！</el-link>
        </div>

        <div class="logout">
          <el-popconfirm title="您确认退出吗？" @confirm="logout">
            <span slot="reference" :style="[{fontSize:'15px'},{padding:'2px'}]">退出</span>
            <i slot="reference" title="logout" class="el-icon-arrow-down" :style="[{fontSize:'15px'}]"></i>
          </el-popconfirm>
        </div>
      </el-header> -->
      <el-header>
        <div style="display: inline-block;width: 10%;height: 100%;line-height: 60px;font-size: 20px;font-family: '楷体';color:#fff;position: absolute;left: 1%;cursor: pointer;" v-if="showIcon != 0" @click="returnHome">
          <i class="el-icon-back"></i>
          返回主页
        </div>
        <div class="titles">
          {{ title }}
          <i class="el-icon-s-home" v-if="showIcon == 0"></i>
          <i class="el-icon-edit" v-if="showIcon == 1"></i>
          <i class="el-icon-moon-night" v-if="showIcon == 2"></i>
          <i class="el-icon-star-off" v-if="showIcon == 3"></i>
          <i class="el-icon-setting" v-if="showIcon == 4"></i>
        </div>
        <div class="info">
          <div class="userInfo">
            <el-avatar class="weibo-avatar" :src="yourAvatarUrl" size="small"></el-avatar>
            <el-link :underline="false">{{ name }} 欢迎您！</el-link>
          </div>
          <div class="logOut">
            <!-- <el-popconfirm title="您确认退出吗？" @confirm="logout">
              <span slot="reference" :style="[{fontSize:'15px'},{padding:'2px'},{marginRight:'10px'}]">退出</span>
              <i slot="reference" title="logout" class="el-icon-arrow-down" :style="[{fontSize:'15px'}]"></i>
            </el-popconfirm> -->
            <el-popover placement="top" width="160" v-model="logOutVisible">
              <p style="font-size: 15px;text-align: center;">您确认退出吗？</p><br>
              <div style="text-align: right; margin: 0;margin-right: -6px;margin-bottom: -6px;">
                <el-button
                  :style="[{ width: '60px' }, { backgroundColor: 'white' }, { height: '28px' }, { border: 'none' }, { margin: '0 2px' },
                  { padding: '0 8px' }, { display: 'inline-flex' }, { alignItems: 'center' }, { justifyContent: 'center' }, { boxShadow: 'none' }]"
                  @click="logOutVisible = false"><span style="font-size: 12px;color: #409eff;">取消</span></el-button>
                <el-button
                  :style="[{ width: '60px' }, { backgroundColor: '#409eff' }, { height: '28px' }, { borderRadius: '4px' }, { margin: '0 2px' },
                  { padding: '0 8px' }, { display: 'inline-flex' }, { alignItems: 'center' }, { justifyContent: 'center' }, { boxShadow: 'none' }]"
                  @click="logout"><span
                    style="font-size: 12px;color: #fff;margin-top: -1px;padding: 0;">确定</span></el-button>
              </div>
              <span slot="reference" :style="[{ fontSize: '15px' }, { padding: '2px' }, { marginRight: '10px' }]">退出</span>
              <i slot="reference" title="logout" class="el-icon-arrow-down" :style="[{ fontSize: '15px' }]"></i>
            </el-popover>
          </div>
        </div>
      </el-header>
      <el-main>
        <router-view @route-change="getTitle"></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import { getUserInfoApi } from '@/api/user'
import { mapMutations } from 'vuex'
import { mapState } from 'vuex'
// import "@/assets/points.js"

export default {
  name: 'LayoutPage',

  data() {
    return {
      title:'主页',
      showIcon: 0,
      logOutVisible: false,
      userInfo: {},
      name: '',
      yourAvatarUrl:''
      

    }
  },
  computed: {
    ...mapState('user', ['token'])
  },

  created() {
    // console.log(this.token)
    this.getUserInfo()
  },

  methods: {
    returnHome(){
      this.$router.push('/home');
    },
    getTitle(showTitle,val){
      if(val === 5){
        location.reload();
      }else{
        this.title = showTitle;
        this.showIcon = val;
      }
    },

    // 退出登录
    ...mapMutations('user', ['logout']),

    // 获取用户信息
    async getUserInfo() {
      try {
        this.userInfo = await getUserInfoApi({
          id: this.token
        })
        this.name = this.userInfo.data.userName
        // this.avatar = this.userInfo.data.userAvatar
        this.yourAvatarUrl = this.userInfo.data.userAvatar
      } catch (e) { }
    },

    handleOpen(key, keyPath) {
      console.log(key, keyPath)
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath)
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../../styles/index.scss';

.layout-page {
  height: 100vh;

  // .el-aside {
  //   background: #fff;

  //   .img1 {
  //     width: 150px;
  //     height: 64px;
  //     margin: 10px auto;

  //     img {
  //       width: 100%;
  //       height: 100%;
  //     }
  //   }

  //   .el-menu {
  //     border-right: none;
  //     margin-top: 20px;

  //     &-item {
  //       background-color: transparent !important;

  //       >span,
  //       i {
  //         padding-left: 5px;
  //       }
  //     }
  //   }
  // }

  // .el-header {
  //   box-shadow: 0px 0px 35px 0px rgba(154, 161, 171, 0.15);
  //   background: $--color-primary;
  //   display: flex;
  //   justify-content: flex-end;
  //   align-items: center;
  //   z-index: 999;

  //   .imgdkd {
  //     width: 100px;
  //     height: 40px;
  //     position: absolute;
  //     left: 30px;
  //   }

  //   .user {
  //     display: flex;
  //     align-items: center;
  //     background: $--color-primary;
  //     height: 60px;
  //     padding: 0 15px;

  //     img {
  //       width: 50px;
  //       height: 40px;
  //       object-fit: cover;
  //     }

  //     .el-avatar {
  //       margin-right: 15px;
  //     }

  //     .el-link.el-link--default {
  //       color: #fff;
  //     }
  //   }

  //   .logout {
  //     font-size: 20px;
  //     color: #fff;
  //     cursor: pointer;
  //     padding: 0 15px;
  //   }
  // }

  // .el-footer {
  //   display: flex;
  //   justify-content: space-between;
  //   align-items: center;
  //   color: #aaa;
  //   border-top: 1px solid rgba(152, 166, 173, 0.2);
  //   font-size: 14px;

  // }
  .el-header{
    box-shadow: 0px 0px 35px 0px rgba(154, 161, 171, 0.15);
    background-color: #443B77;
    height: 60px;
    margin-bottom: 0px;
    position: relative;
    padding: 0;
    .titles{
      width: 20%;
      position: absolute;
      font-family: '楷体';
      font-size: 20px;
      left: 50%;
      top: 50%;
      transform: translate(-10%,-10px);
      display: inline-block;
      color: #fff;
    }
    .info {
      display: inline-block;
      position: absolute;
      right: 60px;
      width: 320px;
      height: 100%;
      display: flex;
      justify-content: space-between;
      align-items: center;
      background-color: #443B77;
      .userInfo{
        display: flex;
        align-items: center;
        .el-avatar {
          margin-right: 15px;
        }
        .el-link.el-link--default{
          color: #fff;
        }
      }
      .logOut{
        display: flex;
        align-items: center;
        // font-size: 20px;
        color: #fff;
        cursor: pointer;
        padding: 0 15px;
      }
    }
  }
  .el-main{
    padding: 0;
  }

}
</style>
