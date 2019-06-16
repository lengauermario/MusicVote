<template>
  <v-tabs fixed-tabs 
      color="white"
      slider-color="black" >
    <v-tab style=" border-bottom: 2px solid lightgrey;">
      Playlist
    </v-tab>
    <v-tab style=" border-bottom: 2px solid lightgrey;">
      Hinzufügen
    </v-tab>
    <v-tab-item>
      <v-card flat>
          <v-card-text>
            <song-preview/>
            <voting-list/>
          </v-card-text>
        </v-card>
    </v-tab-item>
    <v-tab-item >
        <v-card flat>
          <v-card-text><add-view/></v-card-text>
        </v-card>
    </v-tab-item>
  </v-tabs>
</template>

<script lang="js">
import Vue from "vue";
import Vuetify from 'vuetify' 
import VotingList from '@/components/VotingList.vue'
import AddView from '@/components/AddView.vue'
import SongPreview from '@/components/SongPreview.vue'
import PlaylistService from '@/services/PlaylistService.ts'
import ReconnectingEventSource from "reconnecting-eventsource";

export default Vue.extend({
  name: 'App',
  components: {
    Vue,
    Vuetify,
    VotingList,
    AddView,
    SongPreview
  },
  data() {
    return {
      eventSource: null
    };
  },
  methods: {
    refresh(){
      this.$store.dispatch("refreshIfNecessary")
    }
  },
  created(){
    window.addEventListener('focus', () => this.refresh());
  },
  destroyed() {
    this.eventSource.close();
    window.removeEventListener('visibilitychange',  this.refresh);
  },
  mounted() {  
    this.eventSource = new ReconnectingEventSource(
      process.env.VUE_APP_API_URL + "/playlist/connect",{withCredentials: false,max_retry_time: 3000,}
    );
    this.eventSource.addEventListener("song_started", e => {
      this.$store.dispatch("peek")
    });
    this.eventSource.addEventListener("add_vote", e => {
      console.log(JSON.parse(e.data));
      this.$store.dispatch("handleVote", JSON.parse(e.data))
    });
    this.eventSource.addEventListener("remove_vote", e => {
      console.log(JSON.parse(e.data));
      this.$store.dispatch("handleVoteRemovement", JSON.parse(e.data))
    });
    this.eventSource.addEventListener("add_song", e => {
      console.log(JSON.parse(e.data));
      this.$store.dispatch("handleSong", JSON.parse(e.data))
    });
    this.eventSource.addEventListener("remove_song", e => {
      console.log(JSON.parse(e.data));
      this.$store.dispatch("handleSongRemovement", JSON.parse(e.data))
    });
    this.$store.dispatch("fetchPlaylist")
  }
});
</script>

<style>
  * {
    font-family: sans-serif;
  }
  .v-tabs__wrapper{
    position: sticky;
    z-index: 5;
  }
</style>
