import { Component, OnInit } from '@angular/core';
import { Song } from 'src/app/models/song.model';
import { SongsService } from 'src/app/services/songs.service';
import { YouTubeService } from 'src/app/services/you-tube.service';
import { YouTubeVideo } from 'src/app/models/you-tube-video.model';

@Component({
  selector: 'app-adding-collection',
  templateUrl: './collection.component.html',
  styleUrls: ['./collection.component.css']
})
export class CollectionComponent implements OnInit {

  list: YouTubeVideo[];
  iconPaths = [
    'assets/images/heartGrey.png',
    'assets/images/heart.png',
    'assets/images/error.png',
    'assets/images/loading.gif',
    'assets/images/plus.png'
  ];
  actualIconPath: string;

  youTubePaths = [
    'assets/images/youTube.png',
    'assets/images/youTubeGrey.png'
  ];

  actualYouTubePath: string;

  filterSong = '';

  constructor(private songsService: SongsService, private youTubeService: YouTubeService) {
  }

  ngOnInit() {
    this.actualIconPath = this.iconPaths[4];
    this.actualYouTubePath = this.youTubePaths[1];
    this.songsService.findAll().subscribe((data) => {
      console.log(data);
      this.list = this.mapSongToYouTubeSong(data);
    });
  }

  setSearchOnYouTube() {
    if (this.actualYouTubePath.localeCompare(this.youTubePaths[0]) === 0) { // YouTubeSearch is On
      this.actualYouTubePath = this.youTubePaths[1];
      this.songsService.findAll().subscribe(data  => {
        this.list = this.mapSongToYouTubeSong(data);
      });
      return false;
     } else {
       this.actualYouTubePath = this.youTubePaths[0];
       this.searchInputChanged(null);
       return true;
     }
  }
  isSearchOnYouTubeOn() {
    if (this.actualYouTubePath.localeCompare(this.youTubePaths[0]) === 0) {
      return true;
     } else {
       return false;
     }
  }

  searchInputChanged(event: Event) {
    if (this.isSearchOnYouTubeOn()) {
      this.youTubeService.search(this.filterSong).subscribe((data) => {
        console.log(data);
        this.list = data;
        this.list.forEach(x => x.iconPath = this.getIconPath(x));
      });
    }
  }

  addVideo(item: YouTubeVideo) {
    item.status = 'DOWNLOADING';
    item.iconPath = this.getIconPath(item);
    if (this.isSearchOnYouTubeOn()) {
      this.youTubeService.downloadVideo(item).subscribe((data) => {
        console.log('Download start');
      });
    } else {
      item.iconPath = this.iconPaths[3];
      this.songsService.addToPlaylist(item.videoId).subscribe((data) => {
        item.iconPath = this.iconPaths[0];
        console.log('added Song to playlist');
      });
    }
  }

  mapSongToYouTubeSong(songs: Song[]) {
    const list: YouTubeVideo[] = [];
    songs.forEach(element => list.push({
      videoId: element.id.toString(),
      title: element.title,
      artist: element.artist,
      thumbNail: element.thumbNail,
      status: 'AVAILABLE',
      iconPath: this.iconPaths[4]
    }));
    return list;
  }

  getIconPath(item: YouTubeVideo) {
    if (item === undefined || item.status === undefined) {
      return this.iconPaths[4];
    }

    switch (item.status) {
      case 'AVAILABLE':
      case 'DOWNLOADABLE':
        return this.iconPaths[4];
      case 'DOWNLOADING':
        return this.iconPaths[3];
      case 'NOT AVAILABLE':
        return this.iconPaths[2];
    }
  }
}
