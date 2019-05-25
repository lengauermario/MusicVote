import {LitElement} from '@polymer/lit-element';

export class PageViewElement extends LitElement {
    static get properties() {
        return {
            active: {type: Boolean}
        }
    }

    // Only render this page if it's actually visible.
    shouldUpdate() {
        return this.active;
    }
}
