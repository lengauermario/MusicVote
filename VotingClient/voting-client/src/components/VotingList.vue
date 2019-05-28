<template>
  <v-container grid-list-md text-xs-center>
    <v-layout row wrap align-center v-for="item in songs" v-bind:key="item.id" >
      <v-flex xs4>
        <v-card dark color="primary">
          <v-img :src="item.thumbNail" :lazy-src="item.thumbNail" aspect-ratio="1" class="grey lighten-2">
             <span class="my-span" style="margin: auto">
              {{item.votes}} Votes
            </span>
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
            <span style="font-weight: bold">{{item.title}}</span> <br>{{item.artist}}
          </v-card-text>
        </v-card>
      </v-flex>
      <v-flex xs2>
        <v-img style="margin: auto auto" :src="item.iconPath" :lazy-src="item.iconPath" aspect-ratio="1" @click="handleVote(item)"/>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import Vue from 'vue'
import Vuetify from 'vuetify' 
import PlaylistService from '@/services/PlaylistService.ts'

export default {
  name: "voting-list",
  data() {
    return {
      songs: [],
      imagePaths: [
        require("@/assets/images/heartGrey.png"),
        require("@/assets/images/heart.png"),
        require("@/assets/images/error.png"),
        require("@/assets/images/loading.gif"),
        require("@/assets/images/plus.png"),
      ],
      defaultThumbnail: require("@/assets/images/defaultCover.png"),
    };
  },
  components: {
    Vue,
    Vuetify
  },
  created() {
    console.log("start votinglist");
    this.fetchPlaylist();
  },
  methods:{    
    fetchPlaylist(){
      PlaylistService.getAll().then(result => {
        this.songs = result;
        this.songs.forEach(song => {
          song = this.prepareSong(song);
        });
        console.log(result);
      });
    },
    handleVote(item){
      if(item.iconPath.includes("Grey")){
        item.iconPath = this.imagePaths[1];
        localStorage.setItem(item.id, "voted");
        PlaylistService.addVote(item.id).then(x => console.log("vote added "+item.id));
      }
      else{
        item.iconPath = this.imagePaths[0];
        localStorage.removeItem(item.id);
        PlaylistService.removeVote(item.id).then(x => console.log("vote removed "+item.id));
      }
    },
    addSong(item){
      console.log(item);
      item = this.prepareSong(item);
      this.songs.push(item);
    },
    prepareSong(item){
      if (!("thumbNail" in item) || item.thumbNail === "default") {
        item.thumbNail = this.defaultThumbnail;
      }
      if(localStorage.getItem(item.id) != null) {
        item.status = "LIKED"
      }
      item.iconPath = this.getIconPath(item);
      return item;
    },
    getIconPath(item) {
      if (item === undefined || item.status === undefined) {
        return this.imagePaths[0];
      }
      switch (item.status) {
        case 'LIKED':
          return this.imagePaths[1];
        case 'AVAILABLE':
          return this.imagePaths[0];
        case 'DOWNLOADABLE':
          return this.imagePaths[4];
        case 'DOWNLOADING':
          return this.imagePaths[3];
        case 'NOT_AVAILABLE':
          return this.imagePaths[2];
      }
    }
  }
};
</script>