$( document ).ready(function() {
    $.get(
        "https://www.googleapis.com/youtube/v3/search", {
            part: 'snipppet, id',
            q: q,
            type: 'video',
            key: ''
        }
    )
});