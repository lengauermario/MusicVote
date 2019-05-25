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
export class SongsService {

  songUrl = 'http://localhost:8080/musicvoting/api/song';
  playlistUrl = 'http://localhost:8080/musicvoting/api/playlist';

  constructor(private http: HttpClient) { }

  findAll() {
    return this.http.get<Song[]>(this.songUrl + '/findall', httpOption);
  }

  addToPlaylist(id) {
    return this.http.post<Song>(this.playlistUrl + '/add/song?id=' + id, {}, httpOption);
  }
}
