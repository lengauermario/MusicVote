<template>  
  <p>home</p>
</template>

<script lang="ts">
import { Vue } from 'vue-property-decorator';
import VotingList from '@/components/VotingList.vue'; // @ is an alias to /src

export default Vue.extend({
  name: 'home',
  components:{
    VotingList
  }
});
</script>
