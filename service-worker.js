const CACHE_NAME='letters-app-v2'
const urlsToCache=['/','/index.html','/logo.png']

self.addEventListener('install',e=>{
  e.waitUntil(caches.open(CACHE_NAME).then(c=>c.addAll(urlsToCache)))
  self.skipWaiting()
})

self.addEventListener('activate',e=>{
  e.waitUntil(self.registration.showNotification("🎉 Welcome!",{
    body:"Thanks for installing the app! Enjoy learning letters ❤️",
    icon:"logo.png",
    badge:"logo.png"
  }))
})

self.addEventListener('fetch',e=>{
  e.respondWith(caches.match(e.request).then(r=>r||fetch(e.request)))
})
