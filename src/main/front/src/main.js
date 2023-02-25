import { createApp } from 'vue'
import App from './App.vue'
import BootstrapVue3 from 'bootstrap-vue-3'
import axios from 'axios'
// import Spotify from 'spotify-web-api-node'
// import VueSpotify from 'vue-spotify'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue-3/dist/bootstrap-vue-3.css'
import router from './router'

// Vue.js 전역 객체에 대한 에러 해결을 위해
// window.Spotify 전역 객체 생성
// window.Spotify = Spotify;

axios.defaults.baseURL="http://localhost:8250"

const app = createApp(App).use(router)
app.use(BootstrapVue3)
app.config.globalProperties.$http = axios 
app.mount('#app')
// app.use(VueSpotify, new Spotify({
//     clientId: 'a44e6aaa247c427f925c1a44d26b0359',
//     clientSecret: 'd42434663ee841a580ad0e943421cf93',
//   }))
// spotifyApi.setAccessToken('a44e6aaa247c427f925c1a44d26b0359');