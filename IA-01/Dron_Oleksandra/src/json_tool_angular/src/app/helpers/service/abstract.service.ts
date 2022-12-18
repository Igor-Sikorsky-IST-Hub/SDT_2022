import {Injectable} from '@angular/core';
import {environment} from "../../../../env/environment";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";

@Injectable({
  providedIn: 'root'
})
export class AbstractService {

  SERVER_URL: string = environment.serverUrl;
  public PUBLIC_PATH: string = `${this.SERVER_URL}/api/v1/public`;
  public PRIVATE_PATH: string = `${this.SERVER_URL}/api/v1/private`;

  public constructor(protected router: Router,
                     protected httpClient: HttpClient,
                     protected toastr: ToastrService) {
  }

}
