import Axios from "axios"


let songUrl = 'http://localhost:8080/musicvoting/api/song/';
let playlistUrl = 'http://localhost:8080/musicvoting/api/playlist/'
export default{

  async getAll() {
    let response = await Axios.get(songUrl+'findall');
    return response.data;
  },
  async addSong(id: number){
    let response = await Axios.post(playlistUrl + 'add/song?id=' + id)
    return response;
  }
}