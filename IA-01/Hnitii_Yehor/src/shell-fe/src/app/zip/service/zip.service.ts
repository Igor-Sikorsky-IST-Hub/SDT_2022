import {Injectable, OnInit} from '@angular/core';
import {CommonService} from "../../common/service/common.service";
import {Observable} from "rxjs";
import {ZipDto} from "../dto/zip-dto";
import {HttpResponse} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ZipService extends CommonService {

  getCachedZips(): Observable<ZipDto[]> {
    return this.http.get<ZipDto[]>(
      `${this.PUBLIC_PATH}/zips`
    );
  }

  download(id: string): Observable<HttpResponse<Blob>> {
    return this.http.get(
      `${this.PUBLIC_PATH}/zips/${id}`,
      {responseType: 'blob', observe: 'response'}
    );
  }

  deleteAllByIdIn(ids: string[]) {
    return this.http.delete(
      `${this.PUBLIC_PATH}/zips`, {
        body: {ids: ids}
      });
  }

}
