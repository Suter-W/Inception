<template>
  <div class="weibo-list">
    <el-card
      class="weibo-card"
      v-for="(post, index) in weiboPosts"
      :key="post.id"
    >
      <div class="weibo-header">
        <span class="weibo-username">{{ post.time }}</span>
      </div>
      <div class="weibo-content">
        {{ post.content }}
      </div>
      <el-divider></el-divider>
      <div class="weibo-actions">
        <!-- <font-awesome-icon :icon="['far', 'heart']" style="margin-right: 12px" /> -->
        <!-- <font-awesome-icon icon="fa-solid fa-user-secret" /> -->
        <font-awesome-icon
          style="margin-right: 12px"
          :class="{ upActive: post.up }"
          :icon="post.up ? 'fa-solid fa-heart' : 'fa-regular fa-heart'"
          @click="like"
        />
        <font-awesome-icon
          icon="fa-regular fa-comment"
          style="margin-right: 12px"
          @click="openComment(post, index)"
        />
        <font-awesome-icon
          style="margin-right: 12px"
          :class="{ starActive: post.star }"
          :icon="post.star ? 'fa-solid fa-star' : 'fa-regular fa-star'"
          @click="star"
        />

        <!-- <v-icon name="heart"  style="margin-right: 12px" :class="{'upActive':post.up}" @click="post.up=!post.up"/>
        <v-icon name="comments"  style="margin-right: 12px" />
        <v-icon name="fa-star"  :class="{'starActive':post.star}" @click="post.star=!post.star"/> -->
      </div>

      <el-collapse accordion v-if="post.comments && post.comments.length > 0">
        <el-collapse-item>
          <!-- <template slot="title">
            <div>
              <div>   {{ post.comments[0].content }}</div>
            <el-divider v-if="post.comments.length > 1"></el-divider>
          </div>

          </template>

          <template
            v-if="post.comments.length > 1"
            v-for="comment in post.comments.slice(1)"
          >
            <div>
              {{ comment.content }}
              <el-divider></el-divider>
            </div>
          </template> -->
          <template
            v-if="post.comments.length"
            v-for="comment in post.comments"
          >
            <div>
              <div class="comment-head weibo-header">
                <div class="ava-box">
                  <el-avatar
                    class="weibo-avatar"
                    :src="post.avatarUrl"
                    size="small"
                  ></el-avatar>
                  <span class="weibo-username">{{ comment.name }}</span>
                </div>
                <span>{{ comment.time }}</span>
              </div>
              <div>{{ comment.content }}</div>
              <el-divider></el-divider>
            </div>
          </template>
        </el-collapse-item>
      </el-collapse>
    </el-card>

    <!-- 评论弹窗 -->
    <el-dialog :visible.sync="dialogVisible" title="评论" width="700px">
      <el-form ref="commentForm" :model="formData" label-width="0px">
        <el-form-item label="">
          <el-input
            type="textarea"
            :rows="6"
            v-model="formData.comment"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-row :gutter="16">
          <el-col :span="12">
            <el-button @click="handleClose">取消</el-button>
          </el-col>
          <el-col :span="12">
            <el-button type="primary" @click="submitForm">确定</el-button>
          </el-col>
        </el-row>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import dayjs from 'dayjs' // 导入日期js
