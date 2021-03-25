import {Component, OnInit} from '@angular/core';

import Map from 'ol/Map';
import View from 'ol/View';
import OSM from 'ol/source/OSM';
import * as olProj from 'ol/proj';
import TileLayer from 'ol/layer/Tile';

@Component({
  selector: 'app-me-contacter',
  templateUrl: './me-contacter.component.html',
  styleUrls: ['./me-contacter.component.scss']
})
export class MeContacterComponent implements OnInit {

   map: Map;

  constructor() { }

  ngOnInit(): void {

    this.map = new Map({
      target: 'map',
      layers: [
        new TileLayer({
          source: new OSM()
        })
      ],
      view: new View({
        center: olProj.fromLonLat([5.0698,43.9970 ]),
        zoom: 12
      })
    });

  }

}
