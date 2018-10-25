import {LitElement} from '@polymer/lit-element'

class MyApp extends LitElement{
    constructor(){
        super();
    }
    static get properties(){
        return{score: Number}
    }
    static get template(){
        return 'asdf'
    }
    _render(){
        return html`The score is ${score}`;
    }
}