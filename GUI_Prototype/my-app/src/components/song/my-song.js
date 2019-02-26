import {html, LitElement} from "@polymer/lit-element";
import '@polymer/paper-input/paper-input.js';
import '@polymer/iron-icon';
import {makeRequest} from "../shared/my-helper";
import {SharedStyles} from '../shared/shared-styles.js';


class MySong extends LitElement {
    static get properties(){
        return {
            id:{
                type: Number
            },
            title: {
                type: String
            },
            artist:{
                type: String
            },
            status: {
                type: String
            },
            liked: {
                type: Boolean
            },
            rightImgSrc: {
                type: String
            },
            thumbnail: {
                type: String
            },
        }
    }

    constructor(){
        super();
        this.liked = false;
        this.renderLeftImgSrc();
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
                    <img src="${this.rightImgSrc}" @click="${(this.status === "DOWNLOADABLE") ? this.addYouTubeVideo : this.changeLikedImgSrc}" class="image" style="margin: auto 0;">
                </div>
            </div>                
            `;
    }
    renderLeftImgSrc(){
        console.log(this.status);
        if(this.status === "DOWNLOADABLE"){
            this.rightImgSrc =  "/images/plus.png";
        }
        else if(this.status === "DOWNLOADING"){
            this.rightImgSrc =  "/images/loading.gif";
        }
        else if(this.status === "AVAILABLE"){
            this.rightImgSrc =  "/images/heartGrey.png";
        }
    }

    changeLikedImgSrc(){
        if (this.liked){
            this.rightImgSrc = "/images/heartGrey.png";
            this.liked = false;
            //send request to Back-End voting minus 1
        }
        else {
            this.rightImgSrc = "/images/heart.png";
            this.liked = true;
            makeRequest("POST", "http://localhost:8085/youtubesearch/api/playlist/addvote")
        }
    }

    async addYouTubeVideo() {
    //    console.log("addYouTubeVideo");
        this.rightImgSrc = "/images/loading.gif";
        let url = "http://localhost:8085/youtubesearch/api/video/dl?id=" + this.id;
        let method = "GET";
        await makeRequest(method, url);
    }
}

window.customElements.define('my-song', MySong);