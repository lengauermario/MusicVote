<template>
  <v-container grid-list-md text-xs-center>
    <v-layout rpw wrap align-center>
      <v-flex xs12>
        <v-text-field v-model="search" label="Suche" @input="searchInputChanged()"></v-text-field>
      </v-flex>
    </v-layout>

    <v-layout
      style="border-bottom: 1px solid #CCC"
      row
      wrap
      align-center
      v-for="item in songs"
      v-bind:key="item.video"
    >
      <template>
        <v-flex xs2>
          <div style="backgroundColor: #141517; height: 50px; width: 50px">
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
          </div>
        </v-flex>
        <v-flex xs9 height="100%">
          <div style="width: 100%">
            <span
              style="text-align: left;font-weight: bold; white-space: nowrap; display: block; overflow: hidden;text-overflow: ellipsis"
            >{{item.title}}</span>
            <span style="text-align: left;width: 100%;display:block">{{item.artist}}</span>
          </div>
        </v-flex>
        <v-flex xs1>
          <i
            class="material-icons"
            :icon="imagePaths[item.iconIndex]"
            @click="handleClick(item)"
            style="cursor: pointer;font-size: 27px !important;"
          >{{imagePaths[item.iconIndex]}}</i>
        </v-flex>
      </template>
    </v-layout>

    <infinite-loading @infinite="fetchLocalSongs" spinner="waveDots" ref="InfiniteLoading">
      <span slot="no-more"></span>
      <span slot="no-results">Keine Lieder gefunden.</span>
    </infinite-loading>
  </v-container>
</template>

<script>
import Vue from "vue";
import Vuetify from "vuetify";
import SongService from "@/services/SongService.ts";

import PlaylistService from "@/services/PlaylistService.ts";
import InfiniteLoading from "vue-infinite-loading";

Vue.use(InfiniteLoading);

export default Vue.extend({
  components: {
    InfiniteLoading
  },
  name: "add-view",
  data() {
    return {
      songs: [],
      imagePaths: ["favorite_border", "favorite", "add"],
      defaultThumbnail: require("@/assets/images/defaultCover.png"),
      search: "",
      songsInPlaylist: [],
      page: 1,
      size: 15,
      loadMore: true
    };
  },
  components: {
    Vue,
    Vuetify
  },
  mounted() {
    this.searchInputChanged();
  },
  created() {
    this.$store.subscribe((mutation, state) => {
      if (mutation.type === "setPlaylist") {
        this.songs.forEach(song => {
          song = this.prepareSong(song);
        });
        this.$store.commit("cleanUpVotes");
        this.songs.splice(0, 0);
      } else if (mutation.type === "changeIconPath") {
        let tmp = this.songs.find(s => s.id === mutation.payload.songId);
        if (tmp) {
          this.prepareSong(tmp);
          this.songs.splice(0, 0);
        }
      } else if (mutation.type === "removeSong") {
        let tmp = this.songs.find(s => s.id === mutation.payload.id);
        if (tmp) {
          this.prepareSong(tmp);
          this.songs.splice(0, 0);
        }
      }
    });
  },
  methods: {
    handleClick(item) {
      if (
        this.$store.state.votes.find(
          v =>
            v.id == item.id &&
            this.$store.state.songs.find(s => s.id == item.id).addedToPlaylist
        )
      ) {
        this.removeVote(item.id);
        PlaylistService.removeVote(item.id);
      } else if (
        this.$store.state.songs &&
        this.$store.state.songs.map(s => s.id).indexOf(item.id) >= 0
      ) {
        this.addVote(
          item.id,
          this.$store.state.songs.find(s => s.id == item.id).addedToPlaylist
        );
        PlaylistService.addVote(item.id);
      } else {
        SongService.addSong(item.id).then(result => {
          let tmp = this.$store.state.songs.find(s => s.id == item.id);
          if (tmp) {
            this.addVote(tmp.id, tmp.addedToPlaylist);
            PlaylistService.addVote(tmp.id);
          }
        });
      }
    },
    searchInputChanged() {
      this.page = 1;
      this.songs = [];
      this.fetchLocalSongs(null, true);
    },
    fetchLocalSongs($state, reset = false) {
      console.log("fetchsongs called");
      SongService.find(this.search, this.page++, this.size).then(result => {
        if (reset) this.songs = [];
        this.songs.push(...result);
        this.songs.forEach(song => {
          song = this.prepareSong(song);
        });
        if ($state) {
          if (result.length > 0) $state.loaded();
          $state.complete();
        }
      });
    },
    addVote(id, timestamp) {
      this.$store.dispatch("addUserVote", { id, timestamp });
    },
    removeVote(id, timestamp) {
      this.$store.dispatch("removeUserVote", id);
    },
    prepareSong(item) {
      if (this.$store.state.songs.map(s => s.id).indexOf(item.id) >= 0) {
        item.iconIndex = this.$store.state.songs.find(
          s => s.id == item.id
        ).iconIndex;
      } else {
        item.iconIndex = 2;
      }
    },

    playlistChanged(tmp) {
      this.songs.forEach(song => {
        song = this.prepareSong(song);
      });
      this.songs.splice(0, 0);
    }
  }
});
</script>
<style>
i[icon$="favorite"] {
  color: #f20643 !important;
}
i[icon$="add"] {
  color: #f20643 !important;
}
</style>
