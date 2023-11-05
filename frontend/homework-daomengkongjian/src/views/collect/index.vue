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
          @click="post.up = !post.up"
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
          @click="post.star = !post.star"
        />

        <!-- <v-icon name="heart"  style="margin-right: 12px" :class="{'upActive':post.up}" @click="post.up=!post.up"/>
        <v-icon name="comments"  style="margin-right: 12px" />
        <v-icon name="fa-star"  :class="{'starActive':post.star}" @click="post.star=!post.star"/> -->
      </div>
    </el-card>
  </div>
</template>

<script>
import dayjs from 'dayjs' // 导入日期js
import { mapState } from 'vuex'
import { getFavoriteDreamsApi, getDreamCommentListApi } from '@/api/dream'
import { getUserInfoApi } from '@/api/user'
export default {
  name: 'collect',

  data () {
    return {
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
      ]
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
      this.dialogVisible = true
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
