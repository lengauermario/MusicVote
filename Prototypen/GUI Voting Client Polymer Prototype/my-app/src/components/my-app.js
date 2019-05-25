import {html, LitElement} from '@polymer/lit-element';
import {setPassiveTouchGestures} from '@polymer/polymer/lib/utils/settings.js';
import {connect} from 'pwa-helpers/connect-mixin.js';
import {installMediaQueryWatcher} from 'pwa-helpers/media-query.js';
import {installOfflineWatcher} from 'pwa-helpers/network.js';
import {installRouter} from 'pwa-helpers/router.js';
import {updateMetadata} from 'pwa-helpers/metadata.js';
import {SharedStyles} from './shared/shared-styles.js';
// This element is connected to the Redux store.
import {store} from '../store.js';
// These are the actions needed by this element.
import {navigate, updateDrawerState, updateOffline} from '../actions/app.js';
// These are the elements needed by this element.
import '@polymer/app-layout/app-drawer/app-drawer.js';
import '@polymer/app-layout/app-header/app-header.js';
import '@polymer/app-layout/app-scroll-effects/effects/waterfall.js';
import '@polymer/app-layout/app-toolbar/app-toolbar.js';
import '@polymer/app-layout/app-scroll-effects/effects/fade-background.js'

class MyApp extends connect(store)(LitElement) {
    constructor() {
        super();
        // To force all event listeners for gestures to be passive.
        // See https://www.polymer-project.org/3.0/docs/devguide/settings#setting-passive-touch-gestures

        const evtSource = new EventSource('http://localhost:8080/musicvoting/api/playlist/connect');
        evtSource.onmessage = function (event) {
            console.log(event);
        };
        evtSource.addEventListener('add_song', (event) => {
            console.log('got a new song');
            console.log(JSON.parse(event.data));

            let myEvent = new CustomEvent('my_event_addsong', {
                detail: {id: JSON.parse(event.data).id},
                bubbles: true,
                composed: true
            });
            this.dispatchEvent(myEvent);
        });

        evtSource.addEventListener('add_vote', (event) => {
            console.log('got a new vote');
            console.log(JSON.parse(event.data));

            let id = JSON.parse(event.data).id;
            let myEvent = new CustomEvent('my_event_addvote', {
                detail: {id: id},
                bubbles: true,
                composed: true
            });
            this.dispatchEvent(myEvent);
        });

        evtSource.addEventListener('remove_song', (event) => {
            console.log('removing a song');
            console.log(JSON.parse(event.data));

            let id = JSON.parse(event.data).id;
            let myEvent = new CustomEvent('my_event_removesong', {
                detail: {id: id},
                bubbles: true,
                composed: true
            });
            this.dispatchEvent(myEvent);
        });

        evtSource.addEventListener('remove_vote', (event) => {
            console.log('removing a vote');
            console.log(JSON.parse(event.data));

            let id = JSON.parse(event.data).id;
            let myEvent = new CustomEvent('my_event_removevote', {
                detail: {id: id},
                bubbles: true,
                composed: true
            });
            this.dispatchEvent(myEvent);
        });

        evtSource.addEventListener('video_download', (event) => {
            console.log('new youtube video ' + JSON.parse(event.data).status);
            console.log(JSON.parse(event.data));

            let myEvent = new CustomEvent('my_event_videodownload', {
                detail: {id: JSON.parse(event.data).id},
                bubbles: true,
                composed: true
            });
            this.dispatchEvent(myEvent);
        });

        evtSource.addEventListener('song_started', (event) => {
            console.log('next song started');
            console.log(JSON.parse(event.data));

            let myEvent = new CustomEvent('my_event_songstarted', {
                detail: {id: JSON.parse(event.data).id},
                bubbles: true,
                composed: true
            });
            this.dispatchEvent(myEvent);
        })
    }

    static get properties() {
        return {
            appTitle: {type: String},
            rell: {type: String},
            _page: {type: String},
            _drawerOpened: {type: Boolean},
            _snackbarOpened: {type: Boolean},
            _offline: {type: Boolean}
        }
    }

