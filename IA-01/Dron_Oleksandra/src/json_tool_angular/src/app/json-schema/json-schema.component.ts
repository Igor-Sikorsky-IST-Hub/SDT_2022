import {Component, OnInit} from '@angular/core';
import Ajv from "ajv"
import {MatRadioChange} from "@angular/material/radio";
import {ToastrService} from 'ngx-toastr';
import {JsonSchemaService} from "./service/json-schema.service";

var parse = require('json-schema-to-markdown')
var FileSaver = require('file-saver');
var flatten = require('flat')

@Component({
  selector: 'app-json-schema',
  templateUrl: './json-schema.component.html',
  styleUrls: ['./json-schema.component.css']
})
export class JsonSchemaComponent implements OnInit {

  public data: string = '';
  public json: any;
  public error: string | undefined;
  isJsonMode: boolean = true;
  currentSchemaId: string | undefined;
  private lastSchemaId: string | undefined;

  constructor(private toastr: ToastrService, private jsonService: JsonSchemaService) {
  }

  prettifyJson(jsonMode: boolean): string {
    const ajv = new Ajv()
    try {
      let value = JSON.parse(this.data);
      const validate = ajv.compile(value)
      let s = JSON.stringify(value, null, 2);
      this.error = '';

      if (jsonMode) {
        return s;
      }

      console.log(flatten(value))

      return JSON.stringify(flatten(value), null, 2);
    } catch (e) {
      if (e instanceof Error) {
        this.error = e.message;
      }
      return '';
    }
  }

  prettifyData() {
    try {
      this.data = JSON.stringify(JSON.parse(this.data), null, 2);
    } catch (e) {
    }
  }

  exportAsMD() {
    if (this.error) {
      this.toastr.error();
      return;
    }

    let json = this.prettifyJson(true);
    const blob = new Blob([parse(JSON.parse(json))], {
      type: 'text/plain'
    });
    FileSaver.saveAs(blob, 'json-schema.md');
  }

  changeMod($event: MatRadioChange) {
    this.isJsonMode = $event.value;
  }

  saveSchema() {
    if (this.error) {
      this.toastr.error(`Invalid schema can't be saved`);
      return;
    }

    let json = JSON.parse(this.prettifyJson(true));

    let schema = {
      title: json.title ? json.title : 'Untitled',
      description: json.description,
      json: JSON.stringify(json),
      previousId: this.lastSchemaId
    };

    console.log(schema)

    this.jsonService.saveSchema(schema).subscribe({
      next: schema => {
        console.log('current id = ' + schema.id)
        this.currentSchemaId = schema.id;
        this.lastSchemaId = schema.id;
      },
      error: err => {
        err.error.errors.forEach((error: { message: string | undefined; }) => this.toastr.error());
      }
    })

  }

  findLast() {
    this.jsonService.findLast()
      .subscribe({
        next: schema => {
          console.log('current id = ' + schema.id)
          this.currentSchemaId = schema.id;
          this.lastSchemaId = schema.id;
          this.data = JSON.stringify(JSON.parse(schema.json), null, 2);
        },
        error: () => {
          this.data = JSON.stringify({
            "type": "object",
            "title": "Example title",
            "description": "description",
            "properties": {
              "foo": {
                "type": "integer"
              },
              "bar": {
                "type": "string"
              }
            },
            "required": [
              "foo"
            ],
            "additionalProperties": false
          }, null, 2);
        }
      })
  }

  ngOnInit(): void {
    this.findLast();
  }

  findPrev() {
    if (!this.currentSchemaId) {
      return;
    }

    this.jsonService.findPrev(this.currentSchemaId)
      .subscribe({
        next: schema => {
          console.log('current id = ' + schema.id)
          this.currentSchemaId = schema.id;
          this.data = JSON.stringify(JSON.parse(schema.json), null, 2);
        }
      });
  }

  findNext() {
    if (!this.currentSchemaId) {
      return;
    }

    this.jsonService.findNext(this.currentSchemaId)
      .subscribe({
        next: schema => {
          console.log('current id = ' + schema.id)
          this.currentSchemaId = schema.id;
          this.data = JSON.stringify(JSON.parse(schema.json), null, 2);
        }
      });
  }
}
