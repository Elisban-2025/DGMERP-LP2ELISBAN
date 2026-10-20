import { Component, inject, signal } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { TerceroService } from './tercero-service';

@Component({
  selector: 'app-tercero-form',
  imports: [ReactiveFormsModule],
  templateUrl: './tercero-form.html',
})
export class TerceroForm {
  private readonly fb = inject(FormBuilder);
  private readonly terceroService = inject(TerceroService);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);

  protected readonly id = signal<number | null>(null);
  protected readonly error = signal<string | null>(null);
  protected readonly loading = signal(false);
  protected readonly errorCarga = signal(false);

  protected readonly form = this.fb.nonNullable.group({
    tipoDocumento: ['RUC', [Validators.required]],
    numeroDocumento: ['', [Validators.required, Validators.maxLength(20)]],
    razonSocial: ['', [Validators.required, Validators.maxLength(100)]],
    nombreComercial: [''],
    celularWhatsapp: [''],
    correo: ['', [Validators.email]],
    esCliente: [0],
    esProveedor: [1],
    idCategoria: [1, [Validators.required]]
  });

  constructor() {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      const id = Number(idParam);
      this.id.set(id);
      this.loading.set(true);
      this.terceroService.obtener(id).subscribe({
        next: (tercero) => {
          this.form.patchValue(tercero);
          this.loading.set(false);
        },
        error: () => {
          this.errorCarga.set(true);
          this.error.set('No se pudo cargar el tercero.');
          this.loading.set(false);
        },
      });
    }
  }

  guardar(): void {
    if (this.loading() || this.errorCarga()) return;
    this.error.set(null);

    const rsControl = this.form.controls.razonSocial;
    rsControl.setValue(rsControl.value.trim());

    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const valor = this.form.getRawValue();
    const idActual = this.id();

    // Inyectamos una dirección por defecto para que el backend lo acepte
    const payload = {
      ...valor,
      direcciones: [
        {
          tipoDireccion: "FISCAL",
          direccionCompleta: "Av. Principal 123",
          referencia: "Generado por sistema"
        }
      ]
    };

    const peticion = idActual ? this.terceroService.actualizar(idActual, payload) : this.terceroService.crear(payload);

    this.loading.set(true);
    peticion.subscribe({
      next: () => this.router.navigate(['/crm/terceros']),
      error: (err) => {
        console.error(err);
        this.error.set('Error del backend. Abre F12 (Consola) para ver el detalle exacto.');
        this.loading.set(false);
      },
    });
  }

  cancelar(): void {
    this.router.navigate(['/crm/terceros']);
  }

  protected mensajeValidacion(campo: string): string {
    const control = this.form.get(campo);
    if (!control || !control.touched) return '';
    if (control.hasError('required')) return 'Este campo es obligatorio.';
    if (control.hasError('email')) return 'Formato de correo inválido.';
    if (control.hasError('maxlength')) return 'Superó el límite de caracteres.';
    return '';
  }
}
