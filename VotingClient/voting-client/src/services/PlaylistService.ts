import Axios from "axios"
let url = 'http://localhost:8080/musicvoting/api/playlist/';
export default{

  async getAll() {
    let response = await Axios.get(url);
    return response.data;
  }
  
}