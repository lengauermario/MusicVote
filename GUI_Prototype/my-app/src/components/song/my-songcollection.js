import {html, LitElement} from "@polymer/lit-element";
import '@polymer/paper-input/paper-input.js';
import '@polymer/iron-icon';
import './my-song.js'
import {makeRequest} from "../shared/my-helper";


class MySongCollection extends LitElement {
    static get properties(){
        return {
            songs: {
                type: Array,
            }
        }
    }

    constructor(){
        super();
        document.addEventListener('my-searchEvent', async  (e) => {
            this.refresh(e.detail.text, e.detail.searchYT);
        });
    }


    render() {
        if(this.songs !== undefined){
            return html`
            ${this.songs.map(i => html`<my-song id="${i.id}" status="${i.status}" title="${i.title.substring(0, 36)}" artist="${i.artist.substring(0, 17)}" thumbnail="${i.thumbnail}"></my-song>`)}`;
        }
        return html`wird geladen`;

    }
    updated(_changedProperties) {
        let myEvent = new CustomEvent('my-songsUpdated', {
            detail: {},
            bubbles: true,
            composed: true });
        this.dispatchEvent(myEvent);
    }

    async refresh(searchText, searchOnYt){
        if(searchOnYt){
            let url =  "http://localhost:8080/musicvoting/api/video?queryTerm="+searchText;
            let method = "GET";
            let result = await makeRequest(method, url);
            result = JSON.parse(result);
            this.songs = result.map(entry => ({id: entry.videoId, status: entry.status, title: entry.title, artist: entry.artist, thumbnail: entry.thumbNail}));
            console.log(this.songs);
        }
        else{
            let url =  "http://localhost:8080/musicvoting/api/song/findall";
            let method = "GET";
            let result = await makeRequest(method, url);
            result = JSON.parse(result);
            this.songs = result.map(entry => ({id: entry.id, status: entry.status, title: entry.title, artist: entry.artist, thumbnail: entry.thumbNail}));
            console.log(this.songs);
      //      this.songs = songs.filter( function(item){return (item.title.contains(searchText));} );
        }
    }
}

window.customElements.define('my-songcollection', MySongCollection);