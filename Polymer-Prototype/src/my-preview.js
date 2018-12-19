import {LitElement, html} from "@polymer/lit-element";
import '@polymer/paper-input/paper-input.js';
import '@polymer/iron-icon';
import './shared-styles.js';


class MyPreview extends LitElement {

    render() {
        return html`         
         <my-audioplayer></my-audioplayer>
        `;
    }
    constructor(){
        super();
    }
}
window.customElements.define('my-preview', MyPreview);