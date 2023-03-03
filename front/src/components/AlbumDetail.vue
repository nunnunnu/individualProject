<template>
    <b-container>
        <div class="albumDetail">
            <br>
            <h2 class="pb-4 mb-4 fst-italic border-bottom">앨범 정보</h2>
            <br>
            <div v-if="data!=null">
                <div class="row">
                    <div class="col-3">
                        <img :src="`http://localhost:8250/image/album/${data.uri}`"
                            style="max-width: 100%; height: auto;" align="right" class="rounded float-start">
                    </div>
                    <div class="col-5">
                        <p align="left" class="typeInfo">[{{ data.type }}]</p>
                        <p align="left">{{ data.name }}</p>
                        <div align="left">
                            <router-link :to="{name:'artistDetail', params:{seq:data.artist.seq}}"
                                style="font-size: 15px">가수명 : {{ data.artist.name }}</router-link>
                        </div>
                        <p align="left"></p>
                        <p align="left">장르 : {{ data.genreList }}</p>
                        <p align="left">소속사 : {{ data.agencyName }}</p>
                        <p align="left">발매사 : {{ data.pubName }}</p>
                        <p align="left">발매일 : {{ data.regDt }}</p>
                    </div>
                    <div class="col-3">
                        <p align="left">댓글 수 : {{ data.comment }}</p>
                        <div v-if="!isLogin">
                            <div align="left">
                                <p type="button" class="btn btn-success" @click="isLoggedIn()">
                                    평점 : {{ data.grade }} </p>
                            </div>
                        </div>
                        <div v-if="isLogin">
                            <div align="left">
                                <p type="button" class="btn btn-success" data-bs-toggle="modal"
                                    data-bs-target="#exampleModal">
                                    평점 : {{ data.grade }} </p>
                            </div>
                            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                                aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h1 class="modal-title fs-5" id="exampleModalLabel">앨범 평점</h1>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <div class="stars">
                                                <img v-for="star in stars" :key="star" :src="getStarImage(star)"
                                                    @click="setRating(star)" width="50" height="50" />
                                            </div>
                                            <p>별점: {{ rating }}</p>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary"
                                                data-bs-dismiss="modal">취소</button>
                                            <button type="button" class="btn btn-success" data-bs-dismiss="modal" @click="gradeAdd(data.seq)">등록</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <br>
                <hr>
                <br>
                <h5 class="pb-4 mb-4 fst-italic border-bottom">수록곡({{ data.songCount }})</h5>
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">번호</th>
                            <th scope="col">곡정보</th>
                            <th scope="col">좋아요</th>
                            <th scope="col">듣기</th>
                            <th scope="col">담기</th>
                            <th scope="col">다운</th>
                            <th scope="col">뮤비</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="item in data.song" :key="item.seq">
                            <th scope="row">{{ item.order }}</th>
                            <td align="left">
                                <span v-if="item.title" class="badge rounded-pill text-bg-success">title</span>
                                <router-link :to="{name:'songDetail', params:{seq:item.seq}}">{{ item.name }}
                                </router-link>
                                <br>
                        <tr v-for="artist in item.artists" :key="artist.seq">
                            <!-- <p style="font-size:12px">{{ artist.name }} </p> -->
                            <router-link :to="{name:'artistDetail', params:{seq:artist.seq}}" style="font-size:12px">
                                {{ artist.name }}</router-link>
                        </tr>
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
                            <svg data-bs-toggle="modal" data-bs-target="#staticBackdrop" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                class="bi bi-plus-square" viewBox="0 0 16 16">
                                <path
                                    d="M14 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h12zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2z" />
                                <path
                                    d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z" />
                            </svg>
                            <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                    <div class="modal-header">
                                        <h1 class="modal-title fs-5" id="staticBackdropLabel">재생목록</h1>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <button type="button" @click="nowPlayingAdd(item)" class="btn btn-success">재생목록 추가</button>
                                        <br>
                                        <br>
                                        <button type="button" @click="playListAdd(item)" class="btn btn-success">플레이리스트 추가</button>
                                    </div>
                                    </div>
                                </div>
                            </div>
                        </td>
                        <td>
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                class="bi bi-download" viewBox="0 0 16 16">
                                <path
                                    d="M.5 9.9a.5.5 0 0 1 .5.5v2.5a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1v-2.5a.5.5 0 0 1 1 0v2.5a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2v-2.5a.5.5 0 0 1 .5-.5z" />
                                <path
                                    d="M7.646 11.854a.5.5 0 0 0 .708 0l3-3a.5.5 0 0 0-.708-.708L8.5 10.293V1.5a.5.5 0 0 0-1 0v8.793L5.354 8.146a.5.5 0 1 0-.708.708l3 3z" />
                            </svg>
                        </td>
                        <td>
                            <div v-if="item.movie!=null">
                                <router-link :to="{name:'movie', params:{tag:item.movie}}">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                        class="bi bi-tv-fill" viewBox="0 0 16 16">
                                        <path
                                            d="M2.5 13.5A.5.5 0 0 1 3 13h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zM2 2h12s2 0 2 2v6s0 2-2 2H2s-2 0-2-2V4s0-2 2-2z" />
                                    </svg>
                                </router-link>
                            </div>
                            <div v-else-if="item.movie==null">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                    class="bi bi-tv" viewBox="0 0 16 16">
                                    <path
                                        d="M2.5 13.5A.5.5 0 0 1 3 13h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zM13.991 3l.024.001a1.46 1.46 0 0 1 .538.143.757.757 0 0 1 .302.254c.067.1.145.277.145.602v5.991l-.001.024a1.464 1.464 0 0 1-.143.538.758.758 0 0 1-.254.302c-.1.067-.277.145-.602.145H2.009l-.024-.001a1.464 1.464 0 0 1-.538-.143.758.758 0 0 1-.302-.254C1.078 10.502 1 10.325 1 10V4.009l.001-.024a1.46 1.46 0 0 1 .143-.538.758.758 0 0 1 .254-.302C1.498 3.078 1.675 3 2 3h11.991zM14 2H2C0 2 0 4 0 4v6c0 2 2 2 2 2h12c2 0 2-2 2-2V4c0-2-2-2-2-2z" />
                                </svg>
                            </div>
                        </td>
                        </tr>
                    </tbody>
                </table>
                <br>
                <h5 class="pb-4 mb-4 fst-italic border-bottom">앨범소개</h5>
                <p>
                <a class="btn btn-success" data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
                    펼치기
                </a>
                </p>
                <div class="collapse" id="collapseExample">
                <div class="card card-body">
                    <div v-html="albumExplan"></div>
                </div>
                </div>
                <br>
                <hr>
                <br>
                <h5 class="pb-4 mb-4 fst-italic border-bottom">댓글</h5>
                <br>
                <div class="row">
                    <div class="col-11">
                        <div class="input-group">
                            <span class="input-group-text">댓글</span>
                            <textarea class="form-control" aria-label="With textarea"></textarea>
                        </div>
                    </div>
                    <div class="col-1">
                        <button class="btn btn-success btn-lg">입력</button>
                    </div>
                </div>
                <br>
                <p align="left">총 {{ data.comment }}개</p>
                <hr>
            </div>
        </div>
    </b-container>
