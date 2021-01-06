import {NgModule} from '@angular/core';
import {CoreLibComponent} from './core-lib.component';
import {HttpClientModule} from '@angular/common/http';
import {CommonModule} from '@angular/common';
import {ReactiveFormsModule} from '@angular/forms';


@NgModule({
  declarations: [CoreLibComponent],
  imports: [
    CommonModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  exports: [CoreLibComponent]
})
export class CoreLibModule { }
