import { Component, OnInit, inject, signal } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { RouterLink } from '@angular/router';
import { TerceroService } from './tercero-service';
import { Tercero } from './tercero.model';

@Component({
  selector: 'app-terceros-list',
  imports: [RouterLink],
  templateUrl: './terceros-list.html',
})
export class TercerosList implements OnInit {
  private readonly terceroService = inject(TerceroService);
  protected readonly terceros = signal<Tercero[]>([]);
  protected readonly error = signal<string | null>(null);
  protected readonly loading = signal(false);

  ngOnInit(): void {
    this.cargar();
  }

  cargar(): void {
    this.loading.set(true);
    this.error.set(null);
    this.terceroService.listar().subscribe({
      next: (data) => {
        // Magia del frontend: Filtramos y ocultamos los que tienen Borrado Lógico ('I', '0', etc.)
        const soloActivos = data.filter(t => t.estado !== 'I' && t.estado !== '0' && t.estado !== 'INACTIVO');
        this.terceros.set(soloActivos);
      },
      error: () => {
        this.error.set('No se pudo cargar la lista de terceros.');
        this.loading.set(false);
      },
      complete: () => this.loading.set(false),
    });
  }

  eliminar(id: number): void {
    if (!confirm(`¿Está seguro de eliminar o inactivar al tercero con ID ${id}?`)) {
      return;
    }
    this.terceroService.eliminar(id).subscribe({
      next: () => this.cargar(),
      error: (err: HttpErrorResponse) => {
        this.error.set('No se pudo eliminar el tercero. Revise si tiene operaciones asociadas.');
      },
    });
  }
}