    render() {
        // Anything that's related to rendering should be done in here.
        return html`
    <style>
      :host {
        --app-drawer-width: 256px;
        display: block;

        --app-primary-color: #E91E63;
        --app-secondary-color: #293237;
        --app-dark-text-color: var(--app-secondary-color);
        --app-light-text-color: white;
        --app-section-even-color: #f7f7f7;
        --app-section-odd-color: white;

        --app-header-background-color: white;
        --app-header-text-color: var(--app-dark-text-color);
        --app-header-selected-color: var(--app-primary-color);

        --app-drawer-background-color: var(--app-secondary-color);
        --app-drawer-text-color: var(--app-light-text-color);
        --app-drawer-selected-color: #78909C;
      }

      app-header {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        text-align: center;
        background-color: var(--app-header-background-color);
        color: var(--app-header-text-color);
        border-bottom: 1px solid #eee;
      }

      .toolbar-top {
        background-color: var(--app-header-background-color);
      }

      [main-title] {
        font-family: 'Pacifico';
        text-transform: lowercase;
        font-size: 30px;
        /* In the narrow layout, the toolbar is offset by the width of the
        drawer button, and the text looks not centered. Add a padding to
        match that button */
        padding-right: 44px;
      }

      .toolbar-list {
        display: none;
      }

      .toolbar-list > a {
        display: inline-block;
        color: var(--app-header-text-color);
        text-decoration: none;
        line-height: 30px;
        padding: 4px 24px;
      }

      .toolbar-list > a[selected] {
        color: var(--app-header-selected-color);
        border-bottom: 4px solid var(--app-header-selected-color);
      }

      .menu-btn {
        background: none;
        border: none;
        fill: var(--app-header-text-color);
        cursor: pointer;
        height: 44px;
        width: 44px;
      }

      .drawer-list {
        box-sizing: border-box;
        width: 100%;
        height: 100%;
        padding: 24px;
        background: var(--app-drawer-background-color);
        position: relative;
      }

      .drawer-list > a {
        display: block;
        text-decoration: none;
        color: var(--app-drawer-text-color);
        line-height: 40px;
        padding: 0 24px;
      }

      .drawer-list > a[selected] {
        color: var(--app-drawer-selected-color);
      }

      /* Workaround for IE11 displaying <main> as inline */
      main {
        display: block;
      }

      .main-content {
        padding-top: 64px;
        min-height: calc(100vh - 64px - 104px);
      }

      .page {
        display: none;
      }

      .page[active] {
        display: block;
      }

      footer {
        padding: 24px;
        background: var(--app-drawer-background-color);
        color: var(--app-drawer-text-color);
        text-align: center;
      }

      /* Wide layout: when the viewport width is bigger than 460px, layout
      changes to a wide layout. */
      @media (min-width: 460px) {
        .toolbar-list {
          display: block;
        }

        .menu-btn {
          display: none;
        }

        .main-content {
          padding-top: 100px;
          min-height: calc(100vh - 212px);
        }

        /* The drawer button isn't shown in the wide layout, so we don't
        need to offset the title */
        [main-title] {
          padding-right: 0px;
        }
      }
    </style>

    <!-- Header -->
    <app-header fixed slot="header" style=" z-index:5; padding: 0; top: 0;">
      ${SharedStyles}
      <app-toolbar class="toolbar-top" style="padding: 0; height: 80px; margin 0">
      <div class="tabs is-fullwidth" style="width: 100%; position: sticky; z-index: 5">
        <ul>
          <li style="width: 50%" class="${(this._page === 'view1') ? 'is-active' : ''}">
            <a href="/view1"> Playlist  </a>
          </li>
          <li style="width: 50%" class="${(this._page === 'view2') ? 'is-active' : ''}">
            <a  href="/view2">Add</a>
          </li>
        </ul>
      </div>
        
      </app-toolbar>
    </app-header>

    <!-- Main content -->
    <main role="main" class="main-content">
      <my-view1 class="page" ?active="${this._page === 'view1'}"></my-view1>
      <my-view2 class="page" ?active="${this._page === 'view2'}"></my-view2>
      <my-view404 class="page" ?active="${this._page === 'view404'}"></my-view404>
    </main>

    <footer>
      <p> Your Music-Voting team &hearts;</p>
    </footer>
    `;
    }

    firstUpdated() {
        //this.reel = document.querySelector('.main-content');
        installRouter((location) => store.dispatch(navigate(decodeURIComponent(location.pathname))));
        installOfflineWatcher((offline) => store.dispatch(updateOffline(offline)));
        installMediaQueryWatcher(`(min-width: 460px)`,
            () => store.dispatch(updateDrawerState(false)));
    }

    updated(changedProps) {
        //  this.reel = document.querySelector('.main-content');
        if (changedProps.has('_page')) {
            const pageTitle = this.appTitle + ' - ' + this._page;
            updateMetadata({
                title: pageTitle,
                description: pageTitle
                // This object also takes an image property, that points to an img src.
            });
        }
    }

    slideLeft() {
        this.reel = document.querySelector('.main-content');
        if (this.reel != null) {
            this.reel.style.transform = "translateX(0%)";
        }

    }

    slideRight() {
        this.reel = document.querySelector('.main-content');
        if (this.reel != null) {
            this.reel.style.transform = "translateX(-50%)";

        }
    }

    stateChanged(state) {
        this._page = state.app.page;
    }
}

window.customElements.define('my-app', MyApp);