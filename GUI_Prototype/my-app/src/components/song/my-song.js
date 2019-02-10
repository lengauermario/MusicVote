import {LitElement, html} from "@polymer/lit-element";
import '@polymer/paper-input/paper-input.js';
import '@polymer/iron-icon';
import { SharedStyles } from '../shared/shared-styles.js';


class MySong extends LitElement {
    static get properties(){
        return {
            liked: {
                type: Boolean
            },
            likedImgSrc: {
                type: String
            },
            id:{
                type: Number
            },
            thumbnail: {
                type: String
            },
            title: {
                type: String
            },
            artist:{
                type: String
            }

        }
    }

    constructor(){
        super();
        this.liked = false;
        this.likedImgSrc = "/images/heartGrey.png";
    }

    render() {
        return html`
            ${SharedStyles}
            <div class="columns is-mobile" style="border-bottom: 1px solid black; margin-bottom: 0.7em">
                <div class="column is-4">
                    <img src="${this.thumbnail}" class="image" style="margin: auto 0; ">
                </div>
                <div class="column is-" style="margin: auto 0">
                    <h6 class="title is-6" style="margin-bottom: 0">${this.title}</h6>
                    <h6 class="subtitle is-6" style="margin: 0 0 15px 0">${this.artist}</p>
                </div>
                <div class="column is-2" style="margin: auto auto;">
                    <img src="${this.likedImgSrc}" @click="${this.changeLikedImgSrc}" class="image" style="margin: auto 0;">
                </div>
            </div>                
            `;
    }

    changeLikedImgSrc(){
        if (this.liked){
            this.likedImgSrc = "/images/heartGrey.png";
            this.liked = false;
            //send request to Back-End voting minus 1
        }
        else {
            this.likedImgSrc = "/images/heart.png";
            this.liked = true;
            //send request to Back-End voting plus 1
        }
    }
}

window.customElements.define('my-song', MySong);