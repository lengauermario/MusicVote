/* function streamSong(){
    var foo = document.getElementById('player');
    foo.play();
}

function stopPlayer(){
    var foo = document.getElementById('player');
    foo.pause();
}

function onTimeChanged(){
    progressbar = document.getElementById('progressbar');
    var player = document.getElementById('player');
    //console.log(progressbar.getAttribute('aria-valuenow'));
    //progressbar.setAttribute('aria-valuenow', player.currentTime / player.duration);
    var val = player.currentTime / player.duration;
    var increment = 10/player.duration;
    var percent = Math.min(increment * player.currentTime * 10, 100);
    console.log(val);
    progressbar.value= val;
    //$('.progress-bar').css('width', percent+'%').attr('aria-valuenow', percent);  
}
 */
function init() {

    var audio = document.getElementById('audio');
    audio.style.display = "block";
    audio.crossOrigin = "anonymous";
    var context = new AudioContext();
    var src = context.createMediaElementSource(audio);
    var analyser = context.createAnalyser();

    var canvas = document.getElementById("canvas");
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
    var ctx = canvas.getContext("2d");

    src.connect(analyser);
    analyser.connect(context.destination);

    analyser.fftSize = 256;

    var bufferLength = analyser.frequencyBinCount;
    console.log(bufferLength);

    var dataArray = new Uint8Array(bufferLength);

    var WIDTH = canvas.width;
    var HEIGHT = canvas.height;

    var barWidth = (WIDTH / bufferLength) * 2.5;
    var barHeight;
    var x = 0;

    function renderFrame() {
        requestAnimationFrame(renderFrame);

        x = 0;

        analyser.getByteFrequencyData(dataArray);

        ctx.fillStyle = "#ffffff";
        ctx.fillRect(0, 0, WIDTH, HEIGHT);

        for (var i = 0; i < bufferLength; i++) {
            barHeight = dataArray[i] * 1.4;

            /* var r = barHeight + (25 * (i/bufferLength));
            var g = 250 * (i/bufferLength);
            var b = 50;
     */
            var r = 10;
            var g = ((dataArray[i] / 1.4) + (i / bufferLength));
            var b = 250;
            ctx.fillStyle = "rgb(" + r + "," + g + "," + b + ")";
            ctx.fillRect(x, HEIGHT - barHeight, barWidth, barHeight);

            x += barWidth + 1;
        }
    }

    audio.play();
    renderFrame();

}

function play(id) {
    //var id = document.getElementById("id_input").value;
    var audio = document.getElementById('audio');
    //src="http://localhost:8085/musicvoting/api/song/getmp3?id=138"
    audio.src = "http://localhost:8085/musicvoting/api/song/getmp3?id=" + id;
    init();
}


function playNextSong() {
    fetch("http://localhost:8085/musicvoting/api/playlist/pop", {
        method: 'GET',
        credentials: 'include',
        headers: {
            "Content-Type": "text/plain"
        }
    })
        .then(async function (response) {
            let tmp = await response.json();
            play(tmp.id);
        });
}

/*function nextSong(){
    fetch("http://localhost:8085/musicvoting/api/song/playlist/pop");
    playNextSong();
}*/