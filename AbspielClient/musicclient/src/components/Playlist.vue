<template>
  <div>
    <v-layout row>
      <v-flex xs12>
        <v-card dark color="primary">
          <div style="height: 50px">
            <p
              style="position: absolute;width: 100%;height: 100%;margin: 0;line-height: 50px;"
            >Playlist</p>
          </div>
        </v-card>
      </v-flex>
    </v-layout>
    <v-layout row>
      <v-flex xs12>
        <div style="height: 70vh; overflow-y: scroll">
          <v-list>
            <v-list-tile v-for="song in playlist" :key="song.id">
              <v-list-tile-content>{{song.votes}}</v-list-tile-content>
              <v-list-tile-content>{{song.id}}</v-list-tile-content>
              <v-list-tile-content>{{song.title}}</v-list-tile-content>
            </v-list-tile>
          </v-list>
        </div>
      </v-flex>
    </v-layout>
    <v-layout row fluidwrap></v-layout>
  </div>
</template>

<script lang="js">

export default {
  name: "Playlist",
  data() {
    return { 
        /**@type {Song[]} */
        playlist: []
     };
  },
  created(){
      fetch("http://localhost:8085/musicvoting/api/playlist/", {
        method: "GET",
        credentials: "include"
      }).then(async function(res) {
        let tmp = await res.json();
        this.playlist = tmp;
        this.sort();
      }.bind(this));
  },
  methods: {
    addSong(song) {
        this.playlist.push(song);
        this.sort();
    },
    removeSong(id){
        const index = this.getIndexOfSong(id);
        if(index != -1)
            this.playlist.splice(index, 1);
        this.sort();
    },
    addVote(id){
        this.playlist[this.getIndexOfSong(id)].votes++;
        this.sort();
    },
    removeVote(id){
        this.playlist[this.getIndexOfSong(id)].votes--;
        this.sort();
    },
    getIndexOfSong(id){
        for(var i = 0;i < this.playlist.length;i++){
            if(this.playlist[i].id == id)
                return i;
        }
        return -1;
    },
    sort(){
      this.playlist.sort((a,b) => {
        if(a.votes > b.votes) return -1;
        else if(a.votes < b.votes) return 1;
        else{
          if(new Date(a.time + 'Z') >= new Date(b.time + 'Z')) return 1;
          else return -1;
        }
      });
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
