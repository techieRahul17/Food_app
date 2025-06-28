# Food_app

A comprehensive food delivery application backend developed in Java. This app models an online food ordering and delivery system with support for restaurant management, menu management, customer ordering, cart, and order tracking functionalities.

## Features

- **Restaurant Management:** Add, update, and manage restaurant details, including cuisine type, availability, and working hours.
- **Menu Management:** Each restaurant can manage its menu items, including offers, availability, and pricing.
- **Customer Cart:** Customers can add items to a cart and review their total order amount before placing an order.
- **Order Management:** Supports order placement, order status tracking, and assignment of delivery personnel.
- **Data Access Layer:** DAO (Data Access Object) classes for interacting with the database for restaurants, menu items, orders, and more.
- **Offer Management:** Menu items can have special offers, and the offer availability is tracked.
- **Extensible Model:** Well-defined Java classes for core entities like `Order`, `Item`, `Cart`, and `Restaurant`.

## Technologies Used

- **Language:** Java
- **Database:** JDBC-based SQL (DBUtil for database connectivity)
- **Architecture:** Object-Oriented Programming, DAO design pattern

## Project Structure

```
food_deli_app/
  └── src/
      └── food_app/
          ├── dao/          # Data Access Objects for entities
          ├── db/           # Database utilities
          └── models/       # Core models (Order, Item, Cart, Restaurant, etc.)
```

## How it Works

- **Restaurants** can register their details and manage menus.
- **Customers** browse restaurants and menu items, add items to their cart, and place orders.
- **Orders** are tracked with statuses and can be updated as they are processed.
- **Admins** or restaurant owners can update menu items and see incoming orders.

## Getting Started

1. **Clone the Repository**
   ```sh
   git clone https://github.com/Ramcharan-Swaminathan/Food_app.git
   ```
2. **Configure Database**
   - Set up your SQL database and update the DBUtil configuration.
3. **Build and Run**
   - Use your preferred Java IDE or run with:
     ```sh
     javac -d bin src/food_app/**/*.java
     java -cp bin food_app.Main
     ```

## Authors

- **Ramcharan S**
- **Keshav K S**
- **Sakthivel T**

### Contributed by

- **Rahul V S**

---

> This project implements a solid foundation for a scalable food delivery backend and can be extended with user interfaces, payment integration, and more advanced business logic.
