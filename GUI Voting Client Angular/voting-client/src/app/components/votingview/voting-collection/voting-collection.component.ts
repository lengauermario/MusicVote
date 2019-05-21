import { Component, OnInit } from '@angular/core';
import { YouTubeVideo } from 'src/app/models/you-tube-video.model';
import { SongsService } from 'src/app/services/songs.service';
import { YouTubeService } from 'src/app/services/you-tube.service';
import { Song } from 'src/app/models/song.model';
import { PlaylistService } from 'src/app/services/playlist.service';

@Component({
  selector: 'app-voting-collection',
  templateUrl: './voting-collection.component.html',
  styleUrls: ['./voting-collection.component.css']
})
export class VotingCollectionComponent implements OnInit {
  list: Song[];
  iconPaths = [
    'assets/images/heartGrey.png',
    'assets/images/heart.png',
    'assets/images/error.png',
    'assets/images/loading.gif',
    'assets/images/plus.png'
  ];
  defaultThumbnail = 'assets/images/defaultCover.png';

  constructor(private playlistService: PlaylistService) {
  }

  ngOnInit() {
    this.playlistService.getPlaylist().subscribe((data) => {
      console.log(data);
      data.forEach(x => (x.thumbNail === 'default') ? x.thumbNail = this.defaultThumbnail : null);
      data.forEach(x => x.iconPath = this.iconPaths[0]);
      this.list = data;
    });
  }

  getIconPath(item: Song) {
    item.iconPath = this.iconPaths[0];
  }

  changeVote(item: Song) {
    if (item.iconPath === this.iconPaths[0]) {
      this.playlistService.addVote(item.id).subscribe(x => {
        item.iconPath = this.iconPaths[1];
        
      });
    } else {
      this.playlistService.removeVote(item.id).subscribe(x => {
        item.iconPath = this.iconPaths[0];
      });
    }
  }

}
