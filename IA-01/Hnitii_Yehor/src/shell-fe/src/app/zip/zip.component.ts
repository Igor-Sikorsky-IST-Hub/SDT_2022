import {Component, OnInit} from '@angular/core';
import {ZipService} from "./service/zip.service";
import {ZipDto} from "./dto/zip-dto";
import * as FileSaver from "file-saver";

@Component({
  selector: 'app-zip',
  templateUrl: './zip.component.html',
  styleUrls: ['./zip.component.css']
})
export class ZipComponent implements OnInit {

  displayedColumns: string[] = ['checked', 'filename', 'path', 'createdDate', 'action'];
  dataSource: ZipDto[] = [];

  public constructor(private zipService: ZipService) {
  }

  ngOnInit(): void {
    this.fetchCachedZips();
  }

  private fetchCachedZips() {
    this.zipService.getCachedZips().subscribe(zips => this.dataSource = zips);
  }

  uncheckAll() {
    this.dataSource.forEach(item => item.checked = false);
  }

  download(id: string) {
    this.zipService
      .download(id)
      .subscribe(res => {
        console.log(res)
        var contentDisposition = res.headers.get('content-disposition');
        // @ts-ignore
        var filename = contentDisposition.split(';')[1].split('filename')[1].split('=')[1]
          .trim()
          .replaceAll('\"', '');
        var binaryData = [];
        binaryData.push(res.body);
        console.log(filename);
        // @ts-ignore
        FileSaver.saveAs(new Blob([res.body]), filename);
      })
  }

  deleteSelected() {
    let selectedIds = this.dataSource.filter(x => x.checked).map(x => x.id);
    this.zipService
      .deleteAllByIdIn(selectedIds)
      .subscribe(() => this.fetchCachedZips())
  }

}
