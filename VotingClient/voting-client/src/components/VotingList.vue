<template>
  <v-container grid-list-md text-xs-center>
    <transition-group name="list-complete" tag="p">
    <v-layout style="border-top: 1px solid #CCC" row wrap align-center v-for="item in $store.state.songs" v-bind:key="item.id" class="list-complete-item" >
      <v-flex xs2>
        <div style="backgroundColor: #141517; height: 50px; width: 50px">
          <div style="line-height: 50px;white-space: nowrap;color: white;font-weight: bold;font-size: 20px">{{item.votes}}</div>
        </div>
      </v-flex>
      <v-flex xs9 height="100%">
          <div style="width: 100%">
            <span style="font-weight: bold; white-space: nowrap; display: block; overflow: hidden;text-overflow: ellipsis">{{item.title}}</span> 
            <span style="">{{item.artist}}</span>
          </div>
      </v-flex>
      <v-flex xs1>
        <i class="material-icons" :icon="imagePaths[item.iconIndex]" @click="handleClick(item)" style="cursor: pointer;font-size: 27px !important;">{{imagePaths[item.iconIndex]}}</i>
      </v-flex>
    </v-layout>
    </transition-group>
    <v-snackbar v-model="snackbar" :timeout="timeout" bottom>Playlist wird neu geladen</v-snackbar>
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
      snackbar: false,
      timeout: 1000,
      imagePaths: [
          "favorite_border",
          "favorite"
      ],
      defaultThumbnail: require("@/assets/images/defaultCover.png"),
    };
  },
  components: {
    Vue,
    Vuetify
  },
  created() {
    this.$store.subscribe((mutation, state) => {
      if(mutation.type === "setPlaylist"){
        this.snackbar = true
        state.songs.forEach(song => {
            song = this.prepareSong(song);
        });
        this.$store.commit("cleanUpVotes")
      }
      if(mutation.type == "setPlaylist" || mutation.type == "removeVote" || mutation.type == "addVote"|| mutation.type == "addSong"|| mutation.type == "removeSong")
        this.sort();
    })
  },
  methods:{    
    handleClick(item){
      if(this.$store.state.votes.indexOf(item.id) >= 0){
        this.removeVote(item.id);
        PlaylistService.removeVote(item.id)
      }
      else{
        this.addVote(item.id);
        PlaylistService.addVote(item.id)
      }
    },
    prepareSong(item){
      if(this.$store.state.votes && this.$store.state.votes.indexOf(item.id) >= 0){
        this.$store.commit("changeIconPath", {songId: item.id, iconIndex: 1})
      }
      else{
        this.$store.commit("changeIconPath", {songId: item.id, iconIndex: 0})
      }      
      return item;
    },
    addVote(id){
      this.$store.dispatch("addUserVote", id)
    },
    removeVote(id){
      this.$store.dispatch("removeUserVote", id)
    },
    sort(){	
      this.$store.state.songs.sort((a,b) => {	
        if(a.votes > b.votes) return -1;	
        else if(a.votes < b.votes) return 1;	
        else{	
          if(a.addedToPlaylist >= b.addedToPlaylist) return 1;	
          else return -1;	
        }	
      });	
    },
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
i[icon$="favorite"]{
  color: #F20643 !important;
}
</style>
