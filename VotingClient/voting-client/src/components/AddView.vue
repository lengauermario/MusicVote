<template>
  <v-container grid-list-md text-xs-center>
    <v-layout rpw wrap align-center>
      <v-flex xs9>
        <v-text-field v-model="search" label="Suche..." @input="searchInputChanged()"></v-text-field>
      </v-flex>
      <v-flex xs3>
        <v-img
          :src="this.youTubeImgPath"
          :lazy-src="this.youTubeImgPath"
          @click="turnYouTubeSearch()"
        />
      </v-flex>
    </v-layout>
    <v-layout row wrap align-center v-for="item in songs" v-bind:key="item.video">
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
            :lazy-src="item.iconPath"
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
import YouTubeService from "@/services/YouTubeService";

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
      youTubeSearch: true,
      youTubeImgPath: ""
    };
  },
  components: {
    Vue,
    Vuetify
  },
  created() {
    this.youTubeImgPath = this.imagePaths[6];
    this.searchInputChanged();
    search:"";
  },
  methods: {
    addSong(item) {
      item.iconPath = this.imagePaths[3];
      if(this.isSearchOnYouTubeOn()){
        YouTubeService.downloadVideo(item).then(result =>{
          item.iconPath = this.imagePaths[3];
          console.log("Youtubedownload start")
        })
      }
      else {
        SongService.addSong(item.id).then(result => {
          console.log("Song added to Playlist");
        });
      }
    },
    turnYouTubeSearch() {
      if (this.isSearchOnYouTubeOn()) {
        // YouTubeSearch is off turn on
        this.youTubeImgPath = this.imagePaths[6];
      } else {
        this.youTubeImgPath = this.imagePaths[5];
      }
      this.searchInputChanged();
    },
    isSearchOnYouTubeOn() {
      if (!this.youTubeImgPath.includes("Grey")) {
        return true;
      } else {
        return false;
      }
    },
    searchInputChanged() {
      if (this.isSearchOnYouTubeOn()) {
        YouTubeService.search(this.search).then((data) => {
          console.log(data);
          this.songs = data;
          this.songs.forEach(x => x.iconPath = this.getIconPath(x));
        });
      }
      else {
        this.fetchLocalSongs();
      }
    },
    fetchLocalSongs(){
      SongService.getAll().then(result => {
        this.songs = result;
        this.songs.forEach(song => {
          song.videoId = song.id;
          if (!("thumbNail" in song) || song.thumbNail === "default") {
            song.thumbNail = this.defaultThumbnail;
          }
          song.iconPath = this.getIconPath();
        });

        console.log(result);
      });
    },
    getIconPath(item) {
      if (item === undefined || item.status === undefined) {
        return this.imagePaths[4];
      }
      switch (item.status) {
        case 'AVAILABLE':
        case 'DOWNLOADABLE':
          return this.imagePaths[4];
        case 'DOWNLOADING':
          return this.imagePaths[3];
        case 'NOT_AVAILABLE':
          return this.imagePaths[2];
      }
    }
  }
});
</script>