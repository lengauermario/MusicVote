<template>
  <v-container grid-list-md text-xs-center>
    <v-text-field v-model="search" label="Suche..."></v-text-field>
    <v-layout row wrap align-center v-for="item in songs" v-bind:key="item.id">
      <template v-if="item.title.toUpperCase().includes(search.toUpperCase())">
        <v-flex xs4>
          <v-card dark color="primary">
            <v-img
              :src="item.thumbNail"
              :lazy-src="item.thumbNail"
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
        <v-flex xs6 height="100%">
          <v-card color="primary">
            <v-card-text fillheight class="px-0">
              <span style="font-weight: bold">{{item.title}}</span>
              <br>
              {{item.artist}}
            </v-card-text>
          </v-card>
        </v-flex>
        <v-flex xs2>
          <v-img
            style="margin: auto auto"
            :src="item.iconPath"
            :lazy-src="item.thumbNail"
            aspect-ratio="1"
            @click="addSong(item)"
          />
        </v-flex>
      </template>
    </v-layout>
  </v-container>
</template>

<script>
import Vue from "vue";
import Vuetify from "vuetify";
import SongService from "@/services/SongService.ts";

export default Vue.extend({
  name: "add-view",
  data() {
    return {
      songs: [],
      iconPaths: [
        require("@/assets/images/heartGrey.png"),
        require("@/assets/images/heart.png"),
        require("@/assets/images/error.png"),
        require("@/assets/images/loading.gif"),
        require("@/assets/images/plus.png")
      ],
      defaultThumbnail: require("@/assets/images/defaultCover.png"),
      search: "",
      youTubeSearch: true
    };
  },
  components: {
    Vue,
    Vuetify
  },
  created() {
    this.initializePlaylist();
  },
  methods: {
    initializePlaylist() {
      SongService.getAll().then(result => {
        this.songs = result;
        this.songs.forEach(song => {
          if (!("thumbNail" in song) || song.thumbNail === "default") {
            song.thumbNail = this.defaultThumbnail;
          }
          song.iconPath = this.iconPaths[4];
        });
        console.log(result);
      });
    },
    addSong(item) {
      var i = this.songs.filter(obj => {
        return obj.id === item.id;
      });
      i.iconPath = this.iconPaths[3];
      SongService.addSong(item.id).then(result => {
        console.log("Song added to Playlist");
      });
    }
  }
});
</script>