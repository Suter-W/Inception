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
      userInfo: {},
      name: '',
      avatar: '',

      postContent: '' // 用来存储发布内容
      // yourAvatarUrl: "your_avatar.jpg", // 设置用户头像的URL
    }
  },

  computed: {
    ...mapState('user', ['token'])
  },

  created () {
    // console.log(this.token)
    this.getUserInfo()
  },

  methods: {
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

<style scoped>
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
</style>
