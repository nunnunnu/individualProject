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
                                <div style="display:flex; flex-direction: row;">
                                    <tr v-for="(artist, index) in item.artists" :key="artist.seq">
                                        <router-link :to="{name:'artistDetail', params:{seq:artist.seq}}" style="font-size:12px">
                                            {{ artist.name }}</router-link>
                                        <span v-if="index < item.artists.length - 1">, </span>
                                    </tr>
                                </div>
                        </td>
                        <td>
                            <div @click="likeUnlike(item.seq)" class="onclick">
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
                            <div v-if="item.files.length!=0" class="onclick">
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
                            <div v-if="item.files.length!=0" class="onclick">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-plus-circle-fill" viewBox="0 0 16 16">
  <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM8.5 4.5a.5.5 0 0 0-1 0v3h-3a.5.5 0 0 0 0 1h3v3a.5.5 0 0 0 1 0v-3h3a.5.5 0 0 0 0-1h-3v-3z" @click="openPopup(item)"/>
</svg>
                            </div>
                            <div v-else>
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-plus-circle-dotted" viewBox="0 0 16 16">
  <path d="M8 0c-.176 0-.35.006-.523.017l.064.998a7.117 7.117 0 0 1 .918 0l.064-.998A8.113 8.113 0 0 0 8 0zM6.44.152c-.346.069-.684.16-1.012.27l.321.948c.287-.098.582-.177.884-.237L6.44.153zm4.132.271a7.946 7.946 0 0 0-1.011-.27l-.194.98c.302.06.597.14.884.237l.321-.947zm1.873.925a8 8 0 0 0-.906-.524l-.443.896c.275.136.54.29.793.459l.556-.831zM4.46.824c-.314.155-.616.33-.905.524l.556.83a7.07 7.07 0 0 1 .793-.458L4.46.824zM2.725 1.985c-.262.23-.51.478-.74.74l.752.66c.202-.23.418-.446.648-.648l-.66-.752zm11.29.74a8.058 8.058 0 0 0-.74-.74l-.66.752c.23.202.447.418.648.648l.752-.66zm1.161 1.735a7.98 7.98 0 0 0-.524-.905l-.83.556c.169.253.322.518.458.793l.896-.443zM1.348 3.555c-.194.289-.37.591-.524.906l.896.443c.136-.275.29-.54.459-.793l-.831-.556zM.423 5.428a7.945 7.945 0 0 0-.27 1.011l.98.194c.06-.302.14-.597.237-.884l-.947-.321zM15.848 6.44a7.943 7.943 0 0 0-.27-1.012l-.948.321c.098.287.177.582.237.884l.98-.194zM.017 7.477a8.113 8.113 0 0 0 0 1.046l.998-.064a7.117 7.117 0 0 1 0-.918l-.998-.064zM16 8a8.1 8.1 0 0 0-.017-.523l-.998.064a7.11 7.11 0 0 1 0 .918l.998.064A8.1 8.1 0 0 0 16 8zM.152 9.56c.069.346.16.684.27 1.012l.948-.321a6.944 6.944 0 0 1-.237-.884l-.98.194zm15.425 1.012c.112-.328.202-.666.27-1.011l-.98-.194c-.06.302-.14.597-.237.884l.947.321zM.824 11.54a8 8 0 0 0 .524.905l.83-.556a6.999 6.999 0 0 1-.458-.793l-.896.443zm13.828.905c.194-.289.37-.591.524-.906l-.896-.443c-.136.275-.29.54-.459.793l.831.556zm-12.667.83c.23.262.478.51.74.74l.66-.752a7.047 7.047 0 0 1-.648-.648l-.752.66zm11.29.74c.262-.23.51-.478.74-.74l-.752-.66c-.201.23-.418.447-.648.648l.66.752zm-1.735 1.161c.314-.155.616-.33.905-.524l-.556-.83a7.07 7.07 0 0 1-.793.458l.443.896zm-7.985-.524c.289.194.591.37.906.524l.443-.896a6.998 6.998 0 0 1-.793-.459l-.556.831zm1.873.925c.328.112.666.202 1.011.27l.194-.98a6.953 6.953 0 0 1-.884-.237l-.321.947zm4.132.271a7.944 7.944 0 0 0 1.012-.27l-.321-.948a6.954 6.954 0 0 1-.884.237l.194.98zm-2.083.135a8.1 8.1 0 0 0 1.046 0l-.064-.998a7.11 7.11 0 0 1-.918 0l-.064.998zM8.5 4.5a.5.5 0 0 0-1 0v3h-3a.5.5 0 0 0 0 1h3v3a.5.5 0 0 0 1 0v-3h3a.5.5 0 0 0 0-1h-3v-3z"/>
