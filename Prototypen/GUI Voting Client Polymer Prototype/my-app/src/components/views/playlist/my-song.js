import {html, LitElement} from "@polymer/lit-element";
import '@polymer/paper-input/paper-input.js';
import '@polymer/iron-icon';
import {makeRequest} from "../../shared/my-helper";
import {SharedStyles} from '../../shared/shared-styles.js';


class MySong extends LitElement {
    constructor() {
        super();
        this.liked = false;
        this.updated(null);
        this.IconSrc = this.setIconSrcPath();

    }

    static get properties() {
        return {
            id: {
                type: String
            },
            votes: {
                type: Number
            },
            title: {
                type: String
            },
            artist: {
                type: String
            },
            thumbnail: {
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

    firstUpdated(_changedProperties) {

    }

    updated(changedProperties) {
        if (this.thumbnail === "default") {
            this.thumbnail = "/images/covers/default.png";
        }
        console.log(changedProperties)
    }

    render() {
        return html`
            ${SharedStyles}
            <div class="columns is-mobile" style="border-bottom: 1px solid black; margin-bottom: 0.7em">
                <div class="column is-4" style="margin: auto auto;">
                    <h4 class="title is-4" style="margin: auto 0;">${this.votes} Votes</h4>
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

    setIconSrcPath() {
        return "/images/heartGrey.png";
    }

    changeLikedImgSrc() {
        if (this.liked) {
            this.IconSrc = "/images/heartGrey.png";
            this.liked = false;
            makeRequest("POST", "http://localhost:8080/musicvoting/api/playlist/remove/vote?id=" + this.id)
        } else {
            this.IconSrc = "/images/heart.png";
            this.liked = true;
            console.log(this.id);
            makeRequest("POST", "http://localhost:8080/musicvoting/api/playlist/add/vote?id=" + this.id)
        }
    }
}

window.customElements.define('my-song', MySong);