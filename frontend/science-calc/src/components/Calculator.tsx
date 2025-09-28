import React, { useState } from 'react';

type ApiResponse = Record<string, unknown> | { error: string };

interface HistoryEntry {
  label: string;
  data: ApiResponse;
}

const Calculator: React.FC = () => {
  const [sqrtX, setSqrtX] = useState('');
  const [factN, setFactN] = useState('');
  const [lnX, setLnX] = useState('');
  const [powX, setPowX] = useState('');
  const [powB, setPowB] = useState('');
  const [loading, setLoading] = useState(false);
  const [history, setHistory] = useState<HistoryEntry[]>([]);

  async function callApi(path: string, label: string) {
    setLoading(true);
    try {
      const res = await fetch(path);
      const json: ApiResponse = await res.json();
      if (!res.ok) throw new Error((json as any).error || JSON.stringify(json));
      const entry = { label, data: json };
      setHistory((h) => [entry, ...h].slice(0, 10));
    } catch (err) {
      const entry = { label, data: { error: String(err) } };
      setHistory((h) => [entry, ...h].slice(0, 10));
    } finally {
      setLoading(false);
    }
  }

  const handleSqrt = () => {
    if (!sqrtX.trim()) return alert('Enter a value for √x');
    callApi(`/api/sqrt?x=${encodeURIComponent(sqrtX)}`, `sqrt(${sqrtX})`);
  };

  const handleFactorial = () => {
    if (!factN.trim()) return alert('Enter an integer for n!');
    if (!/^-?\d+$/.test(factN.trim())) return alert('Factorial requires integer input');
    callApi(`/api/factorial?n=${encodeURIComponent(factN)}`, `fact(${factN})`);
  };

  const handleLn = () => {
    if (!lnX.trim()) return alert('Enter a value for ln(x)');
    callApi(`/api/ln?x=${encodeURIComponent(lnX)}`, `ln(${lnX})`);
  };

  const handlePower = () => {
    if (!powX.trim() || !powB.trim()) return alert('Enter base and exponent');
    callApi(
      `/api/power?x=${encodeURIComponent(powX)}&b=${encodeURIComponent(powB)}`,
      `power(${powX}, ${powB})`
    );
  };

  return (
    <div className="calc">
      <div className="grid">
        <section className="card">
          <h3>Square root</h3>
          <input value={sqrtX} onChange={(e) => setSqrtX(e.target.value)} placeholder="x (>=0)" />
          <button onClick={handleSqrt} disabled={loading}>Compute √x</button>
        </section>

        <section className="card">
          <h3>Factorial</h3>
          <input value={factN} onChange={(e) => setFactN(e.target.value)} placeholder="n (integer >=0)" />
          <button onClick={handleFactorial} disabled={loading}>Compute n!</button>
        </section>

        <section className="card">
          <h3>Natural log</h3>
          <input value={lnX} onChange={(e) => setLnX(e.target.value)} placeholder="x (>0)" />
          <button onClick={handleLn} disabled={loading}>Compute ln(x)</button>
        </section>

        <section className="card">
          <h3>Power</h3>
          <input value={powX} onChange={(e) => setPowX(e.target.value)} placeholder="base x" />
          <input value={powB} onChange={(e) => setPowB(e.target.value)} placeholder="exponent b" />
          <button onClick={handlePower} disabled={loading}>Compute x^b</button>
        </section>
      </div>

      <section className="history">
        <h3>Results</h3>
        {history.length === 0 && <p>No results yet.</p>}
        {history.map((h, i) => (
          <div key={i} className="result">
            <strong>{h.label}</strong>
            <pre>{JSON.stringify(h.data, null, 2)}</pre>
          </div>
        ))}
      </section>
    </div>
  );
};

export default Calculator;
