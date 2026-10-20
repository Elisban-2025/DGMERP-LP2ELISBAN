import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./core/layout/layout').then((m) => m.Layout),
    children: [
      {
        path: '',
        loadComponent: () => import('./core/inicio/inicio').then((m) => m.Inicio),
      },
      {
        path: 'crm/terceros',
        loadComponent: () => import('./features/crm/terceros/terceros-list').then((m) => m.TercerosList),
      },
      {
        path: 'crm/terceros/nuevo',
        loadComponent: () => import('./features/crm/terceros/tercero-form').then((m) => m.TerceroForm),
      },
      {
        path: 'crm/terceros/:id/editar',
        loadComponent: () => import('./features/crm/terceros/tercero-form').then((m) => m.TerceroForm),
      },
      {
        path: 'compras',
        loadComponent: () => import('./features/compras/compras-list').then((m) => m.ComprasList),
      },
      {
        path: 'compras/nuevo',
        loadComponent: () => import('./features/compras/compra-form').then((m) => m.CompraForm),
      },
      {
        path: 'compras/:id/editar',
        loadComponent: () => import('./features/compras/compra-form').then((m) => m.CompraForm),
      }
    ],
  },
];
