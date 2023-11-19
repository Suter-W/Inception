<template>
    <div class="weibo-list">
        <div class="top-background">
            <img :src="backgroundUrl" alt="avatar" style="position: absolute;width: 100%;left: 0;height: 320px;z-index: 1;opacity: 0.6;">
        </div>
        <div class="userInfo">
            <el-avatar 
            class="weibo-avatar"
            :src="avatarUrl"
            size="large">
            </el-avatar>
            <span class="weibo-nickname">
                &nbsp;{{ nickname }}
            </span>
            <span class="selectOptions">
                <el-button  @click="followChange"><span>{{ followInfo }}</span></el-button>
            </span>
        </div>
        <el-divider class = "myDivider"></el-divider>
        <div class="showInfo">
            <el-tabs v-model="activeInfoName" type="card" @tab-click="handleActiveInfoNameClick">
                <el-tab-pane label="个性标签" name="userSignature">
                    <template slot="label">
                        <span style="width: 159px; display: inline-block;text-align: center;">个性标签</span>
                    </template>
                    <div class="userSignatureShow">
                        {{ bio }}
                    </div>
                </el-tab-pane>
                <el-tab-pane label="动态" name="logDream">
                    <template slot="label">
                        <span style="width: 159px; display: inline-block;text-align: center;">动&nbsp;&nbsp;&nbsp;&nbsp;态</span>
                    </template>
                    <div class="logDreamsDiv" v-for="(post, index) in logDreams" :key="post.id">
                        <div class="logDreamElem">
                            <div style="left: 8px;font-weight: bold;">{{ post.time }}</div>
                            <div style="left: 8px;text-indent: 2em;margin-top: 12px;">{{post.content}}</div>
                            <!-- <div style="left: 8px;">{{post.likeCount}}</div> -->
                            <div class="icons" style="text-align: right;">
                                <el-badge :value="post.likeCount" :max="99" 
                                    class="item" :style="{marginRight:'24px'}">
                                    <font-awesome-icon
                                        style="margin-right: 12px"
                                        :class="{ upActive: post.isLike }"
                                        :style="{ fontSize: '24px',color: post.isLike ? 'red' : 'gray',}"
                                        :icon="post.isLike ? 'fa-solid fa-heart' : 'fa-regular fa-heart'"
                                    />
                                </el-badge>
                                <font-awesome-icon
                                    style="margin-right: 12px"
                                    :class="{ starActive: post.isFavorite }"
                                    :style="{
                                        fontSize: '24px',
                                        marginLeft: '24px',
                                        color: post.isFavorite ? 'red' : 'gray',
                                    }"
                                    :icon="post.isFavorite ? 'fa-solid fa-star' : 'fa-regular fa-star'"
                                />
                                    <!-- :icon="post.isFavorite ? 'fa-solid fa-star' : 'fa-regular fa-star'" -->

                                <!-- {{post.likeCount}} -->
                            </div>
                            <el-divider></el-divider>
                        </div>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="关注列表" name="followUsers">
                    <template slot="label">
                        <span style="width: 159px; display: inline-block;text-align: center;">关注列表</span>
                    </template>
                    <div class="followeesTables" v-for="(post, index) in followees" :key="post.id">
                        <div style="height: 40px;display: flex;">
                            <div class="everyFolloweesInfo">
                                <el-avatar :src="post.userAvatarUrl" class="avatar">
                                </el-avatar>
                                <span>
                                    &nbsp;{{post.name}}
                                </span>
                            </div>
                            <div class="everyIsFollowed">
                                <el-button @click="everyFollowCheck(post,index)" v-if="tokenID != post.id"><span>{{ post.isFollowShowInfo }}</span></el-button>
                            </div>
                        </div>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="粉丝列表" name="fanUsers">
                    <template slot="label">
                        <span style="width: 159px; display: inline-block;text-align: center;">粉丝列表</span>
                    </template>
                    <div class="followeesTables" v-for="(post, index) in followers" :key="post.id">
                        <div style="height: 40px;display: flex;">
                            <div class="everyFolloweesInfo">
                                <el-avatar :src="post.userAvatarUrl" class="avatar">
                                </el-avatar>
                                <span>
                                    &nbsp;{{post.name}}
                                </span>
                            </div>
                            <div class="everyIsFollowed">
                                <el-button @click="everyFollowerCheck(post,index)" v-if="tokenID != post.id"><span>{{ post.isFollowShowInfo }}</span></el-button>
                            </div>
                        </div>
                    </div>
                </el-tab-pane>
            </el-tabs>
        </div>
    </div>
