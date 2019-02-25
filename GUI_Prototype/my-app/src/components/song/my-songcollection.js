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
            console.log(e.detail.text);
            console.log(e.detail.searchYT);
            this.refresh(e.detail.text, e.detail.searchYT);
        });
      //  this.refresh("Thunder", false);
        //setInterval(this.refresh, 5000);
    }


    render() {
        if(this.songs !== undefined){
            return html`
            ${this.songs.map(i => html`<my-song id="${i.videoId}" isYouTube="${true}" title="${i.title.substring(0, 36)}" artist="${i.channel.substring(0, 17)}" thumbnail="${i.thumbNail}"></my-song>`)}`;
        }
        return html`wird geladen`;

    }

    async refresh(searchText, searchOnYt){
        if(searchOnYt){
            let url =  "http://localhost:8080/youtubesearch/api/video?queryTerm="+searchText;
            let method = "GET";
            let result = await makeRequest(method, url);
            console.log(result);
            this.songs = JSON.parse(result);
        }
        else{
            let url =  "http://localhost:8080/youtubesearch/api/video?queryTerm="+searchText;
            let method = "GET";
            let result = await makeRequest(method, url);
            console.log(result);
            //   var oldSongs = this.songs;
            this.songs = JSON.parse(result);
        }
    }
}

window.customElements.define('my-songcollection', MySongCollection);