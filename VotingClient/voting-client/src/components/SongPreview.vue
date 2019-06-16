<template>
  <v-container grid-list-md text-xs-center>
      <v-layout row wrap>
    <v-flex xs5>
        <v-card dark color="primary">
        <v-img
            :src="defaultThumbnail"
            :lazy-src="defaultThumbnail"
            aspect-ratio="1"
            class="grey lighten-2"
        >
            <template v-slot:placeholder>
            <v-layout fill-height align-center justify-center ma-0>
                <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
            </v-layout>
            </template>
        </v-img>
        </v-card>
    </v-flex>
    <v-flex xs7 height="100%">
        <div style="width: 100%;height:100%">
            <span style="font-weight: bold;float:left;overflow-wrap: break-word;text-align: left;padding-top: 5px;font-size: large">{{this.title}}</span>
            <br/>
            <span style="text-align: left;float:left">{{this.artist}}</span>
          </div>
    </v-flex>
      </v-layout>
  </v-container>
</template>

<script>
import Vue from "vue";
import Vuetify from "vuetify";
import PlaylistService from '@/services/PlaylistService.ts'

export default Vue.extend({
  name: "song-preview",
  data() {
    return {
      defaultThumbnail: require("@/assets/images/defaultCover.png"),
      title: "Es wird noch kein Titel gespielt",
      artist: "Wiedergabe muss erst gestartet werden"
    };
  },
  components: {
    Vue,
    Vuetify,
    PlaylistService
  },
  created() {
      this.$store.subscribe((mutation, store) => {
        if(mutation.type === "setCurrentSong"){
          const song = mutation.payload
          if(song){
            this.title = song.title
            this.artist = song.artist
          }
        }
      })
      this.$store.dispatch("peek")
  }
});
</script>