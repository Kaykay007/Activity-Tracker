This activity tracker application was built for Jane, 
a student with bad time management skills. 
She needed an application where she can log the activities
she needs to do for the day and the time allocated to complete these activities. 
Below are the tools used in building the application and the functionalities.
Tools
· Spring MVC
· Thymeleaf
· Git
· Spring Data JPA
· JUnit/Mockito
· CSS
User Stories
As a user, Jane will be able to
· Login
· Create a task.
A task is made up of
® Title
® Description
® Status
® Created At
® Updated At
® Completed At
· Jane will be able to view all tasks.
· View a particular task.
· View all pending tasks.
· View all done tasks.
· View all in-progress tasks.
· Move a task to done tasks.
· Move a task back to pending tasks from in progress.
· Edit a task.
Jane will be able to edit the following fields of a task
® Title
® Description
· Delete a task
Categories: pending, done, in progress
Overview of the implementation
· The code was implemented on the DRY concept and Basic OOP concept.
· Endpoints adhere to REST API patterns.
· Various optimizations were applied where necessary (e.g., pagination).
· Efficient SQL custom queries were used.
· Exception Handling
· Custom response structure both on success and failure
· The methods were tested with Mockito
· Database design was normalized, and entities have the necessary relationship mapping
· Timestamp was added to tables where necessary
