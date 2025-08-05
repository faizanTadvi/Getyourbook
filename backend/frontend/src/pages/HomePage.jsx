import React, { useState, useEffect } from 'react';

// --- BookCard Component ---
// A small component to display a single book's information.
function BookCard({ book }) {
  return (
    <div className="bg-white rounded-lg shadow-md overflow-hidden">
      <div className="p-6">
        <h3 className="text-xl font-bold text-gray-800 mb-2">{book.title}</h3>
        <p className="text-gray-600 mb-1">by {book.author}</p>
        <p className="text-sm text-gray-500 mb-4">Condition: {book.bookCondition}</p>
        <div className="mb-4">
          <p className="text-gray-700">{book.description}</p>
        </div>
        <div className="flex justify-between items-center">
          <span className={`text-lg font-bold ${book.listingType === 'Donate' ? 'text-green-600' : 'text-blue-600'}`}>
            {book.listingType === 'Donate' ? 'Donation' : `$${book.price.toFixed(2)}`}
          </span>
          <button className="bg-gray-800 hover:bg-gray-900 text-white font-bold py-2 px-4 rounded">
            Contact Seller
          </button>
        </div>
        <div className="text-xs text-gray-400 mt-4">
          Listed by: {book.user.username}
        </div>
      </div>
    </div>
  );
}


// --- HomePage Component ---
// This page now fetches and displays all book listings.
export default function HomePage({ user, onLogout, onNavigateToAddBook }) {
  const [books, setBooks] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchBooks = async () => {
      const token = localStorage.getItem('token');
      if (!token) {
        setError("You are not logged in.");
        setIsLoading(false);
        return;
      }

      try {
        const response = await fetch('http://localhost:8080/api/books', {
          headers: {
            'Authorization': `Bearer ${token}`,
          },
        });

        if (!response.ok) {
          throw new Error('Failed to fetch books.');
        }

        const data = await response.json();
        setBooks(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setIsLoading(false);
      }
    };

    fetchBooks();
  }, []); // The empty array [] means this effect runs once when the component mounts.

  return (
    <div className="min-h-screen bg-gray-100 p-4 sm:p-8">
      <div className="max-w-7xl mx-auto">
        {/* Header Section */}
        <div className="bg-white p-4 sm:p-6 rounded-lg shadow-md mb-6">
          <div className="flex flex-col sm:flex-row justify-between items-center">
            <h1 className="text-2xl sm:text-3xl font-bold text-gray-800 mb-4 sm:mb-0">Welcome, {user.username}!</h1>
            <div className="flex items-center space-x-4">
              <button 
                onClick={onNavigateToAddBook}
                className="bg-purple-600 hover:bg-purple-700 text-white font-bold py-2 px-4 rounded"
              >
                + List a Book
              </button>
              <button 
                onClick={onLogout}
                className="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded"
              >
                Logout
              </button>
            </div>
          </div>
        </div>

        {/* Book Listings Section */}
        <div className="bg-white p-4 sm:p-6 rounded-lg shadow-md">
          <h2 className="text-2xl font-bold text-gray-700 mb-4">Available Books</h2>
          {isLoading && <p>Loading books...</p>}
          {error && <p className="text-red-500">{error}</p>}
          {!isLoading && !error && (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
              {books.map(book => (
                <BookCard key={book.id} book={book} />
              ))}
            </div>
          )}
           {!isLoading && books.length === 0 && <p>No books have been listed yet. Be the first!</p>}
        </div>
      </div>
    </div>
  );
}
