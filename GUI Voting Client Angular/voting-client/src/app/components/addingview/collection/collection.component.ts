import { Component, OnInit } from '@angular/core';
import { Song } from 'src/app/models/song.model';
import { SongsService } from 'src/app/services/songs.service';
import { YouTubeService } from 'src/app/services/you-tube.service';

@Component({
  selector: 'app-adding-collection',
  templateUrl: './collection.component.html',
  styleUrls: ['./collection.component.css']
})
export class CollectionComponent implements OnInit {

  list: Song[];


  constructor(private songsService: SongsService, private youTubeService: YouTubeService) { }

  ngOnInit() {
    this.songsService.findAll().subscribe((data) => {
      console.log(data);
      this.list = data;
    });
  }

}
