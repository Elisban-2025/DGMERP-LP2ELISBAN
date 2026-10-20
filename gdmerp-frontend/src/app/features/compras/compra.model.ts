export interface Compra {
  idCompra?: number;
  idTercero: number;
  fechaCompra?: string;
  serieComprobante: string;
  numeroComprobante: string;
  montoTotal?: number;
  estado?: string;
  fechaEmision?: string;
  total?: number;
  detalles?: any[];
}
