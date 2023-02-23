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
                    <p v-if="data.fan!=null" align="left">팬 수 : {{data.fan}}</p>
                </div>
                <div class="col-1">
                    <hr class="rowhr">
                </div>
                <div class="col-3">
                    <h5 v-if="data.type=='그룹'">그룹 멤버</h5>
                    <h5 v-if="data.type=='솔로'">소속 그룹</h5>
                    <tr v-for="artist in data.artists" :key="artist.seq">
                        <div align="left">
                            <router-link :to="{name:'artistChannel', params:{seq:artist.seq}}" @click="changeArtist(artist.seq)">{{ artist.name }}</router-link>
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
            <router-view />
        </div>
    </b-container>
</template>
<script>
    import axios from 'axios'
    export default {
        name: 'artistChannel',
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
                axios.get("http://localhost:8250/artist/channel/" + seq)
                    .then((e) => {
                        this.data = e.data.data
                    })
            },
            explan() {    
                return  this.data.explan
            },
            changeArtist(changeSeq){
                this.seq = changeSeq;
                this.loadPage(changeSeq)
            },
            seqChange(changeSeq){
                this.seq = changeSeq
                this.loadPage(changeSeq)
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