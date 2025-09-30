// Common utilities and helper functions for Movie Vault

// API base URL
const API_BASE_URL = '/api';

// -------------------- AUTH FUNCTIONS --------------------

/**
 * Get the current logged-in user from localStorage
 * @returns {Object|null} The user object or null if not logged in
 */
function getCurrentUser() {
    try {
        return JSON.parse(localStorage.getItem('currentUser') || null);
    } catch (e) {
        console.error('Error parsing user data:', e);
        return null;
    }
}

/**
 * Get the type of the current user (user or theater)
 * @returns {string|null} The user type or null
 */
function getUserType() {
    return localStorage.getItem('userType');
}

/**
 * Check if a user is currently logged in
 * @returns {boolean} True if a user is logged in
 */
function isLoggedIn() {
    return !!getCurrentUser();
}

/**
 * Check if logged in as a regular user
 * @returns {boolean} True if logged in as user
 */
function isUser() {
    return isLoggedIn() && getUserType() === 'user';
}

/**
 * Check if logged in as a theater
 * @returns {boolean} True if logged in as theater
 */
function isTheater() {
    return isLoggedIn() && getUserType() === 'theater';
}

/**
 * Log out the current user and redirect to login page
 */
function logout() {
    localStorage.removeItem('currentUser');
    localStorage.removeItem('userType');
    window.location.href = 'index.html';
}

/**
 * Redirect to login page if not logged in
 * @returns {boolean} True if logged in, false otherwise
 */
function requireLogin() {
    if (!isLoggedIn()) {
        window.location.href = 'index.html';
        return false;
    }
    return true;
}

/**
 * Redirect to login page if not logged in as user
 * @returns {boolean} True if logged in as user, false otherwise
 */
function requireUser() {
    if (!isUser()) {
        window.location.href = 'index.html';
        return false;
    }
    return true;
}

/**
 * Redirect to login page if not logged in as theater
 * @returns {boolean} True if logged in as theater, false otherwise
 */
function requireTheater() {
    if (!isTheater()) {
        window.location.href = 'index.html';
        return false;
    }
    return true;
}

// -------------------- API FUNCTIONS --------------------

/**
 * Make an API request to the backend
 * @param {string} endpoint - The API endpoint to call
 * @param {Object} options - Request options
 * @returns {Promise<Object>} The response data
 */
async function fetchApi(endpoint, options = {}) {
    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, {
            ...options,
            headers: {
                'Content-Type': 'application/json',
                ...options.headers
            }
        });
        
        const data = await response.json();
        return { success: response.ok, data, status: response.status };
    } catch (error) {
        console.error(`API Error (${endpoint}):`, error);
        return { success: false, error: error.message, status: 0 };
    }
}

/**
 * Make a GET request to the API
 * @param {string} endpoint - The API endpoint
 * @returns {Promise<Object>} The response data
 */
function getApi(endpoint) {
    return fetchApi(endpoint);
}

/**
 * Make a POST request to the API
 * @param {string} endpoint - The API endpoint
 * @param {Object} data - The data to send
 * @returns {Promise<Object>} The response data
 */
function postApi(endpoint, data) {
    return fetchApi(endpoint, {
        method: 'POST',
        body: JSON.stringify(data)
    });
}

/**
 * Make a PUT request to the API
 * @param {string} endpoint - The API endpoint
 * @param {Object} data - The data to send
 * @returns {Promise<Object>} The response data
 */
function putApi(endpoint, data) {
    return fetchApi(endpoint, {
        method: 'PUT',
        body: JSON.stringify(data)
    });
}

/**
 * Make a DELETE request to the API
 * @param {string} endpoint - The API endpoint
 * @returns {Promise<Object>} The response data
 */
function deleteApi(endpoint) {
    return fetchApi(endpoint, {
        method: 'DELETE'
    });
}

// -------------------- DATE FUNCTIONS --------------------

/**
 * Format a date string to a locale date
 * @param {string} dateString - The date string to format
 * @returns {string} The formatted date
 */
