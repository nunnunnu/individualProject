<template>
    <b-container>
    <div class="popup_wrap">
        <div class="popup">
            <div align="right">
                <button class="btn btn-white" @click="closePopup">&times;</button>
            </div>
            <br>
            <h4>재생 목록 추가</h4>
            <hr>
            <div>
                <button type="button" @click="nowPlayingAdd" class="btn btn-success">재생목록 추가</button>
                <br>
                <br>
                <button  type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasTop" aria-controls="offcanvasTop"
                    class="btn btn-success">플레이리스트 추가</button>
            </div>
            <div class="mylistPopup">
            <div class="offcanvas offcanvas-top" tabindex="-1" id="offcanvasTop" aria-labelledby="offcanvasTopLabel">
                <div class="offcanvas-header">
                    <h5 class="offcanvas-title" id="offcanvasBottomLabel">내 플레이리스트</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
                </div>
                <div class="offcanvas-body small">
            <div class="row">
                <div class="col-auto">
                <Button class="btn btn-success" @click="createPlayList">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-folder-plus" viewBox="0 0 16 16">
                        <path d="m.5 3 .04.87a1.99 1.99 0 0 0-.342 1.311l.637 7A2 2 0 0 0 2.826 14H9v-1H2.826a1 1 0 0 1-.995-.91l-.637-7A1 1 0 0 1 2.19 4h11.62a1 1 0 0 1 .996 1.09L14.54 8h1.005l.256-2.819A2 2 0 0 0 13.81 3H9.828a2 2 0 0 1-1.414-.586l-.828-.828A2 2 0 0 0 6.172 1H2.5a2 2 0 0 0-2 2zm5.672-1a1 1 0 0 1 .707.293L7.586 3H2.19c-.24 0-.47.042-.683.12L1.5 2.98a1 1 0 0 1 1-.98h3.672z"/>
                        <path d="M13.5 10a.5.5 0 0 1 .5.5V12h1.5a.5.5 0 1 1 0 1H14v1.5a.5.5 0 1 1-1 0V13h-1.5a.5.5 0 0 1 0-1H13v-1.5a.5.5 0 0 1 .5-.5z"/>  
                    </svg> 
                    새로운 플레이리스트 만들기</Button>
                </div>
                <div class="col-auto" v-if="input">
                    <input class="form-control form-control-sm" type="text" placeholder="생성할 이름을 입력해주세요" aria-label=".form-control-sm example"  v-model="name">
                </div>
                <div class="col-auto" v-if="input">
                    <Button class="btn btn-outline-success btn-sm" @click="savePlayList">저장</Button>
                </div>
            </div>
            <br>
            <div class="row">
                <div v-for="list in playlist" :key="list.seq" @click="addPlayListSong(list.seq)" class="onclick">
                    <h6 align="left">{{ list.title }}</h6>
                    <div class="row">
                        <div class="col-auto">
                            <p align="left">{{ list.regDt }}</p>
                        </div>
                        <div class="col-auto">
                            <span>{{ list.songcount }}곡</span>
                        </div>
                        <hr>
                    </div>
                </div>
            </div>
            </div>
            </div>
            </div>
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
            song:Object
        },
        data(){
            return{
                input:false,
                name: new Date(),
                playlist:null
            }
        },
        mounted(){
            this.loadMyPlayList()
        },
        methods:{
            closePopup(){
                this.$emit("closePopup")
            },
            nowPlayingAdd(){
                let songlist = JSON.parse(sessionStorage.getItem('playlist') ?? '[]')
                songlist.push(this.song)
                sessionStorage.setItem('playlist',JSON.stringify(songlist))
                this.$emit("nowPlayingAdd")
                this.$emit("closePopup")
                
            },
            createPlayList(){
                this.input=!this.input
            },
            savePlayList(){
                const token = Cookies.get('accessToken')
                
                axios.put(
                    `/playlist/`+this.name,{}, {
                        headers: {
                            Authorization: 'Bearer ' + Cookies.get('accessToken')
                    }
                })
                .then((response) => {
                    alert(response.data.message)
                    this.name=null
                    this.loadMyPlayList()
                })
                .catch((error) => {
                    const member = Cookies.get('member')
                    const refresh = Cookies.get('refreshToken')
                    axios.post("/member/refresh", {
                            id: member,
                            refresh: refresh
                        })
                        .then((e) => {
                            Cookies.set('accessToken', e.data.token)
                            this.savePlayList()
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
            loadMyPlayList(){
                axios.get(
                    `/playlist`, {
                        headers: {
                            Authorization: 'Bearer ' + Cookies.get('accessToken')
                    }
                })
                .then((response) => {
                    this.playlist=response.data
                })
                .catch((error) => {
                    // const member = Cookies.get('member')
                    const refresh = Cookies.get('refreshToken')
                    axios.post("/member/refresh", {
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
            addPlayListSong(seq){
                console.log(this.song.seq)
                axios.put(
                    `/playlist/song/${seq}/${this.song.seq}`, {}, {
                        headers: {
                            Authorization: 'Bearer ' + Cookies.get('accessToken')
                    }
                })
                .then((response) => {
                    console.log(response)
                    alert("저장되었습니다.")
                    // this.$emit("closePopup")
                    this.loadMyPlayList()
                })
                .catch((error) => {
                    console.log(error)
                    const member = Cookies.get('member')
                    const refresh = Cookies.get('refreshToken')
                    axios.post("/member/refresh", {
                        // id: member,
                        refresh: refresh
                    })
                    .then((e) => {
                        Cookies.set('accessToken', e.data.token)
                        this.addPlayListSong()
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
</script>
<style>
    .popup_wrap{
        position: fixed; width:100vw; height: 100vh;
        left: 0; top: 0; z-index: 9999; background-color: #55555570;
        padding-bottom: 102px;
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