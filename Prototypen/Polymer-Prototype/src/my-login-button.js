import {LitElement, html} from "@polymer/lit-element";
import '@polymer/paper-input/paper-input.js';
import '@polymer/paper-button/paper-button.js';
import './shared-styles.js';
import './my-preview';


class MyLoginButton extends LitElement {
    render() {
        return html`
          <style>
            paper-button{
                line-height: 80%;
            }
           </style>
         <paper-button raised  @click="${this.handleLogin}" id="login">Login</paper-button>
         <paper-button raised  @click="${this.handleLogout}" id="logout">Logout</paper-button>
        `;
    }

    constructor() {
        super();
    }

    handleLogin() {
        console.log("clicked login buttton");
        const event = new CustomEvent('login', { bubbles: true, composed: true });
        this.dispatchEvent(event);
    }
    handleLogout(){
        console.log("clicked logout buttton");
        const event = new CustomEvent('logout', { bubbles: true, composed: true });
        this.dispatchEvent(event);
    }
}

window.customElements.define('my-login-button', MyLoginButton);