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
            },
            searchInput:{
                type: String,
            }
        }
    }

    constructor(){
        super();
        this.searchYt = false;
        this.imgSrc = "/images/youTubeGrey.png";
        this.searchbar = "Suche MP3";
        this.searchInput= "";
        document.getElementById("searchInput")
    }

    render() {
        return html`
            ${SharedStyles}
            <div class="columns is-mobile">
                <div class="column is-three-quarters">
                    <div class="field">
                        <p class="control has-icons-left">
                        <input @input="${this.changeInput}" id="searchInput" class="input" type="text" placeholder="${this.searchbar}" value="${this.searchInput}">
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

    changeInput(event){
        console.log(event);
        let myEvent = new CustomEvent('my-searchEvent', {
            detail: { text: event.currentTarget.value, searchYT: this.searchYt },
            bubbles: true,
            composed: true });

        this.dispatchEvent(myEvent);
    }
}

window.customElements.define('my-search', MySearch);