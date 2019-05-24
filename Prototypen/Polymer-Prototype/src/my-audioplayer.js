import {LitElement, html} from "@polymer/lit-element";
import '@polymer/paper-input/paper-input.js';
import '@polymer/paper-button/paper-button.js';
import './shared-styles.js';
import './my-preview';


class MyAudioPlayer extends LitElement {
    render() {
        return html`
        <table>
            <tr>
                <td><p id="title">Title: ${this.name}</p></td>
            </tr>
            <tr>
                <td><paper-button raised  @click="${this.next}" id="start">Nächstes Lied</paper-button></td>
                <td><paper-button raised  @click="${this.stop}" id="stop">Stop</paper-button></td>
            </tr>
        </table>
        `;
    }

    constructor() {
        super();
        this.name = "Hier würde der Title stehen";
        this.path = "/MP3/Roadin.mp3";
        this.audio = new Audio();
    }
    static get properties() {
        return {
            name: { type: String},
            path: { type: String},
            audio: {type: Audio}
        };
    }

    next() {
        console.log("clicked Start buttton");

        var path = fetch('//localhost:8080/musicvoting/api/song/getHighestVote', {
            headers: new Headers({
                'Content-Type': 'application/json',
            })
        }).then(res => res.json())
            .then(function (response) {
                var path = response.path;
                path = "/MP3/Roadin.mp3";
               // this.path = path;
                console.log(path);
                var audio = new Audio(path);
                audio.play();

            });

        this.name = "Roadin";
    }

    stop(){
        console.log("audio stop");
        audio.pause();
    }
}

window.customElements.define('my-audioplayer', MyAudioPlayer);