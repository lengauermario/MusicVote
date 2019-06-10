<template>
  <v-container grid-list-md text-xs-center>
    <transition-group name="list-complete" tag="p">
    <v-layout row wrap align-center v-for="item in $store.state.songs" v-bind:key="item.id" class="list-complete-item" >
      <v-flex xs4>
        <v-card dark color="primary">
          <v-img :src="defaultThumbnail" :lazy-src="defaultThumbnail" aspect-ratio="1" class="grey lighten-2">
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
    this.$store.subscribe((mutation, state) => {
      if(mutation.type === "setPlaylist"){
        state.songs.forEach(song => {
            song = this.prepareSong(song);
        });
        this.$store.commit("cleanUpVotes")
      }
    })
  },
  methods:{    
    handleVote(item){
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
        this.$store.commit("changeIconPath", {songId: item.id, iconPath: this.imagePaths[1]})
      }
      else{
        this.$store.commit("changeIconPath", {songId: item.id, iconPath: this.imagePaths[0]})
      }      
      return item;
    },
    addVote(id){
      this.$store.commit("addVote", id)
    },
    removeVote(id){
      this.$store.commit("removeVote", id)
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
</style>
