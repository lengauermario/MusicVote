import {LitElement, html} from "@polymer/lit-element";
import '@polymer/paper-input/paper-input.js';
import '@polymer/iron-icon';
import './shared-styles.js';


class MySearch extends LitElement {
    render() {
        return html`
         <paper-input label="Suche..." id="search"></paper-input>
        `;
    }
}

window.customElements.define('my-search', MySearch);
