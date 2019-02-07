import {LitElement, html} from "@polymer/lit-element";
import '@polymer/paper-input/paper-input.js';
import '@polymer/iron-icon/iron-icon.js';
import '@polymer/iron-icons/iron-icons.js';
import '@polymer/iron-input/iron-input.js';
import { SharedStyles } from '../shared/shared-styles.js';


class MySearch extends LitElement {
    static get properties(){
        return {
            imgSrc: {
                type: String,
            },
            searchYt:{
                type: Boolean,
            },
            searchbar:{
                type: String,
            }
        }
    }

    constructor(){
        super();
        this.searchYt = false;
        this.imgSrc = "/images/youTubeGrey.png";
        this.searchbar = "Suche MP3";

        document.getElementById("searchInput")
    }

    render() {
        return html`
            ${SharedStyles}
            <div class="columns is-mobile">
                <div class="column is-three-quarters">
                    <div class="field">
                        <p class="control has-icons-left">
                        <input id="searchInput" class="input" type="text" @KeyUp="${this.changeInput}" placeholder="${this.searchbar}">
                        <span class="icon is-small is-left">
                            <i class="icon"><iron-icon icon="search" style="horiz-align: center"></iron-icon></i>
                          <i class="fas fa-envelope"></i>
                        </span>
                      </p>
                    </div>
                </div>
                <div class="column">
                        <img class="is-5by3" @click="${this.changeYouTubeColor}" src="${this.imgSrc}" style="margin: 0.5vh auto;">
                </div>
            </div>
             `;
    }

    changeYouTubeColor(){
        if (!this.searchYt){
            this.imgSrc = "images/youTube.png";
            this.searchbar = "Suche auf YouTube";
            this.searchYt = !this.searchYt;
        }
        else {
            this.imgSrc = "/images/youTubeGrey.png";
            this.searchbar = "Suche MP3-Files";
            this.searchYt = !this.searchYt;
        }
    }

    async changeInput(){
        var input = this.shadowRoot.querySelector("#searchInput").value;
        //Look on server on input
        console.log("searching");
        var xmlHttp = new XMLHttpRequest();
        xmlHttp.open( "GET", " http://localhost:8080/youtubesearch/api/video?queryTerm="+input, false );
        xmlHttp.send( null );
        var jsonObj = JSON.parse(xmlHttp.responseText);
        var dom = document.querySelector('my-songcollection');
        jsonObj.forEach(function (obj) {
     /*       var song = document.createElement("my-song");
            song.id = obj['videoId'];
            song.title = obj['title'];
            song.artist = obj['channel'];
            song.thumbnail = obj['thumbNail'];
            dom.appendChild(song);*/
            console.log(obj);
        });
        console.log(jsonObj);
        console.log("send")


    }
}

window.customElements.define('my-search', MySearch);