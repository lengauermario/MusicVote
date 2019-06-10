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
    this.$store.subscribe((mutation, state) => {
      if(mutation.type === "setPlaylist"){
        this.songs.forEach(s => {
            this.prepareSong(s);
        });
        this.$store.commit("cleanUpVotes")
        this.songs.splice(0,0);
      }
    })
  },
  methods: {
    addSong(item) {
      if(this.$store.state.votes && this.$store.state.votes.indexOf(item.id) >= 0){
        this.removeVote(item.id);
        PlaylistService.removeVote(item.id)
      }
      else if(this.$store.state.songs && this.$store.state.songs.map(s => s.id).indexOf(item.id) >= 0){
         this.addVote(item.id);
          PlaylistService.addVote(item.id)
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
    addVote(id){
      this.$store.commit("addVote", id)
    },
    removeVote(id){
      this.$store.commit("removeVote", id)
    },
    prepareSong(item){
      if(this.$store.state.songs.map(s => s.id).indexOf(item.id) >= 0){
        item.iconPath = this.$store.state.songs.find(s => s.id == item.id).iconPath
      }
      else{
        item.iconPath = this.imagePaths[4];
      }
    },

    playlistChanged(tmp){
      this.songs.forEach(song => {
          song = this.prepareSong(song);
      });
      this.songs.splice(0,0);
    }
  }
});
</script>