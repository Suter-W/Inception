<template>
  <div class="page">
      <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
        <!-- 左侧侧边栏 -->
          <div class="sidebar-header">
            <h2>盗梦空间</h2>
          </div>
          <el-menu
          active-text-color="#ffffff"
            default-active="mine"
            class="menu"
            @select="handleMenuSelect"
          >
            <el-menu-item index="mine" :route="{path:'/setting/mine'}"> 我的账号</el-menu-item>
            <!-- <el-menu-item index="message" :route="{path:'/setting/message'}"> 消息通知</el-menu-item> -->
            <el-menu-item index="infoset" :route="{path:'/setting/infoset'}">账号设置</el-menu-item>
          </el-menu>
          <div class="logout">
            <el-button type="primary" @click="handleLogout">退出</el-button>
          </div>
      </el-aside>

    <!-- 右侧内容区域 -->
    <div class="content">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import { getUserInfoApi } from '@/api/user'
import { mapMutations } from 'vuex'
import { mapState } from 'vuex'
export default {
  computed: {
    ...mapState('user', ['token'])
  },

  created () {
    // console.log(this.token)
    this.getUserInfo()
  },

  methods: {
    // 退出登录
    ...mapMutations('user', ['logout']),

    // 获取用户信息
    async getUserInfo () {
      try {
        this.userInfo = await getUserInfoApi({
          id: this.token
        })
        this.name = this.userInfo.data.userName
        this.avatar = this.userInfo.data.userAvatar
      } catch (e) {}
    },

    // handleMenuSelect(index) {
    //   // 在这里处理菜单点击事件
    //   if (index === "myAccount") {
    //     // 处理我的账号菜单点击事件
    //   } else if (index === "notifications") {
    //     // 处理消息通知菜单点击事件
    //   } else if (index === "accountSettings") {
    //     // 处理账号设置菜单点击事件
    //   }
    // },
    handleMenuSelect (index) {
      switch (index) {
        case 'mine':
          this.$router.push({ path: '/setting/mine' })
          break
        case 'infoset':
          this.$router.push({ path: '/setting/infoset' })
          break
        default:
          // 默认处理
          break
      }
    },
    handleLogout () {
      // 处理退出按钮点击事件
      // 可以执行退出逻辑，例如注销用户或跳转到登录页面
      this.$confirm('是否退出登录?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.logout()
        this.$message({
          type: 'info',
          message: '已退出'
        })
      }).catch(() => {
      })
    }
  }
}
</script>

<style scoped lang="scss">
@import '../../styles/index.scss';

.page {
  display: flex;
  height:calc(100vh - 80px - 40px)
}

.el-aside {
  width: 260px;
  padding: 20px;
  height:100%;
  display:flex;
  flex-direction: column;
  .menu{
    flex:1;
    .el-menu-item{
      display: flex;
    align-items: center;
    justify-content: center;

    }
    .is-active{
      box-shadow:10px 10px 20px 0px  #fff !important;
  padding: 15px 5px !important;
  background-color:  $--color-primary !important;
  border-radius: 10px !important;

    }
  }
}

.sidebar-header {
  text-align: center;
  margin-bottom: 20px;
}

.menu {
  background-color: #f0f2f5;
  padding: 20px;
}

.logout {
  text-align: center;
  margin-top: 20px;
}

.content {
  flex: 1;
  padding: 20px;
  // background-color: #ecf5ff;
  background: url('/src/assets/background.jpg');
}

</style>
