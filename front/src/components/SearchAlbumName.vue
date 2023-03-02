<template>
    <b-container>
        <div class="search">
        <h4 align="left" class="pb-4 mb-4 fst-italic border-bottom">앨범 명으로 검색</h4>
        <div class="albums">
            <tr v-for="data in data" :key="data.seq">
                <router-link :to="{name:'albumDetail', params:{seq:data.seq}}" style="font-size:15px">
                <div class="card mb-3" style="max-width: 423px;">
                    <div class="row g-0">
                        <div class="col-md-4">
                            <img :src="`http://localhost:8250/image/album/${data.uri}`"
                                style="max-width: 100%; height: auto;" align="right" class="rounded float-start">
                        </div>
                        <div class="col-md-8">
                            <div class="card-body">
                                <h6 class="card-title"><span v-html="keywordTag(data.name)"></span></h6>
                            </div>
                        </div>
                    </div>
                </div>
            </router-link>
            </tr>
        </div>
        <br>
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
    </b-container>
</template>
<script>
    import axios from 'axios'
    export default {
        name: 'searchAlbumName',
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
                axios.get("http://localhost:8250/search/albumName?keyword=" + this.childKeyword+"&page="+this.currentPage)
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
.search{
    padding-bottom: 102px;
}
</style>