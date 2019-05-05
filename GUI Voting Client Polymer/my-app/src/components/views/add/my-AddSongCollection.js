import {html, LitElement} from "@polymer/lit-element";
import '@polymer/paper-input/paper-input.js';
import '@polymer/iron-icon';
import './my-AddSong.js'
import {makeRequest} from "../../shared/my-helper";


class MyAddSongCollection extends LitElement {
    constructor() {
        super();
        document.addEventListener('my-searchEvent', async (e) => {
            this.refresh(e.detail.text, e.detail.searchYT);
        });
        //     this.ytSearchUrl="http://localhost:8080/musicvoting/api/video?queryTerm=";
        //       this.songUrl="http://localhost:8080/musicvoting/api/song/findall"
    }

    static get properties() {
        return {
            songs: {
                type: Array,
            },
            ytSearchUrl: {
                type: String
            },
            songUrl: {
                type: String
            }
        }
    }

    render() {
        if (this.songs !== undefined) {
            return html`
            ${this.songs.map(i => html`<my-addsong id="${i.id}" status="${i.status}" title="${i.title.substring(0, 36)}" artist="${i.artist.substring(0, 17)}" thumbnail="${i.thumbnail}"></my-addsong>`)}`;
        }
        return html`wird geladen`;
    }

    updated(_changedProperties) {
        let myEvent = new CustomEvent('my-songsUpdated', {
            detail: {},
            bubbles: true,
            composed: true
        });
        this.dispatchEvent(myEvent);
    }

    async refresh(searchText, searchOnYt) {
        if (searchOnYt) {
            let url = this.ytSearchUrl + searchText;
            let method = "GET";
            let result = await makeRequest(method, url);
            result = JSON.parse(result);
            this.songs = result.map(entry => ({
                id: entry.videoId,
                status: entry.status,
                title: entry.title,
                artist: entry.artist,
                thumbnail: entry.thumbNail
            }));
        } else {
            let method = "GET";
            let result = await makeRequest(method, this.songUrl);
            result = JSON.parse(result);
            result = result.map(entry => ({
                id: entry.id,
                status: entry.status,
                title: entry.title,
                artist: entry.artist,
                thumbnail: entry.thumbNail
            }));
            this.songs = result.filter(function (item) {
                return (item.title.toUpperCase().includes(searchText.toUpperCase()));
            });
        }
    }
}

window.customElements.define('my-addsongcollection', MyAddSongCollection);