</template>

<script>
import dayjs from 'dayjs' // 导入日期js
import { mapState } from 'vuex'
import { 
    getUserInfoApi,
    isFollowApi,
    followApi,
    deleteFollowApi,
    getFolloweesApi,
    getFollowersApi,
    getShowUserLogDreamsApi
} from '@/api/user'
export default {

  data () {
    return {
        tokenID:0,
        backgroundUrl:'https://inception-avatar.oss-cn-shanghai.aliyuncs.com/f4e61b7c78fb46baafd3fae85d9d2949.',
        userShowID: 0,
        nickname:'',
        avatarUrl:'',
        bio:'',
        isFollow: '',
        followInfo:'',
        activeInfoName:'userSignature',
        followees:[],
        followers:[],
        logDreams:[],
    }
  },

  computed: {
    ...mapState('user', ['token'])
  },

  mounted () {

  },

  created () {
    this.tokenID = this.token
    const showUserId = this.$route.params.showUserId
    console.log(showUserId);
    this.userShowID = showUserId;
    this.getUserInfo();
    this.getFollowees();
    this.getFollowers();
    this.getUserLogDreams();
    // alert(showUserId);
  },

  methods: {
    handleActiveInfoNameClick(tab, event) {
        console.log(tab, event);
    },
    async getUserInfo () {
      try {
        this.userInfo = await isFollowApi({
            userID:this.token,
            hostID: this.userShowID
        })
        this.nickname = this.userInfo.data.host.userName
        this.avatarUrl = this.userInfo.data.host.userAvatar
        this.bio = this.userInfo.data.host.userSignature
        this.isFollow = this.userInfo.data.isFollow
        if(!this.isFollow){
            this.followInfo = '+ 关注'
        }else{
            this.followInfo = '取消关注'
        }
      } catch (e) {}
    },
    async getFollowees(){
        let queue = []
        try {
            const res = await getFolloweesApi({
                userID:this.userShowID,
            // hostID: this.userShowID
            })
            queue = res.data
        } catch (e) {}
        for(let i = 0;i < queue.length;i ++){
            const userIsFollow = await isFollowApi({
                userID:this.token,
                hostID: queue[i].followeeID,
            })
            let boolUserIsFollow = userIsFollow.data.isFollow
            const newUser = {
                id: queue[i].followeeInfo.userID,
                name:queue[i].followeeInfo.userName,
                userAvatarUrl: queue[i].followeeInfo.userAvatar,
                IsFollowed:boolUserIsFollow,
                isFollowShowInfo:(boolUserIsFollow) ? '取消关注': '+ 关注'
            }
            this.followees.push(newUser);
        }
    },
    async getFollowers(){
        let queue = []
        try {
            const res = await getFollowersApi({
                userID:this.userShowID,
            // hostID: this.userShowID
            })
            queue = res.data
        } catch (e) {}
        for(let i = 0;i < queue.length;i ++){
            const userIsFollow = await isFollowApi({
                userID:this.token,
                hostID: queue[i].followerID,
            })
            let boolUserIsFollow = userIsFollow.data.isFollow
            const newUser = {
                id: queue[i].followerInfo.userID,
                name:queue[i].followerInfo.userName,
                userAvatarUrl: queue[i].followerInfo.userAvatar,
                IsFollowed:boolUserIsFollow,
                isFollowShowInfo:(boolUserIsFollow) ? '取消关注': '+ 关注'
            }
            this.followers.push(newUser);
        }
    },
    async getUserLogDreams(){
        let queue = []
        try {
            const res = await getShowUserLogDreamsApi({
            hostID: this.userShowID,
            userID:this.token,
        })
            queue = res.data
        } catch (e) {}
        for(let i = 0;i < queue.length;i ++){
            // const userIsFollow = await isFollowApi({
            //     userID:this.token,
            //     hostID: queue[i].followerID,
            // })
            // let boolUserIsFollow = userIsFollow.data.isFollow
            const newLogDream = {
                time: queue[i].dreamTime,
                content:queue[i].dreamContent,
                likeCount: queue[i].likeCount,
                isLike:queue[i].isLike,
                isFavorite:queue[i].isFavorite
            }
            this.logDreams.push(newLogDream);
        }
    },
    followChange(){
        if(this.followInfo === '+ 关注'){
            this.followHost(this.token,this.userShowID);
            this.followInfo = '取消关注'
        }else{
            this.deleteFollowHost(this.token,this.userShowID);
            this.followInfo = '+ 关注'
        }
    },
    async followHost(temp_userID,temp_hostID){
        try{
            const res = await followApi({
                userID: temp_userID,
                hostID: temp_hostID
            })
            if(res.code === 1){
                this.$message({
                    message: '关注成功',
                    type: 'success'
                })
            }
        }catch(e){}
    },
    async deleteFollowHost(temp_userID,temp_hostID){
        try{
            const res = await deleteFollowApi({
                userID: temp_userID,
                hostID: temp_hostID
            })
            if(res.code === 1){
                
            }
        }catch(e){}
    },
    everyFollowCheck(post,index){
        if(this.followees[index].IsFollowed){
            this.deleteFollowHost(this.token,this.followees[index].id);
            this.followees[index].IsFollowed = false;
            this.followees[index].isFollowShowInfo = '+ 关注';
            this.followers = [];
            this.getFollowers();
        }else{
            this.followHost(this.token,this.followees[index].id);
            this.followees[index].IsFollowed = true;
            this.followees[index].isFollowShowInfo = '取消关注';
            this.followers = [];
            this.getFollowers();
        }
    },
    everyFollowerCheck(post,index){
        if(this.followers[index].IsFollowed){
            this.deleteFollowHost(this.token,this.followers[index].id);
            this.followers[index].IsFollowed = false;
            this.followers[index].isFollowShowInfo = '+ 关注';
            this.followees = [];
            this.getFollowees();
        }else{
            this.followHost(this.token,this.followers[index].id);
            this.followers[index].IsFollowed = true;
            this.followers[index].isFollowShowInfo = '取消关注';
            this.followees = [];
            this.getFollowees();
        }
    },
    
  }
}
</script>

