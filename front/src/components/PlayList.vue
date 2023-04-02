<template>
    <div v-if="songs!=null && songs.length!=0">
        <div class="HTML_Audio_player">
            <div class="Audio_Player_image">
                <img class="imgTag" style="border-radius: 60px;" :src="imgLoad(songs[index==null?0:index].album.uri)"/></div>
            <div class="player-content">
                <div class="player-info">
                    <a class="song-name" target="_blank">
                        <router-link :to="{name:'songDetail', params:{seq:songs[this.index==null?0:this.index].seq}}">
                            {{ songs[this.index==null?0:this.index].name }}
                        </router-link>
                        <a class="btn btn-dark" data-bs-toggle="offcanvas" href="#offcanvasExample" role="button"
                            aria-controls="offcanvasExample"
                            style="--bs-btn-padding-y: .1rem; --bs-btn-padding-x: .5rem; --bs-btn-font-size: .75rem;">
                            재생목록
                        </a>
                        <!-- 재생목록 모달창 -->
                        <div class="offcanvas offcanvas-start" tabindex="-1" id="offcanvasExample"
                            aria-labelledby="offcanvasExampleLabel">
                            <div class="offcanvas-header">
                                <h5 class="offcanvas-title" id="offcanvasExampleLabel">재생목록</h5>
                                <hr>
                                <button type="button" class="btn-close" data-bs-dismiss="offcanvas"
                                    aria-label="Close"></button>
                            </div>
                            <tr v-for="(s, idx) in songs" :key="s.seq">
                            <div class="offcanvas-body">
                                    <div v-bind:class="{playing: idx==index || (index==null && idx ==0)}" >
                                    <div @click="indexChange(idx)">
                                        <div class="row">
                                            <div class="col-auto">
                                            </div>
                                            <div class="col-auto">
                                                <img style="border-radius: 60px;" :src="imgLoad(s.album.uri)"
                                                    width="40" />
                                            </div>
                                            <div class="col-auto">
                                                <p>{{s.name}}</p>
                                            </div>
                                        </div>
                                    </div>
                        <div style="display:flex; flex-direction: row;">
                                    <tr v-for="(a, index) in s.artists" :key="a.seq">
                                        <div @click="changeChanel(a.seq)">
                                            <router-link :to="{name:'artistDetail', params:{seq:a.seq}}" style="font-size:12px">
                                                {{ a.name }}</router-link>
                                            <span v-if="index < s.artists.length - 1">, </span>
                                        </div>
                                    </tr>
                        </div>
                        </div>
                    </div>
                    <hr>
                </tr>
        </div>
        </a>
        <div  style="display:flex; flex-direction: row; justify-content: center;">
            <tr v-for="(artist, index) in songs[this.index==null?0:this.index].artists" :key="artist.seq">
                <router-link :to="{name:'artistDetail', params:{seq:artist.seq}}" style="font-size:12px">
                    {{ artist.name }}</router-link>
                <span v-if="index < songs[this.index==null?0:this.index].artists.length - 1">, </span>
            </tr>
        </div>
    </div>
    <div v-if="mp3!=null && index!=null" class="k2_audio_player">
        <audio ref="audioPlayer" @ended="playNext()" controls autoplay style="width: 80%;">
            <source :src="mp3" @onPlay="indexSetting" type="audio/mpeg" />
        </audio>
    </div>
    <div v-if="mp3!=null && index==null" class="k2_audio_player">
        <audio ref="audioPlayer" @ended="playNext()" controls style="width: 80%;">
            <source :src="mp3"  type="audio/mpeg" />
        </audio>
    </div>
    </div>
    </div>
    </div>
    <div v-else>
        <div class="HTML_Audio_player">
            <div class="Audio_Player_image"><img style="border-radius: 60px;"
                    src="https://downloadwap.com/thumbs2/wallpapers/p2ls/2019/abstract/26/fdb51ff613237395.jpg" /></div>
            <div class="player-content">
                <div class="player-info">
                    <p>재생할 곡이 없습니다.</p>
                </div>
                <a class="btn btn-dark" data-bs-toggle="offcanvas" href="#offcanvasExample" role="button"
                    aria-controls="offcanvasExample"
                    style="--bs-btn-padding-y: .1rem; --bs-btn-padding-x: .5rem; --bs-btn-font-size: .75rem;">
                    재생목록
                </a>
                <div class="offcanvas offcanvas-start" tabindex="-1" id="offcanvasExample"
                    aria-labelledby="offcanvasExampleLabel">
                    <div class="offcanvas-header">
                        <h5 class="offcanvas-title" id="offcanvasExampleLabel">재생목록</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
                    </div>
                    <div class="offcanvas-body">
                        <span v-if="songs==null">재생할 곡이 없습니다.</span>
                        <span v-else>

                        </span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import axios from 'axios'
    import Cookies from 'js-cookie'
    export default {
        name: 'playList',
        data() {
            return {
                mp3: null,
                index: sessionStorage.getItem('nowIndex'),
                songs: null,
                img: null
            }
        },
        mounted() {
            this.setPlayList()
            this.nowPlaying()
            // this.$refs.audioPlayer.addEventListener('ended', this.playNext);
            
        },
        watch: {
            // img(newValue) {
            // // This will be called whenever the "img" property is updated
            // console.log('New image source:', newValue);
            // }
            index(newIndex){
                const list = JSON.parse(sessionStorage.getItem('playlist') ?? '[]')
                this.img = this.imgLoad(list[newIndex].album.uri)
                const imgTag = new Image();
                imgTag.onload = () => {
                    this.img = this.imgLoad(list[newIndex].album.uri);
                    console.log(this.img)
                    this.$forceUpdate()
                };
                imgTag.src = this.imgLoad(list[newIndex].album.uri);
                console.log(imgTag)
            }
        },
        methods: {
            searchClick(keyword) {
                this.$router.push({
                    name: "totalSearch",
                    params: {
                        key: keyword
                    },
                });
            },
            nowPlaying() {
                if (this.songs != null && this.songs.length != 0) {
                    axios.get(
                        `http://15.164.21.92:8250/songfile/${this.songs[this.index==null?0:this.index].files[0].uri}/${this.songs[this.index==null?0:this.index].seq}`, {
                            headers: {
                                Authorization: 'Bearer ' + Cookies.get('accessToken')
                            },
                            responseType: 'blob'
                        })
                    .then((response) => {
                        const blobUrl = URL.createObjectURL(response.data);
                        if (blobUrl) {
                            this.mp3 = blobUrl;
                            return blobUrl
                        }
                    })
                    .catch((error) => {
                        const member = Cookies.get('member')
                        const refresh = Cookies.get('refreshToken')
                        axios.post("/member/refresh", {
                            id: member,
                            refresh: refresh
                        })
                        .then((e) => {
                            console.log(e.data.token)
                            Cookies.set('accessToken', e.data.token)
                            this.nowPlaying()
                        })
                        .catch((error) => {
                            alert("다시 로그인해주세요")
                            Cookies.remove('refreshToken')
                            Cookies.remove('accessToken')
                            Cookies.remove('member')
                            sessionStorage.removeItem("nowIndex")
                            sessionStorage.removeItem("playlist")
                            this.$router.push("/login")
                        })
                    })
                }
            },
            imgLoad(albumUri) {
                this.img = `http://15.164.21.92:8250/image/album/${albumUri}`
                return this.img
            },
            indexChange(idx) {
                sessionStorage.setItem('nowIndex', idx)
                this.index = idx
                // this.nowPlaying()
                this.mp3 = this.nowPlaying()
                // this.$router.go()
                // this.$refs.audioPlayer.play();
            },
            playNext() {
                if (this.songs.length - 1 <= this.index) {
                    this.index = 0
                }else if(this.index==null){
                    this.index=1
                } else {
                    this.index++;
                }
                sessionStorage.setItem('nowIndex', this.index)
                this.mp3 = this.nowPlaying()
                
                // this.$refs.audioPlayer.play();

            },
            setPlayList() {
                console.log("test")
                this.songs = JSON.parse(sessionStorage.getItem('playlist'))
                this.nowPlaying()
            },
            indexSetting(){
                console.log("playTest")
                if(sessionStorage.getItem("nowIndex")==null){
                    this.index=0;
                    sessionStorage.setItem('nowIndex', 0)
                }
            },
            changeChanel(changeSeq){
                this.$emit('changeChanel', changeSeq)
            }
        }
    }
</script>
<style>
.playing {
    /* color: black; */
    color: #03C988; 
    animation: pulse 2s ease-in-out infinite; 
}
@keyframes pulse {
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.05);
  }
  100% {
    transform: scale(1);
  }
}
</style>