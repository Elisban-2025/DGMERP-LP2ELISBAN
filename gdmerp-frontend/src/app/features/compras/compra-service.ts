import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiService } from '../../core/services/api-service';
import { Compra } from './compra.model';

@Injectable({ providedIn: 'root' })
export class CompraService {
  private readonly http = inject(HttpClient);
  private readonly api = inject(ApiService);
  private readonly resource = '/api/v1/compras';

  listar(): Observable<Compra[]> {
    return this.http.get<Compra[]>(this.api.buildUrl(this.resource));
  }
  obtener(id: number): Observable<Compra> {
    return this.http.get<Compra>(this.api.buildUrl(`${this.resource}/${id}`));
  }
  crear(compra: Compra): Observable<Compra> {
    return this.http.post<Compra>(this.api.buildUrl(this.resource), compra);
  }
  actualizar(id: number, compra: Compra): Observable<Compra> {
    return this.http.put<Compra>(this.api.buildUrl(`${this.resource}/${id}`), compra);
  }
  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(this.api.buildUrl(`${this.resource}/${id}`));
  }

  // 1. Método buscar con filtros de fecha y estado
  buscar(estado?: string, desde?: string, hasta?: string): Observable<Compra[]> {
    let params = new HttpParams();
    if (estado) params = params.set('estado', estado);
    if (desde) params = params.set('desde', desde);
    if (hasta) params = params.set('hasta', hasta);
    return this.http.get<Compra[]>(this.api.buildUrl(this.resource), { params });
  }

  // 2. Método anular (llama al backend mediante PATCH)
  anular(id: number): Observable<Compra> {
    return this.http.patch<Compra>(this.api.buildUrl(`${this.resource}/${id}/anular`), {});
  }
}