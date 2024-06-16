import React from 'react';
import { createRoot } from 'react-dom/client';

import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import SearchPage from "./search/components/SearchPage";
import LibraryMain from "./library/components/library";

const rootElement = document.getElementById('root');
const root = createRoot(rootElement);

root.render(
  <Router>
      <Routes>
        <Route path="/" element={<SearchPage />} />
        <Route path="/library" element={<LibraryMain />} />
      </Routes>
  </Router>
);