import { mapState } from 'vuex'
import {
  getFavoriteDreamsApi,
  getDreamCommentListApi,
  publishCommentApi,
  likeApi,
  cancelLikeApi,
  favoriteApi,
  cancelFavoriteApi
} from '@/api/dream'
import { getUserInfoApi } from '@/api/user'
export default {
  name: 'collect',

  data () {
    return {
      dialogVisible: false,

      formData: {
        comment: ''
      },

      // 单个评论
      comment: {
        name: '', content: '', time: ''
      },

      // 单个梦境
      dream: {
        id: 0,
        time: '',
        avatarUrl: '',
        username: '',
        content: '',
        likes: 0,
        commentsCount: 0,
        favorites: 0,
        up: false, // 是否点赞
        comments: [], // 评论列表

        star: false // 是否收藏
      },

      weiboPosts: [
        // {
        //   id: 1,
        //   time: '2023-10-21 10:10:10',
        //   avatarUrl: "avatar1.jpg",
        //   username: "User1",
        //   content: "This is the content of the first post.",
        //   likes: 10,
        //   comments: 5,
        //   favorites: 3,
        //   up: false, // 是否点赞
        //   comments: [
        //     { name: "z", content: "啊阿斯顿", time: "2023-10-12 10:00:00" },
        //     { name: "z", content: "啊阿斯顿123", time: "2023-10-12 10:00:00" },
        //   ], // 评论

        //   star: false // 是否收藏
        // },
        // {
        //   id: 2,
        //   time: '2023-10-22 10:10:10',

        //   avatarUrl: "avatar2.jpg",
        //   username: "User2",
        //   content: "Another interesting post goes here.",
        //   likes: 15,
        //   comments: 8,
        //   favorites: 7,
        //   up: false, // 是否点赞
        //   comments: [], // 评论

        //   star: false // 是否收藏
        // }
      ],

      actionPost: {
        // 记录当前操作的微博项
        index: '',
        post: null,
        dreamId: ''
      }
    }
  },

  computed: {
    ...mapState('user', ['token'])
  },

  created () {
    this.loadData()
  },

  methods: {
    async loadData () {
      this.getDreams()
    },

    async getDreams () {
      let queue = []
      try {
        const res = await getFavoriteDreamsApi({
          userID: this.token
        })
        queue = res.data
      } catch (e) {
      }

      for (let i = 0; i < queue.length; i++) {
        const comments = await this.getDreamCommentList(queue[i].dreamID)

        // 封装dream
        const newDream = {
          id: queue[i].dreamID,
          time: queue[i].dreamTime,
          avatarUrl: queue[i].userAvatar,
          username: queue[i].userName,
          content: queue[i].dreamContent,
          likes: queue[i].likeCount,
          up: queue[i].isLike,
          star: queue[i].isFavorite,
          comments: comments,
          commentsCount: comments.length
        }

        // 将dream装入weiboPost
        this.weiboPosts.push(newDream)
      }
    },

    // 获取某梦境的评论列表
    async getDreamCommentList (dreamId) {
      try {
        const comments = []
        const res = await getDreamCommentListApi({
          dreamID: dreamId
        })
        for (let i = 0; i < res.data.length; i++) {
          const comment = {
            name: await this.getCommentUserName(res.data[i].userID),
            content: res.data[i].commentContent,
            time: res.data[i].commentTime
          }
          comments.push(comment)
        }
        return comments
      } catch (e) {
      }
    },

    // 获取评论的用户名
    async getCommentUserName (userID) {
      try {
        let userName = ''
        const res = await getUserInfoApi({
          id: userID
        })
        userName = res.data.userName
        return userName
      } catch (e) {
      }
    },

    handleLink (linknName) {
      this.$router.push({
        name: linknName
      })
    },

    openComment (row, index) {
      this.actionPost.index = index
      this.actionPost.post = row
      this.actionPost.dreamId = this.weiboPosts[index].id
      // console.log('当前操作项信息', this.actionPost)

      this.dialogVisible = true
    },
    handleClose () {
      this.formData = {
        comment: ''
      }
      this.dialogVisible = false
    },

    async submitForm () {
      // 在这里执行提交评论的逻辑
      // 可以使用 this.formData.comment 访问评论内容
      // 这里仅演示如何关闭对话框
      /* 模拟评论输入交互 */
      const username = await this.getCommentUserName(this.token)

      try {
        const res = await publishCommentApi({
          dreamID: this.actionPost.dreamId,
          userID: this.token,
          commentContent: this.formData.comment
        })
        if (res.code === 1) {
          // this.actionPost.post  当前操作的微博项
          this.weiboPosts[this.actionPost.index].comments.push({
            name: username,
            content: this.formData.comment,
            time: dayjs().format('YYYY-MM-DD HH:mm:ss')
          })
          this.handleClose()
        }
      } catch (e) {
      }
    },

    // 用户点赞
    async like () {
      try {
        if (!this.actionPost.post.up) {
          const res = await likeApi({
            userID: this.token,
            dreamID: this.actionPost.dreamId
          })
          if (res.code === 1) {
            this.actionPost.post.up = true
          }
        } else {
          const res = await cancelLikeApi({
            userID: this.token,
            dreamID: this.actionPost.dreamId
          })
          if (res.code === 1) {
            this.actionPost.post.up = false
          }
        }
      } catch (e) {
      }
    },

    // 用户收藏
    async star () {
      try {
        if (!this.actionPost.post.star) {
          const res = await favoriteApi({
            userID: this.token,
            dreamID: this.actionPost.dreamId
          })
          if (res.code === 1) {
            this.actionPost.post.star = true
          }
        } else {
          const res = await cancelFavoriteApi({
            userID: this.token,
            dreamID: this.actionPost.dreamId
          })
          if (res.code === 1) {
            this.actionPost.post.star = false
          }
        }
      } catch (e) {
      }
    }
  }
}
</script>

<style scoped lang="scss">
.comment-head {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}
::v-deep .el-icon-arrow-right:before {
  content: "展开";
}
::v-deep .el-collapse-item__arrow.is-active {
  transform: rotate(0deg);
  &::before {
    content: "收起";
  }
}

.upActive {
  color: red;
}
.starActive {
  color: red;
}
.weibo-list {
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 1000px;
  margin: 0 auto;
}

.weibo-card {
  margin-top: 30px;
  padding: 20px;
  width: 100%;
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

.weibo-actions {
  display: flex;
  justify-content: flex-end;
}

.weibo-add-button {
    display: none;
}

.ava-box{
  display: flex;
  align-items: center;
  font-size: 16px;
}
</style>
