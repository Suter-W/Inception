<template>
  <div class="content">
    <div class="info">
      <h2 class="section-title">个人信息</h2>

      <div class="info-item">
        <h3>头像</h3>
        <img src="https://img.zcool.cn/community/018a145e8be3b5a80120a89592780b.jpg@1280w_1l_2o_100sh.jpg" alt="Avatar" class="avatar">
      </div>

      <div class="info-item">
        <h3>昵称</h3>
        <p class="nickname">{{ nickname }}</p>
      </div>

      <div class="info-item">
        <h3>个性签名</h3>
        <p class="bio">{{ bio }}</p>
      </div>
    </div>
  </div>
</template>

<script>
import { getUserInfoApi } from '@/api/user'
import { mapState } from 'vuex'

export default {
  data () {
    return {
      userInfo: {},
      avatarUrl: 'path_to_avatar_image.jpg',
      nickname: 'UserNickname',
      bio: 'A short bio describing the user...'
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
        this.nickname = this.userInfo.data.userName
        this.avatarUrl = this.userInfo.data.userAvatar
      } catch (e) {}
    }
  }
}
</script>

<style scoped lang="scss">
@import '../../../styles/index.scss';

.content {
  flex: 1;
  padding: 40px;
  // background-color: #ecf5ff;
  background: url('/src/assets/background.jpg');
}
.avatar {
  width: 30px; /* 设置头像宽度 */
  height: 30px; /* 设置头像高度 */
  border-radius: 50%; /* 使图像呈现圆形 */
  object-fit: cover; /* 保持图像比例填充整个元素 */
}

.info {
  padding: 20px;
  width: 80%;
  margin: 0 auto;
  border: 1px solid #eee;
  border-radius: 10px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  font-family: Arial, sans-serif;
  background-color: #f9f9f9;
  opacity: 0.9;
  .section-title {
    font-size: 28px;
    margin-bottom: 20px;
    color: #333;
    text-align: center;
  }

  .info-item {
    margin-bottom: 25px;

    h3 {
      font-size: 20px;
      margin-bottom: 10px;
      color: #444;
    }

    .avatar {
      width: 100px;
      height: 100px;
      border: 1px solid #ccc;
      border-radius: 5px;
    }

    .nickname,
    .bio {
      font-size: 16px;
      padding: 10px;
      border: 2px solid #ddd;
      border-radius: 5px;
    }

    .bio {
      height: 150px;
    }
  }
}
</style>
