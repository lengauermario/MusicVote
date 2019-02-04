import {LitElement, html} from "@polymer/lit-element";
import '@polymer/paper-input/paper-input.js';
import '@polymer/iron-icon';
import './my-song.js'
import { SharedStyles } from '../shared/shared-styles.js';


class MySongCollection extends LitElement {
    render() {
        return html`
            ${SharedStyles}
            <div>
                <my-song></my-song>
                <my-song></my-song>
                <my-song></my-song>
                <my-song></my-song>
                <my-song></my-song>
                <my-song></my-song>
                <my-song></my-song>
                <my-song></my-song>
            </div>
            `;
    }

   /* addElement(){
        var song = document.createElement("my-song");
        this.$.container.appendChild(song);
    }*/
}

window.customElements.define('my-songcollection', MySongCollection);