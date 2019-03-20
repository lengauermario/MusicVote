import {html} from '@polymer/lit-element';
import {PageViewElement} from '../page-view-element.js';
// These are the shared styles needed by this element.
import {SharedStyles} from '../shared/shared-styles.js';

class MyView404 extends PageViewElement {
  render() {
    return html`
      ${SharedStyles}
      <section>
        <h2>Oops! You hit a 404</h2>
        <p>Die Seite nach der du Suchst scheint nicht zu existieren. Gehe zurück
           <a href="/">home</a> und probiere es nocheinmal.
        </p>
      </section>
    `
  }
}

window.customElements.define('my-view404', MyView404);
