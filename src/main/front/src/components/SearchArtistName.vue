<template>
    <b-container>
        <h4 align="left" class="pb-4 mb-4 fst-italic border-bottom">곡 명으로 검색</h4>
        <div class="artists">
            <tr v-for="data in data" :key="data.seq">
                <div class="card">
                    <img :src="`http://localhost:8250/image/artist/${data.uri}`"
                                style="max-width: 100%; height: 240px;" align="right" class="rounded float-start">
                    <div class="card-body">
                    <router-link :to="{name:'artistChannel', params:{seq:data.seq}}"
                                    style="font-size:15px"><span v-html="keywordTag(data.name)"></span></router-link>
                    <p class="card-text"><small class="text-muted">{{ data.debut }}</small></p>
                    <p class="card-text"><small class="text-muted">{{ data.agency }}</small></p>
                    <p class="card-text"><small class="text-muted">[{{ data.type }}]</small></p>
                    <router-link :to="{name:'artistChannel', params:{seq:data.seq}}" style="font-size:15px">
                                    상세보기</router-link>
                    </div>
                </div>
            </tr>
        </div>
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
    </b-container>
</template>
<script>
    import axios from 'axios'
    export default {
        name: 'searchArtistName',
        // props : ['key'],
        data() {
            return {
                data:null,
                childKeyword: null,
                currentPage: 0,
                totalPage: 0,
                size: 0
            }
        },
        created() {
            // this.childKeyword = this.keyword
            this.childKeyword = this.$route.params.key;
            this.loadPage()
        },
        methods: {
            loadPage() {
                axios.get("http://localhost:8250/search/artistName?keyword=" + this.childKeyword+"&page="+this.currentPage)
                    .then((e) => {
                        this.data = e.data.data.content
                        console.log(this.data)
                        this.totalPage=e.data.data.totalPages
                        this.size=e.data.data.size        
                    })
            },
            pageClick(page){
                this.currentPage=page
                this.loadPage()
            },
            prePage(){
                this.currentPage = this.currentPage-1
                this.loadPage()
            },
            keywordTag(str){
                return str.replace(new RegExp(this.childKeyword, 'gi'), match => `<span class="highlight">${match}</span>`)
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
</style>