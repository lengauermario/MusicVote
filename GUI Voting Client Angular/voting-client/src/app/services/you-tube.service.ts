import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { YouTubeVideo } from '../models/you-tube-video.model';

const httpOption = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})


export class YouTubeService {

  url = 'http://localhost:8080/musicvoting/api/video/';

  constructor(private http: HttpClient) { }

  downloadVideo(video: YouTubeVideo) {
    return this.http.put<YouTubeVideo>(this.url, video, httpOption);
  }
}
