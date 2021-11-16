# Disseny d'estructura de dades PR1

### Author
[Carles Gallel Soler](http://www.gallel.com/)

### License
[MIT](https://choosealicense.com/licenses/mit/)

### Structure
The project has two differenced parts: the source of the code in the folder `src` and the tests in the folder `tests`. The first of them follow the following structure:

    .
    ├── exceptions										# Exception files
    │   ├── ActivityNotFoundException.java				# When an activity cannot be found
    │   ├── DEDException.java							# All exceptions extends this
    │   ├── LimitExceededException.java					# When an activity reaches the limit of seats
    │   ├── NoActivitiesException.java					# When a container does not have any activity
    │   ├── NoRatingsException.java						# When a container does not have any rating
    │   ├── NoRecordsException.java						# When a container does not have any record
    │   ├── OrganizationNotFoundException.java			# When an organization cannot be found
    │   ├── UserNotFoundException.java					# When a user cannot be found
    │   └── UserNotInActivityException.java				# When a user is not in a specific activity
    ├── model											# Models of the project
    │   ├── Activity.java								# Activity
    │   ├── Organization.java							# Organization
    │   ├── Rating.java									# Rating
    │   ├── Record.java									# Record
    │   ├── Ticket.java									# Ticket
    │   ├── User.java									# User
    ├── util											# Utilities
    │   ├── OrderedVector.java							# Ordered vector with no limit elements
    │   └── OrderedVectorDictionary.java				# Ordered dictionary vector with limit of elements
    ├── SafetyActivities4Covid19.java					# ADT interface
    └── SafetyActivities4Covid19Impl.java				# ADT implementation

The second one, the tests, have the following files:

    .
    ├── utils											# Utilities
    │   └── DateUtils.java								# Datetime creator
    ├── FactorySafetyActivities4Covid19.java			# Factory of the ADT
    ├── OrderedVectorDictionaryTest.java				# Ordered dictionary vector tests
    ├── OrderedVectorTest.java							# Ordered vector tests
    ├── SafetyActivities4Covid19PR1ExtendedTest.java	# Extended ADT tests
    └── SafetyActivities4Covid19PR1Test.java			# Main ADT tests

### Personalized ADT
The project contains two personalized ADT: an ordered vector and an ordered dictionary vector defined in the following files, respectively `OrderedVector.java` and `OrderedVectorDictionary.java`. Both of them didn't exist in the library of this subject, so the project has to define them as a new ADT. The ordered vector has been used to store the best activities of all the system ordered by its rating. The second one has been used for the cultural activities, using the id of that activity as a key.

### Custom methods in the ADT
One private method has been added to the ADT according to respect the modularity of the code. In this case, that method update the most active user, so it will replace the content of the pointer or not, according to the amount of activities of the users.

### Interface
According to handle the exceptions `ExcepcioContenidorPle` and `ExcepcioPosicioInvalida` the signature of `addUser` and `addOrganization` have been modified to add the throws statement.

### Models
All the models has its constructors, getters and setters. Only the models that contain another model inside has another methods.

For instance, the model `User` has to store the activities where the user participates. For that reason, this model contains methods to insert an activity, check if it participates in a specific activity or just return an iterator of the activities.

The model `Organization` stores the activities that the organizations organize, so in this case, they need a method to insert that and an iterator to get all of them.

Similarly, the model `Activity` contains the ratings of that activity in a linked list and the tickets created for that activity. So this model has to be capable to insert ratings and enqueue and dequeue tickets to the queue of them.

To sum up, all the models that have structure inside them, have their own methods to manage that attribute according to the principle of information encapsulation.

### Exceptions
The source contains all the needed exceptions that the interface of the ADT need. The list of them can be found in the section `Structure` of this file.

### Tests
The initial version of the project has its own tests. Nevertheless, in order to test all the scenarios that can be produced in that project, it contains an extended version of that tests handling the pointer of the most active user and all the exceptions that the main test file didn't check.

- **testUsersFull**: catch the exception threw when the array of users is full.
- **testOrganizationsFull**: catch the exception threw when someone tries to insert an organization in an invalid position.
- **testActivitiesFull**: catch the exception threw when the ordered dictionary vector is full.
- **testCheckNoRecords**: catch the exception threw when there are no records in the stack of records.
- **testGetAllActivitiesByUser**: check the iterators of the activities by user and catch the exception threw when there are no activities for that user.
- **testAddRatingIllegal**: catch all the exceptions threw of method addRating.
- **testCreateTicketAndAssignNoUserNoActivity**: catch all the exceptions threw of method createTicket.
- **testAssignSeatNoActivity**: catch the exception threw of assignSeat method when the activity is not found.
- **testBestActivityEmpty**: catch the exception threw of bestActivity method when there are no activities yet.
- **testMostActiveUser**: check the return of the pointer of mostActiveUser method and catch the exception threw when there are no active users.

Last but not least, every new ADT creating for this project has its own tests to check its correct performance for every public method.
- **testOrderedVectorAdd**: check if the elements are added correctly.
- **testOrderedVectorIsEmpty**: check the method estaBuit.
- **testOrderedVectorIsFull**: check the method estaPle.
- **testOrderedVectorNumElems**: check the amount of elements of the ordered vector.
- **testOrderedVectorGet**: check the method get to obtain the element of specific index.
- **testOrderedVectorUpdate**: check the update of an element.
- **testOrderedVectorDictionaryInsert**: check if the elements are added correctly.
- **testOrderedVectorDictionaryIsEmpty** check the method estaBuit.
- **testOrderedVectorDictionaryIsFull**: check the method estaPle.
- **testOrderedVectorDictionaryGet**: check the method get by its key.

The project contains 4 images for each test file successfully passed.
- `img/testSafetyActivities4Covid19PR1Test.png`
- `img/testSafetyActivities4Covid19PR1ExtendedTest.png`
- `img/testOrderedVectorTest.png`
- `img/testOrderedVectorDictionaryTest.png`

### Others
Additionally, in the folder named as UML there is an image of the conceptual design of the project.
