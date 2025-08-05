import React, { useState } from 'react';

// The component now accepts a prop to handle navigation
export default function SignupPage({ onNavigateToLogin }) {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    contactDetails: ''
  });
  const [message, setMessage] = useState('');
  const [isError, setIsError] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.id]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    setMessage('');
    setIsError(false);
    try {
      const response = await fetch('http://localhost:8080/api/users/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData),
      });
      if (response.ok) {
        const newUser = await response.json();
        setIsError(false);
        setMessage(`Success! User "${newUser.username}" created. You can now log in.`);
        setFormData({ username: '', email: '', password: '', contactDetails: '' });
      } else {
        const errorText = await response.text();
        setIsError(true);
        setMessage(errorText || 'Registration failed.');
      }
    } catch (error) {
      setIsError(true);
      setMessage('Could not connect to the server.');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gray-100 flex items-center justify-center">
      <div className="bg-white p-8 rounded-lg shadow-md w-full max-w-md">
        <h2 className="text-2xl font-bold mb-6 text-center text-gray-800">Create Your Account</h2>
        <form onSubmit={handleSubmit}>
          {/* Form fields are the same as before */}
          <div className="mb-4">
            <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="username">Username</label>
            <input className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700" id="username" type="text" value={formData.username} onChange={handleChange} required />
          </div>
          <div className="mb-4">
            <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="email">Email</label>
            <input className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700" id="email" type="email" value={formData.email} onChange={handleChange} required />
          </div>
          <div className="mb-4">
            <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="password">Password</label>
            <input className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700" id="password" type="password" value={formData.password} onChange={handleChange} required />
          </div>
          <div className="mb-6">
            <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="contactDetails">Contact Details</label>
            <input className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700" id="contactDetails" type="text" value={formData.contactDetails} onChange={handleChange} required />
          </div>
          <div className="flex items-center justify-between mb-4">
            <button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded w-full" type="submit" disabled={isLoading}>
              {isLoading ? 'Signing Up...' : 'Sign Up'}
            </button>
          </div>
        </form>
        {message && <div className={`text-center p-2 rounded ${isError ? 'bg-red-100 text-red-700' : 'bg-green-100 text-green-700'}`}>{message}</div>}
        
        {/* --- This is the navigation link --- */}
        <div className="text-center mt-4">
          <p className="text-gray-600">
            Already have an account?{' '}
            <button onClick={onNavigateToLogin} className="font-bold text-blue-500 hover:text-blue-700">
              Sign In
            </button>
          </p>
        </div>
      </div>
    </div>
  );
}
