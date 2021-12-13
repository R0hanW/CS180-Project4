# README
- Rohan Wadhwa - Submitted Vocareum Workspace
- Aidan Chen - Submitted Report on Brightspace
- Arjav Seshachalam - Submitted presentation

# Instructions
1. Run RunningServer.java from backend
2. Run Main.java 

By following the above steps you will be landing in the login page, after which you sign up or login accordingly
once you are successfully logged in, you will be able to see the courses available, to view each course
you click on the course name which will take you to the course page in which you should be able to make the appropriate
posts and depending on the restrictions the teacher made you can add replies and react to polls


# Classes 
## GUI
There are multiple different classes that fall under the GUI Folder, each of these classes
have a purpose, The classes help set up the structure of what the frame will look like such as dimensions
 and then to mainly create appropriate JPanels that can be used when the scenario rises


## ProgramManager
- Contains utility methods for program, e.g.
    - Sends ArrayList Objects to the Running Server class
    - Stores all users and courses in arrayList and provides methods for them (findUser, modifyUser, removeCourse, etc.)
- Testing Done:
    - Checking to see if the RunningServer class received the ArrayLists properly
    - Interaction with other classes: Ensures that User, Comment, Post, and Course classes have object permanancy and provides utility methods so the objects of these classes can be used easily in Main.
## RunningServer
- Contains the writeFile/ReadFile methods 
- Runs before main to handle any incoming data from any machine
    - Receives the ArrayLists from the ProgramManager class and updates the text file with updated data.
- Testing Done:
    - Checking if the Text Files were updating properly
## Network
- Starts up as a thread when Main is called to update the data that the clients have access to
- Testing Done:
    - Check to see if the ArrayLists are updating every second
    - Examples of bugs found/fixed
       - **Bug:** Data would be lost in both the Text file and the ArrayList
       - **Fix:** Synchronize the write and read file methods and add Thread.sleep so a race condition isn't possible
## Course  
- Stores all the necessary data for each course and contains methods to process this data, including:
    - ArrayList of Discussion Posts for each course
    - Author + title of each course
    - Display methods to display each course
    - toString() method which is used to write data for each course to Courses.txt
- Testing Done:
    - Most testing was done by running Main either manually or through test cases and initializing new objects for Course
    - Examples of bugs found/fixed:
        - **Bug:** Remove post not removing any posts even when existing post is passed in as parameter
        - **Fix:** Fixed equals method for post
    - Interaction with other classes: Contains all the posts for a course, so relies on Post and Comment to function properly. Provides methods to analyze each course easily in main, such as displayCourse()

## Post  
- Stores all the necessary data for each post and contains methods to process this data, including: 
    - Topic + author + description of each post
    - ArrayList of Comments for each Post
    - Methods to change comments for each post, such as removeComment() and addComment()
    - Methods to display polling, posts, etc. 
    - Three ArrayLists that store options, results, and users who have voted for each poll
    - toString() to write each comment to Courses.txt
- Testing Done:
    - Most testing was done by running Main either manually or through test cases and initializing new objects for Post
    - Examples of bugs found/fixed:
        - **Bug:** Timestamp for data was formatted in dd/MM (16/11) when it was supposed to be formatted in MM/dd (11/16)
        - **Fix:** Had a DateTimeFormatter object responsible for formatting the timestamp, changed it from dd/MM to MM/dd
        - **Bug:** Poll would add extra empty options every time Courses.java was read.
        - **Fix:** Extra pollResult() value of 0 was being added every time addPollOption() was called, add extra boolean parameter so that this was functionality was only done when intended
    - Interaction with other classes: Courses uses Post class to store an arrayList of all the discussion posts, Post uses Comment class to store an ArrayList of comments.

## Comment  
- Stores all the necessary data for each comment and contains methods to process this data, including:
    - Author + content + timestamp + likes + grade of each comment
    - ArrayList of replies to each comment
    - Methods to upvote and grade each comment
    - Methods to display each comment
    - toString() to write each comment to Courses.txt
- Testing Done:
    - Most testing was done by running Main either manually or through test cases and initializing new objects for Comment
    - Example of bugs found/fixed:
        - **Bug:** User could upvote comment infinite times.
        - **Fix:** Created arraylist of users who had liked comments in comment, added user as a parameter for addVote() and checked if they had already liked the comment or not.
- Interaction with other classes: Post used Comment class to store the replies to a Post.

## User
- Stores all the necessary data for each user and contains methods to process this data, including:
    - Name + username + password of each user
    - Boolean isTeacher, true if user was teacher
    - Methods to get and set all variables
    - ArrayList of comments given by user
- Testing done:
    - Most testing was done by running Main either manually or through test cases and initializing new objects for User
    - Examples of bugs found/fixed:
        - **Bug:** Comment was not being removed when user was being deleted, and comment.getOwner() was returning a nullpointer exception
        - **Fix:** Created arrayList of comments inside User classes and added comment to arrayList inside of post.addComment()
- Interaction with other classes: Used by post, main, comments to store author of each object and view of user editing object had permissions to do so. Used by main to login and create new users.

## Main
- Starts the Thread for the graphic user interface and the data handling classes to run together.
- Testing done:
    - Examples of bugs found/fixed:
        - **Bug:** When the GUI was refreshed a new JFrame would be created
        - **Fix:** Made the GUI functionality concurrent so the start of the thread would also only call one JFrame
        - **Bug:** Data would be updating every second causing the Text Fields to empty as the user tried to input data
        - **Fix:** Added an if statement to ensure the GUI would only refresh as the panels display data. 
