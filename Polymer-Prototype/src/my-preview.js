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
                            <td><a id="title">Here will be the title</a></td>
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


    setTitle(title) {
        console.log(title);
    }
}
window.customElements.define('my-preview', MyPreview);