</svg>
                            </div>
                            
                        </td>
                        <td>
                            <div v-if="item.files.length!=0" class="onclick">
                                <svg @click="openDown(item)" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-earmark-arrow-down-fill" viewBox="0 0 16 16">
                                    <path d="M9.293 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V4.707A1 1 0 0 0 13.707 4L10 .293A1 1 0 0 0 9.293 0zM9.5 3.5v-2l3 3h-2a1 1 0 0 1-1-1zm-1 4v3.793l1.146-1.147a.5.5 0 0 1 .708.708l-2 2a.5.5 0 0 1-.708 0l-2-2a.5.5 0 0 1 .708-.708L7.5 11.293V7.5a.5.5 0 0 1 1 0z"/>
                                </svg>
                            </div>
                            <div v-else>
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-earmark-arrow-down" viewBox="0 0 16 16">
  <path d="M8.5 6.5a.5.5 0 0 0-1 0v3.793L6.354 9.146a.5.5 0 1 0-.708.708l2 2a.5.5 0 0 0 .708 0l2-2a.5.5 0 0 0-.708-.708L8.5 10.293V6.5z"/>
  <path d="M14 14V4.5L9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2zM9.5 3A1.5 1.5 0 0 0 11 4.5h2V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.5v2z"/>
</svg>
                            </div>
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
                <div class="row" @click="loginCheck">
                    <div class="col-11">
                        <div class="input-group">
                            <span class="input-group-text">댓글</span>
                            <input class="form-control" aria-label="With textarea" v-model="commentDetail">
                            <div class="col-1">
                            </div>
                            <div class="col-2" v-if="isLogin">
                                <input class="form-control form-control-sm" id="formFileSm" type="file" @change="imageChange">
                                <br>
                            </div>
                            <div v-else>
                                <div class="mb-3">
                                    <input class="form-control" type="file" id="formFileDisabled" disabled>
                                </div>
                            </div>
                            <div class="col-1">
                                <div class="img" style="width:100px; height: 100px; border: 1px solid #999;" ref="imgbox">
                            </div>
                            </div>
                        </div>
                        <div class="mb-3">
                            <br>
</div>
                    </div>
                    <div class="col-1">
                        <button class="btn btn-success btn-lg" @click="saveComment">등록</button>
                    </div>
                </div>
                <br>
                <p align="left">총 {{ data.comment }}개</p>
                <hr>
                <div class="row">
                    <tr v-for="cmt in comment" :key="cmt.seq">
                        <div class="row" align="left">
                            <div class="col-auto">
                                <p>닉네임 : {{ cmt.name }}</p>
                            </div>
                            <div class="col-auto" v-if="cmt.uri!=null">
                                <img :src='`http://localhost:8250/image/comment/${cmt.uri}`'  width="80" height="80">
                            </div>
                            <div class="col-auto">
                                <p>{{ cmt.content }}</p>
                            </div>
                            <div class="col-auto">
                                <p>{{ cmt.create }}</p>
                            </div>
                        </div>
                            <details align="left" class="onclick">
                                <summary>답댓글({{ cmt.child.length }})</summary>
                                <br>
                                <div class="row">
                                    <tr v-for="rep in cmt.child" :key="rep.seq">
                                        <div class="row">
                                        <div class="col-1"></div>
                                    <div class="col-auto">
                                        <p>닉네임 : {{ rep.name }}</p>
                                    </div>
                                    <div class="col-auto">
                                        <p>{{ rep.content }}</p>
                                    </div>
                                    <div class="col-auto" align="right">
                                        <p>{{ rep.create }}</p>
                                    </div>
                                    <hr>
                                </div>
                                </tr>
                                <div class="row" @click="loginCheck">
                                    <div class="col-1"></div>
                                    <div v-if="isLogin" class="col">
                                        <input class="form-control form-control-sm" type="text" placeholder="답댓글 입력창" aria-label=".form-control-sm example" v-model="childComment">
                                    </div>
                                    <div class="col-2">
                                        <button type="button" class="btn btn-success"
                                                style="--bs-btn-padding-y: .25rem; --bs-btn-padding-x: .5rem; --bs-btn-font-size: .75rem;" @click="saveChildComment(cmt.seq)">
                                        등록
                                        </button>
                                    </div>
                                </div>
                            </div>
                            </details>
                        <hr>
                    </tr>
                </div>
            </div>
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

        </div>
        <playListPopup v-if="popup" @closePopup="closePopup" :song="seletedSong" @nowPlayingAdd="nowPlayingAdd"/>
        <downPopup v-if="downPopup" @closeDown="closeDown" :song="seletedSong" @nowPlayingAdd="nowPlayingAdd"/>
    </b-container>
