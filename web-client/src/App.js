import './App.css';
import { Routes, Route } from 'react-router-dom';
import Home from './pages/home';
import CreateNewAlias from './components/create-new-alias';
import UrlsList from './components/urls-list';

function App() {
  return (
    <div className="App">
      <>
        <Routes>
          <Route path='/' element={<Home />} />
          <Route path={'/create-alias'} element={<CreateNewAlias />} />
          <Route path={'/urls-list'} element={<UrlsList />} />
        </Routes>
      </>
    </div>
  );
}

export default App;