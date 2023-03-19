<template>
    <b-container>
    <div class="popup_wrap">
        <div class="popup">
            <div align="right">
                <button class="btn btn-white" @click="closePopup">&times;</button>
            </div>
            <div v-if="songlist!=null">
                <div align="left" @click="nowPlay">
                    <button class="btn btn-success btn-sm"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-earmark-play-fill" viewBox="0 0 16 16">
      <path d="M9.293 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V4.707A1 1 0 0 0 13.707 4L10 .293A1 1 0 0 0 9.293 0zM9.5 3.5v-2l3 3h-2a1 1 0 0 1-1-1zM6 6.883a.5.5 0 0 1 .757-.429l3.528 2.117a.5.5 0 0 1 0 .858l-3.528 2.117a.5.5 0 0 1-.757-.43V6.884z"/>
    </svg>play</button>
                </div>
            </div>
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">번호</th>
                        <th scope="col">커버이미지</th>
                        <th scope="col">곡정보</th>
                        <!-- <th scope="col">앨범</th> -->
                        <th scope="col">듣기</th>
                        <th scope="col">삭제</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(item, index) in songlist" :key="item.seq">
                            <th scope="row">{{ index+1 }}</th>
                            <td>
                                <router-link :to="{name:'albumDetail', params:{seq:item.album.seq}}" style="font-size:15px">
                                    <img style="border-radius: 5px;"
                                    width="50" height="50"
                                    :src="`http://localhost:8250/image/album/${item.album.uri}`" />
                                </router-link>
                            </td>
                            <td align="left">
                                <router-link :to="{name:'songDetail', params:{seq:item.seq}}">{{ item.name }}</router-link>
                                <br>
                                <tr v-for="artist in item.artists" :key="artist.seq">
                                    <router-link :to="{name:'artistDetail', params:{seq:artist.seq}}" style="font-size:12px">{{ artist.name }}</router-link>
                                </tr>
                            </td>
                            <!-- <td>
                                <router-link :to="{name:'albumDetail', params:{seq:item.album.seq}}" style="font-size:15px">{{ item.album.name }}</router-link>
                            </td> -->
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
                                <button class="btn btn-dark btn-sm" @click="deleteSong(item.playListOrder   )">삭제</button>
                            </td>
                    </tr>
                </tbody>
            </table>
            <div align="center" v-if="songlist==undefind || songlist==null">저장된 곡이 없습니다.</div>
            <br>
            
        </div>
    </div>
</b-container>
</template>
<script>
// import { Axios } from 'axios'
    import axios from 'axios'
    import Cookies from 'js-cookie'
    
    export default{
        name:"playListPopup",
        props:{
            seq:Number
        },
        data(){
            return{
                songlist:null,
                img:null,
                size:null
            }
        },
        mounted(){
            this.loadSongList()
            
        },
        methods:{
            loadSongList(){
                axios.get(
                    `http://localhost:8250/playlist/song/`+this.seq, {
                        headers: {
                            Authorization: 'Bearer ' + Cookies.get('accessToken')
                    }
                })
                .then((response) => {
                    this.songlist=response.data.data
                    console.log(this.songlist)
                    // this.size=this.songlist.length
                })
                .catch((error) => {
                    const member = Cookies.get('member')
                    const refresh = Cookies.get('refreshToken')
                    axios.post("http://localhost:8250/member/refresh", {
                            // id: member,
                            refresh: refresh
                        })
                        .then((e) => {
                            Cookies.set('accessToken', e.data.token)
                            this.loadMyPlayList()
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
            },
            closePopup(){
                this.$emit("closePopup")
            },
            playSong(item){
                axios.get("http://localhost:8250/ticket/check",{
                    headers: {
                        Authorization: 'Bearer ' + Cookies.get('accessToken')
                    }
                })
                .then((e)=>{
                    let song = JSON.parse(sessionStorage.getItem('playlist') ?? '[]')
                    song.push(item)
                    sessionStorage.setItem('playlist',JSON.stringify(song))
                    sessionStorage.setItem('nowIndex',song.length-1)
                    this.$router.go();
                })
                .catch((error)=>{
                    console.log(error)
                    if(confirm(error.response.data.message+"\n확인을 누르시면 이용권 페이지로 이동합니다.")){
                        this.$router.push("/ticket")
                    }else{
                        sessionStorage.removeItem('playlist')
                        sessionStorage.removeItem('nowIndex')
                        this.$router.go();
                    }
                })
                // let songlist = []
                
            },
            nowPlay(){
                axios.get("http://localhost:8250/ticket/check",{
                    headers: {
                        Authorization: 'Bearer ' + Cookies.get('accessToken')
                    }
                })
                .then((e)=>{
                    sessionStorage.setItem("nowIndex",0)
                    sessionStorage.setItem("playlist", JSON.stringify(this.songlist))
                    this.$router.go();
                })
                .catch((error)=>{
                    console.log(error)
                    if(confirm(error.response.data.message+"\n확인을 누르시면 이용권 페이지로 이동합니다.")){
                        this.$router.push("/ticket")
                    }else{
                        sessionStorage.removeItem('playlist')
                        sessionStorage.removeItem('nowIndex')
                        this.$router.go();
                    }
                })
                
            },
            deleteSong(order){
                if(confirm("정말 삭제하시겠습니까?")){
                    axios.delete(
                        `http://localhost:8250/playlist/`+this.seq+"/"+order, {
                            headers: {
                                Authorization: 'Bearer ' + Cookies.get('accessToken')
                        }
                    })
                    .then((response) => {
                        alert("삭제되었습니다.")
                        this.loadSongList()
                    })
                    .catch((error) => {
                        console.log(error)
                        const member = Cookies.get('member')
                        const refresh = Cookies.get('refreshToken')
                        axios.post("http://localhost:8250/member/refresh", {
                            // id: member,
                            refresh: refresh
                        })
                        .then((e) => {
                            Cookies.set('accessToken', e.data.token)
                            this.loadMyPlayList()
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
            }
        }
    }
</script>
<style>
    .popup_wrap{
        position: fixed; width:100vw; height: 100vh;
        left: 0; top: 0; z-index: 9999; background-color: #55555570;
    }
    .popup{
        position:absolute; left:50%;
        top: 35%;
        margin-left:-150px; margin-top:-130px;
        background-color: white;
        padding:30px;
    }
    .mylistPopup{
        padding-bottom: 102px;
    }
</style>