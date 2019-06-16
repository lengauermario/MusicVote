import Vuex, { ActionTree } from "vuex";
import Vue from "vue"
import Song from "@/model/Song"
import VuexPersist from "vuex-persist"
import PlaylistService from '@/services/PlaylistService';
import createMutationsSharer from "vuex-shared-mutations";

Vue.use(Vuex)

export interface RootState {
    updateId: number
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
        updateId: 0
    },
    mutations: {
        setPlaylist(state, { songs, updateId }) {
            state.songs = songs
            state.updateId = updateId
        },
        addLocalVote(state, id){
            state.votes.push(id)
        },
        removeLocalVote(state, id){
            state.votes.splice(state.votes.indexOf(id), 1)
        },
        cleanUpVotes(state) {
            state.votes = state.votes
                .filter(x => state.songs
                    .map(y => y.id)
                    .indexOf(x) >= 0
                )
        },
        changeIconPath(state, {songId, iconIndex}){
            const song = state.songs.find(song => song.id == songId)
            if(song){
                song.iconIndex = iconIndex;
            }
        },
        setCurrentSong(state, {song}){
            state.currentSong = song;
        },
        addVote(state, {updateId, id}){
            state.updateId = updateId;
            state.songs.find(s => s.id == id)!.votes++;
        },
        removeVote(state, {updateId, id}){
            state.updateId = updateId;
            state.songs.find(s => s.id == id)!.votes--;
        },
        addSong(state, {updateId, song}){
            state.songs.push(song);
            state.updateId = updateId;
        },
        removeSong(state, {updateId, id}){
            state.songs.splice(state.songs.indexOf(state.songs.find(s => s.id == id)!), 1)
            state.updateId = updateId;
        }
    },
    actions: {
        peek({ commit }) {
            PlaylistService.peek().then(song => {
               commit("setCurrentSong", song)
            });
        },
        fetchPlaylist({ state, commit }) {
            console.log("fetch playlist")
            PlaylistService.getAll().then(result => {
                result.songs.forEach(element => {
                    let tmp = state.votes.indexOf(element.id) >= 0 ? 1 : 0;
                    element.iconIndex = tmp;
                });
                console.log(result);
                commit("setPlaylist", result)
            })
        },
        refreshIfNecessary({ state, dispatch, commit }) {
            PlaylistService.getVersion().then(x => {
                if (x != state.updateId)
                    dispatch("fetchPlaylist")
                commit("cleanUpVotes")
            });
        },
        handleVote({state, dispatch, commit}, {updateId, id}){
            if(state.updateId != updateId - 1)
                dispatch("fetchPlaylist")
            else
                commit("addVote", {updateId, id})
        },
        handleVoteRemovement({state, dispatch, commit}, {updateId, id}){
            if(state.updateId != updateId - 1)
                dispatch("fetchPlaylist")
            else
                commit("removeVote", {updateId, id})
        },
        handleSong({state, dispatch, commit}, {updateId, song}){
            if(state.updateId != updateId - 1)
                dispatch("fetchPlaylist")
            else{
                commit("addSong", {updateId, song})
                commit("changeIconPath", {songId: song.id, iconIndex: 0})
            }
        },
        handleSongRemovement({state, dispatch, commit}, {updateId, id}){
            if(state.updateId != updateId - 1)
                dispatch("fetchPlaylist")
            else
                commit("removeSong", {updateId, id})
        },
        removeUserVote({state, commit}, id){
            commit("removeLocalVote", id)
            commit("changeIconPath", {songId: id, iconIndex: 0})
        },
        addUserVote({state, commit}, id){
            commit("addLocalVote", id)
            commit("changeIconPath", {songId: id, iconIndex: 1})
        }
    },
    plugins: [persistenceStrategy.plugin, createMutationsSharer({ predicate: ["setPlaylist", "addLocalVote", "removeLocalVote","cleanUpVotes","changeIconPath", "setCurrentSong"] })]
})

export default store;