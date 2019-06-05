<template>
  <v-container grid-list-md text-xs-center>
      <v-layout row wrap align-center>

    <v-flex xs5>
        <v-card dark color="primary">
        <v-img
            :src="this.thumbNail"
            :lazy-src="this.thumbNail"
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
        <v-card color="primary">
        <v-card-text fillheight class="px-0">
            <span style="font-weight: bold">{{this.title}}</span>
            <br>
            {{this.artist}}
        </v-card-text>
        </v-card>
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
      thumbNail: this.defaultThumbnail,
      title: "Start",
      artist: "Der Admin muss noch starten"
    };
  },
  components: {
    Vue,
    Vuetify,
    PlaylistService
  },
  Create() {
      
  },
  methods: {
    refresh(){
        PlaylistService.peek().then( song => {
            console.log(song)
            localStorage.removeItem(song.id);
            this.thumbNail = (song.thumbNail == "default")? this.defaultThumbnail : song.thumbNail;
            this.title = song.title;
            this.artist = song.artist
        });
    }
  }
});
</script>