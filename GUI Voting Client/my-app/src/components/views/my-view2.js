import {html} from '@polymer/lit-element';
import {PageViewElement} from '../page-view-element.js';
import './add/my-search.js';
import './add/my-AddSongCollection.js'
// These are the shared styles needed by this element.
import {SharedStyles} from '../shared/shared-styles.js';

class MyView2 extends PageViewElement {
    render() {
        return html`
      ${SharedStyles}
      <section class="background" style="position: sticky; top: 64px; z-index: 5; padding-top: 0.6em; padding-bottom: 0.3em;">
        <my-search></my-search>
      </section>
      <section style="padding-top: 0.8em;">
        <my-addsongcollection ytSearchUrl="http://localhost:8080/musicvoting/api/video?queryTerm=" songUrl="http://localhost:8080/musicvoting/api/song/findall"></my-addsongcollection>
      </section>
    `;
    }
}

window.customElements.define('my-view2', MyView2);
