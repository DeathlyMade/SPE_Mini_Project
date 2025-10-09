import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
  plugins: [react()],
  server: {
    // Proxy API calls to backend during dev (backend running on :8081)
    host:true,
    port: 5173,
    strictPort: false,
    proxy: {
      '/api': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        secure: false
      }
    },
    hmr: {
      host: 'localhost'
    }
  },
  build: {
    outDir: 'dist',
    emptyOutDir: true
  }
});
