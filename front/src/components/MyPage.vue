<template>
    <b-container>
    <div class="myPage">
            
            <h2 class="pb-4 mb-4 fst-italic border-bottom">마이 뮤직</h2>
            <div class="area">
                <h4 class="pb-4 mb-4 fst-italic border-bottom">선호 장르</h4>
                <Pie :data="chartDataReturn()" :options="options" />
            </div>
            <br>
            <br>
            <br>
            <br>
            <br>
        <h4 class="pb-4 mb-4 fst-italic border-bottom">플레이리스트</h4>
        <div align="left">
            <div class="row" >
                <div class="col-auto" >
                    <button type="button" @click="inputChange" class="btn btn-success btn-sm">추가</button>
                </div>
                <div class="col-auto" v-if="input">
                    <input class="form-control form-control-sm" type="text" placeholder="생성할 이름을 입력해주세요" aria-label=".form-control-sm example"  v-model="name">
                </div>
                <div class="col-auto" v-if="input">
                        <Button class="btn btn-outline-success btn-sm" @click="savePlayList">저장</Button>
                    </div>
            </div>
        </div>
        <br>
        <div v-for="list in playlist" :key="list.seq">
                    <h6 align="left"  @click="playlistShow(list.seq)">{{ list.title }}</h6>
                    <div class="row">
                        <div class="col-auto"  @click="playlistShow(list.seq)">
                            <p align="left">{{ list.regDt }}</p>
                        </div>
                        <div class="col-auto"  @click="playlistShow(list.seq)">
                            <span>{{ list.songcount }}곡</span>
                        </div>
                        <div class="col-auto">
                            <button class="btn btn-dark" style="--bs-btn-padding-y: .25rem; --bs-btn-padding-x: .5rem; --bs-btn-font-size: .75rem;" @click="delPlaylist(list.seq)">
                                플레이리스트 삭제</button>
                        </div>
                        <hr>
                    </div>
                </div>
    </div>
    <songlist :seq="seq" v-if="popup" @closePopup="closePopup"/>
    </b-container>
</template>
<script>
    import Cookies from 'js-cookie'
    import axios from 'axios'
    import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
    import { Pie } from 'vue-chartjs';
    import songlist from '@/components/PlayListSongListPopup';

    ChartJS.register(ArcElement, Tooltip, Legend);
    
    export default {
        name: 'myPage',
        components: {
            Pie, songlist
        },
        data() {
            return {
                chart:null,
                labels:null,
                isLogin:null,
                playlist:null,
                seq:null,
                popup:false,
                input:false,
                name:new Date(),
                options : {
                    responsive: true,
                    maintainAspectRatio: false
                }
            }
        },
        created() {
            if (Cookies.get('accessToken') != null) {
                this.isLogin = true
                this.loadPage()
                this.loadMyPlayList()
            } else {
                alert("로그인 후 이용가능한 서비스입니다.")
                this.isLogin = false
                this.$router.push("/login")
            }
        },
        methods: {
            loadPage() {
                axios.get("/listen/genre", {
                    headers: {
                    Authorization: 'Bearer ' + Cookies.get('accessToken')
                    }
                })
                .then((e) => {
                    this.chart = e.data.data
                    this.labels = e.data.data.labels
                    this.data = e.data.data.data
                    // console.log(this.chart)
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
            chartDataReturn(){
                return {
                    labels: this.labels,
                    datasets: [
                        {
                            backgroundColor: ['#B9F3E4', '#EA8FEA', '#FFAACF', '#F6E6C2', '#F6F7C1','#BEF0CB','#D1FFF3','#C1AEFC','ECF2FF','E3DFFD','E5D1FA','FFF4D2','B9F3FC','AEE2FF','93C6E7','FEDEFF','FF597B','FF8E9E','F9B5D0','EEEEEE'],
                            data: this.data
                        }
                    ],
                }// 
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
            playlistShow(seq){
                this.seq=seq
                // console.log(seq)
                this.popup=true
            },
            closePopup(){
                this.popup = false
            },
            inputChange(){
                if(this.input){
                    this.name = new Date()
                }
                this.input=!this.input
            },
            savePlayList(){
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
                    this.input = false;
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
            delPlaylist(seq){
                if(confirm("정말 삭제하겠습니까? 삭제한 플레이리스트는 되돌릴 수 없습니다.")){
                    console.log(seq)
                    axios.delete(
                        `/playlist/`+seq, {
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
                }
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
    .area {height:300px;}
    .myPage{
        padding-bottom: 102px;
    }
</style>
