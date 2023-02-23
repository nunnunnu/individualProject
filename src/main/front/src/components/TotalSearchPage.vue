<template>
    <h4 class="pb-4 mb-4 fst-italic border-bottom">곡 명으로 검색</h4>
</template>
<script>
import axios from 'axios'
    export default {
        name: 'totalSearch',
        data(){
            return{
                keyword:null
            }
        },
        create(){
            this.keyword = this.$route.params.keyword;
            console.log(this.keyword)
        },
        methods: {
            loadPage() {
                axios.get("http://localhost:8250/search/album?keyword="+this.keyword)
                    .then((e) => {
                        this.data = e.data.data.content
                        this.totalPage=e.data.data.totalPages
                        this.size=e.data.data.size                        
                    })
            }
        }
    }
</script>
<style>
</style>