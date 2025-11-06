import React from 'react';
import Calculator from './components/Calculator';

const App: React.FC = () => {
  return (
    <div className="app">
      <header>
        <h1>Scientific Calculator</h1>
        <p className="subtitle">React + TypeScript frontend, Spring Boot backend (Test4)</p>
      </header>
      <main>
        <Calculator />
      </main>
      <footer>
        <small>Backend: Spring Boot API at <code>/api/*</code></small>
      </footer>
    </div>
  );
};

export default App;
