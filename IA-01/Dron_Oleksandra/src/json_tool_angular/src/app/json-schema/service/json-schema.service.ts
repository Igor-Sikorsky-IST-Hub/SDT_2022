import {Injectable} from '@angular/core';
import {AbstractService} from "../../helpers/service/abstract.service";
import {Observable} from "rxjs";
import {JsonSchema} from "../../helpers/model/common-dtos";

@Injectable({
  providedIn: 'root'
})
export class JsonSchemaService extends AbstractService {

  saveSchema(schema: { title: string, description: string, json: string, previousId: string | undefined }): Observable<JsonSchema> {
    return this.httpClient
      .post<JsonSchema>(`${this.PRIVATE_PATH}/json-schemas`, schema);
  }

  findLast(): Observable<JsonSchema> {
    return this.httpClient
      .get<JsonSchema>(`${this.PRIVATE_PATH}/json-schemas/last`);
  }

  findPrev(currentSchemaId: string): Observable<JsonSchema> {
    return this.httpClient
      .get<JsonSchema>(`${this.PRIVATE_PATH}/json-schemas/${currentSchemaId}/previous`);
  }

  findNext(currentSchemaId: string): Observable<JsonSchema> {
    return this.httpClient
      .get<JsonSchema>(`${this.PRIVATE_PATH}/json-schemas/${currentSchemaId}/next`);
  }
}
