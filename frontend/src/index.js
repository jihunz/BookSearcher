import React from 'react';
import { createRoot } from 'react-dom/client'; // Import from "react-dom/client" instead of "react-dom"
// import reportWebVitals from './reportWebVitals';

import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import SearchPage from "./search/components/SearchPage";
import LibraryMain from "./library/components/library";

const rootElement = document.getElementById('root');
const root = createRoot(rootElement);

root.render(
  <Router>
    <React.StrictMode>
      <Routes>
        <Route path="/" element={<SearchPage />} />
        <Route path="/library" element={<LibraryMain />} />
      </Routes>
    </React.StrictMode>
  </Router>
);

// reportWebVitals();