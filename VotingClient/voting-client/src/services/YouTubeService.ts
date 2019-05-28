import Axios from "axios"

let url = 'http://localhost:8080/musicvoting/api/video';

export default{
  async downloadVideo(video: YouTubeSong) {
    let response = await Axios.post(url + '/dl', video);
    return response.data;
  },
  async search(input: string) {
    let response = await Axios.get(url +'?queryTerm='+input)
    return response.data;
  }
}