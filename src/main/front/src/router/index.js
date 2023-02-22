import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import AlbumDetail from '../components/AlbumDetail.vue'
import ArtistChanel from '../components/ArtistChanel.vue'
import ArtistDetail from '../components/ArtistDetail.vue'
import SongDetail from '../components/SongDetail.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/album/detail:seq',
    name: 'albumDetail',
    component: AlbumDetail,
    props:true
  },
  {
    path: '/song/detail:seq',
    name: 'songDetail',
    component: SongDetail,
    props:true
  },
  {
    path: '/artist/chanel:seq',
    name: 'artistChanel',
    component: ArtistChanel,
    props:true,
    children:[
      { 
        path: 'detail',
        name: 'artistDetail',
        component: ArtistDetail,
        props:true
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