</template>
<script>
    import axios from 'axios'
    import Cookies from 'js-cookie'
    // import playList from '@/components/PlayList.vue'
    export default {
        name: 'albumDetail',
        // components: {
        //     playList
        // },
        props: {},
        data() {
            return {
                data: null,
                albumExplan: "",
                rating: 0,
                stars: [1, 2, 3, 4, 5],
                isLogin: null
            }
        },
        created() {
            this.seq = this.$route.params.seq;
            if (Cookies.get('accessToken') != null) {
                this.isLogin = true
            } else {
                this.isLogin = false
            }
            this.loadPage(this.seq)
        },
        methods: {
            loadPage(seq) {
                axios.get("http://localhost:8250/album/detail/" + seq, {
                    headers: {
                        Authorization: `Bearer `+Cookies.get('accessToken')
                    }
                })
                    .then((e) => {
                        this.data = e.data.data
                        this.albumExplan = e.data.data.explan
                    })
            },
            explan() {
                return this.data.explan
            },
            setRating(star) {
                if (this.rating === star) {
                    // 이미 선택된 별을 다시 클릭한 경우, 별점을 0으로 초기화합니다.
                    this.rating = 0;
                } else {
                    this.rating = star;
                }
            },
            getStarImage(star) {
                if (star <= this.rating) {
                    // 선택된 별 이하의 모든 별은 색이 채워진 이미지를 사용합니다.
                    return "https://www.citypng.com/public/uploads/small/11659043903eyvtsezy4ggvxqpml1vm20llfrhutwvziycxyopxjeexufvl7arkm3fduk80kwyi2vuxie3ootphq1kvtyqibnzb58i3gylfrbz5.png"
                } else {
                    // 선택되지 않은 별은 빈 이미지를 사용합니다.
                    return "https://icones.pro/wp-content/uploads/2021/02/icone-etoile-vide-verte.png"
                }
            },
            gradeAdd(seq) {
                if(this.rating==0){
                    alert("평점이 입력되지않았습니다. 평점은 1점부터 등록할 수 있습니다.")
                }else{
                    const token = Cookies.get('accessToken');
                    axios.put("http://localhost:8250/grade/"+seq+"?grade="+this.rating, {} , {
                        headers: {
                            Authorization: `Bearer `+token
                        }
                    })
                    .then((e) => {
                        if(e.data.status){
                            alert("등록되었습니다.")
                            this.rating=0;
                        }else{
                            alert(e.data.message)
                        }
                    })
                }
            },
            isLoggedIn() {
                if (!this.isLogin) {
                    alert("로그인 후 이용가능한 서비스입니다.")
                    this.$router.push("/login")
                }
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
            
            },
            playListAdd(item){
                
            },
            nowPlayingAdd(item){
                let songlist = JSON.parse(sessionStorage.getItem('playlist') ?? '[]')
                songlist.push(item)
                sessionStorage.setItem('playlist',JSON.stringify(songlist))
                this.$root.songs =  JSON.parse(sessionStorage.getItem('playlist'))
                
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
        font-size: 20px
    }

    .albumDetail {
        padding-bottom: 102px;
    }
</style>