<template>
  <v-layout row>
    <v-flex xs10>
      <audio
        id="audio"
        style="width: 100%;outline: 0"
        autoplay="true"
        @ended="playNextSong"
        crossorigin="anonymous"
        controls
      ></audio>
    </v-flex>
    <v-flex xs2>
      <v-btn style="100%" color="primary" @click="playNextSong()">
        <i class="material-icons">skip_next</i>
      </v-btn>
    </v-flex>
  </v-layout>
</template>

<script>
export default {
  name: "Player",
  data() {
    return {};
  },

  components: {},
  methods: {
    playNextSong() {
      fetch(process.env.VUE_APP_API_URL + "/playlist/pop", {
        method: "GET",
        credentials: "include",
        headers: {
          "Content-Type": "text/plain"
        }
      }).then(
        async function(response) {
          let tmp = await response.json();
          let audio = document.getElementById("audio");
          audio.src = process.env.VUE_APP_API_URL + "/song/getmp3?id=" + tmp.id;
          audio.play();
        }.bind(this)
      );
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
