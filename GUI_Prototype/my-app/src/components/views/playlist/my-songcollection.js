import {html, LitElement} from "@polymer/lit-element";
import '@polymer/paper-input/paper-input.js';
import '@polymer/iron-icon';
import './my-song.js'
import {makeRequest} from "../../shared/my-helper";


class MySongCollection extends LitElement {
    static get properties(){
        return {
            songs: {
                type: Array,
            },
            songUrl: {
                type: String
            }
        }
    }

    constructor(){
        super();
    }

    render() {
        this.load();
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

    async load() {
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
        this.songs = result;
    }
}

window.customElements.define('my-songcollection', MySongCollection);