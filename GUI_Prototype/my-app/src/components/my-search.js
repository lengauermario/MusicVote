import {LitElement, html} from "@polymer/lit-element";
import '@polymer/paper-input/paper-input.js';
import '@polymer/iron-icon/iron-icon.js';
import '@polymer/iron-icons/iron-icons.js';
import '@polymer/iron-input/iron-input.js';
import { SharedStyles } from './shared-styles.js';


class MySearch extends LitElement {
    static get properties(){
        return {
            imgSrc: {
                type: String,
            },
            searchbar:{
                type: String,
            }
        }
    }

    constructor(){
        super();
        this.imgSrc = "/images/youTubeGrey.png";
        this.searchbar = "Suche MP3";
    }

    render() {
        return html`
            ${SharedStyles}
            <div class="ui grid">
                <div class="eleven wide column">
                    <div class="ui search">
                    
                        <div class="ui left icon fluid input">
                            <input class="prompt" type="text" placeholder="${this.searchbar}">
                            <i class="icon"><iron-icon icon="search" style="horiz-align:  center"></iron-icon></i>
                          </div>
                    </div>
                </div>
                <div class="five wide column">
                        <img class="ui centered image" @click="${this.changeYouTubeColor}" src="${this.imgSrc}" style="margin: 0.5vh auto;">
                </div>
            </div>
             `;
    }

    changeYouTubeColor(){
        if(this.imgSrc === "/images/youTubeGrey.png"){
            this.imgSrc = "images/youTube.png";
            this.searchbar = "Suche MP3 & YouTube";
        }
        else {
            this.imgSrc = "/images/youTubeGrey.png";
            this.searchbar = "Suche MP3";
        }
        var img = document.getElementById("YTImg");
        console.dir(img);
    }
}

window.customElements.define('my-search', MySearch);