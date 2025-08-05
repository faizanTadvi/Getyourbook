import React, { useState } from 'react';
import SignupPage from './pages/SignupPage.jsx';
import LoginPage from './pages/LoginPage.jsx';
import AddBookPage from './pages/AddBookPage.jsx';
import HomePage from './pages/HomePage.jsx'; // Import the new HomePage

// --- Main App Component ---
// Manages the entire application's state and routing.
export default function App() {
  const [currentUser, setCurrentUser] = useState(null);
  const [activePage, setActivePage] = useState('home');
  const [authPage, setAuthPage] = useState('signup');

  const handleLoginSuccess = (user) => {
    setCurrentUser(user);
    setActivePage('home');
  };

  const handleLogout = () => {
    localStorage.removeItem('token'); // Clear the token on logout
    setCurrentUser(null);
    setAuthPage('login');
  };

  // --- Conditional Rendering Logic ---
  if (currentUser) {
    switch (activePage) {
      case 'add-book':
        return <AddBookPage user={currentUser} onBookListed={() => setActivePage('home')} />;
      case 'home':
      default:
        return <HomePage user={currentUser} onLogout={handleLogout} onNavigateToAddBook={() => setActivePage('add-book')} />;
    }
  } else {
    if (authPage === 'signup') {
      return <SignupPage onNavigateToLogin={() => setAuthPage('login')} />;
    } else {
      return <LoginPage onNavigateToSignup={() => setAuthPage('signup')} onLoginSuccess={handleLoginSuccess} />;
    }
  }
}