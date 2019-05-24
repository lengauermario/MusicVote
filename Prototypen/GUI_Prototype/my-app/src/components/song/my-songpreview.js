import {html, LitElement} from "@polymer/lit-element";
import '@polymer/paper-input/paper-input.js';
import '@polymer/iron-icon';
import '@polymer/iron-image/iron-image.js'
import {SharedStyles} from '../shared/shared-styles.js';


class MySongPreview extends LitElement {
    static get properties() {
        return {
            id: {
                type: Number
            },
            thumbnail: {
                type: String
            },
            title: {
                type: String
            },
            artist: {
                type: String
            }

        }
    }

    constructor() {
        super();
        this.title = "Dreamer";
        this.artist = "Sunrise Avenue";
        this.thumbnail = "/images/covers/dreamer_sunriseavenue.jpg";
    }

    render() {
        return html`
            ${SharedStyles}
            
            <div class="columns is-mobile">
                <div class="column">
                    <iron-image sizing="contain" preload src="${this.thumbnail}" class="image is-square" style="border-radius: 0.2em; z-index: 0"></iron-image>
                </div>
                <div class="column" style="position: relative; ">
                    <div style="position: absolute; top: 50%; transform: translateY(-50%);">
                        <h3 class="title is-4" style="margin-bottom: 0">${this.title}</h3>
                        <h4 class="title is-6"style="margin: 0 0 15px 0">${this.artist}</h4>
                    </div>
                </div>
            </div>
            `;
    }
}

window.customElements.define('my-songpreview', MySongPreview);