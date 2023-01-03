import {Injectable} from '@angular/core';
import {HttpParams, HttpResponse} from "@angular/common/http";
import {FileDto} from "../dto/file-manager-dto";
import {Observable} from "rxjs";
import {CommonService} from "../../common/service/common.service";

@Injectable({
  providedIn: 'root'
})
export class FileService extends CommonService {

  getDirectory(path: string): Observable<FileDto[]> {
    let queryParams = new HttpParams().append('path', path);
    return this.http.get<FileDto[]>(
      this.PUBLIC_PATH + '/file-system/folders',
      {params: queryParams}
    );
  }

  download(path: string): Observable<HttpResponse<Blob>> {
    let queryParams = new HttpParams().append('path', path);
    return this.http.get(
      this.PUBLIC_PATH + '/file-system/files',
      {params: queryParams, responseType: 'blob', observe: 'response'}
    );
  }

  delete(paths: string[]) {
    return this.http.delete(
      this.PUBLIC_PATH + '/file-system/files', {
        body: {paths: paths}
      });
  }

  transfer(paths: string[], destination: string) {
    return this.http.put(
      this.PUBLIC_PATH + '/file-system/files/transfer',
      {destinationPath: destination, paths: paths}
    );
  }

  copy(paths: string[], destination: string) {
    return this.http.put(
      this.PUBLIC_PATH + '/file-system/files/copy',
      {destinationPath: destination, paths: paths}
    );
  }

  findFiles(path: string, search: string): Observable<FileDto[]> {
    let queryParams = new HttpParams()
      .append('path', path)
      .append('search', search)

    return this.http.get<FileDto[]>(
      this.PUBLIC_PATH + '/file-system/files/search',
      {params: queryParams}
    );
  }

  uploadFile(fileToUpload: File, path: string) {
    const formData = new FormData();

    formData.append("file", fileToUpload);
    formData.append("path", path);

    return this.http.post(
      this.PUBLIC_PATH + '/file-system/files',
      formData
    );
  }

  createFolder(path: string) {
    return this.http.post(
      this.PUBLIC_PATH + '/file-system/folders',
      {
        paths: [path]
      }
    );
  }
}
