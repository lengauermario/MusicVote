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
  data () {
    return {
      timestamp: 0
    }
  },
  methods: {
    refresh(){
      if(this.$refs.votinglist)
        this.$refs.votinglist.refreshIfNecessary();  
      if(this.$refs.songpreview)
        this.$refs.songpreview.refresh(); 
    }
  },
  created(){
    //window.addEventListener('resume', () => {this.abc += "resume"});
    window.addEventListener('focus', () => this.refresh());
    /* window.addEventListener('blur', () => this.abc += "blur");
    window.addEventListener('visibilitychange', () => this.abc += "visibilitychange");
    window.addEventListener('freeze', () => this.abc += "freeze");
    window.addEventListener('beforeunload', () => this.abc += "beforeunload");
    window.addEventListener('pagehide', () => this.abc += "pagehide");
    window.addEventListener('unload', () => this.abc += "unload");
    window.addEventListener('pagehide', () => this.abc += "pagehide");
    window.addEventListener('pageshow', () => this.abc += "pageshow");
    window.addEventListener("beforeunload", function(e){
      this.abc += "called\n"
      this.shouldReload = true
    }, false); */
    
  },
  destroyed() {
    window.removeEventListener('visibilitychange',  this.refresh);
  },
  mounted() {  
    /*setInterval(() => {
        if(this.shouldReload){
          this.$refs.votinglist.fetchPlaylist()
          this.shouldReload = false
        }
      }, 10000);*/
    const eventSource = new EventSource(
      process.env.VUE_APP_API_URL + "/playlist/connect"
    );
    eventSource.addEventListener("change", e => {
      let tmp = JSON.parse(e.data);
      this.$refs.votinglist.refresh(tmp);
    });
    eventSource.addEventListener("song_started", e => {
      this.$refs.songpreview.refresh();
    });
    PlaylistService.getAll().then(result => {
      this.$refs.votinglist.refresh(result);
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
