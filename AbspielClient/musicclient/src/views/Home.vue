<template>
  <v-app id="inspire">
    <v-toolbar dark color="secondary" fixed app>
      <v-toolbar-title>MusicVoting</v-toolbar-title>
    </v-toolbar>
    <v-content>
      <div id="app">
        <v-app id="inspire">
          <v-container grid-list-md text-xs-center>
            <v-layout row>
              <v-flex xs7>
                <v-layout row>
                  <v-flex xs12>
                    <playing-song ref="playingSong"/>
                  </v-flex>
                </v-layout>
                <v-layout row>
                  <v-flex xs12></v-flex>
                </v-layout>
                <v-layout row>
                  <v-flex xs12>
                    <Player/>
                  </v-flex>
                </v-layout>
              </v-flex>
              <v-flex xs5>
                <playlist ref="playlist"/>
              </v-flex>
            </v-layout>
          </v-container>
        </v-app>
      </div>
    </v-content>
    <v-footer dark color="transparent" app>
      <span class="black--text">&copy;MusicVoting by HTL Leonding 2019</span>
    </v-footer>
  </v-app>
</template>

<script lang="js">
import Vue from "vue";
import Player from "@/components/Player.vue";
import PlayingSong from "@/components/PlayingSong.vue";
import Playlist from "@/components/Playlist.vue";

export default Vue.extend({
  name: "Home",
  components: {
    Player,
    PlayingSong,
    Playlist
  },
  mounted() { 
    const eventSource = new EventSource(
      process.env.VUE_APP_API_URL + "/playlist/connect"
    );
    eventSource.addEventListener("change", e => {
      let tmp = JSON.parse(e.data);
      this.$refs.playlist.refresh(tmp);
    });
    eventSource.addEventListener("song_started", e => {
      this.$refs.playingSong.refresh();
    });
  }
});
</script>
