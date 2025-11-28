self.addEventListener('activate',()=>{})

self.addEventListener('notificationclick',e=>{
  e.notification.close()
})
