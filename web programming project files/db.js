var mongoose = require('mongoose');

mongoose.connect(
    'mongodb+srv://nadineelleissy:nadineelleissy@cluster0.ky47u.mongodb.net/',
    { useNewUrlParser: true }
  )
  .then(() => console.log("DB connected successfully"))
  .catch((err) => console.error("DB connection error:", err));
