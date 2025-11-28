const CACHE_NAME = 'letters-app-v1';
const urlsToCache = [
  '/',
  '/index.html',
  '/logo.png'
];

self.addEventListener('install', event => {
  event.waitUntil(
    caches.open(CACHE_NAME).then(cache => cache.addAll(urlsToCache))
  );
});

self.addEventListener('fetch', event => {
  event.respondWith(
    caches.match(event.request).then(response => {
      return response || fetch(event.request);
    })
  );
});

if ('serviceWorker' in navigator && 'Notification' in window) {
    navigator.serviceWorker.register('service-worker.js')
    .then(reg => {
            Notification.requestPermission().then(permission => {
                
                if (permission === "granted") {                   
                    reg.showNotification("🎉 Welcome!", {
                        body: "Thanks for installing the app! Enjoy learning letters ❤️",
                        icon: "logo.png",
                        badge: "logo.png"
                    });
                }
            });
    });
 }
