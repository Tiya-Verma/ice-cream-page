# Ice-Cream Shop Pre-Order System

## Project Description

This application will serve as a **digital pre-order system** for ice-cream shops, allowing customers to place their orders in advance and reducing wait times during busy periods. The system will enable customers to browse available flavors, customize their orders with various toppings and sizes, and schedule pickup times. Shop employees will be able to manage incoming orders, track preparation progress, and maintain inventory of available items.

The primary users of this application will be **ice-cream shop customers** who want to avoid long queues and **shop staff members** who need to efficiently manage order fulfillment. This project is of particular interest to me because it addresses a real-world problem I've experienced firsthand - waiting in long lines at popular ice-cream shops during peak hours. By creating a system that streamlines the ordering process, we can improve customer satisfaction while helping small businesses operate more efficiently and serve more customers during busy periods.

## User Stories

* As a user, I want to be able to add an ice cream order to my pre-order queue so that I can avoid waiting in line
* As a user, I want to be able to view the list of all pending pre-orders so that I can see the current queue status
* As a user, I want to be able to select ice cream flavors, toppings, and sizes when creating my order so that I can customize it to my preferences
* As a user, I want to be able to mark an order as completed/picked up so that it is removed from the active queue
* As a user, I want to be able to view the estimated preparation time for my order so that I know when to arrive for pickup
* As a user, I want to be able to cancel my pre-order if my plans change before the ice cream is prepared
* As a user, I want to be able to save the entire state of my ice cream shop (including all pending and completed orders) to a file so that I can preserve my data between application sessions
* As a user, I want to be able to load the entire state of my ice cream shop from a file so that I can resume exactly where I left off at some earlier time

# Instructions for End User

- You can generate the first required action related to the user story "adding multiple Orders to an OrderQueue" by filling out the order form on the left side of the application (customer name, flavor, size, and toppings) and clicking the "Add Order" button
- You can generate the second required action related to the user story "adding multiple Orders to an OrderQueue" by clicking the "Complete Order" button in the Actions panel and selecting an order from the dropdown menu to mark it as completed
- You can locate my visual component by looking at the Actions panel on the right side of the application - there is a colorful ice cream cone graphic displayed in the center of that panel
- You can save the state of my application by clicking the "Save Orders" button in the Actions panel on the right, or by clicking on the "File" menu at the top of the window and selecting "Save Order Queue"
- You can reload the state of my application by clicking the "Load Orders" button in the Actions panel on the right, or by clicking on the "File" menu at the top of the window and selecting "Load Order Queue"

# Phase 4: Task 2
Event Log: </br>
Mon Aug 04 22:59:43 PDT 2025: Topping Chocolate Drizzle was added to order #1. </br>
Mon Aug 04 22:59:43 PDT 2025: Topping Waffle was added to order #1.</br>
Mon Aug 04 22:59:43 PDT 2025: Order with ID 1 was added to the queue.</br>
Mon Aug 04 23:00:05 PDT 2025: Topping Sprinkles was added to order #2.</br>
Mon Aug 04 23:00:05 PDT 2025: Topping Marshmallow was added to order #2.</br>
Mon Aug 04 23:00:05 PDT 2025: Order with ID 2 was added to the queue.</br>
Mon Aug 04 23:00:09 PDT 2025: Topping Chocolate Drizzle was added to order #1.</br>
Mon Aug 04 23:00:09 PDT 2025: Topping Waffle was added to order #1.</br>
Mon Aug 04 23:00:09 PDT 2025: Order with ID 1 was added to the queue.</br>
Mon Aug 04 23:00:09 PDT 2025: Topping Sprinkles was added to order #2.</br>
Mon Aug 04 23:00:09 PDT 2025: Topping Marshmallow was added to order #2.</br>
Mon Aug 04 23:00:09 PDT 2025: Order with ID 2 was added to the queue.</br>
Mon Aug 04 23:00:15 PDT 2025: Order with ID 2 was found in the queue.</br>
Mon Aug 04 23:00:15 PDT 2025: Order #2 was marked as completed.</br>
Mon Aug 04 23:00:15 PDT 2025: Order with ID 2 was completed.</br>
Mon Aug 04 23:00:18 PDT 2025: Topping Chocolate Drizzle was added to order #1.</br>
Mon Aug 04 23:00:18 PDT 2025: Topping Waffle was added to order #1.</br>
Mon Aug 04 23:00:18 PDT 2025: Order with ID 1 was added to the queue.</br>
Mon Aug 04 23:00:18 PDT 2025: Topping Sprinkles was added to order #2.</br>
Mon Aug 04 23:00:18 PDT 2025: Topping Marshmallow was added to order #2.</br>
Mon Aug 04 23:00:18 PDT 2025: Order #2 was marked as completed.</br>
Mon Aug 04 23:00:18 PDT 2025: Completed order with ID 2 was added to completed orders.</br>

# Phase 4: Task 3

- Personally, I had a really fun time working on the project and it also helped me work through some random ideas that come to mind, things I would otherwise not spend time working on.
- I think that I could have improved on the interface of the project if I had more time to work on it, I would have made the interface more interactive and tried to make it look like an actual ice-cream shop app.
- Also, if I had more time to work on this project, I would consider refactoring the 'OrderQueue' class to improve its single responsibility and reduce coupling, because right now, the 'OrderQueue' class handles both managing the order collections and processing order state changes, which violates the single responsibility principle. 
- So, I would extract the order processing into a separate 'OrderProcessor' class that would be responsible for handling order completion, cancellation, and state transitions. By doing this, the 'OrderQueue' class would focus onlyon maintaining the collections of pending and completed orders, while the 'OrderProcessor' class would handle the states of the orders. 