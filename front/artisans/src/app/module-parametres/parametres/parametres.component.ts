import {Component, OnInit} from '@angular/core';
import {Apollo} from 'apollo-angular';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-parametres',
  templateUrl: './parametres.component.html',
  styleUrls: ['./parametres.component.scss']
})
export class ParametresComponent implements OnInit {

  taxes = [];
  conditionsReglements = [];
  error: any;

  constructor(private apollo: Apollo, private http: HttpClient) {
  }

  ngOnInit() {

    const query = `{
      allTaxes{
        id
        nom
        taux
      }
      
       allConditionsReglements{
          id
          condition
       }
    }`;

    this.http.post('api/graphql', query).subscribe((response: any) => {
      this.taxes = response?.data?.allTaxes;
      this.conditionsReglements = response?.data?.allConditionsReglements;
      this.error = response.error;
    });


  }

}
