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
        <div style="height: 70vh; overflow-y: scroll;overflow-x: hidden;">
          <transition-group name="list-complete" tag="p">
            <v-layout row v-for="item in playlist" v-bind:key="item.id" class="list-complete-item">
              <v-flex xs1>{{item.votes}}</v-flex>
              <v-flex xs5>{{item.title}}</v-flex>
              <v-flex xs5>{{item.artist}}</v-flex>
              <v-flex xs1>
                <i class="material-icons" @click="remove(item.id)" style="cursor: pointer">clear</i>
              </v-flex>
            </v-layout>
          </transition-group>
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
      fetch(process.env.VUE_APP_API_URL + "/playlist/", {
        method: "GET",
        credentials: "include"
      }).then(async function(res) {
        let tmp = await res.json();
        this.playlist = tmp.songs;
        this.sort();
      }.bind(this));
  },
  methods: {
    remove(id){
      fetch(process.env.VUE_APP_API_URL + "/playlist/remove/song?id=" + id, {
        method: "POST",
        credentials: "include"
      });
    },	
    addSong(song) {	
        console.log('test 2');
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
          if(a.addedToPlaylist >= b.addedToPlaylist) return 1;	
          else return -1;	
        }	
      });
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.list-complete-item {
  transition: all 1s;
  margin-right: 10px;
}
.list-complete-enter, .list-complete-leave-to
/* .list-complete-leave-active below version 2.1.8 */ {
  opacity: 0;
  transform: translateX(300px);
}
.list-complete-leave-active {
  position: absolute;
}
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
