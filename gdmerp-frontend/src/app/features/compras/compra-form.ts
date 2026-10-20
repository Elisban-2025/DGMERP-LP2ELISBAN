import { Component, OnInit, computed, inject, signal } from '@angular/core';
import { FormArray, FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router'; // <-- Quitamos RouterLink de aquí
import { HttpErrorResponse } from '@angular/common/http';

// Material Imports
import { MatAutocompleteModule, MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider'; // <-- AGREGAMOS ESTO
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';

// Services and Models
import { TerceroService } from '../crm/terceros/tercero-service';
import { Tercero } from '../crm/terceros/tercero.model';
import { CompraService } from './compra-service';
import { InsumoService } from './insumo-service';
import { Insumo } from './insumo.model';

@Component({
  selector: 'app-compra-form',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatAutocompleteModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatSelectModule,
    MatCardModule,
    MatDividerModule,
    MatDatepickerModule, // <-- AGREGADO
    MatNativeDateModule  // <-- AGREGADO
  ],
  templateUrl: './compra-form.html',
})

export class CompraForm implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly compraService = inject(CompraService);
  private readonly terceroService = inject(TerceroService);
  private readonly insumoService = inject(InsumoService);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);

  // Señales de estado
  protected readonly id = signal<number | null>(null);
  protected readonly loading = signal(false);
  protected readonly error = signal<string | null>(null);
  
  // Listas desplegables
  protected readonly terceros = signal<Tercero[]>([]);
  protected readonly insumos = signal<Insumo[]>([]);

  // Búsqueda de Insumos (Autocomplete)
  protected readonly insumoBusqueda = signal('');
  protected readonly insumosFiltrados = computed(() => {
    const texto = this.insumoBusqueda().trim().toLowerCase();
    if (!texto) return this.insumos();
    return this.insumos().filter((i) => i.nombre.toLowerCase().includes(texto));
  });

  // El Formulario Maestro-Detalle
  protected readonly form = this.fb.group({
    idTercero: [0, [Validators.required, Validators.min(1)]],
    fechaCompra: [new Date(), Validators.required], // <-- AQUÍ ESTÁ EL CAMBIO (new Date)
    serieComprobante: ['', Validators.required],
    numeroComprobante: ['', Validators.required],
    montoTotal: [{ value: 0, disabled: true }], 
    detalles: this.fb.array([], Validators.required)
  });

  ngOnInit(): void {
    this.cargarListas();
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.id.set(Number(idParam));
      this.cargarCompra(this.id()!);
    } else {
       // Si es nuevo, inicializamos con una fila vacía
       this.agregarDetalle();
    }
  }

  // GETTER PARA EL FORMARRAY
  get detalles(): FormArray {
    return this.form.get('detalles') as FormArray;
  }

  private cargarListas(): void {
    this.terceroService.listar().subscribe({
      next: (data) => this.terceros.set(data),
      error: () => this.error.set('No se pudo cargar la lista de proveedores.')
    });

    this.insumoService.listar().subscribe({
      next: (data) => this.insumos.set(data),
      error: () => this.error.set('No se pudo cargar la lista de insumos.')
    });
  }

  cargarCompra(id: number): void {
    this.loading.set(true);
    this.compraService.obtener(id).subscribe({
      next: (compra: any) => {
        let fecha = compra.fechaEmision || compra.fechaCompra;
        if (fecha && fecha.includes('T')) fecha = fecha.split('T')[0];
        
        this.form.patchValue({
          idTercero: compra.idTercero,
          fechaCompra: fecha,
          serieComprobante: compra.serieComprobante,
          numeroComprobante: compra.numeroComprobante,
          montoTotal: compra.total || compra.montoTotal
        });
        this.loading.set(false);
      },
      error: () => {
        this.error.set('No se pudo cargar la compra.');
        this.loading.set(false);
      }
    });
  }

  // LOGICA DEL DETALLE (FORMARRAY)
  agregarDetalle(): void {
    const detalleForm = this.fb.group({
      idInsumo: [0, [Validators.required, Validators.min(1)]],
      cantidadComprada: [1, [Validators.required, Validators.min(1)]],
      precioUnitario: [0, [Validators.required, Validators.min(0)]],
      subtotal: [{ value: 0, disabled: true }] 
    });

    // Suscribirse a los cambios de cantidad o precio para recalcular
    detalleForm.valueChanges.subscribe(() => {
        this.calcularTotales();
    });

    this.detalles.push(detalleForm);
  }

  eliminarDetalle(index: number): void {
    this.detalles.removeAt(index);
    this.calcularTotales();
  }

  calcularTotales(): void {
    let granTotal = 0;
    this.detalles.controls.forEach(control => {
      const cantidad = control.get('cantidadComprada')?.value || 0;
      const precio = control.get('precioUnitario')?.value || 0;
      const sub = cantidad * precio;
      
      control.get('subtotal')?.setValue(sub, { emitEvent: false }); 
      granTotal += sub;
    });
    
    this.form.patchValue({ montoTotal: granTotal }, { emitEvent: false });
  }

  // BUSQUEDA AUTOCOMPLETE DE INSUMOS
  buscarInsumo(texto: string, index: number): void {
    this.insumoBusqueda.set(texto);
    this.detalles.at(index).get('idInsumo')?.setValue(0);
  }

  onInsumoSeleccionado(evento: MatAutocompleteSelectedEvent, index: number): void {
    const insumoElegido = this.insumos().find(i => i.nombre === evento.option.value);
    this.detalles.at(index).get('idInsumo')?.setValue(insumoElegido?.idInsumo || 0);
  }

  // GUARDAR COMPRA
  guardar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      this.error.set('Por favor, revise los campos marcados en rojo.');
      return;
    }

    // --- PASO 1 DE LA S9: CONFIRMACIÓN ANTES DE GUARDAR ---
    const cantidadLineas = this.detalles.length;
    const montoTotal = this.form.get('montoTotal')?.value || 0;
    const resumen = `Vas a registrar una compra de ${cantidadLineas} línea(s) por un total de S/ ${montoTotal.toFixed(2)}. ¿Confirmar?`;
    
    if (!confirm(resumen)) {
      return; // Si el usuario cancela, detenemos el proceso aquí
    }
    // --------------------------------------------------------

    this.error.set(null);
    this.loading.set(true);

    const formData = this.form.getRawValue();
    const payload = {
        idTercero: Number(formData.idTercero),
        fechaEmision: formData.fechaCompra,
        serieComprobante: formData.serieComprobante,
        numeroComprobante: formData.numeroComprobante,
        montoTotal: formData.montoTotal,
        tipoComprobante: 'FACTURA',      
        condicionPago: 'CONTADO',        
        costoFlete: 0,
        detalles: formData.detalles.map((d: any) => ({
            idInsumo: d.idInsumo,
            cantidadComprada: d.cantidadComprada,
            precioUnitario: d.precioUnitario,
            subtotal: d.cantidadComprada * d.precioUnitario,
            factorConversion: 1, 
            descripcion: 'Insumo'
        }))
    };

    const peticion = this.id()
      ? this.compraService.actualizar(this.id()!, payload as any)
      : this.compraService.crear(payload as any);

    peticion.subscribe({
      next: () => this.router.navigate(['/compras']),
      error: (err: HttpErrorResponse) => {
          console.error(err);
          this.error.set('Error al guardar la compra.');
          this.loading.set(false);
      }
    });
  }

  cancelar(): void {
    this.router.navigate(['/compras']);
  }
}
