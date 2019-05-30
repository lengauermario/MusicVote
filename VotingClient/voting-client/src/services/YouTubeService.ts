import Axios from "axios"

let url = process.env.VUE_APP_API_URL + '/video';

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