import {
  ApplicationConfig,
  provideBrowserGlobalErrorListeners,
  provideZonelessChangeDetection,
  APP_INITIALIZER,
  importProvidersFrom
} from '@angular/core';
import { provideRouter } from '@angular/router';
// 1. Se añadió withInterceptorsFromDi en la importación
import { provideHttpClient, withInterceptors, withInterceptorsFromDi } from '@angular/common/http';
import { routes } from './app.routes';
import { traceIdInterceptor } from './core/interceptors/trace-id-interceptor';

// Importaciones de Keycloak añadidas
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';
import { environment } from '../environments/environment';

// Función de inicialización de Keycloak añadida
function initializeKeycloak(keycloak: KeycloakService) {
  return () =>
    keycloak.init({
      config: {
        url: environment.keycloak.url,
        realm: environment.keycloak.realm,
        clientId: environment.keycloak.clientId
      },
      initOptions: {
        onLoad: 'login-required', // Exige iniciar sesión inmediatamente
        checkLoginIframe: false
      }
    });
}

export const appConfig: ApplicationConfig = {
  providers: [
    // TUS CONFIGURACIONES ORIGINALES (Intactas)
    provideBrowserGlobalErrorListeners(),
    provideZonelessChangeDetection(),
    provideRouter(routes),
    
    // 2. SE AÑADIÓ withInterceptorsFromDi() AQUÍ:
    provideHttpClient(
      withInterceptors([traceIdInterceptor]),
      withInterceptorsFromDi() 
    ),
    
    // NUEVAS CONFIGURACIONES DE KEYCLOAK
    importProvidersFrom(KeycloakAngularModule),
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService]
    }
  ],
};
