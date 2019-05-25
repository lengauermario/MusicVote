import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Song } from '../models/song.model';

const httpOption = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class PlaylistService {

  url = 'http://localhost:8080/musicvoting/api/playlist/';

  constructor(private http: HttpClient) { }

  addSong(id: number) {
    return this.http.post(this.url + 'add/song?id=' + id, httpOption);
  }

  removeSong(id: number) {
    return this.http.post(this.url + 'remove/song?id=' + id, httpOption);
  }

  addVote(id: number) {
    return this.http.post(this.url + 'add/vote?id=' + id, httpOption);
  }

  removeVote(id: number) {
    return this.http.post(this.url + 'remove/vote?id=' + id, httpOption);
  }

  getPlaylist() {
    return this.http.get<Song[]>(this.url, httpOption);
  }




}
