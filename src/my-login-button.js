import {LitElement, html} from "@polymer/lit-element";
import '@polymer/paper-input/paper-input.js';
import '@polymer/paper-button/paper-button.js';
import './shared-styles.js';

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
        //implement your login you need
    }
}

window.customElements.define('my-login-button', MyLoginButton);
