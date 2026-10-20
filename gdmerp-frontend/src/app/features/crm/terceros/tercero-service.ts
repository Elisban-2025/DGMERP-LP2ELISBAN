import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiService } from '../../../core/services/api-service';
import { Tercero } from './tercero.model';

@Injectable({ providedIn: 'root' })
export class TerceroService {
  private readonly http = inject(HttpClient);
  private readonly api = inject(ApiService);
  private readonly resource = '/api/v1/terceros';

  listar(): Observable<Tercero[]> {
    return this.http.get<Tercero[]>(this.api.buildUrl(this.resource));
  }
  
  obtener(id: number): Observable<Tercero> {
    return this.http.get<Tercero>(this.api.buildUrl(`${this.resource}/${id}`));
  }
  
  crear(tercero: Tercero): Observable<Tercero> {
    return this.http.post<Tercero>(this.api.buildUrl(this.resource), tercero);
  }
  
  actualizar(id: number, tercero: Tercero): Observable<Tercero> {
    return this.http.put<Tercero>(this.api.buildUrl(`${this.resource}/${id}`), tercero);
  }
  
  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(this.api.buildUrl(`${this.resource}/${id}`));
  }
}
