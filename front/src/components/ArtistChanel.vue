<template>
    <b-container>
        <div>
            <!-- <Child v-on:parentChange="seqChange"/> -->
        </div>
        <br>
        <h2 class="pb-4 mb-4 fst-italic border-bottom">아티스트 정보</h2>
        <!-- <Header :seq = "seq" /> -->
        <br>
        <div v-if="data!=null">
            <div class="row">
                <div class="col-3">
                    <img :src="`http://localhost:8250/image/artist/${data.uri}`" style="max-width: 100%; height: auto;" align="right" 
                        class="rounded float-start" id="artistimg">
                </div>
                <div class="col-5">
                    <h2 align="left">{{ data.name }}</h2>
                    <br>
                    <p v-if="data.debut!=null" align="left">데뷔 : {{ data.debut }}</p>
                    <p v-if="data.type!=null" align="left">활동 유형 : {{ data.type }}</p>
                    <p v-if="data.agency!=null" align="left">소속사 : {{ data.agency }}</p>
                    <div class="row">
                    <div class="col-4">
                        <div v-if="data.isFan">
                            <button type="button" class="btn btn-light" style="--bs-btn-padding-y: .25rem; --bs-btn-padding-x: .5rem; --bs-btn-font-size: .75rem;" @click="fan">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-star-fill" viewBox="0 0 16 16">
                                    <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
                                </svg> 팬
                            </button>
                        </div>
                        <div v-else>
                            <button type="button" class="btn btn-success" style="--bs-btn-padding-y: .25rem; --bs-btn-padding-x: .5rem; --bs-btn-font-size: .75rem;" @click="fan">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-star" viewBox="0 0 16 16">
                                <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"/>
                            </svg>팬맺기
                            </button>
                        </div>
                    </div>
                    <div class="col-5">
                        <p v-if="data.fan!=null" align="left">
                            팬 수 : {{data.fan}}</p>
                    </div>
                </div>
                </div>
                <div class="col-1">
                    <hr class="rowhr">
                </div>
                <div class="col-3">
                    <h5 v-if="data.type=='그룹'">그룹 멤버</h5>
                    <h5 v-if="data.type=='솔로'">소속 그룹</h5>
                    <tr v-for="artist in data.artists" :key="artist.seq">
                        <div align="left">
                            <router-link :to="{name:'artistDetail', params:{seq:artist.seq}}" @click="changeSeq(artist.seq)">{{ artist.name }}</router-link>
                        </div>
                    </tr>
                    <br>

                </div>
            </div>
            <hr>
            <div>
                <nav class="router_area">
                    <router-link :to="`/artist/channel${this.seq}/detail`">상세정보 </router-link>
                    <router-link :to="`/artist/channel${this.seq}/songList`">곡정보</router-link>
                    <router-link :to="`/artist/channel${this.seq}/albumList`">앨범정보</router-link>
                </nav>
            </div>
            <router-view @changeSeq="changeSeq"/>
        </div>
    </b-container>
</template>
<script>
    import axios from 'axios'
    import Cookies from 'js-cookie'
    export default {
        name: 'artistChannel',
        props: ['artistSeq'],
        data() {
            return {
                seq:null,
                data: null,
                albumExplan: "",
                isLogin:null
            }
        },
        created() {
            if (Cookies.get('accessToken') != null) {
                this.isLogin = true
            } else {
                this.isLogin = false
            }
            this.seq = this.$route.params.seq;
            this.loadPage(this.seq)
            console.log(this.artistSeq)
        },
        watch:{
            artistSeq(newSeq){
                console.log(newSeq)
                this.loadPage(newSeq)

            }
        },
        methods: {
            loadPage(seq) {
                axios.get("/artist/channel/" + seq+"/"+(this.isLogin?"login":"unLogin"),{
                    headers: {
                        Authorization: `Bearer `+Cookies.get('accessToken')
                    }
                })
                .then((e) => {
                    this.data = e.data.data
                    console.log(this.data.isFan)
                })
                .catch((error)=>{
                    console.log(error)
                    if(error.response.status==403){
                        const member = Cookies.get('member')
                        const refresh = Cookies.get('refreshToken')
                        axios.post("/member/refresh", {
                            id:member,
                            refresh:refresh
                        })
                        .then((e)=>{
                            Cookies.set('accessToken', e.data.token)
                            // this.loadPage(this.seq)
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
            explan() {    
                return  this.data.explan
            },
            changeArtist(changeSeq){
                this.seq = changeSeq;
                this.loadPage(changeSeq)
            },
            changeSeq(changeSeq){
                this.seq = changeSeq
                this.loadPage(this.seq)
            },
            fan(){
                if(this.data.isFan){
                    if(confirm("정말 팬맺기를 취소하시겠습니까?")){
                        this.artistFan()
                    }
                }else{
                    this.artistFan()
                }
            },
            artistFan(){
                if(!this.isLogin){
                    alert("로그인 후 이용가능한 기능입니다.")
                    this.$router.push("/login")
                }else{
                    axios.post("/fan/" + this.seq,{},{
                        headers: {
                            Authorization: `Bearer `+Cookies.get('accessToken')
                        }
                    })
                    .then((e) => {
                        alert(e.data.message)
                        this.loadPage(this.seq)
                    })
                    .catch((error)=>{
                        console.log(error)
                        if(error.response.status==403){
                            const member = Cookies.get('member')
                            const refresh = Cookies.get('refreshToken')
                            axios.post("/member/refresh", {
                                id:member,
                                refresh:refresh
                            })
                            .then((e)=>{
                                Cookies.set('accessToken', e.data.token)
                                this.fan()
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
            changeChanel(seq){
                this.loadPage(seq)
            }
        }
    }
</script>

<style>

.router_area{
    /* tac+p10+bb1 */
    text-align: center;
    padding: 10px;
    border-bottom: 1px;
}
.router_area a {
  display: inline-block; margin :5px;
  text-decoration: none; color:#999; padding: 10px;
  border-radius:3px
}
/* 라우터링크에 마우스 올렸을때 스타일 */
.router_area a:hover{
  background: #999; color: #fff;
}
/* 현태 표시되고있는 페이지의 링크스타일 */
.router_area a.router-link-active{
  background-color: #000; color:#fff
}
hr.rowhr{
    /* display: block; */
    width:1px;
    background-color:#000;
    /* position:absolute; */
    top:0;
    bottom:0;
    /* left:150px; */

}
</style>
