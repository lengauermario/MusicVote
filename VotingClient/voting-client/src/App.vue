<template>
  <v-tabs fixed-tabs 
      color="white"
      slider-color="black" >
    <v-tab style=" border-bottom: 2px solid lightgrey;">
      Voting View
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

export default Vue.extend({
  name: 'App',
  components: {
    Vue,
    Vuetify,
    VotingList,
    AddView,
    SongPreview
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
    window.removeEventListener('visibilitychange',  this.refresh);
  },
  mounted() {  
    const eventSource = new EventSource(
      process.env.VUE_APP_API_URL + "/playlist/connect"
    );
    eventSource.addEventListener("change", e => {
      this.$store.commit("setPlaylist", JSON.parse(e.data))
    });
    eventSource.addEventListener("song_started", e => {
      this.$store.dispatch("peek")
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
