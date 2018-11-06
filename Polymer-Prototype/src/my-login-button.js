import {LitElement, html} from "@polymer/lit-element";
import '@polymer/paper-input/paper-input.js';
import '@polymer/paper-button/paper-button.js';
import './shared-styles.js';
import './my-preview';

class MyLoginButton extends LitElement {
    render() {
        return html`
          <style>
            #login{
                line-height: 80%;
            }
           </style>
         <paper-button raised  @click="${this.handleClick}" id="login">Login</paper-button>
        `;
    }

    handleClick() {
        console.log("clicked login buttton");
    }
}

window.customElements.define('my-login-button', MyLoginButton);
