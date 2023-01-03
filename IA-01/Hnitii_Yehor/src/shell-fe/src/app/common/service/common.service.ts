import {Injectable} from '@angular/core';
import {HttpClient, HttpParams, HttpResponse} from "@angular/common/http";
import {environment} from "../../../../envs/environment.dev";


@Injectable({
  providedIn: 'root'
})
export class CommonService {

  SERVER_URL: string = environment.serverUrl;
  PUBLIC_PATH: string = this.SERVER_URL + '/api/v1/public';

  constructor(protected http: HttpClient) {
  }


}
