<template>
  <div>
    <nav class="navbar navbar-expand-lg bg-light">
      <div class="container">
        <div class="container-fluid">
          <!-- <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo03"
            aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button> -->
          <router-link to="/">Water Melon</router-link>
          <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
              <li class="nav-item">
                <router-link to="/album/new">최신 앨범</router-link>
              </li>
              <!-- <li class="nav-item"> -->
                <!-- <a class="nav-link" href="#">일간차트</a> -->
              <!-- </li> -->
              <li class="nav-item">
                <router-link to="/song/new">최신 음악</router-link>
              </li>
              <li class="nav-item">
                <!-- <router-link :to="{name:'loginPage', params:{url:this.$router}}">로그인</router-link> -->
              </li>
            </ul>
            <!-- <form class="d-flex" role="search"> -->
              <div class="row">
                <div class="col-10">
                  <input class="form-control me-2" type="search" placeholder="Search" v-model="key">
                </div>
                <div class="col-2">
                  <button class="btn btn-outline-success" type="submit" @click="searchClick(key)">Search</button>
                </div>
              </div>
            <!-- </form> -->
          </div>
        </div>
      </div>
    </nav>
    <router-view />
  </div>
</template>
<script>
   export default {
    // name: 'main',
    data() {
      return {
        keywordSearch: null,
        this:null
      }
    },
    methods:{
      searchClick(keyword){
        this.$router.push({
          name: "totalSearch",
          params: { key: keyword },
        });
      }
    }
   }
</script>
<style>
@import "@/assets/css/fonts.css";
@import "@/assets/css/reset.css";
  #app {
    text-align: center;
  }

  nav {
    padding: 30px;
  }

  nav a {
    font-weight: bold;
    color: #000000;
  }

  nav a.router-link-exact-active {
    color: #097d11;
  }
</style>