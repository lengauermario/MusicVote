import { html } from '@polymer/lit-element';
import { PageViewElement } from './page-view-element.js';
import './my-search.js';
import './my-songpreview.js'
import './my-songcollection.js'


// These are the shared styles needed by this element.
import { SharedStyles } from './shared-styles.js';

class MyView1 extends PageViewElement {
  render() {
    return html`
      ${SharedStyles}
      <section>
        <table>
        
        </table>
        <my-songpreview></my-songpreview>
      </section>
      <section>
        <my-search></my-search>
        <my-songcollection></my-songcollection>
      </section>
    `;
  }
}

window.customElements.define('my-view1', MyView1);