function formatDate(dateString) {
    if (!dateString) return 'N/A';
    try {
        const date = new Date(dateString);
        return date.toLocaleDateString();
    } catch (e) {
        return dateString;
    }
}

/**
 * Format a date string to a locale time
 * @param {string} dateString - The date string to format
 * @returns {string} The formatted time
 */
function formatTime(dateString) {
    if (!dateString) return 'N/A';
    try {
        const date = new Date(dateString);
        return date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
    } catch (e) {
        return dateString;
    }
}

/**
 * Format a date string to a locale date and time
 * @param {string} dateString - The date string to format
 * @returns {string} The formatted date and time
 */
function formatDateTime(dateString) {
    if (!dateString) return 'N/A';
    try {
        const date = new Date(dateString);
        return date.toLocaleString();
    } catch (e) {
        return dateString;
    }
}

/**
 * Convert an HTML datetime-local input value to ISO string
 * @param {string} dateTimeLocal - The datetime-local value
 * @returns {string|null} The ISO string or null
 */
function dateTimeLocalToISO(dateTimeLocal) {
    if (!dateTimeLocal) return null;
    try {
        const date = new Date(dateTimeLocal);
        return date.toISOString();
    } catch (e) {
        return null;
    }
}

// -------------------- UI FUNCTIONS --------------------

/**
 * Show a loading indicator in an element
 * @param {HTMLElement} element - The element to show the loading indicator in
 * @param {string} message - The loading message
 */
function showLoading(element, message = 'Loading...') {
    element.innerHTML = `<div class="loading">${message}</div>`;
}

/**
 * Show an error message in an element
 * @param {HTMLElement} element - The element to show the error in
 * @param {string} message - The error message
 */
function showError(element, message = 'An error occurred. Please try again.') {
    element.innerHTML = `<div class="error-message" style="display: block;">${message}</div>`;
}

/**
 * Show a message in an element
 * @param {HTMLElement} element - The element to show the message in
 * @param {string} message - The message
 */
function showMessage(element, message) {
    element.innerHTML = `<div class="message">${message}</div>`;
}

/**
 * Create a showtime card element
 * @param {Object} showtime - The showtime object
 * @returns {HTMLElement} The showtime card element
 */
function createShowtimeCard(showtime) {
    const card = document.createElement('div');
    card.className = 'showtime-card';
    
    const startTime = new Date(showtime.startTime);
    const endTime = new Date(showtime.endTime);
    
    card.innerHTML = `
        <div class="showtime-movie">${showtime.movie.title}</div>
        <div class="showtime-theater">${showtime.theater.name}, ${showtime.theater.city || 'N/A'}</div>
        <div class="showtime-details">
            <div>Date: ${startTime.toLocaleDateString()}</div>
            <div>Time: ${startTime.toLocaleTimeString()} - ${endTime.toLocaleTimeString()}</div>
            <div>Duration: ${showtime.movie.duration} mins</div>
        </div>
        <div class="showtime-actions">
            <button class="btn book-btn" data-id="${showtime.id}" data-title="${showtime.movie.title}" data-theater="${showtime.theater.name}" data-time="${startTime.toLocaleTimeString()}">Book Now</button>
        </div>
    `;
    
    return card;
}

/**
 * Create a booking item element
 * @param {Object} booking - The booking object
 * @returns {HTMLElement} The booking item element
 */
function createBookingItem(booking) {
    const item = document.createElement('div');
    item.className = 'booking-item';
    
    const showtime = booking.showTime;
    console.log(showtime);
    const startTime = new Date(showtime.startTime);
    
    item.innerHTML = `
        <div class="booking-details">
            <h4>${showtime.movie.title}</h4>
            <p>${showtime.theater.name}, ${showtime.theater.city || 'N/A'}</p>
            <p>Date: ${startTime.toLocaleDateString()}</p>
            <p>Time: ${startTime.toLocaleTimeString()}</p>
        </div>
        <div>
            <button class="btn btn-alt cancel-btn" data-id="${booking.id}">Cancel</button>
        </div>
    `;
    
    return item;
}