import {LitElement, html} from "@polymer/lit-element";
import '@polymer/paper-input/paper-input.js';
import '@polymer/paper-button/paper-button.js';
import './shared-styles.js';
import './my-preview';


class MyLoginButton extends LitElement {
    render() {
        return html``;
    }

    constructor() {
        super();
    }

    start() {
        console.log("clicked Start buttton");
        var path = "";

    }
    handleLogout(){
        console.log("clicked logout buttton");
        const event = new CustomEvent('logout', { bubbles: true, composed: true });
        this.dispatchEvent(event);
    }
}

window.customElements.define('my-login-button', MyLoginButton);