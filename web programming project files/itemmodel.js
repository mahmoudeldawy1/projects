var mongoose = require('mongoose');

var itemSchema = new mongoose.Schema({
    item_id: { type: Number },
    item_name: { type: String },
    item_quantity: { type: Number }
});

module.exports = mongoose.model('Item', itemSchema);