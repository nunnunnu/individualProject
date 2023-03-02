<template>
    <b-container>
        <div class="songDetail">
        <br>
        <h2 class="pb-4 mb-4 fst-italic border-bottom">곡 정보</h2>
        <br>
        <div v-if="data!=null">
            <div class="row">
                <div class="col-3">
                    <img :src="`http://localhost:8250/image/album/${data.album.uri}`"
                        style="max-width: 100%; height: auto;" align="right" class="rounded float-start">
                </div>
                <div class="col-5">
                    <!-- <p align="left">[{{ data.type }}]</p> -->
                    <h2 align="left">{{ data.name }}</h2>
                    <tr v-for="item in data.artists" :key="item.seq">
                        <router-link :to="{name:'artistDetail', params:{seq:item.seq}}" style="font-size:25px">
                            {{ item.name }}</router-link>
                    </tr>
                    <br>
                    <div align="left">
                        <router-link :to="{name:'albumDetail', params:{seq:data.album.seq}}" style="font-size:20px">앨범명
                            : {{ data.album.name }}
                        </router-link>
                        <p align="left">발매일 : {{ data.regDt }}</p>
                        <p align="left">장르 : {{ data.genre }}</p>
                    </div>
                </div>
                <div class="col-3">
                    <div @click="likeUnlike(data.seq)">
                                <span v-if="liked"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-heart-fill" viewBox="0 0 16 16">
      <path fill-rule="evenodd" d="M8 1.314C12.438-3.248 23.534 4.735 8 15-7.534 4.736 3.562-3.248 8 1.314z"/>
    </svg></span>
                                <span v-else><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-heart" viewBox="0 0 16 16">
  <path d="m8 2.748-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01L8 2.748zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143c.06.055.119.112.176.171a3.12 3.12 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15z"/>
</svg></span>
                    {{data.likes}}
</div>
                </div>
            </div>
            <br>
            <hr>
            <br>
            <h3 class="pb-4 mb-4 fst-italic border-bottom">가사</h3>
            <!-- <details align="left">
                <summary>펼치기</summary>
                <div v-html="data.lyrics"></div>
            </details> -->
            <p>
                <a class="btn btn-success" data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
                    펼치기
                </a>
                </p>
                <div class="collapse" id="collapseExample">
                <div class="card card-body">
                    <div v-html="data.lyrics"></div>
                </div>
                </div>
            <br>
            <h3 class="pb-4 mb-4 fst-italic border-bottom">작사/작곡</h3>
            <div class="creator">
                <tr v-for="creator in data.creates" :key="creator.seq">
                    <div class="card">
                        <img :src="`http://localhost:8250/image/artist/${creator.uri}`" class="card-img-top">
                        <div class="card-body">
                            <p class="card-title">{{creator.name}}</p>
                            <p class="card-text">{{creator.type}}</p>
                            <router-link :to="{name:'artistDetail', params:{seq:creator.seq}}">상세보기</router-link>
                        </div>
                    </div>
                </tr>
            </div>
            <br>
            <div v-if="data.movie!=null">
            <h3 class="pb-4 mb-4 fst-italic border-bottom">뮤직비디오</h3>
            <div v-html="movie()"></div>
            <br>
        </div>
        <div v-if="user!=null">
            <div class="box">
                <h3 class="pb-4 mb-4 fst-italic border-bottom">스트리밍 리포트</h3>
                <div v-if="user.count==0">
                    <p>아직 곡을 감상하지 않으셨습니다.</p>
                </div>
                <div v-if="user.count!=0">
                    <div class="row">
                        <div class="col-auto">
                            내가 처음 들은 날 : 
                        </div>
                        <div class="col-4">
                            {{ user.date }}
                        </div>
                        <div class="col-auto">
                            총 감상 횟수 : 
                        </div>
                        <div class="col-4">
                            {{ user.count}}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
<br>
</div>
    </b-container>
</template>
<script>
    import axios from 'axios'
    import Cookies from 'js-cookie'
    export default {
        name: 'albumDetail',
        props : ['seq'],
        data() {
            return {
                data: null,
                isLogin:null,
                user:null,
                liked:null
            }
        },
        created() {
            // this.seq = this.$route.params.seq;
            this.loadPage(this.seq)
            if (Cookies.get('accessToken') != null) {
                this.isLogin = true
                this.listenCount()
            } else {
                this.isLogin = false
            }
            this.checkLiked()
        },
        methods: {
            loadPage(seq) {
                axios.get("http://localhost:8250/song/detail/"+seq)
                    .then((e) => {
                        this.data = e.data.data
                    })                
            },
            checkLiked(){
                axios.get("http://localhost:8250/like/" + this.seq, {
                    headers: {
                        Authorization: `Bearer `+Cookies.get('accessToken')
                    }
                })
                    .then((e) => {
                        if(e.data.data){
                            this.liked=true
                        }else{
                            this.liked = false
                        }
                        // this.liked=e.data.data
                    })
            },
            movie() {    
                return this.data.movie
            },
            listenCount(){
                axios.get("http://localhost:8250/listen/song/"+this.seq, {
                    headers: {
                    Authorization: 'Bearer ' + Cookies.get('accessToken')
                    }
                })
                .then((e) => {
                    this.user = e.data.data
                })
            },
            likeUnlike(seq){
                if(!this.isLogin){
                    alert("로그인 후 이용가능한 서비스입니다.")
                    this.$router.push("/login")
                }else{
                    axios.post("http://localhost:8250/likeUnlike/"+seq , {}, {
                        headers: {
                            Authorization: `Bearer `+Cookies.get('accessToken')
                        }
                    })
                    .then((e)=>{
                        this.checkLiked()
                        this.loadPage(this.seq)
                    })
                }

            }
        }
    }
</script>

<style>
    p {
        font-size: 20px
    }

    .creator {
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        object-fit: cover;

    }

    .card-img-top {
        height: 150px;
        overflow: hidden;
    }
    .box{
        font-size: 23px;
    }
    .songDetail{
        padding-bottom: 102px;
    }
</style>