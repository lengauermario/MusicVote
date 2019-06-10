import Vuex, { ActionTree } from "vuex";
import Vue from "vue"
import Song from "@/model/Song"
import VuexPersist from "vuex-persist"
import PlaylistService from '@/services/PlaylistService';

Vue.use(Vuex)

export interface RootState {
    timestamp: number
    votes: number[]
    songs: Song[]
    currentSong: Song | null
}

const persistenceStrategy = new VuexPersist({
    reducer: (state: any) => ({votes: state.votes})
})

const store = new Vuex.Store<RootState>({
    state: {
        votes: [],
        songs: [],
        currentSong: null,
        timestamp: 0
    },
    mutations: {
        setPlaylist(state, { songs, timestamp }) {
            state.songs = songs
            state.timestamp = timestamp
        },
        addVote(state, id){
            state.votes.push(id)
        },
        removeVote(state, id){
            state.votes.splice(state.votes.indexOf(id), 1)
        },
        cleanUpVotes(state) {
            state.votes = state.votes
                .filter(x => state.songs
                    .map(y => y.id)
                    .indexOf(x) >= 0
                )
        },
        changeIconPath(state, {songId, iconPath}){
            const song = state.songs.find(song => song.id == songId)
            if(song){
                song.iconPath = iconPath;
            }
        },
        setCurrentSong(state, {song}){
            state.currentSong = song;
        }
    },
    actions: {
        peek({ commit }) {
            PlaylistService.peek().then(song => {
               commit("setCurrentSong", song)
            });
        },
        fetchPlaylist({ state, commit }) {
            PlaylistService.getAll().then(result => {
                commit("setPlaylist", result)
            })
        },
        refreshIfNecessary({ state, dispatch }) {
            PlaylistService.getVersion().then(x => {
                if (x != state.timestamp)
                    dispatch("fetchPlaylist")
            });
        }
    },
    plugins: [persistenceStrategy.plugin]
})

export default store;