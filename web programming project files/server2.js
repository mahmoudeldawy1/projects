
var express = require("express");
var bodyParser = require("body-parser");
var port = 5000;
var db = require('./db'); 
var app = express();


var Product = require('./productModel');
var Category = require('./categoryModel');


app.use(bodyParser.json());


//product operations


// GET route for fetching all products
app.get("/products", async (req, res) => {
  try {
    // Fetch all products from the database
    var products = await Product.find();
    res.status(201).json(products);
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
});

// get route for fetching a specific product
app.get("/products/:id", async (req, res) => {
    try {
      // Fetch all products from the database
      var wantedproduct = await Product.findById(req.params.id);
      res.status(201).json(wantedproduct);
    } catch (error) {
      res.status(400).json({ error: error.message });
    }
  });

// POST route for adding a new product
app.post("/products", async (req, res) => {
  try {
    // Create a new product document using the request body
    var product = new Product(req.body);
    var savedProduct = await product.save();
    res.status(201).json(savedProduct);
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
});

// PUT route for updating a product by ID
app.put("/products/:id", async (req, res) => {
  try {
    // Find the product by ID and update it with the request body
    var updatedProduct = await Product.findByIdAndUpdate(req.params.id, req.body, { new: true });
    res.status(201).json(updatedProduct);
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
});

// DELETE route for deleting a product by ID
app.delete("/products/:id", async (req, res) => {
  try {
    // Find the product by ID and delete it
    var deletedProduct = await Product.findByIdAndDelete(req.params.id);
    res.status(201).json(deletedProduct);
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
});


//category operations


// GET route for fetching all categories
app.get("/categories", async (req, res) => {
  try {
    // Fetch all categories from the database
    var categories = await Category.find();
    res.status(201).json(categories);
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
});

//get route for fetching a specific category
app.get("/categories/:id", async (req, res) => {
    try {
      var wantedcategory = await Category.findById(req.params.id);
      res.status(201).json(wantedcategory);
    } catch (error) {
      res.status(400).json({ error: error.message });
    }
  });

// POST route for adding a new category
app.post("/categories", async (req, res) => {
  try {
    // Create a new category document using the request body
    var category = new Category(req.body);
    var savedCategory = await category.save();
    res.status(201).json(savedCategory);
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
});

// PUT route for updating a category by ID
app.put("/categories/:id", async (req, res) => {
  try {
    // Find the category by ID and update it with the request body
    var updatedCategory = await Category.findByIdAndUpdate(req.params.id, req.body, { new: true });
    res.status(201).json(updatedCategory);
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
});

// DELETE route for deleting a category by ID
app.delete("/categories/:id", async (req, res) => {
  try {
    // Find the category by ID and delete it
    var deletedCategory = await Category.findByIdAndDelete(req.params.id);
    res.status(201).json(deletedCategory);
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
});


app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});
