import {LitElement, html} from "@polymer/lit-element";
import '@polymer/paper-input/paper-input.js';
import '@polymer/iron-icon';
import './my-song.js'
import { SharedStyles } from '../shared/shared-styles.js';


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
        this.refresh();
        setInterval(this.refresh, 1000);
    }


    render() {
        return html`
            ${this.songs.map(i => html`<my-song id="${i.videoId}" title="${i.title.substring(0,36)}" artist="${i.channel.substring(0,17)}" thumbnail="${i.thumbNail}"></my-song>`)}`;
    }

    /*update(changedProperties) {
        console.log(changedProperties);
        console.log(this.songs);
    }*/

    refresh(){
        var xmlHttp = new XMLHttpRequest();
        xmlHttp.open( "GET", " http://localhost:8080/youtubesearch/api/video?queryTerm=Thunder", false );
        xmlHttp.send( null );
        this.songs = JSON.parse(xmlHttp.responseText);
    }
}

window.customElements.define('my-songcollection', MySongCollection);