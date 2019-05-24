var CLIENT_ID = '909305710711-tbdnidgovo2pgfqus20fuoodfc9vsphj.apps.googleusercontent.com';

        // Array of API discovery doc URLs for APIs used by the quickstart
        var DISCOVERY_DOCS = ["https://www.googleapis.com/discovery/v1/apis/youtube/v3/rest"];

        // Authorization scopes required by the API. If using multiple scopes,
        // separated them with spaces.
        var SCOPES = 'https://www.googleapis.com/auth/youtube.readonly';

        var authorizeButton = document.getElementById('authorize-button');
        var signoutButton = document.getElementById('signout-button');

        /**
         *  On load, called to load the auth2 library and API client library.
         */
        function handleClientLoad() {
        gapi.load('client:auth2', initClient);
        }

        /**
         *  Initializes the API client library and sets up sign-in state
         *  listeners.
         */
        function initClient() {
        gapi.client.init({
            discoveryDocs: DISCOVERY_DOCS,
            clientId: CLIENT_ID,
            scope: SCOPES
        }).then(function () {
            // Listen for sign-in state changes.
            gapi.auth2.getAuthInstance().isSignedIn.listen(updateSigninStatus);

            // Handle the initial sign-in state.
            updateSigninStatus(gapi.auth2.getAuthInstance().isSignedIn.get());
            authorizeButton.onclick = handleAuthClick;
            signoutButton.onclick = handleSignoutClick;
        });
        }

        /**
         *  Called when the signed in status changes, to update the UI
         *  appropriately. After a sign-in, the API is called.
         */
        function updateSigninStatus(isSignedIn) {
        if (isSignedIn) {
            authorizeButton.style.display = 'none';
            signoutButton.style.display = 'block';
            getFirstVideo();
        } else {
            authorizeButton.style.display = 'block';
            signoutButton.style.display = 'none';
        }
        }

        /**
         *  Sign in the user upon button click.
         */
        function handleAuthClick(event) {
        gapi.auth2.getAuthInstance().signIn();
        }

        /**
         *  Sign out the user upon button click.
         */
        function handleSignoutClick(event) {
        gapi.auth2.getAuthInstance().signOut();
        }

        function createResource(properties) {
            var resource = {};
            var normalizedProps = properties;
            for (var p in properties) {
            var value = properties[p];
            if (p && p.substr(-2, 2) == '[]') {
                var adjustedName = p.replace('[]', '');
                if (value) {
                normalizedProps[adjustedName] = value.split(',');
                }
                delete normalizedProps[p];
            }
            }
            for (var p in normalizedProps) {
            // Leave properties that don't have values out of inserted resource.
            if (normalizedProps.hasOwnProperty(p) && normalizedProps[p]) {
                var propArray = p.split('.');
                var ref = resource;
                for (var pa = 0; pa < propArray.length; pa++) {
                var key = propArray[pa];
                if (pa == propArray.length - 1) {
                    ref[key] = normalizedProps[p];
                } else {
                    ref = ref[key] = ref[key] || {};
                }
                }
            };
            }
            return resource;
        }

        function removeEmptyParams(params) {
            for (var p in params) {
            if (!params[p] || params[p] == 'undefined') {
                delete params[p];
            }
            }
            return params;
        }

        function executeRequest(request) {
            request.execute(function(response) {
                console.log(response);
                var video = response.items[0];
                showVideo(video);
            });
        }

        function buildApiRequest(requestMethod, path, params, properties) {
            params = removeEmptyParams(params);
            var request;
            if (properties) {
            var resource = createResource(properties);
            request = gapi.client.request({
                'body': resource,
                'method': requestMethod,
                'path': path,
                'params': params
            });
            } else {
            request = gapi.client.request({
                'method': requestMethod,
                'path': path,
                'params': params
            });
            }
            executeRequest(request);
        }

        /**
         * Append text to a pre element in the body, adding the given message
         * to a text node in that element. Used to display info from API response.
         *
         * @param {string} message Text to be placed in pre element.
         */
        function showVideo(video) {
            var title = video.snippet.title;
            var channel = video.snippet.channelTitle;
            var thumbnailsrc = video.snippet.thumbnails.default.url;
            document.getElementById("title").text = title;
            document.getElementById("channel").text = channel;
            document.getElementById("thumbnail").src = thumbnailsrc;
        }

        function getFirstVideo() {
        buildApiRequest('GET',
                '/youtube/v3/search',
                {'maxResults': '25',
                 'part': 'snippet',
                 'q': 'raf camora beste leben',
                 'type': ''});   
                        
        }