</template>
<script>
    import axios from 'axios'
    import Cookies from 'js-cookie'
    import playListPopup from '@/components/PlayListAddPopup.vue'
    import downPopup from '@/components/SongDownPopup.vue'
    export default {
        name: 'albumDetail',
        components: {
            playListPopup, downPopup
        },
        props: {},
        data() {
            return {
                data: null,
                albumExplan: "",
                rating: 0,
                stars: [1, 2, 3, 4, 5],
                isLogin: null,
                popup:false,
                seletedSong:null,
                comment:null,
                imgData:null,
                commentDetail:"",
                childComment:"",
                totalPage:null,
                size:null,
                currentPage:0,
                downPopup:false,
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
            this.loadComment()
        },
        methods: {
            loadPage(seq) {
                axios.get("http://localhost:8250/album/detail/"+seq+"/"+(this.isLogin?"login":"unLogin"), {
                    headers: {
                        Authorization: `Bearer `+Cookies.get('accessToken')
                    }
                })
                .then((e) => {
                    this.data = e.data.data
                    this.albumExplan = e.data.data.explan
                    
                })
                .catch((error)=>{
                    if(error.response.status==403){
                        const member = Cookies.get('member')
                        const refresh = Cookies.get('refreshToken')
                        axios.post("http://localhost:8250/member/refresh", {
                            id:member,
                            refresh:refresh
                        })
                        .then((e)=>{
                            Cookies.set('accessToken', e.data.token)
                            this.loadPage(this.seq)
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
                    .catch((error)=>{
                        const member = Cookies.get('member')
                        const refresh = Cookies.get('refreshToken')
                        axios.post("http://localhost:8250/member/refresh", {
                            id:member,
                            refresh:refresh
                        })
                        .then((e)=>{
                            Cookies.set('accessToken', e.data.token)
                            this.gradeAdd(seq)
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
                    .catch((error)=>{
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
                    })
                }
            },
            playSong(item){
                if(!this.isLogin){
                    alert("로그인 후 이용가능합니다.")
                    this.$router.push("/login")
                }else{
                    axios.get("http://localhost:8250/ticket/check",{
                        headers: {
                            Authorization: 'Bearer ' + Cookies.get('accessToken')
                        }
                    })
                    .then((e)=>{
                        let songlist = JSON.parse(sessionStorage.getItem('playlist') ?? '[]')
                        songlist.push(item)
                        sessionStorage.setItem('playlist',JSON.stringify(songlist))
                        sessionStorage.setItem('nowIndex',songlist.length-1)
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
                }
            },
            playListAdd(item){
            },
            nowPlayingAdd(){
                if(!this.isLogin){
                    alert("로그인 후 이용가능합니다.")
                    this.$router.push("/login")
                }
                axios.get("http://localhost:8250/ticket/check",{
                    headers: {
                        Authorization: 'Bearer ' + Cookies.get('accessToken')
                    }
                })
                .then((e)=>{
                    this.$emit('setPlayList')
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
            openPopup(item){
                if(this.isLogin){
                    this.seletedSong = item
                    this.popup=true
                }else{
                    alert("로그인 후 이용가능한 기능입니다.")
                    this.$router.push("/login")
                }
            },
            closePopup(){
                if(this.isLogin){
                    this.popup=false
                }else{
                    alert("로그인 후 이용가능한 기능입니다.")
                    this.$router.push("/login")
                }
            },
            loginCheck(){
                if(!this.isLogin){
                    alert("로그인 후 이용가능한 서비스입니다.")
                    this.$router.push("/login")
                }
            },
            loadComment(){
                axios.get("http://localhost:8250/album/comment/"+this.seq+"?page="+this.currentPage
                )
                .then((e) => {
                    this.comment = e.data.data.content
                    this.albumExplan = e.data.data.explan
                    this.totalPage=e.data.data.totalPages
                    this.size=e.data.data.size     
                })
            },
            imageChange(event){
                const imgbox = this.$refs.imgbox //imgbox ref를 가진 div
                if(event.target.files && event.target.files[0]){ //파일있는지 검사
                    const reader = new FileReader(); //파일 읽어들이는 클래스
                    // reader.onload((e)=>{
                        //     imgbox.style.backgroundImage = e.target.result
                        // })
                        reader.addEventListener("load", function(e){
                            //이미지 로드 완료 시
                            imgbox.style.backgroundImage= `url('${e.target.result}')` //배경 이미지 설정
                            imgbox.style.backgroundSize= '100% auto' //크기설정
                            imgbox.style.backgroundPosition= 'center' //가운데로
                        })
                        this.imgData = event.target.files[0]
                    reader.readAsDataURL(this.imgData) //이미지 초기화
                }else{
                    imgbox.style.backgroundImage = ""
                }
                
            },
            saveComment(){
                const formdata = new FormData()
                formdata.append("album", this.data.seq)
                formdata.append("comment", this.commentDetail)
                formdata.append("file", this.imgData)
                console.log(this.imgData)

                axios.put("http://localhost:8250/comment", formdata , {
                        headers: {
                            Authorization: `Bearer `+Cookies.get('accessToken')
                        }
                    })
                    .then((e)=>{
                        this.loadComment()
                        this.commentDetail=""
                        this.imgData=null
                        const reader = new FileReader(); //파일 읽어들이는 클래스
                        // reader.readAsDataURL(this.imgData)
                    })
                    .catch((error)=>{
                        console.log(error)
                        if(error.response.status==403){
                            const member = Cookies.get('member')
                            const refresh = Cookies.get('refreshToken')
                            axios.post("http://localhost:8250/member/refresh", {
                            id:member,
                            refresh:refresh
                            })
                                .then((e)=>{
                                Cookies.set('accessToken', e.data.token)
                                this.saveComment()
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
                        }else{
                            alert(error.response.data.err)
                        }
                    })
            },
            saveChildComment(seq){
                const formdata = new FormData()
                formdata.append("album", this.data.seq)
                formdata.append("comment", this.childComment)
                formdata.append("parentSeq", seq)

                axios.put("http://localhost:8250/comment", formdata , {
                        headers: {
                            Authorization: `Bearer `+Cookies.get('accessToken')
                        }
                    })
                    .then((e)=>{
                        this.loadComment()
                        this.commentDetail=null
                        this.imgData=null
                        const reader = new FileReader(); //파일 읽어들이는 클래스
                        reader.readAsDataURL(this.imgData)
                    })
                    .catch((error)=>{
                        if(error.response.status==403){
                            const member = Cookies.get('member')
                            const refresh = Cookies.get('refreshToken')
                            axios.post("http://localhost:8250/member/refresh", {
                            id:member,
                            refresh:refresh
                            })
                                .then((e)=>{
                                Cookies.set('accessToken', e.data.token)
                                this.saveComment()
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
                        }else{
                            alert(error.response.data.err)
                        }
                    })
            },
            pageClick(page){
                this.currentPage=page
                this.loadComment()
            },
            prePage(){
                this.currentPage = this.currentPage-1
                this.loadComment()
            },
            openDown(item){
                if(!this.isLogin){
                    alert("로그인 후 이용가능한 서비스입니다.")
                    this.$router.push("/login")
                }else{
                    this.downPopup=true
                    this.seletedSong = item
                }
            },
            closeDown(){
                this.downPopup=false
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

