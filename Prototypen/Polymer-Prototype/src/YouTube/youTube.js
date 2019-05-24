import {LitElement, html} from "@polymer/lit-element";
import GooglePlayerApi from 'https://www.youtube.com/player_api';
import GoogleApi from 'https://apis.google.com/js/api.js';

export default class YouTube extends HTMLElement{
    constructor(){
        super();
        gapi.load('client:auth2', {
            callback: function () {
                YouTube.initClient();
            },
            onerror: function() {
                console.log('gapi.client failed to load!');
            },
            timeout: 5000, // 5 seconds.
            ontimeout: function() {
                alert('gapi.client could not load in a timely manner!');
            }
        });

    }
    connectedCallback(){
        document.addEventListener('login', e => this.login(e));
        document.addEventListener('logout', e => this.logout(e));
        document.addEventListener('startPlay', e => this.logout(e));
    }

    login(e){
        gapi.auth2.getAuthInstance().signIn().then(function () {
            console.log('User signed in.');
            const event = new CustomEvent('startPlay', { bubbles: true, composed: true });
         //   this.dispatchEvent(event);
        });

    }

    logout(e){
        gapi.auth2.getAuthInstance().signOut().then(function () {
            console.log('User signed out.');
        });
    }

    static updateSigninStatus(isSignedIn){
        console.log("updated Signin Status "+isSignedIn);
    }

    static initClient() {
        gapi.client.init({
            discoveryDocs: ["https://www.googleapis.com/discovery/v1/apis/youtube/v3/rest"],
            clientId: '909305710711-tbdnidgovo2pgfqus20fuoodfc9vsphj.apps.googleusercontent.com',
            scope: 'https://www.googleapis.com/auth/youtube.readonly'
        }).then(function () {
            console.log("initialize Client")
        });
    }

}

customElements.define('my-youtube', YouTube);