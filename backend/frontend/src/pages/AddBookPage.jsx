import React, { useState } from 'react';

// The component now sends the JWT with the request.
export default function AddBookPage({ user, onBookListed }) {
  const [formData, setFormData] = useState({
    title: '',
    author: '',
    isbn: '',
    description: '',
    bookCondition: 'Good',
    listingType: 'Sell',
    price: '',
  });

  const [message, setMessage] = useState('');
  const [isError, setIsError] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  const handleChange = (e) => {
    const { id, value } = e.target;
    setFormData(prev => ({ ...prev, [id]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    setMessage('');
    setIsError(false);

    const token = localStorage.getItem('token');

    // --- DEBUGGING STEP ---
    // Let's log the token to the browser's console to inspect it.
    console.log('Retrieved token:', token);

    if (!token) {
      setMessage('You are not logged in. Please log in to list a book.');
      setIsError(true);
      setIsLoading(false);
      return;
    }

    const bookData = {
      ...formData,
      userId: user.id,
      price: formData.listingType === 'Donate' ? 0.00 : formData.price,
    };

    const headers = {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    };

    // --- DEBUGGING STEP ---
    // Let's also log the headers object.
    console.log('Sending request with headers:', headers);

    try {
      const response = await fetch('http://localhost:8080/api/books', {
        method: 'POST',
        headers: headers, // Use the headers object
        body: JSON.stringify(bookData),
      });

      if (response.ok) {
        onBookListed();
      } else {
        const errorText = await response.text();
        setIsError(true);
        setMessage(errorText || 'Failed to list book. Please try again.');
      }
    } catch (error) {
      setIsError(true);
      setMessage('Could not connect to the server.');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gray-100 flex items-center justify-center py-12 px-4">
      <div className="bg-white p-8 rounded-lg shadow-md w-full max-w-2xl">
        <h2 className="text-2xl font-bold mb-6 text-center text-gray-800">List Your Textbook</h2>
        <form onSubmit={handleSubmit} className="space-y-6">
          {/* Form fields are the same as before */}
          <div>
            <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="title">Book Title</label>
            <input id="title" type="text" value={formData.title} onChange={handleChange} required className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700" />
          </div>
          <div>
            <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="author">Author(s)</label>
            <input id="author" type="text" value={formData.author} onChange={handleChange} required className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700" />
          </div>
          <div>
            <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="isbn">ISBN (Optional)</label>
            <input id="isbn" type="text" value={formData.isbn} onChange={handleChange} className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700" />
          </div>
          <div>
            <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="description">Description</label>
            <textarea id="description" value={formData.description} onChange={handleChange} required className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 h-24" />
          </div>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            <div>
              <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="bookCondition">Condition</label>
              <select id="bookCondition" value={formData.bookCondition} onChange={handleChange} className="shadow border rounded w-full py-2 px-3 text-gray-700">
                <option>New</option><option>Like New</option><option>Good</option><option>Used</option>
              </select>
            </div>
            <div>
              <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="listingType">Listing Type</label>
              <select id="listingType" value={formData.listingType} onChange={handleChange} className="shadow border rounded w-full py-2 px-3 text-gray-700">
                <option>Sell</option><option>Donate</option>
              </select>
            </div>
            <div>
              <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="price">Price ($)</label>
              <input id="price" type="number" min="0.00" step="0.01" value={formData.price} onChange={handleChange} disabled={formData.listingType === 'Donate'} className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 disabled:bg-gray-200" />
            </div>
          </div>
          <div className="flex items-center justify-center pt-4">
            <button type="submit" disabled={isLoading} className="bg-purple-600 hover:bg-purple-700 text-white font-bold py-2 px-6 rounded focus:outline-none focus:shadow-outline disabled:bg-purple-300">
              {isLoading ? 'Listing...' : 'List My Book'}
            </button>
          </div>
        </form>
        {message && <div className={`mt-4 text-center p-2 rounded ${isError ? 'bg-red-100 text-red-700' : 'bg-green-100 text-green-700'}`}>{message}</div>}
      </div>
    </div>
  );
}
