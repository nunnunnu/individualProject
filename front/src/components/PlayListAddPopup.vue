<template>
    <div class="popup_wrap">
        <div class="popup">
            <div align="right">
                <button class="btn btn-light">&times;</button>
            </div>
            <br>
            <h4>재생 목록 추가</h4>
            <hr>
            <div class="modal-body">
                <button type="button" data-bs-dismiss="modal" aria-label="Close" @click="nowPlayingAdd" class="btn btn-success">재생목록 추가</button>
                <br>
                <br>
                <button type="button" @click="playListAdd(item)" class="btn btn-success">플레이리스트 추가</button>
            </div>
        </div>
    </div>
</template>
<script>
// import { Axios } from 'axios'
    export default{
        name:"playListPopup",
        props:{
            song:Object
        },
        data(){
            return{
            }
        },
        mounted(){
            console.log(this.song)
        },
        methods:{
        }
    }
</script>
<style>
    .popup_wrap{
        position: fixed; width:100vw; height: 100vh;
        left: 0; top: 0; z-index: 9999; background-color: #55555570;
    }
    .popup{
        position:absolute; left:50%;
        top: 35%;
        margin-left:-150px; margin-top:-130px;
        background-color: white;
        padding:30px;
    }
</style>