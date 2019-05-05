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

  url = 'http://localhost:8080/musicvoting/api/song/';

  constructor(private http: HttpClient) { }

  findAll() {
    return this.http.get<Song[]>(this.url + 'findall', httpOption);
  }
}
