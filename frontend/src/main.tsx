// import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './main.css'
import App from './App'
import { BrowserRouter } from 'react-router-dom'
import Header from './components/layouts/Header'

createRoot(document.getElementById('root')!).render(
  <BrowserRouter>
    <Header />
    <App />
  </BrowserRouter>
)
