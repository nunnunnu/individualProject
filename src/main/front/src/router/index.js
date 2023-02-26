import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import AlbumDetail from '../components/AlbumDetail.vue'
import ArtistChannel from '../components/ArtistChanel.vue'
import ArtistDetail from '../components/ArtistDetail.vue'
import SongDetail from '../components/SongDetail.vue'
import Movie from '../components/MoviePage.vue'
import ArtistSongList from '../components/ArtistSongList.vue'
import ArtistAlbumList from '../components/ArtistAlbumList.vue'
import NewAlbum from '../components/NewAlbumPage.vue'
import NewSong from '../components/NewMusicPage.vue'
import TotalSearch from '../components/TotalSearchPage.vue'
import searchPage from '../views/SearchPage.vue'
import SongNameSearch from '../components/SongNameSearch.vue'
import ArtistNameSearch from '../components/SearchArtistName.vue'
import SongLyricsSearch from '../components/SearchSongLyrics.vue'
import AlbumNameSearch from '../components/SearchAlbumName.vue'
import LoginPage from '../views/Login.vue'
import JoinMember from '../components/MemberJoinPage.vue'

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
    path: '/song/movie:tag',
    name: 'movie',
    component: Movie,
    props:true
  },
  {
    path: '/album/new',
    name: 'newAlbum',
    component: NewAlbum,
  },
  {
    path: '/song/new',
    name: 'newSong',
    component: NewSong,
  },
  {
    path: '/artist/channel:seq',
    name: 'artistChannel',
    component: ArtistChannel,
    props:true,
    children:[
      { 
        path: 'detail',
        name: 'artistDetail',
        component: ArtistDetail,
        props:true
      },
      { 
        path: 'albumList',
        name: 'artistAblumList',
        component: ArtistAlbumList,
        props:true
      },
      { 
        path: 'songList',
        name: 'artistSongList',
        component: ArtistSongList,
        props:true
      }
    ]
  },
  {
    path: '/search:key',
    name: 'searchPage',
    component: searchPage,
    props:true,
    children:[
      {
        path: '/search/total:key',
        name: 'totalSearch',
        component: TotalSearch,
        props:true
      },
      {
        path: '/search/songname:key',
        name: 'songNameSearch',
        component: SongNameSearch,
        props:true
      },
      {
        path: '/search/artistname:key',
        name: 'searchArtistName',
        component: ArtistNameSearch,
        props:true
      },
      {
        path: '/search/songlyrics:key',
        name: 'searchSongLyrics',
        component: SongLyricsSearch,
        props:true
      },
      {
        path: '/search/albumName:key',
        name: 'searchAlbumName',
        component: AlbumNameSearch,
        props:true
      }
    ]
  },
  {
    path: '/login',
    name: 'loginPage',
    component: LoginPage,
  },
  {
    path: '/join',
    name: 'joinPage',
    component: JoinMember,
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
