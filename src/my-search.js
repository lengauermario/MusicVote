import { PolymerElement, html } from '@polymer/polymer/polymer-element.js';
import './shared-styles.js';

class MyView1 extends PolymerElement {
    static get template() {
        return html`
      <style include="shared-styles">
        :host {
          display: block;

          padding: 10px;
        }
      </style>

      <paper-search-input id="basic"></paper-search-input>
<script>
  var seedElement = document.querySelector('paper-search-input#basic');
  document.querySelector('#execute').addEventListener('click', function() {
    seedElement.executeSearch();
  });
  document.querySelector('#clear').addEventListener('click', function() {
    seedElement.clear();
  });
  seedElement.addEventListener("paper-search-input-execute", function(e) {
    alert('You searched for: ' + e.detail.value);
  });
</script>
    `;
    }
}

window.customElements.define('my-view1', MyView1);
