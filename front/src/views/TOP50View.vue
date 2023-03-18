<template>
    <b-container>
    <div class="chart">
        <h3 class="pb-4 mb-4 fst-italic border-bottom">
            대한민국 TOP50
        </h3>
        <p align="right">해당차트는 스포티파이 기준입니다.</p>
        <div v-if="tracks!=null">
            <table class="table table-sm">
                <thead>
                    <th></th>
                    <th>이미지</th>
                    <th>곡정보</th>
                    <th>가수</th>
                    <th>앨범</th>
                </thead>
                <tbody>
                    <tr v-for="(track, index) in tracks" :key="track.id">
                        <td>{{ index + 1 }}</td>
                        <td>
                            <img :src="track.track.album.images[0].url" width="80" height="80">
                        </td>
                        <td>{{ track.track.name }}</td>
                        <td>{{ track.track.artists[0].name }}</td>
                        <td>{{ track.track.album.name }}</td>
                        <td>{{ track.track.album.release_date }}</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div v-else>
            현재 차트를 표시할 수 없습니다. 관리자에게 문의해주세요.
        </div>
    </div>
    </b-container>
</template>
<script>
    import Cookies from 'js-cookie'
    import axios from 'axios'

    export default {
        name: 'musicTop',
        data() {
            return {
                isLogin: null,
                tracks: null
            }
        },
        mounted() {
            if (sessionStorage.getItem("spotifyAccess") == null) {
                this.getSpotifyToken()
            }
            this.loadTracks()
        },
        methods: {
            getSpotifyToken() {
                axios.get("http://localhost:8250/spotify")
                    .then((e) => {
                        console.log(e)
                        sessionStorage.setItem("spotifyAccess", e.data.spotifyAccess)
                        sessionStorage.setItem("spotifyRefresh", e.data.spotifyRefresh)
                    })
            },
            loadTracks() {
                axios.get('https://api.spotify.com/v1/playlists/37i9dQZEVXbNxXF4SkHj9F', {
                        headers: {
                            Authorization: 'Bearer ' + sessionStorage.getItem("spotifyAccess")
                        }
                    })
                    .then(response => {
                        this.tracks = response.data.tracks.items;
                        console.log(this.tracks);

                    })
                    .catch(error => {
                        console.log(error);
                        sessionStorage.removeItem("accessToken")
                        sessionStorage.removeItem("refreshToken")
                    });
            }
        }
    }
</script>
<style>
.chart{
    padding-bottom: 102px;
}
</style>