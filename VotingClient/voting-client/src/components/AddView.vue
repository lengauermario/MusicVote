<template>
  <v-container grid-list-md text-xs-center>
    <v-layout rpw wrap align-center>
      <v-flex xs12>
        <v-text-field v-model="search" label="Suche..." @input="searchInputChanged()"></v-text-field>
      </v-flex>
    </v-layout>
    <v-layout row wrap align-center v-for="item in songs" v-bind:key="item.video">
      <template>
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
            :src="item.iconPath" :lazy-src="item.iconPath"
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

import PlaylistService from '@/services/PlaylistService.ts'

export default Vue.extend({
  name: "add-view",
  data() {
    return {
      songs: [],
      imagePaths: [
        require("@/assets/images/heartGrey.png"),
        require("@/assets/images/heart.png"),
        require("@/assets/images/error.png"),
        require("@/assets/images/loading.gif"),
        require("@/assets/images/plus.png"),
        require("@/assets/images/youTube.png"),
        require("@/assets/images/youTubeGrey.png")
      ],
      defaultThumbnail: require("@/assets/images/defaultCover.png"),
      search: "",
      songsInPlaylist: [],
    };
  },
  components: {
    Vue,
    Vuetify
  },
  created() {
    this.searchInputChanged();
    search:"";
  },
  methods: {
    addSong(item) {
      if(item.iconPath.includes('Grey')){
        item.iconPath = this.imagePaths[1];
        this.pushToLocalStorage(item.id);
        PlaylistService.addVote(item.id).then(x => {});
      }
      else if(item.iconPath.includes('heart')) {
        item.iconPath = this.imagePaths[0];
        this.removeFromLocalStorage(item.id);
        PlaylistService.removeVote(item.id).then(x => {});
      }
      else{
        SongService.addSong(item.id).then(result => {
        });
      }     
    },
    searchInputChanged() {
      this.fetchLocalSongs();
    },
    fetchLocalSongs(){
      SongService.find(this.search).then(result => {
        this.songs = result;
        this.songs.forEach(song => {
          song = this.prepareSong(song);
        });
      });
    },

    prepareSong(song){
     song.videoId = song.id;
      if (!("thumbNail" in song) || song.thumbNail === "default") {
        song.thumbNail = this.defaultThumbnail;
      }
      let tmp = JSON.parse(localStorage.getItem("votes"));
      if(tmp != null && tmp.indexOf(song.id) >= 0) {
        song.iconPath = this.imagePaths[1];
      }
      else if(this.songsInPlaylist.indexOf(song.id) >= 0){
        song.iconPath = this.imagePaths[0];
      }
      else{
        song.iconPath = this.imagePaths[4];
      }
      return song;
    },

    playlistChanged(tmp){
      this.songsInPlaylist = tmp;
      this.songs.forEach(song => {
          song = this.prepareSong(song);
        });
        this.songs.splice(0,0);
    },
    pushToLocalStorage(id){
      var votes = JSON.parse(localStorage.getItem("votes"));
      if(votes == null)
        votes = [];
      votes.push(id);
      localStorage.setItem("votes", JSON.stringify(votes));
    },
    removeFromLocalStorage(id){
      var votes = JSON.parse(localStorage.getItem("votes"));
      votes.splice(votes.indexOf(id), 1);
      localStorage.setItem("votes", JSON.stringify(votes));
    },
    refreshLocalStorage(){
      var votes = JSON.parse(localStorage.getItem("votes"));
      let intersection = null;
      if(votes)
        intersection = votes.filter(x => this.songsInPlaylist.indexOf(x)>= 0)
      localStorage.setItem("votes", JSON.stringify(intersection));
    }
  }
});
</script>