npm<template>
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
            <song-preview ref="songpreview"/>
            <voting-list ref="votinglist"/>
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

export default Vue.extend({
  name: 'App',
  components: {
    Vue,
    Vuetify,
    VotingList,
    AddView,
    SongPreview
  },
  data () {
    return {
      
    }
  },  
  mounted() {
    const eventSource = new EventSource(
      "http://localhost:8080/musicvoting/api/playlist/connect"
    );
    eventSource.addEventListener("add_song", e => {
      this.$refs.votinglist.addSong(JSON.parse(e.data));
    });
    eventSource.addEventListener("add_vote", e => {
      this.$refs.votinglist.addVote(JSON.parse(e.data).id);
    });
    eventSource.addEventListener("remove_song", e => {
      this.$refs.votinglist.removeSong(JSON.parse(e.data).id);
    });
    eventSource.addEventListener("remove_vote", e => {
      this.$refs.votinglist.removeVote(JSON.parse(e.data).id);
    });
    eventSource.addEventListener("song_started", e => {
      this.$refs.songpreview.refresh();
    });
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
