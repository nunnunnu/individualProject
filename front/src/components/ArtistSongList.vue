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
                    <th scope="col">좋아요</th>
                    <th scope="col">뮤비</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(item, index ) in data" :key="item.seq">
                <th scope="row">{{ index+1 }}</th>
                <td>
                                <span v-if="item.title" class="badge text-bg-primary">title</span>
                                <router-link :to="{name:'songDetail', params:{seq:item.seq}}">{{ item.name }}</router-link>
                                <br>
                                <tr v-for="artist in item.artists" :key="artist.seq">
                                    <router-link :to="{name:'artistDetail', params:{seq:artist.seq}}" style="font-size:12px">{{ artist.name }}</router-link>
                                </tr>
                            </td>
                <td>
                    <tr v-for="artist in item.artist" :key="artist.seq">
                        <router-link :to="{name:'artistDetail', params:{seq:artist.seq}}">{{ artist.name }}</router-link>
                    </tr>
                </td>
                <td>
                    <router-link :to="{name:'albumDetail', params:{seq:item.album.seq}}">{{ item.album.name }}</router-link>
                </td>
                <td>
                    <div @click="likeUnlike(item.seq)">
                                <span v-if="item.isLiked"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-heart-fill" viewBox="0 0 16 16">
      <path fill-rule="evenodd" d="M8 1.314C12.438-3.248 23.534 4.735 8 15-7.534 4.736 3.562-3.248 8 1.314z"/>
    </svg></span>
                                <span v-else><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-heart" viewBox="0 0 16 16">
  <path d="m8 2.748-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01L8 2.748zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143c.06.055.119.112.176.171a3.12 3.12 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15z"/>
</svg></span>
                    {{ item.like }}
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
            this.loadPage(this.seq)
            if(Cookies.get('accessToken')!=null){
                this.isLogin=true;
            }else{
                this.isLogin=false
            }
            console.log(this.isLogin)
        },
        methods: {
            loadPage(seq) {
                axios.get("http://localhost:8250/song/artist/part/" + seq, {
                    headers: {
                        Authorization: `Bearer `+Cookies.get('accessToken')
                    }
                })
                    .then((e) => {
                        if(!e.data.status){
                            this.dataLength=0
                        }else{
                            this.data = e.data.data.content
                        }

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
                        alert(e.data.message)
                        this.loadPage(this.seq)
                    })
                }

            }
        }
    }
</script>

<style>
.songList{
    padding-bottom: 102px;
}
</style>