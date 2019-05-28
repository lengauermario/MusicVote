import Axios from "axios"
let url = 'http://localhost:8080/musicvoting/api/playlist/';
export default{

  async getAll() {
    let response = await Axios.get(url);
    return response.data;
  },
  async addVote(id: number) {
    let response = await Axios.post(url+"add/vote?id="+id);
    return response.data;
  },
  async removeVote(id: number) {
    let response = await Axios.post(url+"remove/vote?id="+id);
    return response.data;
  }
}