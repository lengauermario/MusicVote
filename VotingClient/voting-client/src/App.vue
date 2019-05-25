<template>
  <v-tabs fixed-tabs 
      color="white"
      slider-color="black" >
    <v-tab style=" border-bottom: 2px solid grey;">
      Voting View
    </v-tab>
    <v-tab style=" border-bottom: 2px solid lightgrey;">
      Hinzufügen
    </v-tab>
    <v-tab-item>
      <v-card flat>
          <v-card-text><voting-list/></v-card-text>
        </v-card>
    </v-tab-item>
    <v-tab-item >
        <v-card flat>
          <v-card-text><add-view/></v-card-text>
        </v-card>
    </v-tab-item>
  </v-tabs>
</template>

<script>
import Vue from 'vue'
import Vuetify from 'vuetify' 
import VotingList from '@/components/VotingList.vue'
import AddView from '@/components/AddView.vue'

export default {
  name: 'App',
  components: {
    Vue,
    Vuetify,
    VotingList,
    AddView
  },
  data () {
    return {
      //
    }
  }
}
</script>

<style>
  * {
    font-family: sans-serif;
  }
</style>
