import {html, LitElement} from "@polymer/lit-element";
import '@polymer/paper-input/paper-input.js';
import '@polymer/iron-icon';
import {makeRequest} from "../../shared/my-helper";
import {SharedStyles} from '../../shared/shared-styles.js';


class MySong extends LitElement {
    static get properties(){
        return {
            id:{
                type: String
            },
            title: {
                type: String
            },
            artist:{
                type: String
            },
            thumbnail: {
                type: String
            },
            status: {
                type: String
            },
            liked: {
                type: Boolean
            },
            IconSrc: {
                type: String
            },

        }
    }

    constructor(){
        super();
        this.liked = false;
        console.log(this.status);
        this.IconSrc = this.setIconSrcPath();
    }

    updated(changedProperties) {
        if(this.thumbnail === "undefined"){
            this.thumbnail = "/images/covers/default.png";
        }
    }

    render() {
        return html`
            ${SharedStyles}
            <div class="columns is-mobile" style="border-bottom: 1px solid black; margin-bottom: 0.7em">
                <div class="column is-4">
                    <img src="${this.thumbnail}" class="image" style="margin: auto 0; ">
                </div>
                <div class="column" style="margin: auto 0">
                    <h6 class="title is-6" style="margin-bottom: 0">${this.title}</h6>
                    <h6 class="subtitle is-6" style="margin: 0 0 15px 0">${this.artist}</p>
                </div>
                <div class="column is-2" style="margin: auto auto;">
                    <img src="${this.IconSrc}" @click="${this.changeLikedImgSrc}" class="image" style="margin: auto 0;">
                </div>
            </div>                
            `;
    }

    setIconSrcPath(){
        if(this.status === "DOWNLOADABLE"){
            return "/images/plus.png";
        }
        else if(this.status === "DOWNLOADING"){
            return "/images/loading.gif";
        }
        else if(this.status === "AVAILABLE"){
            return "/images/heartGrey.png";
        }
        else if(this.status === "NOT_AVALABLE"){
            return "/images/error.png";
        } else if (this.status == null) {
            return "/images/heartGrey.png"
        }
    }

    changeLikedImgSrc(){
        if (this.liked){
            this.IconSrc = "/images/heartGrey.png";
            this.liked = false;
            //send request to Back-End voting minus 1
        }
        else {
            this.IconSrc = "/images/heart.png";
            this.liked = true;
            console.log(this.id);
            makeRequest("POST", "http://localhost:8080/musicvoting/api/song/playlist/addvote?"+this.id)
        }
    }
}

window.customElements.define('my-song', MySong);