<template>
    <b-container>
    <div v-if="detail!=null">
        <div v-if="detail.introduce!=null">
            <h5 class="pb-4 mb-4 fst-italic border-bottom" align="left">아티스트 소개</h5>
            <p align="left">{{ detail.introduce }}</p>
        </div>
        <br>
        <h5 class="pb-4 mb-4 fst-italic border-bottom" align="left">활동 정보</h5>
        <div v-if="detail.debut!=null" class="row">
            <div class="col-1" align="left">
                <p>데뷔</p>
            </div>
            <div class="col" align="left">
                <p>{{ detail.debut }}</p>
            </div>
        </div>
        <div v-if="detail.type!=null" class="row">
            <div class="col-1" align="left">
                <p>유형</p>
            </div>
            <div class="col" align="left">
                <p>{{ detail.type }}</p>
            </div>
        </div>
        <div v-if="detail.agency!=null" class="row">
            <div class="col-1" align="left">
                <p>소속사</p>
            </div>
            <div class="col" align="left">
                <p>{{ detail.agency }}</p>
            </div>
        </div>
        <br>
        <div v-if="detail.type=='솔로'">
        <h5 class="pb-4 mb-4 fst-italic border-bottom" align="left">신상 정보</h5>
        <div v-if="detail.realname!=null" class="row">
            <div class="col-1" align="left">
                <p>본명</p>
            </div>
            <div class="col" align="left">
                <p>{{ detail.realname }}</p>
            </div>
        </div>
        <div v-if="detail.country!=null" class="row">
            <div class="col-1" align="left">
                <p>국적</p>
            </div>
            <div class="col" align="left">
                <p>{{ detail.country }}</p>
            </div>
        </div>
        <div v-if="detail.birth!=null" class="row">
            <div class="col-1" align="left">
                <p>생일</p>
            </div>
            <div class="col" align="left">
                <p>{{ detail.birth }}</p>
            </div>
        </div>
        <div v-if="detail.constellation!=null" class="row">
            <div class="col-1" align="left">
                <p>별자리</p>
            </div>
            <div class="col" align="left">
                <p>{{ detail.constellation }}</p>
            </div>
        </div>
        <br>
        <h5 class="pb-4 mb-4 fst-italic border-bottom" align="left">소속 그룹</h5>
        </div>
        <div v-if="detail.type=='그룹'">
            <h5 class="pb-4 mb-4 fst-italic border-bottom" align="left">그룹 멤버</h5>
        </div>
        <div class="artists">
        <tr v-for="art in detail.artists" :key="art.seq">
            <!-- <div class="col"> -->
            <div class="card">
                <img :src="`http://localhost:8250/image/artist/${art.uri}`" class="card-img-top">
                <div class="card-body">
                    <p class="card-title">{{art.name}}</p>
                    <a :href="`http://localhost:8080/artist/channel${art.seq}`">상세보기</a>
                </div>
            </div>
        </tr>
        </div>
        <br>
    </div>
    </b-container>
</template>
<script>
import axios from 'axios'
    export default {
        name: 'artistDetail',
        data() {
            return {
                childSeq: null,
                detail: null,
            }
        },
        props : ['seq'],
        created() {
            // this.seq = this.$route.params.seq;
            this.childSeq = this.seq;
            this.loadPage(this.childSeq)
        },
        // watch: {
        //     '$route' (to, from) {
        //     console.log(to)
        //     }
        // },
        methods: {
            loadPage(seq) {
                axios.get("http://localhost:8250/artist/detail/" + seq)
                    .then((e) => {
                        this.detail = e.data.data
                    })
            },
            explan() {    
                return  this.data.explan
            },
            changeArtist(changeSeq){
                // this.childSeq = changeSeq;
                // this.loadPage(changeSeq)
                // this.$router.go(-1)
                // this.$router.push('/artist/channel' + changeSeq);
                // Object.assign(changeSeq)
                // this.$emit('parentChange', changeSeq)
                console.log(changeSeq)
                
            }
        }
    }

</script>
<style>
.artists{
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    object-fit: cover;

}
.card-img-top{
    height:150px;
    overflow: hidden;
}
</style>