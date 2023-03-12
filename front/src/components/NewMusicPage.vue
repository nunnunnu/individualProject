<template>
    <b-container>
        <br>
        <h2 class="pb-4 mb-4 fst-italic border-bottom">최신 음악</h2>
        <table class="table">
                <thead>
                    <tr>
                        <th scope="col">번호</th>
                        <th scope="col">곡정보</th>
                        <th scope="col">앨범</th>
                        <th scope="col">좋아요</th>
                        <th scope="col">듣기</th>
                        <th scope="col">담기</th>
                        <th scope="col">다운</th>
                        <th scope="col">뮤비</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(item, index) in data" :key="item.seq">
                            <th scope="row">{{ currentPage*size+(index+1) }}</th>
                            <td align="left">
                                <span v-if="item.title" class="badge rounded-pill text-bg-success">title</span>
                                <router-link :to="{name:'songDetail', params:{seq:item.seq}}">{{ item.name }}</router-link>
                                <br>
                                <tr v-for="artist in item.artists" :key="artist.seq">
                                    <router-link :to="{name:'artistDetail', params:{seq:artist.seq}}" style="font-size:12px">{{ artist.name }}</router-link>
                                </tr>
                            </td>
                            <td>
                                <router-link :to="{name:'albumDetail', params:{seq:item.album.seq}}" style="font-size:15px">{{ item.album.name }}</router-link>
                            </td>
                            <td>
                                <div @click="likeUnlike(item.seq)">
                                <span v-if="item.isliked"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-heart-fill" viewBox="0 0 16 16">
      <path fill-rule="evenodd" d="M8 1.314C12.438-3.248 23.534 4.735 8 15-7.534 4.736 3.562-3.248 8 1.314z"/>
    </svg></span>
                                <span v-else><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-heart" viewBox="0 0 16 16">
  <path d="m8 2.748-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01L8 2.748zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143c.06.055.119.112.176.171a3.12 3.12 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15z"/>
</svg></span>
                                {{item.likes}}
</div>
                            </td>
                            <td>
                                <div v-if="item.files.length!=0">
                                <svg @click="playSong(item)" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                    class="bi bi-play-fill" viewBox="0 0 16 16">
                                    <path
                                        d="m11.596 8.697-6.363 3.692c-.54.313-1.233-.066-1.233-.697V4.308c0-.63.692-1.01 1.233-.696l6.363 3.692a.802.802 0 0 1 0 1.393z" />
                                </svg>
                            </div>
                                <div v-else>
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-play" viewBox="0 0 16 16">
    <path d="M10.804 8 5 4.633v6.734L10.804 8zm.792-.696a.802.802 0 0 1 0 1.392l-6.363 3.692C4.713 12.69 4 12.345 4 11.692V4.308c0-.653.713-.998 1.233-.696l6.363 3.692z"/>
    </svg>
                                </div>
                            </td>
                            <td>
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-plus-square" viewBox="0 0 16 16">
  <path d="M14 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h12zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2z"/>
  <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
</svg>
                            </td>
                            <td>
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-download" viewBox="0 0 16 16">
  <path d="M.5 9.9a.5.5 0 0 1 .5.5v2.5a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1v-2.5a.5.5 0 0 1 1 0v2.5a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2v-2.5a.5.5 0 0 1 .5-.5z"/>
  <path d="M7.646 11.854a.5.5 0 0 0 .708 0l3-3a.5.5 0 0 0-.708-.708L8.5 10.293V1.5a.5.5 0 0 0-1 0v8.793L5.354 8.146a.5.5 0 1 0-.708.708l3 3z"/>
</svg>
                            </td>
                            <td>
                                <div v-if="item.movie!=null">
                                    <router-link :to="{name:'movie', params:{tag:item.movie}}">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-tv-fill" viewBox="0 0 16 16">
  <path d="M2.5 13.5A.5.5 0 0 1 3 13h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zM2 2h12s2 0 2 2v6s0 2-2 2H2s-2 0-2-2V4s0-2 2-2z"/>
</svg>
</router-link>
                                </div>
                                <div v-else-if="item.movie==null">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-tv" viewBox="0 0 16 16">
  <path d="M2.5 13.5A.5.5 0 0 1 3 13h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zM13.991 3l.024.001a1.46 1.46 0 0 1 .538.143.757.757 0 0 1 .302.254c.067.1.145.277.145.602v5.991l-.001.024a1.464 1.464 0 0 1-.143.538.758.758 0 0 1-.254.302c-.1.067-.277.145-.602.145H2.009l-.024-.001a1.464 1.464 0 0 1-.538-.143.758.758 0 0 1-.302-.254C1.078 10.502 1 10.325 1 10V4.009l.001-.024a1.46 1.46 0 0 1 .143-.538.758.758 0 0 1 .254-.302C1.498 3.078 1.675 3 2 3h11.991zM14 2H2C0 2 0 4 0 4v6c0 2 2 2 2 2h12c2 0 2-2 2-2V4c0-2-2-2-2-2z"/>
