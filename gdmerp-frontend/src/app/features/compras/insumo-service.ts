import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiService } from '../../core/services/api-service';
import { Insumo } from './insumo.model';

@Injectable({ providedIn: 'root' })
export class InsumoService {
  private readonly http = inject(HttpClient);
  private readonly api = inject(ApiService);
  private readonly resource = '/api/v1/insumos'; // Asegúrate de que este endpoint exista en tu Java

  listar(): Observable<Insumo[]> {
    return this.http.get<Insumo[]>(this.api.buildUrl(this.resource));
  }
}
