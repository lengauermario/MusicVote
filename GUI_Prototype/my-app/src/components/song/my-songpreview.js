import {LitElement, html} from "@polymer/lit-element";
import '@polymer/paper-input/paper-input.js';
import '@polymer/iron-icon';
import '@polymer/iron-image/iron-image.js'
import { SharedStyles } from '../shared/shared-styles.js';


class MySongPreview extends LitElement {
    render() {
        return html`
            ${SharedStyles}
            
            <div class="columns is-mobile">
                <div class="column">
                    <iron-image sizing="contain" preload src="/images/covers/dreamer_sunriseavenue.jpg" class="image is-square" style="border-radius: 0.2em; z-index: 0"></iron-image>
                </div>
                <div class="column" style="position: relative; ">
                    <div style="position: absolute; top: 50%; transform: translateY(-50%);">
                        <h3 class="title is-4" style="margin-bottom: 0">Dreamer</h3>
                        <h4 class="title is-6"style="margin: 0 0 15px 0">Sunrise Avenue</h4>
                        <progress class="progress is-small" value="20" max="100" style="margin-bottom: 0.2em;"></progress>
                        <p class="label">0:23</p>
                    </div>
                </div>
            </div>
            `;
    }
}

window.customElements.define('my-songpreview', MySongPreview);