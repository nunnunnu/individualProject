<template>
    <b-container>
        <br>
        <h2 class="pb-4 mb-4 fst-italic border-bottom">뮤직비디오</h2>
        <div v-html="movie()"></div>
        
    </b-container>
</template>
<script>
    import axios from 'axios'
    export default {
        name: 'moviePage',
        props: {},
        data() {
            return {
                tag : null
            }
        },
        created() {
            this.tag = this.$route.params.tag;
        },
        methods: {
            loadPage(seq) {
                
            },
            movie() {    
                return this.tag
            }
        }
    }
</script>

<style>
    p {
        font-size: 20px
    }

    .creator {
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        object-fit: cover;

    }

    .card-img-top {
        height: 150px;
        overflow: hidden;
    }
</style>