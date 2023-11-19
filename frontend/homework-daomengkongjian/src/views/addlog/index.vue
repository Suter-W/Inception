<template>
  <div class="weibo">
    <!-- ... 其他微博内容 ... -->
    <div class="content">

      <el-card class="weibo-card weibo-post-card">
      <div class="weibo-header">
        <el-avatar class="weibo-avatar" :src="yourAvatarUrl" size="small"></el-avatar>
        <span class="weibo-username">{{name}}</span>
        <div class="weibo-post-buttons">
          <el-button type="primary" size="mini" @click="submitNormalPost">正常发布</el-button>
          <el-button type="primary" size="mini" @click="submitAnonymousPost">匿名发布</el-button>
          <el-button type="primary" size="mini" @click="submitPrivatePost">私密发布</el-button>
        </div>
      </div>
      <div class="weibo-content">
        <el-input
          type="textarea"
          :autosize="{ minRows: 20}"
          placeholder="分享你的新鲜事..."
          v-model="postContent"
        ></el-input>
      </div>
    </el-card>
    </div>
    <el-button
      class="weibo-add-button"
      type="text"
      icon="el-icon-edit"
      style="background-color: white;"
      @click="showMoreOptions"
    >
    </el-button>
    <div class="weibo-more-options" v-show="showOptions">
      <div>
        <el-tooltip
          class="item"
          effect="light"
          content="我的收藏"
          placement="bottom"
        >
          <el-button type="text" icon="el-icon-star-off" style="background-color: white;" @click="handleLink('collect')"></el-button>
        </el-tooltip>
      </div>
      <div>
        <el-tooltip class="item" effect="light" content="主页" placement="top">
          <el-button type="text" icon="el-icon-s-home" style="background-color: white;" @click="handleLink('home')"></el-button>
        </el-tooltip>
      </div>
      <div>
        <el-tooltip
          class="item"
          effect="light"
          content="历史梦境"
          placement="top"
          ><el-button @click="handleLink('log')" style="background-color: white;" type="text" icon="el-icon-moon-night"></el-button>
        </el-tooltip>
      </div>
      <div>
        <el-tooltip
          class="item"
          effect="light"
          content="账号及设置"
          placement="bottom"
          ><el-button type="text" style="background-color: white;" icon="el-icon-setting" @click="handleLink('setting')"></el-button>
        </el-tooltip>
      </div>
    </div>

    <!-- ... 其他微博内容 ... -->
  </div>
</template>

<script>
import { publishDreamApi } from '@/api/dream'
import { getUserInfoApi } from '@/api/user'
import { mapState } from 'vuex'
import { Message } from 'element-ui'

export default {
  name: 'AddLog',

  data () {
    return {
      showOptions: false,
      userInfo: {},
      name: '',
      avatar: '',

      postContent: '', // 用来存储发布内容
      yourAvatarUrl: '', // 设置用户头像的URL
    }
  },

  computed: {
    ...mapState('user', ['token'])
  },

  mounted(){
    this.$emit('route-change','写梦',1)
  },

  created () {
    // console.log(this.token)
    this.getUserInfo()
  },

  methods: {
    handleLink (linknName) {
      this.$router.push({
        name: linknName
      })
    },
    showMoreOptions () {
      this.showOptions = !this.showOptions
    },
    // 获取用户信息
    async getUserInfo () {
      try {
        this.userInfo = await getUserInfoApi({
          id: this.token
        })
        this.name = this.userInfo.data.userName
        // this.avatar = this.userInfo.data.userAvatar
        this.yourAvatarUrl = this.userInfo.data.userAvatar
      } catch (e) {}
    },

    // 发布梦境
    async publishDream (userID, dreamContent, dreamStatus) {
      try {
        const res = await publishDreamApi({
          userID: userID,
          dreamContent: dreamContent,
          dreamStatus: dreamStatus
        })
        console.log(res.code)
        return res.code
      } catch (e) {
      }
    },

    async submitNormalPost () {
      // 正常发布逻辑，你可以在这里将 postContent 提交到后端或执行其他操作
      try {
        const result = await this.publishDream(this.token, this.postContent, 0)
        if (result === 1) {
          Message.success('发布成功！')
          this.$router.push({
            name: 'home'
          })
          this.clearPostForm()
        } else {
          Message.error('发布失败！')
        }
      } catch (e) {
      }
    },

    async submitAnonymousPost () {
      // 匿名发布逻辑
      // 可以设置发布标志为匿名，并提交内容
      try {
        const result = await this.publishDream(this.token, this.postContent, 1)
        if (result === 1) {
          Message.success('匿名发布成功！')
          this.$router.push({
            name: 'home'
          })
          this.clearPostForm()
        } else {
          Message.error('发布失败！')
        }
      } catch (e) {
      }
    },

    async submitPrivatePost () {
      // 私密发布逻辑
      // 可以设置发布标志为私密，并提交内容
      try {
        const result = await this.publishDream(this.token, this.postContent, 2)
        if (result === 1) {
          Message.success('私密发布成功！')
          this.$router.push({
            name: 'home'
          })
          this.clearPostForm()
        } else {
          Message.error('发布失败！')
        }
      } catch (e) {
      }
    },

    clearPostForm () {
      // 清空发布表单内容
      this.postContent = ''
    }
  }
}
</script>

<style scoped lang="scss">
.weibo{
  width: 1000px;
  margin: 30px auto;
}
.weibo-post-card {
  width: 100%; /* 与其他微博卡片宽度相同 */
  padding: 20px;
  /* 添加其他样式 */
}
.weibo-header {
  display: flex;
  align-items: center;
}
.weibo-avatar {
  margin-right: 10px;
}

.weibo-username {
  font-weight: bold;
}

.weibo-content {
  padding: 20px 0;
}
.weibo-post-buttons {
  margin-left: auto; /* 将按钮移到右侧 */
  display: flex;
  align-items: center;
}
.weibo-post-buttons el-button {
  margin-left: 10px;
}
.weibo-add-button {
  position: fixed;
  bottom: 60px;
  right: 100px;
  width: 45px;
  height: 45px;
  color: #443B77;
}
.weibo-more-options {
  position: fixed;
  bottom: 60px;
  right: 100px;
  display: flex;
  flex-direction: column;
  div {
    width: 40px;
    height: 40px;
    transition: transform 0.3s; /* 设置位移过渡 */
  }
  .el-button {
    height: 100%;
    padding: 0;
  }
  div:nth-child(1) {
    position: absolute;
    left: -102px;
    bottom: 0px;
  }

  div:nth-child(2) {
    position: absolute;
    left: -72px;
    bottom: 55px;
  }
  div:nth-child(3) {
    position: absolute;
    left: -9px;
    bottom: 54px;
  }
  div:nth-child(4) {
    position: absolute;
    left: 22px;
    bottom: 0px;
  }
}
</style>
