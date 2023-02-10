import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {FileService} from "./service/file.service";
import * as FileSaver from "file-saver";

export class Element {
  path: string = '';
  title: string = '';
  folder: boolean = false;
  checked: boolean = false;
  highlighted: boolean = false;
}

@Component({
  selector: 'app-file-manager',
  templateUrl: './file-manager.component.html',
  styleUrls: ['./file-manager.component.css'],
})
export class FileManagerComponent implements OnChanges, OnInit {

  displayedColumns = ['checked', 'title', 'action'];
  @Input('isPrimary') isPrimary: boolean = false;
  @Input('action') action: string = '';
  @Input('path') path: string = '';
  @Input('destination') destination: string = '';
  splitChar: string = '/';
  @Output() pathEmitter = new EventEmitter<{ primary: boolean; path: string }>();
  @Output() reloadEmitter = new EventEmitter<string>();
  files: Element[] = [];
  search: string = '';
  newFolderName: string | undefined;


  constructor(private fileService: FileService) {
  }

  ngOnInit(): void {
    this.pathEmitter.emit({path: this.path, primary: this.isPrimary})
    this.searchFiles(this.search);
  }

  ngOnChanges(changes: SimpleChanges): void {
    console.log(changes)
    let paths = this.files.filter(x => x.checked).map(x => x.path);

    if (this.isPrimary) {
      if (this.action === 'copy') {
        this.copy(paths);
      } else if (this.action === 'transfer') {
        this.transfer(paths);
      } else if (this.action === 'reload') {
        this.action = '';
        return;
      }
    }

    if (this.action === 'delete') {
      this.delete(paths)
    }

    this.action = '';
    this.ngOnInit();
  }

  uncheckAll() {
    this.files.forEach(file => file.checked = false);
  }

  private initFiles() {
    this.fileService
      .getDirectory(this.path)
      .subscribe(files => {
        this.files = JSON.parse(JSON.stringify(files));
      });
  }

  getAllPaths(): string[] {
    let splitPath = this.path.split(this.splitChar);
    let allPaths: string[] = [];
    let paths = '';

    splitPath.filter(x => x).forEach(path => {
      paths += this.splitChar + path;
      allPaths.push(paths)
    })

    return allPaths;
  }

  getLastPath(path: string): string {
    return path.substring(path.lastIndexOf(this.splitChar) + 1);
  }

  goToPath(param: string) {
    this.path = param;
    this.ngOnInit();
  }

  reload(file: Element) {
    if (file.folder) {
      this.path = file.path;
      this.ngOnInit();
    }
  }

  download(path: string) {
    this.fileService
      .download(path)
      .subscribe(res => {
        console.log(res)
        const contentDisposition = res.headers.get('content-disposition');
        // @ts-ignore
        const filename = contentDisposition.split(';')[1].split('filename')[1].split('=')[1]
          .trim()
          .replaceAll('\"', '');
        let binaryData = [];
        binaryData.push(res.body);
        console.log(filename);
        // @ts-ignore
        FileSaver.saveAs(new Blob([res.body]), filename);
      })
  }


  delete(path: string[]) {
    if (path.length > 0)
      this.fileService.delete(path).subscribe(() => {
        this.ngOnInit();
        this.reloadEmitter.emit('');
      });
  }

  transfer(paths: string[]) {
    this.fileService.transfer(paths, this.destination).subscribe({
      next: () => {
        this.reloadEmitter.emit('reload');
        this.ngOnInit();
      },
      error: err => {
        this.reloadEmitter.emit('reload');
        window.alert(err.error.errors[0].message);
      }
    });
  }

  copy(paths: string[]) {
    this.fileService.copy(paths, this.destination).subscribe({
      next: () => {
        this.reloadEmitter.emit('reload');
        this.ngOnInit();
      },
      error: err => {
        this.reloadEmitter.emit('reload');
        window.alert(err.error.errors[0].message);
      }
    });
  }

  highlight(element: Element) {
    element.highlighted = !element.highlighted;
  }

  searchFiles(search: string) {
    if (search.length < 3) {
      this.initFiles();
      this.uncheckAll();
      return;
    }
    this.fileService
      .findFiles(this.path, search)
      .subscribe(files => {
        let set = new Set(this.files.filter(x => x.checked));
        JSON.parse(JSON.stringify(files)).forEach((el: Element) => set.add(el))
        this.files = [...set];
      });
  }

  uploadFile(event: Event) {
    if (!event) {
      return;
    }
    // @ts-ignore
    console.log(event.target.files[0])// @ts-ignore
    this.fileService.uploadFile(event.target.files[0], this.path).subscribe({
      next: () => {
        this.initFiles();
        // @ts-ignore
        event.target.value = null;
      }
    });
  }

  createFolder() {
    if (!this.newFolderName) {
      return;
    }
    this.fileService
      .createFolder(this.path + this.splitChar + this.newFolderName)
      .subscribe({
        next: () => this.initFiles()
      });
  }

  onFolderNameInput(event: Event) {
    if (!event) {
      return;
    }
    // @ts-ignore
    this.newFolderName = event.target.value;
  }

}

