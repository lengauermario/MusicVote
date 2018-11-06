import {LitElement, html} from "@polymer/lit-element";
import '@polymer/paper-input/paper-input.js';
import '@polymer/paper-button/paper-button.js';
import './shared-styles.js';
import './oauth-authorizator/oauth-authorizator.js';
import './oauth-authorizator/oauth-receiver.js';
import 'https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js';

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
        $.ajax({
            type: 'GET',
            url: 'https://accounts.spotify.com/authorize',
            data: {
                client_id: '911a72e1b7d04465a31d704077a92a86',
                client_secret: '929170c31cbe420fb1840af5241e05b6',
                response_type: "token",
                redirect_uri: 'http://localhost:5500',
                scope: 'user-read-private user-read-email'
            },
            header: {
                'Access-Control-Allow-Origin': "*",
            },
            success: function(value){
                console.log(value);
            },
        });
    }
}

window.customElements.define('my-login-button', MyLoginButton);
