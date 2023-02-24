<template>
    <b-container>
            <div>
                <nav class="router_area">
                  <router-link :to="{name:'totalSearch', params:{key:this.keyword}}">통합검색</router-link>
                  <router-link :to="{name:'songNameSearch', params:{key:this.keyword}}">곡제목</router-link>
                    <router-link :to="{name:'searchArtistName', params:{key:this.keyword}}">아티스트</router-link>
                    <!-- <router-link :to="`/artist/channel${this.seq}/albumList`">앨범</router-link>
                    <router-link :to="`/artist/channel${this.seq}/albumList`">가사</router-link>  -->
                </nav>
            </div>
            <router-view />
    </b-container>
</template>
<script>
    import axios from 'axios'
    export default {
        name: 'searchPage',
        data(){
          return{
            keyword:null
          }
        },
        created(){
          this.keyword = this.$route.params.key;
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

</style>