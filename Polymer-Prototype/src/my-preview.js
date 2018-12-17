import {LitElement, html} from "@polymer/lit-element";
import '@polymer/paper-input/paper-input.js';
import '@polymer/iron-icon';
import './shared-styles.js';


class MyPreview extends LitElement {

    render() {
        return html`         
            <table>
            <tr>
                <td><img id="thumbnail" style="object-fit: cover"></td>
                <td>
                    <table>
                        <tr>
                            <td><a id="title" name="[[title]]">as</a></td>
                        </tr>
                        <tr>
                            <td><a id="channel">Here will be the channel</a></td>
                        </tr>
                    </table>                
                </td>
            </tr>
            </table>
        `;
    }
    constructor(){
        super();
        this.setTitle("Hey")
    }

    setTitle(title) {
        this.title = title;
    }

    static get properties () {
        return {
            title: {
                type: String
            }
        };
    }
}
window.customElements.define('my-preview', MyPreview);