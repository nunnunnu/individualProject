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
              <li class="nav-item">
                <a class="nav-link" href="#">일간차트</a>
              </li>
              <li class="nav-item">
                <router-link to="/song/new">최신 음악</router-link>
              </li>
            </ul>
            <!-- <form class="d-flex" role="search"> -->
              <input class="form-control me-2" type="search" placeholder="Search" name="key">
              <button class="btn btn-outline-success" type="submit" @click="searchClick(key)">Search</button>
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
      }
    },
    methods:{
      searchClick(key){
        console.log(key)
        // this.keywordSearch = keyword
        // this.$router.push("/search?"+this.keywordSearch);
        // this.$router.push({
        //   name: "searchPage",
        //   params: { key: keyword },
        // });
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
    color: #2c3e50;
  }

  nav a.router-link-exact-active {
    color: #87fff5;
  }
</style>