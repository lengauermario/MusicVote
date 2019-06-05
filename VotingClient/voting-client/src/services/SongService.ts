import Axios from "axios"


let songUrl = process.env.VUE_APP_API_URL + '/song/';
let playlistUrl = process.env.VUE_APP_API_URL + '/playlist/'
export default{

  async getAll() {
    let response = await Axios.get(songUrl +'find');
    return response.data;
  },
  async addSong(id: number){
    let response = await Axios.post(playlistUrl + 'add/song?id=' + id)
    return response;
  }
}