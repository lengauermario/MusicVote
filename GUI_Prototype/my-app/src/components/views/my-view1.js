import {html} from '@polymer/lit-element';
import {PageViewElement} from '../page-view-element.js';
import './playlist/my-songpreview.js'
import './playlist/my-songcollection.js'
// These are the shared styles needed by this element.
import {SharedStyles} from '../shared/shared-styles.js';

class MyView1 extends PageViewElement {
  render() {
    return html`
      ${SharedStyles}
      <section style="position: sticky; top: 64px; z-index: 5; padding-bottom: 0.5em">
        <my-songpreview></my-songpreview>
      </section>
      <section class="background" style="padding-top: 0.8em;">
        <my-songcollection songUrl="http://localhost:8080/musicvoting/api/playlist/"></my-songcollection>
      </section>
    `;
  }
}

window.customElements.define('my-view1', MyView1);
