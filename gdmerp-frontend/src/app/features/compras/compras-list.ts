import { Component, OnInit, computed, inject, signal } from '@angular/core';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { CompraService } from './compra-service';
import { Compra } from './compra.model';

@Component({
  selector: 'app-compras-list',
  standalone: true,
  imports: [RouterLink, CurrencyPipe, DatePipe],
  templateUrl: './compras-list.html',
  styleUrls: ['./compras-list.css']
})
export class ComprasList implements OnInit {
  private readonly compraService = inject(CompraService);
  
  protected readonly compras = signal<Compra[]>([]);
  protected readonly estadoFiltro = signal('');
  protected readonly desdeFiltro = signal('');
  protected readonly hastaFiltro = signal('');
  protected readonly error = signal<string | null>(null);
  protected readonly loading = signal(false);

  // ¡Restauramos la reactividad! Angular calculará esto en vivo automáticamente
  protected readonly totalCompras = computed(() => this.compras().length);
  protected readonly montoTotal = computed(() => 
    this.compras().reduce((suma, c) => suma + Number(c.montoTotal || c.total || 0), 0)
  );

  ngOnInit(): void {
    this.cargar();
  }

  cargar(): void {
    this.loading.set(true);
    this.error.set(null);
    
    const desde = this.desdeFiltro() ? `${this.desdeFiltro()}T00:00:00` : undefined;
    const hasta = this.hastaFiltro() ? `${this.hastaFiltro()}T23:59:59` : undefined;

    this.compraService.buscar(this.estadoFiltro() || undefined, desde, hasta).subscribe({
      next: (data: Compra[]) => this.compras.set(data),
      error: () => this.error.set('No se pudo cargar el reporte de compras.'),
      complete: () => this.loading.set(false)
    });
  }

  filtrarPorEstado(estado: string): void {
    this.estadoFiltro.set(estado);
    this.cargar();
  }

  filtrarPorFecha(desde: string, hasta: string): void {
    this.desdeFiltro.set(desde);
    this.hastaFiltro.set(hasta);
    this.cargar();
  }

  anular(id: number): void {
    if (!confirm('¿Anular esta compra? El stock de los insumos ingresados se revertirá.')) return;
    
    this.compraService.anular(id).subscribe({
      next: () => this.cargar(),
      error: () => this.error.set('No se pudo anular la compra.')
    });
  }
}
