# README
Rohan Wadhwa - Submitted Vocareum Workspace
Aidan Cheng - Submitted Report on Brightspace

# Instructions
1. Compile all java files
2. Run Main.java
# Classes 
## ProgramManager
- Contains utility methods for program, e.g.
    - Reading/Writing to Users.txt and Courses.txt so program has Object Permanancy
    - Stores all users and courses in arrayList and provides methods for them (findUser, modifyUser, removeCourse, etc.)
- Testing Done:
    - Read/Write methods was tested through testcases and every time main was run, since object permanancy would be lost if these methods didn't work
    - Examples of bugs found/fixed:
        - **Bug:** Reading comments + posts would return InvalidFormatException when comment had comma/post had comma anywhere in content/title
        - **Fix:** Was using commas to split author,content, etc. of each comment/post; so we surrounded all the String values with "" and made our regex expression for splitting the comment ignore information inside quotes
        - **Bug:** Forgot to add object permanancy when new variables were added (e.x. polls, grades)
        - **Fix:** Updated necessary toString() methods in the respective class and ensured write method in programManager looked for the variables
    - Interaction with other classes: Ensures that User, Comment, Post, and Course classes have object permanancy and provides utility methods so the objects of these classes can be used easily in Main.
Course  - 
- Stores all the necessary data for each course and contains methods to process this data, including:
    - ArrayList of Discussion Posts for each course
    - Author + title of each course
    - Display methods to display each course
    - toString() method which is used to write data for each course to Courses.txt
- Testing Done:
    - Most testing was done by running Main either manually or through test cases and initializing new objects for Course
    - Examples of bugs found/fixed:
        -**Bug:** Remove post not removing any posts even when existing post is passed in as parameter
        -**Fix:** Fixed equals method for post
    - Interaction with other classes: Contains all the posts for a course, so relies on Post and Comment to function properly. Provides methods to analyze each course easily in main, such as displayCourse()

## Post  
-

## Comment  
-

## User
-

## Main
-

## RunTest
-
