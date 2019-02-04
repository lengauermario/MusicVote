import {html} from '@polymer/lit-element';
import {PageViewElement} from '../page-view-element.js';
import '../song/my-search.js';
import '../song/my-songpreview.js'
import '../song/my-songcollection.js'
// These are the shared styles needed by this element.
import {SharedStyles} from '../shared/shared-styles.js';

class MyView1 extends PageViewElement {
  render() {
    return html`
      ${SharedStyles}
      <section style="position: sticky; top: 64px; z-index: 5; padding-bottom: 0.2em">
        <my-songpreview></my-songpreview>
                <my-search></my-search>

      </section>
      <section style="padding-top: 0.8em;">
        <my-songcollection></my-songcollection>
      </section>
    `;
  }
}

window.customElements.define('my-view1', MyView1);