</svg>
                                </div>
                            </td>
                    </tr>
                </tbody>
            </table>
    <div class="pagearea">
        <ul class="pagination justify-content-center">
        <li class="page-item disabled">
            <a v-if="currentPage==0" class="page-link">Previous</a>
        </li>
        <a v-if="currentPage!=0" class="page-link" @click="prePage()">Previous</a>
            <tr v-for="page in totalPage" :key="page">
                <li class="page-item">
                    <a class="page-link" @click="pageClick(page-1)">
                        <font color="red" v-if="page-1==currentPage">{{ page }}</font>
                        <font v-if="page-1!=currentPage">{{ page }}</font>
                    </a>
                </li>
            </tr>
            <li v-if="currentPage+1==totalPage" class="page-item disabled">
            <a class="page-link">Next</a>
            </li>
            <li v-if="currentPage+1!=totalPage" class="page-item">
            <a class="page-link" @click="pageClick(currentPage+1)">Next</a>
            </li>
        </ul>
    </div>
    </b-container>
</template>
<script>
    import axios from 'axios'
    import Cookies from 'js-cookie'
    export default {
        name: 'newSong',
        data() {
            return {
                data: null,
                currentPage: 0,
                totalPage: 0,
                size: 0,
                isLogin:null
            }
        },
        mounted() {
            if(Cookies.get('accessToken')!=null){
                this.isLogin=true
            }else{
                this.isLogin=false
            }
            this.loadPage()


        },
        methods: {
            loadPage() {
                axios.get("http://localhost:8250/song/new/"+(this.isLogin?"login":"unLogin")+"?page="+this.currentPage, {
                    headers: {
                        Authorization: `Bearer `+Cookies.get('accessToken')
                    }
                })
                .then((e) => {
                    this.data = e.data.data.content
                    console.log(this.data)
                    this.totalPage=e.data.data.totalPages
                    this.size=e.data.data.size                        
                })
                .catch((error)=>{
                    console.log(error.response.status)
                    if(error.response.status==403){
                        const member = Cookies.get('member')
                        const refresh = Cookies.get('refreshToken')
                        axios.post("http://localhost:8250/member/refresh", {
                            id:member,
                            refresh:refresh
                        })
                        .then((e)=>{
                            Cookies.set('accessToken', e.data.token)
                            this.loadPage()
                        })
                        .catch((error)=>{
                            alert("다시 로그인해주세요")
                            Cookies.remove('refreshToken')
                            Cookies.remove('accessToken')
                            Cookies.remove('member')
                            sessionStorage.removeItem("nowIndex")
                            sessionStorage.removeItem("playlist")
                            this.$router.push("/login")
                        })
                    }
                })
            },
            pageClick(page){
                this.currentPage=page
                this.loadPage()
            },
            prePage(){
                this.currentPage = this.currentPage-1
                this.loadPage()
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
                        this.loadPage(this.seq)
                    })
                    .catch((error)=>{
                        console.log(error.response.status)
                        if(error.response.status==403){
                            const member = Cookies.get('member')
                            const refresh = Cookies.get('refreshToken')
                            axios.post("http://localhost:8250/member/refresh", {
                                id:member,
                                refresh:refresh
                            })
                            .then((e)=>{
                                Cookies.set('accessToken', e.data.token)
                                this.likeUnlike(seq)
                            })
                            .catch((error)=>{
                                alert("다시 로그인해주세요")
                                Cookies.remove('refreshToken')
                                Cookies.remove('accessToken')
                                Cookies.remove('member')
                                sessionStorage.removeItem("nowIndex")
                                sessionStorage.removeItem("playlist")
                                this.$router.push("/login")
                            })
                        }
                    })
                }

            },
            playSong(item){
                if(!this.isLogin){
                    alert("로그인 후 이용가능합니다.")
                    this.$router.push("/login")
                }
                // let songlist = []
                let songlist = JSON.parse(sessionStorage.getItem('playlist') ?? '[]')
                songlist.push(item)
                sessionStorage.setItem('playlist',JSON.stringify(songlist))
                sessionStorage.setItem('nowIndex',songlist.length-1)
                this.$router.go();
            
            }
        }
    }
</script>

<style>
    .typeInfo {
        font-size: 15px;
        color: gray;
    }

    p {
        font-size: 15px;
    }

    .albums {
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        object-fit: cover;
    }
    .pagearea{
        padding-bottom: 102px;
    }
</style>