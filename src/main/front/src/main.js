import { createApp } from 'vue'
import App from './App.vue'
import BootstrapVue3 from 'bootstrap-vue-3'
import axios from 'axios'
// import Spotify from 'spotify-web-api-node'
// import VueSpotify from 'vue-spotify'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue-3/dist/bootstrap-vue-3.css'
import router from './router'


axios.defaults.baseURL="http://localhost:8250"
// axios.defaults.withCredentials = true

const app = createApp(App).use(router)
app.use(BootstrapVue3)
app.config.globalProperties.$http = axios 
app.mount('#app')