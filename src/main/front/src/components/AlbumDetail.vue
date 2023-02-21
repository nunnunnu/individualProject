<template>
    <h2>앨범정보</h2>

</template>
<script>
import axios from 'axios'
export default {
    name: 'albumDetail',
    props:{
            seq:String
        },
    data() {
        return {
            data: null
        }
    },
    created() {
      this.loadPage(this.seq)
    },
    methods:{
        loadPage(seq){
            axios.get("http://localhost:8250/album/detail/"+seq)
          .then((e) => {
            console.log(seq)
            this.data = e.data.data
          })
        }
    }
}
</script>

<style>
</style>