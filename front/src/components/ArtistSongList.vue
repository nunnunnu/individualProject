<template>
    <b-container>
        <div class="songList">
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">번호</th>
                    <th scope="col">곡명</th>
                    <th scope="col">아티스트</th>
                    <th scope="col">앨범</th>
                    <th scope="col">듣기</th>
                    <th scope="col">좋아요</th>
                    <th scope="col">뮤비</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(item, index ) in data" :key="item.seq">
                <td scope="row">{{ index+1 }}</td>
                <td>
                    <span v-if="item.title" class="badge rounded-pill text-bg-success">title</span>
                    <router-link :to="{name:'songDetail', params:{seq:item.seq}}">{{ item.name }}</router-link>
                    <br>
                </td>
                <td align="center">
                    <div style="display:flex; flex-direction: row;" >
                                    <tr v-for="(artist, index) in item.artists" :key="artist.seq" @click="changeChanel(artist.seq)">
                                        <!-- <router-link :to="{name:'artistDetail', params:{seq:artist.seq}}" style="font-size:12px"> -->
                                            <span class="onclick">
                                                {{ artist.name }}
                                            </span>
                                        <!-- </router-link> -->
                                        <span v-if="index < item.artists.length - 1">, </span>
                                    </tr>
                                </div>
                </td>
                <td>
                    <router-link :to="{name:'albumDetail', params:{seq:item.album.seq}}">{{ item.album.name }}</router-link>
                </td>
                <td>
                    <div v-if="item.files.length!=0">
                        <div class="onclick">

                            <svg @click="playSong(item)" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                            class="bi bi-play-fill" viewBox="0 0 16 16">
                            <path
                            d="m11.596 8.697-6.363 3.692c-.54.313-1.233-.066-1.233-.697V4.308c0-.63.692-1.01 1.233-.696l6.363 3.692a.802.802 0 0 1 0 1.393z" />
                        </svg>
                    </div>
                            </div>
                            <div v-else>
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-play" viewBox="0 0 16 16">
  <path d="M10.804 8 5 4.633v6.734L10.804 8zm.792-.696a.802.802 0 0 1 0 1.392l-6.363 3.692C4.713 12.69 4 12.345 4 11.692V4.308c0-.653.713-.998 1.233-.696l6.363 3.692z"/>
</svg>
                            </div>
                </td>
                <td>
                    <div @click="likeUnlike(item.seq)"  class="onclick">
                                <span v-if="item.isliked"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-heart-fill" viewBox="0 0 16 16">
      <path fill-rule="evenodd" d="M8 1.314C12.438-3.248 23.534 4.735 8 15-7.534 4.736 3.562-3.248 8 1.314z"/>
    </svg></span>
                                <span v-else><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-heart" viewBox="0 0 16 16">
  <path d="m8 2.748-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01L8 2.748zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143c.06.055.119.112.176.171a3.12 3.12 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15z"/>
</svg></span>
                    {{ item.likes }}
</div>
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
        <div v-if="dataLength==0">
            <p>곡정보가 없습니다.</p>
        </div>
    </div>
    </b-container>
</template>
<script>
    import axios from 'axios'
    import Cookies from 'js-cookie'
    export default {
        name: 'artistSongList',
        props: {},
        data() {
            return {
                seq: null,
                data: null,
                dataLength:null,
                isLogin:null
            }
        },
        created() {
            this.seq = this.$route.params.seq;
            if(Cookies.get('accessToken')!=null){
                this.isLogin=true;
            }else{
                this.isLogin=false
            }
            this.loadPage(this.seq)
        },
        methods: {
            loadPage(seq) {
                axios.get("/song/artist/part/" + seq+"/"+(this.isLogin?"login":"unLogin"), {
                    headers: {
                        Authorization: `Bearer `+Cookies.get('accessToken')
                    }
                })
                .then((e) => {
                    console.log(e)
                    if(!e.data.status){
                        this.dataLength=0
                    }else{
                        this.data = e.data.data
                        console.log(this.data)
                    }

                })
                .catch((error)=>{
                    console.log(error.response.status)
                    if(error.response.status==403){
                        const member = Cookies.get('member')
                        const refresh = Cookies.get('refreshToken')
                        axios.post("/member/refresh", {
                            id:member,
                            refresh:refresh
                        })
                        .then((e)=>{
                            Cookies.set('accessToken', e.data.token)
                            this.loadPage(seq)
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
            likeUnlike(seq){
                if(!this.isLogin){
                    alert("로그인 후 이용가능한 서비스입니다.")
                    this.$router.push("/login")
                }else{
                    axios.post("/likeUnlike/"+seq , {}, {
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
                            axios.post("/member/refresh", {
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
                }else{
                    axios.get("/ticket/check",{
                        headers: {
                            Authorization: 'Bearer ' + Cookies.get('accessToken')
                        }
                    })
                    .then((e)=>{
                        let songlist = JSON.parse(sessionStorage.getItem('playlist') ?? '[]')
                        songlist.push(item)
                        sessionStorage.setItem('playlist',JSON.stringify(songlist))
                        sessionStorage.setItem('nowIndex',songlist.length-1)
                        location.reload();
                    })
                    .catch((error)=>{
                        console.log(error)
                        if(confirm(error.response.data.message+"\n확인을 누르시면 이용권 페이지로 이동합니다.")){
                            this.$router.push("/ticket")
                        }else{
                            sessionStorage.removeItem('playlist')
                            sessionStorage.removeItem('nowIndex')
                            location.reload();
                        }
                    })
                }
            
            },
            changeChanel(changeSeq){
                this.childSeq = changeSeq
                this.$emit('changeSeq', this.childSeq)
                this.loadPage(this.childSeq)
            }
        }
    }
</script>

<style>
.songList{
    padding-bottom: 102px;
}
.onclick:hover{
    cursor: pointer;
}
</style>
