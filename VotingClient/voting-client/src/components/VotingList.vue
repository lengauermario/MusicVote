<template>
  <v-container grid-list-md text-xs-center>
    <transition-group name="list-complete" tag="p">
    <v-layout row wrap align-center v-for="item in songs" v-bind:key="item.id" class="list-complete-item" >
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
    </transition-group>
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
      timestamp: 0,
    };
  },
  components: {
    Vue,
    Vuetify
  },
  created() {
    this.fetchPlaylist();
  },
  methods:{    
    fetchPlaylist(){
      PlaylistService.getAll().then(result => {
        this.songs = result.songs;
        this.timestamp = result.timestamp;
        this.songs.forEach(song => {
          song = this.prepareSong(song);
        });
      });
    },
    refreshIfNecessary(){
      PlaylistService.getVersion().then(x => {
          if(x != this.timestamp)
            this.fetchPlaylist();
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
        this.clearLocalStorage(item.id)
        PlaylistService.removeVote(item.id).then(x => console.log("vote removed "+item.id));
      }
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
      if(item.status == 'LIKED')
        return this.imagePaths[1];
      else
        return this.imagePaths[0];      
    },
    clearLocalStorage(id){
      localStorage.removeItem(id);
    },
    handleRemovement(id){
      this.clearLocalStorage(id);
    },
    refresh(data){
      this.songs = data.songs;
      this.timestamp = data.timestamp;
      this.songs.forEach(song => {
          song = this.prepareSong(song);
      });
    }
  }
};
</script>

<style scoped>
.list-complete-item {
  transition: all 1s;
  margin-right: 10px;
}
.list-complete-enter, .list-complete-leave-to {
  opacity: 0;
  transform: translateX(200px);
}
.list-complete-leave-active {
  position: absolute;
}
</style>