<style scoped lang="scss">
.weibo-list {
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 800px;
  border-left-style: solid;
  border-left-width: 2px;
  border-left-color: #ccc;
  border-right-style: solid;
  border-right-color: #ccc;
  border-right-width: 2px;
  margin: 0 auto;
  .top-background {
    position: relative;
    display: inline-block;
    width:100%;
    height: 320px;
    text-align: center;
  }
  .userInfo{
    display: inline-block;
    width: 100%;
    height: 64px;
    text-align: left;
    padding-left: 20px;
    position: relative;
    display: flex;
    z-index: 99;
    .weibo-avatar{
        position: absolute;
        width: 100px;
        height: 100px;
        margin-right: 10px;
        margin-top: -36px;
        // background-size: cover;
        display: flex;
        align-items: center;
        justify-content: center;
        border-color: white;
        border-width: 4px;
        border-style: solid;
    }
    .weibo-nickname{
        position: absolute;
        top: 8px;
        left: 128px;
        display: inline-block;
        font-size: 20px;
        margin-left: 10px;
    }
    .selectOptions{
        position: absolute;
        top: 8px;
        right: 50%;
        .el-button{
            width: 100px;
            height: 36px;
            border-radius: 18px;
            font-size: 20px;
            text-align: center;
            color: white;
            background-color: orange;
            box-shadow:none;
            span{
                position: absolute;
                transform:translate(-50%,-50%);
                color: white;
            }
        }
    }
  }
  .myDivider{
    margin: 0px;
    margin-top: 6px;
  }
  .myEltabs{
    width: 100%;
  }
  .showInfo{
    width: 100%;
    .userSignatureShow{
        // text-indent: 2em;
        padding: 0 20px;
        text-indent: 2em;
        min-Height: 400px;
    }
    .logDreamsDiv{
        padding: 0 20px;
    }
    .followeesTables{
        justify-content: space-between;
        padding: 0 20px;
        min-Height: 80px;
        .everyFolloweesInfo{
            flex: 2;
            display: flex;
            align-items: center;
            // justify-content: center;
            .avatar {
                margin-right: 10px;
                // background-size: cover;
                // display: flex;
                // align-items: center;
                // justify-content: center;
            }
            span{
                margin-left: 10px;
                font-weight: bold;
                font-size: 14px;
            }
        }
        .everyIsFollowed{
            flex: 1;
            .el-button{
                width: 100px;
                height: 36px;
                border-radius: 18px;
                font-size: 20px;
                text-align: center;
                color: white;
                background-color: orange;
                box-shadow:none;
                span{
                    position: absolute;
                    transform:translate(-50%,-50%);
                    color: white;
                }
            }
        }
    }
    // display: flex;
  }
    // ::v-deep .el-tabs{
    //     width: 100%;
    // }
    // ::v-deep .el-tab-pane{
    //     width: 100%;
    // }
}
</style>