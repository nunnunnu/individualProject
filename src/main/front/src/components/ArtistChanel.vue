<template>
    <b-container>
        <br>
        <h2 class="pb-4 mb-4 fst-italic border-bottom">아티스트 정보</h2>
        <Header :seq = "seq" />
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
                    <p align="left">{{ data.debut }}</p>
                    <p align="left">활동 유형 : {{ data.type }}</p>
                    <p align="left">소속사 : {{ data.agency }}</p>
                    <p align="left">팬 수 : {{data.fan}}</p>
                </div>
                <div class="col-1">
                    <div class='v-line' align="right"></div>
                </div>
                <div class="col-3">
                    <h5 v-if="data.type=='그룹'">그룹 멤버</h5>
                    <h5 v-if="data.type=='솔로'">소속 그룹</h5>
                    <tr v-for="artist in data.artists" :key="artist.seq">
                        <div align="left">
                            <router-link :to="{name:'artistChanel', params:{seq:artist.seq}}">{{ artist.name }}</router-link>
                        </div>
                    </tr>
                    <br>

                </div>
            </div>
            <hr>
            <div>
                <nav class="router_area">
                    <!-- <router-link to="/main">메인화면</router-link> a태그임 -->
                    <router-link :to="`/artist/chanel${this.seq}/detail`">상세정보 </router-link>
                    <!-- <router-link :to="`/artist/chanel${this.seq}/detail`">곡 </router-link> -->
                    <!-- <router-link :to="`/artist/chanel${this.seq}/detail`">앨범 </router-link> -->
                </nav>
            </div>
            <router-view/>
        </div>
    </b-container>
</template>
<script>
    import axios from 'axios'
    export default {
        name: 'artistChanel',
        props: {},
        data() {
            return {
                seq:null,
                data: null,
                albumExplan: ""
            }
        },
        created() {
            this.seq = this.$route.params.seq;
            this.loadPage(this.seq)
        },
        methods: {
            loadPage(seq) {
                axios.get("http://localhost:8250/artist/chanel/" + seq)
                    .then((e) => {
                        this.data = e.data.data
                    })
            },
            explan() {    
                return  this.data.explan
            }
        }
    }
</script>

<style>
.v-line {
  border-left : solid;
  height : 280px;
}
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
</style>